package com.docpronto.mapper;

import com.docpronto.domain.dto.DocumentoDto;
import com.docpronto.domain.entity.DocumentoEntity;
import org.springframework.stereotype.Component;

@Component
public class DocumentoMapper {

    public DocumentoDto toDto(DocumentoEntity entity) {
        return new DocumentoDto(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getCreatedAt()
        );
    }
}
