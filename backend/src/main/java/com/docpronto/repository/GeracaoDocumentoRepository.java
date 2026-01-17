package com.docpronto.repository;

import com.docpronto.domain.entity.GeracaoDocumentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeracaoDocumentoRepository extends MongoRepository<GeracaoDocumentoEntity, UUID> {
}
