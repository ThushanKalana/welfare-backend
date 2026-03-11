package com.welfare.welfare_management.dto;

import com.welfare.welfare_management.model.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenDocumentDTO {
    private Long documentId;              // Long not Integer
    private String citizenNic;
    private DocumentType documentType;    // enum type
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
}
