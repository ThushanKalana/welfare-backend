package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.CitizenDocument;
import com.welfare.welfare_management.model.DocumentType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CitizenDocumentRepo extends CrudRepository<CitizenDocument, Long> {

    List<CitizenDocument> findByCitizenNic(String citizenNic);

    boolean existsByCitizenNicAndDocumentType(String citizenNic, DocumentType documentType);

    @Modifying
    @Query("DELETE FROM CitizenDocument d WHERE d.citizenNic = :citizenNic " +
            "AND d.documentType = :documentType")
    void deleteByCitizenNicAndDocumentType(
            @Param("citizenNic") String citizenNic,
            @Param("documentType") DocumentType documentType);

    //Add this for deleteAllDocuments
    @Modifying
    @Query("DELETE FROM CitizenDocument d WHERE d.citizenNic = :citizenNic")
    void deleteByCitizenNic(@Param("citizenNic") String citizenNic);

}


