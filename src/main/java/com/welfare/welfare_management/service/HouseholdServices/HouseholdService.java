package com.welfare.welfare_management.service.HouseholdServices;

import com.welfare.welfare_management.dto.ApplicationDTO;
import com.welfare.welfare_management.dto.HouseholdDTOs.*;
import com.welfare.welfare_management.model.Application;
import com.welfare.welfare_management.model.Citizen;
import com.welfare.welfare_management.model.HouseholdModels.*;
import com.welfare.welfare_management.repo.CitizenRepo;
import com.welfare.welfare_management.repo.ApplicationRepo;
import com.welfare.welfare_management.repo.HouseholdRepos.*;
import com.welfare.welfare_management.service.OfficerStatusService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.welfare.welfare_management.sequrity.JwtUtil;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@Transactional
public class HouseholdService {

    @Autowired
    HouseholdRepo householdRepo;
    @Autowired
    ApplicationRepo applicationRepo;
    @Autowired
    CitizenRepo citizenRepo;

    @Autowired
    HouseBuildingStatusRepo houseBuildingStatusRepo;
    @Autowired
    HouseOwnershipStatusRepo houseOwnershipStatusRepo;
    @Autowired
    HouseholdBuildingAssetsRepo buildingAssetsRepo;
    @Autowired
    HouseholdEconomicEquipmentAssetsRepo economicEquipmentAssetsRepo;
    @Autowired
    HouseholdHouseEquipmentAssetsRepo houseEquipmentAssetsRepo;
    @Autowired
    HouseholdLivestockAssetsRepo livestockAssetsRepo;
    @Autowired
    HouseholdMonthlyExpensesRepo monthlyExpensesRepo;
    @Autowired
    HouseholdMonthlyIncomeRepo monthlyIncomeRepo;
    @Autowired
    HouseholdPowerStatusRepo powerStatusRepo;
    @Autowired
    HouseholdWaterSupplyRepo waterSupplyRepo;
    @Autowired
    HouseholdVehicleAssetsRepo vehicleAssetsRepo;
    @Autowired
    HouseholdMemberRepo memberRepo;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

//    @Autowired
//    HouseholdMemberRepo householdMemberRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OfficerStatusService officerStatusService;


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


    // Get all applications
    public List<HouseholdDTO> getAllHouseholds(String authHeader) {

        validateToken(authHeader);

        List<Household> list = householdRepo.findAll();

        return modelMapper.map(list, new org.modelmapper.TypeToken<List<HouseholdDTO>>() {}.getType());
    }




