package com.welfare.welfare_management.service;

import com.welfare.welfare_management.dto.ApplicationDTO;
import com.welfare.welfare_management.dto.CitizenDTO;
import com.welfare.welfare_management.dto.HouseholdDTOs.HouseholdBasicDTO;
import com.welfare.welfare_management.dto.HouseholdDTOs.HouseholdDTO;
import com.welfare.welfare_management.dto.HouseholdDTOs.LocationDTO;
import com.welfare.welfare_management.dto.UserDTO;
import com.welfare.welfare_management.model.Application;
import com.welfare.welfare_management.model.Citizen;
import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.Status;
import com.welfare.welfare_management.model.User;
import com.welfare.welfare_management.repo.ApplicationRepo;
import com.welfare.welfare_management.repo.CitizenRepo;
import com.welfare.welfare_management.repo.HouseholdRepos.HouseholdRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitizenService {

    @Autowired
    CitizenRepo citizenRepo;

    @Autowired
    ApplicationRepo  applicationRepo;

    @Autowired
    HouseholdRepo  householdRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private EntityManager entityManager;


    // Validate JWT (reuse method)
    private void validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    //Get all citizen
    public List<CitizenDTO> getAllCitizens(String authHeader) {

        validateToken(authHeader);

        List<Citizen> citizenList = citizenRepo.findAll();

        // Map each citizen with their application and household
        return citizenList.stream().map(citizen -> {

            CitizenDTO response = new CitizenDTO();
            response.setCitizenNic(citizen.getCitizenNic());
            response.setCitizenId(citizen.getCitizenId());
            response.setFirstName(citizen.getFirstName());
            response.setLastName(citizen.getLastName());
            response.setAddress(citizen.getAddress());
            response.setEmail(citizen.getEmail());
            response.setMobile(citizen.getMobile());
            response.setQrCode(citizen.getQrCode());
            response.setLatitude(citizen.getLatitude());
            response.setLongitude(citizen.getLongitude());

            // Fetch application for each citizen
            applicationRepo.findByCitizenNic(citizen.getCitizenNic()).ifPresent(application -> {
                ApplicationDTO appDTO = new ApplicationDTO();
                appDTO.setApplicationId(application.getApplicationId());
                appDTO.setStatus(application.getStatus());
                appDTO.setCitizenNic(application.getCitizenNic());
                appDTO.setWelfareType(application.getWelfareType());
                appDTO.setAppliedDate(application.getAppliedDate());
                response.setApplication(appDTO);
            });

            // Fetch household for each citizen
            householdRepo.findByHeadNic(citizen.getCitizenNic()).ifPresent(household -> {
                HouseholdBasicDTO basicHousehold = new HouseholdBasicDTO();
                basicHousehold.setHouseholdId(household.getHouseholdId());
                basicHousehold.setAddress(household.getAddress());
                basicHousehold.setDistrict(household.getDistrict());
                basicHousehold.setGnDivision(household.getGnDivision());
                basicHousehold.setApplicationId(household.getApplicationId());
                basicHousehold.setHeadNic(household.getHeadNic());
                response.setHousehold(basicHousehold);
            });

            return response;

        }).collect(Collectors.toList());
    }

    //Add citizen

    @Transactional
    public CitizenDTO createCitizen(CitizenDTO citizenDTO) {

        //validateToken(authHeader);

        // Check duplicate citizen NIC
        if (citizenRepo.existsById(citizenDTO.getCitizenNic())) {
            throw new RuntimeException(
                    "Citizen already exists with this NIC: " + citizenDTO.getCitizenNic());
        }

        // Save citizen first
        Citizen citizen = modelMapper.map(citizenDTO, Citizen.class);
        citizenRepo.save(citizen);

        Application savedApplication = null;

        // Save application BEFORE household
        if (citizenDTO.getApplication() != null) {

            ApplicationDTO applicationDTO = citizenDTO.getApplication();
            applicationDTO.setCitizenNic(citizenDTO.getCitizenNic());

            // Check duplicate application ID
            if (applicationDTO.getApplicationId() != null &&
                    applicationRepo.findByApplicationId(applicationDTO.getApplicationId()).isPresent()) {
                throw new RuntimeException(
                        "Application already exists with ID: " + applicationDTO.getApplicationId());
            }

            Application application = modelMapper.map(applicationDTO, Application.class);
            application.setStatus(Status.PENDING);           // force status
            application.setCitizenNic(citizenDTO.getCitizenNic()); // force citizenNic

            savedApplication = applicationRepo.save(application); // capture saved entity with generated ID
            //entityManager.flush();                           // flush so household FK works
        }

        // Save household AFTER application
        if (citizenDTO.getHousehold() != null) {

            HouseholdBasicDTO householdBasicDTO = citizenDTO.getHousehold();
            householdBasicDTO.setHeadNic(citizenDTO.getCitizenNic());

            // Auto set applicationId from saved application
            if (savedApplication != null) {
                householdBasicDTO.setApplicationId(savedApplication.getApplicationId());
            }

            // Check duplicate householdId
            if (householdRepo.findByHouseholdId(householdBasicDTO.getHouseholdId()).isPresent()) {
                throw new RuntimeException(
                        "Household ID already exists: " + householdBasicDTO.getHouseholdId());
            }

            Household household = modelMapper.map(householdBasicDTO, Household.class);
            household.setHeadNic(citizenDTO.getCitizenNic());
            householdRepo.save(household);
        }

        //return citizenDTO;
        //return getCitizenByNic(citizenDTO.getCitizenNic());

        // Build response
        CitizenDTO response = new CitizenDTO();
        response.setCitizenNic(citizen.getCitizenNic());
        response.setCitizenId(citizen.getCitizenId());
        response.setFirstName(citizen.getFirstName());
        response.setLastName(citizen.getLastName());
        response.setAddress(citizen.getAddress());
        response.setEmail(citizen.getEmail());
        response.setMobile(citizen.getMobile());
        response.setQrCode(citizen.getQrCode());
        response.setLatitude(citizen.getLatitude());
        response.setLongitude(citizen.getLongitude());

        //  Fetch and map application if exists
        applicationRepo.findByCitizenNic(citizenDTO.getCitizenNic()).ifPresent(application -> {
            ApplicationDTO appDTO = new ApplicationDTO();
            appDTO.setApplicationId(application.getApplicationId());
            appDTO.setStatus(application.getStatus());
            appDTO.setCitizenNic(application.getCitizenNic());
            appDTO.setWelfareType(application.getWelfareType());
            appDTO.setAppliedDate(application.getAppliedDate());
            response.setApplication(appDTO);
        });

        // Fetch and map basic household if exists
        householdRepo.findByHeadNic(citizenDTO.getCitizenNic()).ifPresent(household -> {
            HouseholdBasicDTO basicHousehold = new HouseholdBasicDTO();
            basicHousehold.setHouseholdId(household.getHouseholdId());
            basicHousehold.setAddress(household.getAddress());
            basicHousehold.setDistrict(household.getDistrict());
            basicHousehold.setGnDivision(household.getGnDivision());
            basicHousehold.setApplicationId(household.getApplicationId());
            basicHousehold.setHeadNic(household.getHeadNic());
            response.setHousehold(basicHousehold);
        });

        return response;

    }

    //Update citizen

@Transactional
public CitizenDTO updateCitizen(String authHeader,CitizenDTO citizenDTO) {

    validateToken(authHeader);

    // Check citizen exists
    citizenRepo.findById(citizenDTO.getCitizenNic())
            .orElseThrow(() -> new RuntimeException(
                    "Citizen not found with NIC: " + citizenDTO.getCitizenNic()));

    // Update citizen
    citizenRepo.updateCitizenByNic(
            citizenDTO.getCitizenNic(),
            citizenDTO.getFirstName(),
            citizenDTO.getLastName(),
            citizenDTO.getAddress(),
            citizenDTO.getEmail(),
            citizenDTO.getMobile(),
            citizenDTO.getQrCode()
    );

    // Update application if provided
    if (citizenDTO.getApplication() != null) {

        ApplicationDTO applicationDTO = citizenDTO.getApplication();

        Application existingApp = applicationRepo.findByCitizenNic(citizenDTO.getCitizenNic())
                .orElseThrow(() -> new RuntimeException(
                        "Application not found for NIC: " + citizenDTO.getCitizenNic()));

        existingApp.setWelfareType(applicationDTO.getWelfareType());
        existingApp.setAppliedDate(applicationDTO.getAppliedDate());
        // status only updated if provided
        if (applicationDTO.getStatus() != null) {
            existingApp.setStatus(applicationDTO.getStatus());
        }

        applicationRepo.save(existingApp);
    }

    // Update household if provided
    if (citizenDTO.getHousehold() != null) {

        HouseholdBasicDTO householdBasicDTO = citizenDTO.getHousehold();

        Household existingHousehold = householdRepo.findByHeadNic(citizenDTO.getCitizenNic())
                .orElseThrow(() -> new RuntimeException(
                        "Household not found for NIC: " + citizenDTO.getCitizenNic()));

        existingHousehold.setAddress(householdBasicDTO.getAddress());
        existingHousehold.setDistrict(householdBasicDTO.getDistrict());
        existingHousehold.setGnDivision(householdBasicDTO.getGnDivision());

        householdRepo.save(existingHousehold);
    }

    // Flush writes to DB then clear Hibernate cache
    entityManager.flush();
    entityManager.clear();

    //return citizenDTO;
    return getCitizenByNic(authHeader,citizenDTO.getCitizenNic());
}


    //Delete citizen by nic
    public String deleteCitizen(String authHeader,String citizenNIC) {

        validateToken(authHeader);

        // Fetch the citizen NIC
        Citizen citizenNicToDelete = citizenRepo.findById(citizenNIC)
                .orElseThrow(() -> new RuntimeException("Citizen not found with NIC: " + citizenNIC));

        citizenRepo.deleteById(citizenNicToDelete.getCitizenNic());
        return "Citizen deleted successfully";
    }


    //Get citizen by nic
    public CitizenDTO getCitizenByNic(String authHeader, String citizenNIC) {

        validateToken(authHeader);

        // Fetch citizen
        Citizen citizen = citizenRepo.getCitizenByNic(citizenNIC)
                .orElseThrow(() ->
                        new RuntimeException("Citizen not found with NIC: " + citizenNIC));

        // Build response
        CitizenDTO response = new CitizenDTO();
        response.setCitizenNic(citizen.getCitizenNic());
        response.setCitizenId(citizen.getCitizenId());
        response.setFirstName(citizen.getFirstName());
        response.setLastName(citizen.getLastName());
        response.setAddress(citizen.getAddress());
        response.setEmail(citizen.getEmail());
        response.setMobile(citizen.getMobile());
        response.setQrCode(citizen.getQrCode());
        response.setLatitude(citizen.getLatitude());
        response.setLongitude(citizen.getLongitude());

        //  Fetch and map application if exists
        applicationRepo.findByCitizenNic(citizenNIC).ifPresent(application -> {
            ApplicationDTO appDTO = new ApplicationDTO();
            appDTO.setApplicationId(application.getApplicationId());
            appDTO.setStatus(application.getStatus());
            appDTO.setCitizenNic(application.getCitizenNic());
            appDTO.setWelfareType(application.getWelfareType());
            appDTO.setAppliedDate(application.getAppliedDate());
            response.setApplication(appDTO);
        });

        // Fetch and map basic household if exists
        householdRepo.findByHeadNic(citizenNIC).ifPresent(household -> {
            HouseholdBasicDTO basicHousehold = new HouseholdBasicDTO();
            basicHousehold.setHouseholdId(household.getHouseholdId());
            basicHousehold.setAddress(household.getAddress());
            basicHousehold.setDistrict(household.getDistrict());
            basicHousehold.setGnDivision(household.getGnDivision());
            basicHousehold.setApplicationId(household.getApplicationId());
            basicHousehold.setHeadNic(household.getHeadNic());
            response.setHousehold(basicHousehold);
        });

        return response;
    }

    //update citizen location
    public LocationDTO updateLocation(String authHeader,String nic, Double lat, Double lng) {

        validateToken(authHeader);

        Citizen citizen = citizenRepo.findById(nic)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        citizen.setLatitude(lat);
        citizen.setLongitude(lng);

        Citizen saved = citizenRepo.save(citizen);

        return modelMapper.map(saved, LocationDTO.class);
    }
}
