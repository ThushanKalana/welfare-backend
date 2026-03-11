package com.welfare.welfare_management.service;

import com.welfare.welfare_management.dto.CitizenDocumentDTO;
import com.welfare.welfare_management.model.Citizen;
import com.welfare.welfare_management.model.CitizenDocument;
import com.welfare.welfare_management.model.DocumentType;
import com.welfare.welfare_management.repo.CitizenDocumentRepo;
import com.welfare.welfare_management.repo.CitizenRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitizenDocumentService {

    @Autowired
    private CitizenDocumentRepo documentRepo;

    @Autowired
    private CitizenRepo citizenRepo;

    @Autowired
    private ModelMapper modelMapper;

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

    private final String uploadDir = "uploads/documents/";


    // UPLOAD document

    public CitizenDocumentDTO uploadDocument(String authHeader,
            String citizenNic,
            String documentType,
            MultipartFile file) {

        validateToken(authHeader);

        // Validate citizen exists
        citizenRepo.findById(citizenNic)
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + citizenNic));

        // Validate document type
        DocumentType docType;
        try {
            docType = DocumentType.valueOf(documentType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid document type: " + documentType +
                    ". Allowed: NIC, BIRTH_CERTIFICATE, INCOME_PROOF, ELECTRICITY_BILL, " +
                    "WATER_BILL, BANK_STATEMENT, MEDICAL_REPORT, OTHER");
        }

        // Validate file not empty
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Validate file type - only PDF and images allowed
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("application/pdf") &&
                !contentType.startsWith("image/"))) {
            throw new RuntimeException(
                    "Invalid file type. Only PDF and images are allowed");
        }

        // Validate file size - max 5MB
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds 5MB limit");
        }

        try {
            // Create upload directory if not exists
            Path uploadPath = Paths.get(uploadDir + citizenNic);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique file name
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFileName.substring(
                    originalFileName.lastIndexOf("."));
            String uniqueFileName = docType.name() + "_" +
                    System.currentTimeMillis() + fileExtension;

            // Save file to disk
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            // Add this log line temporarily to verify path
            System.out.println("Saving file to: " + filePath.toString());

            // Replace if same document type already exists
            if (documentRepo.existsByCitizenNicAndDocumentType(citizenNic, docType)) {
                documentRepo.deleteByCitizenNicAndDocumentType(citizenNic, docType);
            }

            // Save record to DB
            CitizenDocument document = new CitizenDocument();
            document.setCitizenNic(citizenNic);
            document.setDocumentType(docType);
            document.setFileName(uniqueFileName);
            document.setFilePath(filePath.toString());
            document.setUploadedAt(LocalDateTime.now());

            return mapToDTO(documentRepo.save(document));

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }

    }


    // GET all documents for a citizen

    public List<CitizenDocumentDTO> getDocumentsByNic(String authHeader, String citizenNic) {

        validateToken(authHeader);

        citizenRepo.findById(citizenNic)
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + citizenNic));

        return documentRepo.findByCitizenNic(citizenNic)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    // DOWNLOAD document

    public UrlResource downloadDocument(String authHeader, Long documentId) {

        validateToken(authHeader);

        CitizenDocument document = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException(
                        "Document not found with ID: " + documentId));

        try {
            Path filePath = Paths.get(document.getFilePath());
            UrlResource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("File not found or not readable");
            }

            // Add this log line temporarily to verify path
            System.out.println("Saving file to: " + filePath.toString());

            return resource;

        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        }

    }


    // DELETE document

    public String deleteDocument(String authHeader, Long documentId) {

        validateToken(authHeader);

        CitizenDocument document = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException(
                        "Document not found with ID: " + documentId));

        // Delete file from disk
        try {
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);

            // Add this log line temporarily to verify path
            System.out.println("Saving file to: " + filePath.toString());

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + e.getMessage());
        }

        // Delete record from DB
        documentRepo.deleteById(documentId);

        return document.getDocumentType()+ "Document deleted successfully";
    }


    // DELETE all documents for a citizen

    public String deleteAllDocuments(String authHeader, String citizenNic) {

        validateToken(authHeader);

        citizenRepo.findById(citizenNic)
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + citizenNic));

        List<CitizenDocument> documents = documentRepo.findByCitizenNic(citizenNic);

        // Delete all files from disk
        documents.forEach(doc -> {
            try {
                Files.deleteIfExists(Paths.get(doc.getFilePath()));
            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to delete file: " + doc.getFileName());
            }
        });

        // Delete all records from DB
        documentRepo.deleteByCitizenNic(citizenNic);

        return "All documents deleted successfully";
    }


    // HELPER - map entity to DTO

    private CitizenDocumentDTO mapToDTO(CitizenDocument document) {
        CitizenDocumentDTO dto = new CitizenDocumentDTO();
        dto.setDocumentId(document.getDocumentId());
        dto.setCitizenNic(document.getCitizenNic());
        dto.setDocumentType(document.getDocumentType());
        dto.setFileName(document.getFileName());
        dto.setFilePath(document.getFilePath());
        dto.setUploadedAt(document.getUploadedAt());
        return dto;
    }


}
