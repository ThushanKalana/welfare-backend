package com.welfare.welfare_management.controller.HouseholdControllers;

import com.welfare.welfare_management.dto.HouseholdDTOs.HouseholdDTO;
import com.welfare.welfare_management.service.HouseholdServices.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;


    // Get all applications
    @GetMapping("/getAllHouseholds")
    public ResponseEntity<?> getAllHouseholds(@RequestHeader("Authorization") String authHeader) {
        try {
            return ResponseEntity.ok(householdService.getAllHouseholds(authHeader));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    //Get full Household details
    @GetMapping("/getFullHouseholdByID/{householdId}")
    public ResponseEntity<?> getFullHouseholdByID(@RequestHeader("Authorization") String authHeader,@PathVariable Integer householdId) {

        try {
            return ResponseEntity.ok(
                    householdService.getFullHouseholdByID(authHeader,householdId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //Create full household details
//    @PostMapping("/addFullHousehold")
//    public ResponseEntity<?> addFullHousehold(@RequestBody HouseholdDTO dto) {
//
//        try {
//            return ResponseEntity.ok(
//                    householdService.createFullHousehold(dto)
//            );
//        } catch (RuntimeException e) {
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body(Map.of("message", e.getMessage()));
//        }
//    }

    @PostMapping("/addFullHousehold")
    public ResponseEntity<?> createFullHousehold(@RequestHeader("Authorization") String authHeader,@RequestBody HouseholdDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(householdService.createFullHousehold(authHeader,dto));
        } catch (RuntimeException e) {
            //  This will now return "National ID already exists: 198512345678"
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //Update full household details
    @PutMapping("/updateFullHousehold")
    public ResponseEntity<?> updateFullHousehold(@RequestHeader("Authorization") String authHeader,@RequestBody HouseholdDTO dto) {

        try {
            return ResponseEntity.ok(
                    householdService.updateFullHousehold(authHeader,dto)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/updateHouseholdStatus/{householdId}")
    public ResponseEntity<?> updateHouseholdStatus(@RequestHeader("Authorization") String authHeader,
            @PathVariable Integer householdId,
            @RequestBody HouseholdDTO status) {
        try {
            return ResponseEntity.ok(
                    householdService.updateHouseholdStatus(authHeader, householdId, status.getStatus()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }


            // Delete household
        @DeleteMapping("/deleteHousehold/{householdId}")
        public ResponseEntity<?> deleteHousehold(@RequestHeader("Authorization") String authHeader,@PathVariable String householdId) {
            try {
                String message = householdService.deleteHousehold(authHeader,householdId);
                return ResponseEntity.ok(Map.of("message", message));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
            }
        }


    // Delete member
    @DeleteMapping("deleteMember/{memberId}")
    public ResponseEntity<?> deleteMember(@RequestHeader("Authorization") String authHeader,@PathVariable Integer memberId) {

        try {
            String message =  householdService.deleteHouseholdMember(authHeader,memberId);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        }

        //householdService.deleteHouseholdMember(memberId);

        //return ResponseEntity.ok("Household member deleted successfully");
    }

}