    //Fetch full household by ID
    public HouseholdDTO getFullHouseholdByID(String authHeader,Integer householdId) {

        validateToken(authHeader);

        // 🔹 Get main household
        Household household = householdRepo.findByHouseholdId(householdId)
                .orElseThrow(() -> new RuntimeException("Household not found"));

        HouseholdDTO dto = modelMapper.map(household, HouseholdDTO.class);

        // 🔹 Members
        List<HouseholdMember> members =
                memberRepo.findByHouseholdId(householdId);

        dto.setMembers(modelMapper.map(members,
                new org.modelmapper.TypeToken<List<HouseholdMemberDTO>>() {
                }.getType()));

        // 🔹 Building Status
        houseBuildingStatusRepo.findByHouseholdId(householdId)
                .ifPresent(status ->
                        dto.setBuildingStatus(
                                modelMapper.map(status, HouseBuildingStatusDTO.class)));

        // 🔹 Ownership
        houseOwnershipStatusRepo.findByHouseholdId(householdId)
                .ifPresent(owner ->
                        dto.setOwnershipStatus(
                                modelMapper.map(owner, HouseOwnershipStatusDTO.class)));

        // 🔹 Building Assets
        buildingAssetsRepo.findByHouseholdId(householdId)
                .ifPresent(asset ->
                        dto.setBuildingAssets(
                                modelMapper.map(asset, HouseholdBuildingAssetsDTO.class)));

        // 🔹 Economic Equipment
        economicEquipmentAssetsRepo.findByHouseholdId(householdId)
                .ifPresent(economic ->
                        dto.setEconomicEquipmentAssets(
                                modelMapper.map(economic, HouseholdEconomicEquipmentAssetsDTO.class)));

        // 🔹 House Equipment
        houseEquipmentAssetsRepo.findByHouseholdId(householdId)
                .ifPresent(houseEquip ->
                        dto.setHouseEquipmentAssets(
                                modelMapper.map(houseEquip, HouseholdHouseEquipmentAssetsDTO.class)));

        // 🔹 Livestock
        livestockAssetsRepo.findByHouseholdId(householdId)
                .ifPresent(live ->
                        dto.setLivestockAssets(
                                modelMapper.map(live, HouseholdLivestockAssetsDTO.class)));

        // 🔹 Monthly Expenses
        monthlyExpensesRepo.findByHouseholdId(householdId)
                .ifPresent(exp ->
                        dto.setMonthlyExpenses(
                                modelMapper.map(exp, HouseholdMonthlyExpensesDTO.class)));

        // 🔹 Monthly Income
        monthlyIncomeRepo.findByHouseholdId(householdId)
                .ifPresent(inc ->
                        dto.setMonthlyIncome(
                                modelMapper.map(inc, HouseholdMonthlyIncomeDTO.class)));

        // 🔹 Power
        powerStatusRepo.findByHouseholdId(householdId)
                .ifPresent(power ->
                        dto.setPowerStatus(
                                modelMapper.map(power, HouseholdPowerStatusDTO.class)));

        waterSupplyRepo.findByHouseholdId(householdId)
                .ifPresent(waterSupply ->
                        dto.setWaterSupply(
                                modelMapper.map(waterSupply, HouseholdWaterSupplyDTO.class)));

        // 🔹 Vehicles
        vehicleAssetsRepo.findByHouseholdId(householdId)
                .ifPresent(vehicle ->
                        dto.setVehicleAssets(
                                modelMapper.map(vehicle, HouseholdVehicleAssetsDTO.class)));

        return dto;
    }


