package com.welfare.welfare_management.service;

import com.welfare.welfare_management.dto.ApplicationDTO;
import com.welfare.welfare_management.dto.CitizenDTO;
import com.welfare.welfare_management.model.Application;
import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.Status;
import com.welfare.welfare_management.repo.ApplicationRepo;
import com.welfare.welfare_management.repo.CitizenRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationService {

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    CitizenRepo citizenRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;



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
        public List<ApplicationDTO> getAllApplications(String authHeader) {

            validateToken(authHeader);

            List<Application> list = applicationRepo.findAll();

            return modelMapper.map(list, new org.modelmapper.TypeToken<List<ApplicationDTO>>() {}.getType());
        }


        // Create application
        public ApplicationDTO createApplication(String authHeader, ApplicationDTO dto) {

            validateToken(authHeader);

            // Check citizen exists
            boolean exists = citizenRepo.existsById(dto.getCitizenNic());

            if (!exists) {
                throw new RuntimeException("Citizen not registered");
            }

//            Application application = modelMapper.map(dto, Application.class);
//
//            application.setStatus(Status.PENDING);
//
//            applicationRepo.save(application);

            Application application = modelMapper.map(dto, Application.class);

            application.setStatus(Status.PENDING);

            Application savedApplication = applicationRepo.save(application);

            return modelMapper.map(savedApplication, ApplicationDTO.class);

        }


        // Update application
        public ApplicationDTO updateApplication(String authHeader, ApplicationDTO dto) {

            validateToken(authHeader);

            Application existing = applicationRepo.findByApplicationId(dto.getApplicationId())
                    .orElseThrow(() -> new RuntimeException("Household not found"));

            boolean exists = citizenRepo.existsById(dto.getCitizenNic());

            if (!exists) {
                throw new RuntimeException("Citizen not registered");
            }

            // Update citizen using repository
            applicationRepo.updateApplicationById(
                    dto.getApplicationId(),
                    dto.getWelfareType(),
                    dto.getCitizenNic(),
                    dto.getStatus()
            );


            Application updateApplication = applicationRepo.findByApplicationId(dto.getApplicationId())
                    .orElseThrow(() -> new RuntimeException(
                            "Application not found: " + dto.getApplicationId()));

            // Update fields safely
//            if (dto.getWelfareType() != null)
//                existing.setWelfareType(dto.getWelfareType());
//
//            if (dto.getCitizenNic() != null)
//                existing.setCitizenNic(dto.getCitizenNic());
//
//            if (dto.getQrCode() != null)
//                existing.setQrCode(dto.getQrCode());

           // applicationRepo.save(existing);

           // return modelMapper.map(existing, ApplicationDTO.class);

            return modelMapper.map(updateApplication, ApplicationDTO.class);
        }


    public ApplicationDTO updateStatus(String authHeader, String id, Status status) {

        validateToken(authHeader);

        Application application = applicationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(status);

        return modelMapper.map(applicationRepo.save(application), ApplicationDTO.class);
    }



    // Delete application
        public String deleteApplication(String authHeader, String id) {

            validateToken(authHeader);

            if (!applicationRepo.existsById(id)) {
                throw new RuntimeException("Application not found");
            }

            applicationRepo.deleteById(id);

            return id +" Application deleted successfully";
        }


        // Get application by ID
        public ApplicationDTO getApplicationById(String authHeader, String id) {

            validateToken(authHeader);

            Application application = applicationRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            return modelMapper.map(application, ApplicationDTO.class);
        }


    // Get application by citizen NIC
    public ApplicationDTO getApplicationByCitizen(String authHeader, String nic) {

        validateToken(authHeader);

        Application application = applicationRepo.findByCitizenNic(nic)
                .orElseThrow(() -> new RuntimeException("Citizen not found with NIC: " + nic));

        return modelMapper.map(application, ApplicationDTO.class);
    }


}
