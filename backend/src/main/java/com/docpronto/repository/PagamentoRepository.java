package com.docpronto.repository;

import com.docpronto.domain.entity.PagamentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends MongoRepository<PagamentoEntity, UUID> {
    
    Optional<PagamentoEntity> findByGeracaoDocumentoId(UUID geracaoDocumentoId);
}
