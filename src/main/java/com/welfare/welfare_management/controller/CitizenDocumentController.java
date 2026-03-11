package com.welfare.welfare_management.controller;

import com.welfare.welfare_management.service.CitizenDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class CitizenDocumentController {

    @Autowired
    private CitizenDocumentService documentService;

    //upload Document
    @PostMapping("/uploadDocument/{citizenNic}")
    public ResponseEntity<?> uploadDocument(@RequestHeader("Authorization") String authHeader,
            @PathVariable String citizenNic,
            @RequestParam("documentType") String documentType,
            @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(documentService.uploadDocument(authHeader,citizenNic, documentType, file));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //get Documents
    @GetMapping("/getDocuments/{citizenNic}")
    public ResponseEntity<?> getDocuments(@RequestHeader("Authorization") String authHeader,@PathVariable String citizenNic) {
        try {
            return ResponseEntity.ok(documentService.getDocumentsByNic(authHeader,citizenNic));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //download Document
    @GetMapping("/downloadDocument/{documentId}")
    public ResponseEntity<?> downloadDocument(@RequestHeader("Authorization") String authHeader,@PathVariable Long documentId) {
        try {
            UrlResource resource = documentService.downloadDocument(authHeader,documentId);  // ✅ UrlResource
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //delete Document
    @DeleteMapping("/deleteDocument/{documentId}")
    public ResponseEntity<?> deleteDocument(@RequestHeader("Authorization") String authHeader,@PathVariable Long documentId) {
        try {
            return ResponseEntity.ok(
                    Map.of("message", documentService.deleteDocument(authHeader,documentId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //delete AllDocuments
    @DeleteMapping("/deleteAllDocuments/{citizenNic}")
    public ResponseEntity<?> deleteAllDocuments(@RequestHeader("Authorization") String authHeader,@PathVariable String citizenNic) {
        try {
            return ResponseEntity.ok(
                    Map.of("message", documentService.deleteAllDocuments(authHeader,citizenNic)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
