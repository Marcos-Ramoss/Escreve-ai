package com.docpronto.repository;

import com.docpronto.domain.entity.DocumentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentoRepository extends MongoRepository<DocumentoEntity, UUID> {
}
