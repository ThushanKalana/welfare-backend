package com.welfare.welfare_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "citizen_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "document_id")
    private Long documentId;              // BIGINT

   // @Column(name = "citizen_nic")
    private String citizenNic;

    @Enumerated(EnumType.STRING)          // maps enum to DB ENUM
    //@Column(name = "document_type")
    private DocumentType documentType;

    //@Column(name = "file_name")
    private String fileName;

    //@Column(name = "file_path")
    private String filePath;

    //@Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