    //Create full household with all sub-tables
    @Transactional
    public HouseholdDTO createFullHousehold(String authHeader,HouseholdDTO dto) {

        validateToken(authHeader);

        //Validate Application
        Application application = applicationRepo.findByApplicationId(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // Validate Head Citizen
        Citizen citizen = citizenRepo.findById(dto.getHeadNic())
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        // Check duplicate householdId before inserting
        Optional<Household> household_id =
                householdRepo.findByHouseholdId(dto.getHouseholdId());

        if (household_id.isPresent()) {
            throw new RuntimeException("This household id already exist to another household");
        }

        // Check duplicate application ID before inserting
        Optional<Household> applicationId =
                householdRepo.findByApplicationId(dto.getApplicationId());

        if (applicationId.isPresent()) {
            throw new RuntimeException("This application already exist to another household");
        }

        // Check duplicate Head NIC before inserting
        Optional<Household> headNic =
                householdRepo.findByHeadNic(dto.getHeadNic());

        if (headNic.isPresent()) {
            throw new RuntimeException("This Head NIC already exist to another household");
        }

        // Save main household
        Household household = modelMapper.map(dto, Household.class);
        Household savedHousehold = householdRepo.save(household);

        Integer householdId = savedHousehold.getHouseholdId();

        // Save child tables (building, assets, income etc.)
        saveChildTables(dto, householdId);

        // Save members
        if (dto.getMembers() != null && !dto.getMembers().isEmpty()) {

            List<HouseholdMember> members = dto.getMembers().stream().map(memberDTO -> {

                String nic = memberDTO.getNationalId().trim().toUpperCase();

                // Duplicate NIC check
                Optional<HouseholdMember> existingMember =
                        memberRepo.findByNationalId(nic);

                if (existingMember.isPresent()) {
                    throw new RuntimeException(
                            "Person already belongs to another household. NIC: " + nic
                    );
                }

                HouseholdMember member = new HouseholdMember();
                member.setFullName(memberDTO.getFullName());
                member.setRelationshipToHead(memberDTO.getRelationshipToHead());
                member.setGender(memberDTO.getGender());
                member.setMarriageStatus(memberDTO.getMarriageStatus());
                member.setDateOfBirth(memberDTO.getDateOfBirth());
                member.setAge(memberDTO.getAge());
                member.setNationalId(memberDTO.getNationalId());
                member.setEducationLevel(memberDTO.getEducationLevel());
                member.setStudentStatus(memberDTO.getStudentStatus());
                member.setEmploymentStatus(memberDTO.getEmploymentStatus());
                member.setMonthlyIncome(memberDTO.getMonthlyIncome());
                member.setSocialWelfareBenefit(memberDTO.getSocialWelfareBenefit());
                member.setDifficultyStatus(memberDTO.getDifficultyStatus());
                member.setDisabilityStatus(memberDTO.getDisabilityStatus());
                member.setChronicIllness(memberDTO.getChronicIllness());
                member.setHouseholdId(householdId);

                return member;
            }).toList();

            memberRepo.saveAll(members);
        }

        // Return full household
        return getFullHouseholdByID(authHeader,householdId);
    }


    //Update full household with all sub-tables
    @Transactional
    public HouseholdDTO updateFullHousehold(String authHeader,HouseholdDTO dto) {

        validateToken(authHeader);

        // Check household exists
        Household existing = householdRepo.findByHouseholdId(dto.getHouseholdId())
                .orElseThrow(() -> new RuntimeException("Household not found"));

        // Validate relations
        Application application = applicationRepo.findByApplicationId(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Citizen citizen = citizenRepo.findById(dto.getHeadNic())
                .orElseThrow(() -> new RuntimeException("Citizen not found"));


        // Update household using repository
        householdRepo.updateHouseholdById(
                dto.getHouseholdId(),
                dto.getHeadNic(),
                dto.getAddress(),
                dto.getDistrict(),
                dto.getGnDivision(),
                dto.getApplicationId(),
                HouseholdStatus.PROCESSING
        );

        Integer householdId = dto.getHouseholdId();

    // Clean old child data
        deleteChildTables(householdId);

        // Insert fresh
        saveChildTables(dto, householdId);

         //Update members - delete old ones and re-insert
        if (dto.getMembers() != null && !dto.getMembers().isEmpty()) {

            // Delete existing members of this household
            memberRepo.deleteByHouseholdId(householdId);

            List<HouseholdMember> updatedMembers = dto.getMembers().stream().map(memberDTO -> {

                if (memberRepo.existsByNationalIdAndMemberIdNot(memberDTO.getNationalId(), memberDTO.getMemberId())) {
                    throw new RuntimeException("Another member already has this NIC");
                }

                // Allow same NIC only if it belongs to another household
                // Skip NIC check for members of THIS household since we just deleted them
                HouseholdMember member = new HouseholdMember();
                member.setFullName(memberDTO.getFullName());
                member.setRelationshipToHead(memberDTO.getRelationshipToHead());
                member.setGender(memberDTO.getGender());
                member.setMarriageStatus(memberDTO.getMarriageStatus());
                member.setDateOfBirth(memberDTO.getDateOfBirth());
                member.setAge(memberDTO.getAge());
                member.setNationalId(memberDTO.getNationalId());
                member.setEducationLevel(memberDTO.getEducationLevel());
                member.setStudentStatus(memberDTO.getStudentStatus());
                member.setEmploymentStatus(memberDTO.getEmploymentStatus());
                member.setMonthlyIncome(memberDTO.getMonthlyIncome());
                member.setSocialWelfareBenefit(memberDTO.getSocialWelfareBenefit());
                member.setDifficultyStatus(memberDTO.getDifficultyStatus());
                member.setDisabilityStatus(memberDTO.getDisabilityStatus());
                member.setChronicIllness(memberDTO.getChronicIllness());
                member.setHouseholdId(householdId);

                return member;
            }).toList();

            memberRepo.saveAll(updatedMembers);
        }

        //Create officer status
        officerStatusService.createOfficerStatus(authHeader,dto.getApplicationId());

//        if (dto.getMembers() != null && !dto.getMembers().isEmpty()) {
//
//            List<HouseholdMember> updatedMembers = dto.getMembers().stream().map(memberDTO -> {
//
//                // For new members (no memberId), check if NIC exists anywhere
//                // For existing members, check if NIC belongs to another member
//                boolean nicExists;
//                if (memberDTO.getMemberId() == null) {
//                    // New member - check if NIC already exists in any member
//                    nicExists = memberRepo.existsByNationalId(memberDTO.getNationalId());
//                } else {
//                    //  Existing member - check if NIC belongs to a DIFFERENT member
//                    nicExists = memberRepo.existsByNationalIdAndMemberIdNot(
//                            memberDTO.getNationalId(), memberDTO.getMemberId());
//                }
//
//                if (nicExists) {
//                    throw new RuntimeException(
//                            "Another member already has NIC: " + memberDTO.getNationalId());
//                }
//
//                // Find existing member to update OR create new one
//                HouseholdMember member = (memberDTO.getMemberId() != null)
//                        ? memberRepo.findById(memberDTO.getMemberId())
//                        .orElse(new HouseholdMember())
//                        : new HouseholdMember();
//
//                member.setFullName(memberDTO.getFullName());
//                member.setRelationshipToHead(memberDTO.getRelationshipToHead());
//                member.setGender(memberDTO.getGender());
//                member.setMarriageStatus(memberDTO.getMarriageStatus());
//                member.setDateOfBirth(memberDTO.getDateOfBirth());
//                member.setAge(memberDTO.getAge());
//                member.setNationalId(memberDTO.getNationalId());
//                member.setEducationLevel(memberDTO.getEducationLevel());
//                member.setStudentStatus(memberDTO.getStudentStatus());
//                member.setEmploymentStatus(memberDTO.getEmploymentStatus());
//                member.setMonthlyIncome(memberDTO.getMonthlyIncome());
//                member.setSocialWelfareBenefit(memberDTO.getSocialWelfareBenefit());
//                member.setDifficultyStatus(memberDTO.getDifficultyStatus());
//                member.setDisabilityStatus(memberDTO.getDisabilityStatus());
//                member.setChronicIllness(memberDTO.getChronicIllness());
//                member.setHouseholdId(householdId);
//
//                return member;
//
//            }).toList();
//
//            memberRepo.saveAll(updatedMembers);
//
//            //  Delete members that were removed from the list
//            List<Integer> submittedMemberIds = dto.getMembers().stream()
//                    .filter(m -> m.getMemberId() != null)
//                    .map(HouseholdMemberDTO::getMemberId)
//                    .toList();
//
//            memberRepo.findByHouseholdId(householdId).forEach(existingMember -> {
//                if (!submittedMemberIds.contains(existingMember.getMemberId())) {
//                    memberRepo.deleteById(existingMember.getMemberId());
//                }
//            });
//        }

        // Flush writes to DB then clear Hibernate cache
        // This forces getFullHouseholdByID to read fresh data from DB
        entityManager.flush();
        entityManager.clear();

        // Return full updated data
        return getFullHouseholdByID(authHeader,householdId);

    }




    // Delete household
    public String deleteHousehold(String authHeader, String householdId) {

        validateToken(authHeader);

        if (!householdRepo.existsById(householdId)) {
            throw new RuntimeException("Household not found");
        }

        householdRepo.deleteById(householdId);

        return householdId+" Household deleted successfully";
    }

    // Delete member
    public String deleteHouseholdMember(String authHeader, Integer memberId) {

        validateToken(authHeader);

        HouseholdMember member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        memberRepo.delete(member);

        return memberId+" Member deleted successfully";
    }


    @Transactional
    public HouseholdDTO updateHouseholdStatus(String authHeader, Integer householdId, HouseholdStatus householdStatus) {

        validateToken(authHeader);

        Household household = householdRepo.findByHouseholdId(householdId)
                .orElseThrow(() -> new RuntimeException(
                        "Household not found with ID: " + householdId));

        // Validate and convert status string to enum
        //HouseholdStatus householdStatus;
//        try {
//            householdStatus = HouseholdStatus.valueOf(status.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("Invalid status: " + status +
//                    ". Allowed: PENDING, PROCESSING, APPROVED, REJECTED");
//        }

        household.setStatus(householdStatus);
        householdRepo.save(household);

        entityManager.flush();
        entityManager.clear();

        return getFullHouseholdByID(authHeader,householdId);
    }



    //Save child tables
    private void saveChildTables(HouseholdDTO dto, Integer householdId) {


        if (dto.getBuildingStatus() != null) {
            HouseBuildingStatus status =
                    modelMapper.map(dto.getBuildingStatus(), HouseBuildingStatus.class);
            status.setHouseholdId(householdId);
            houseBuildingStatusRepo.save(status);
        }

        if (dto.getOwnershipStatus() != null) {
            HouseOwnershipStatus ownership =
                    modelMapper.map(dto.getOwnershipStatus(), HouseOwnershipStatus.class);
            ownership.setHouseholdId(householdId);
            houseOwnershipStatusRepo.save(ownership);
        }

        if (dto.getBuildingAssets() != null) {
            HouseholdBuildingAssets assets =
                    modelMapper.map(dto.getBuildingAssets(), HouseholdBuildingAssets.class);
            assets.setHouseholdId(householdId);
            buildingAssetsRepo.save(assets);
        }

        if (dto.getEconomicEquipmentAssets() != null) {
            HouseholdEconomicEquipmentAssets economic =
                    modelMapper.map(dto.getEconomicEquipmentAssets(),
                            HouseholdEconomicEquipmentAssets.class);
            economic.setHouseholdId(householdId);
            economicEquipmentAssetsRepo.save(economic);
        }

        if (dto.getHouseEquipmentAssets() != null) {
            HouseholdHouseEquipmentAssets equip =
                    modelMapper.map(dto.getHouseEquipmentAssets(),
                            HouseholdHouseEquipmentAssets.class);
            equip.setHouseholdId(householdId);
            houseEquipmentAssetsRepo.save(equip);
        }

        if (dto.getLivestockAssets() != null) {
            HouseholdLivestockAssets livestock =
                    modelMapper.map(dto.getLivestockAssets(),
                            HouseholdLivestockAssets.class);
            livestock.setHouseholdId(householdId);
            livestockAssetsRepo.save(livestock);
        }

        if (dto.getMonthlyExpenses() != null) {
            HouseholdMonthlyExpenses exp =
                    modelMapper.map(dto.getMonthlyExpenses(),
                            HouseholdMonthlyExpenses.class);
            exp.setHouseholdId(householdId);
            monthlyExpensesRepo.save(exp);
        }

        if (dto.getMonthlyIncome() != null) {
            HouseholdMonthlyIncome income =
                    modelMapper.map(dto.getMonthlyIncome(),
                            HouseholdMonthlyIncome.class);
            income.setHouseholdId(householdId);
            monthlyIncomeRepo.save(income);
        }

        if (dto.getPowerStatus() != null) {
            HouseholdPowerStatus power =
                    modelMapper.map(dto.getPowerStatus(),
                            HouseholdPowerStatus.class);
            power.setHouseholdId(householdId);
            powerStatusRepo.save(power);
        }

        if (dto.getWaterSupply() != null) {
            HouseholdWaterSupply water =
                    modelMapper.map(dto.getWaterSupply(),
                            HouseholdWaterSupply.class);
            water.setHouseholdId(householdId);
            waterSupplyRepo.save(water);
        }

        if (dto.getVehicleAssets() != null) {
            HouseholdVehicleAssets vehicle =
                    modelMapper.map(dto.getVehicleAssets(),
                            HouseholdVehicleAssets.class);
            vehicle.setHouseholdId(householdId);
            vehicleAssetsRepo.save(vehicle);
        }

//        if (dto.getMembers() != null && !dto.getMembers().isEmpty()) {
//
//            List<HouseholdMember> members =
//                    dto.getMembers().stream()
//                            .map(m -> {
//                                HouseholdMember member =
//                                        modelMapper.map(m, HouseholdMember.class);
//                                member.setHouseholdId(householdId);
//                                return member;
//                            }).toList();
//
//            memberRepo.saveAll(members);
//        }
    }


    //Delete child tables
    private void deleteChildTables(Integer householdId) {

        houseBuildingStatusRepo.deleteById(householdId);
        houseOwnershipStatusRepo.deleteById(householdId);
        buildingAssetsRepo.deleteById(householdId);
        economicEquipmentAssetsRepo.deleteById(householdId);
        houseEquipmentAssetsRepo.deleteById(householdId);
        livestockAssetsRepo.deleteById(householdId);
        monthlyExpensesRepo.deleteById(householdId);
        monthlyIncomeRepo.deleteById(householdId);
        powerStatusRepo.deleteById(householdId);
        waterSupplyRepo.deleteById(householdId);
        vehicleAssetsRepo.deleteById(householdId);
        //memberRepo.deleteByHouseholdId(householdId);
    }






}