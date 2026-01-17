package com.docpronto.service;

import com.docpronto.domain.dto.DocumentoDto;
import com.docpronto.domain.entity.DocumentoEntity;
import com.docpronto.domain.exception.DocumentoNaoEncontradoException;
import com.docpronto.mapper.DocumentoMapper;
import com.docpronto.repository.DocumentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final DocumentoMapper documentoMapper;

    public DocumentoService(DocumentoRepository documentoRepository, DocumentoMapper documentoMapper) {
        this.documentoRepository = documentoRepository;
        this.documentoMapper = documentoMapper;
    }

    public List<DocumentoDto> listarTodos() {
        List<DocumentoEntity> entities = documentoRepository.findAll();
        return entities.stream()
                .map(documentoMapper::toDto)
                .toList();
    }

    public DocumentoDto buscarPorId(UUID id) {
        DocumentoEntity entity = documentoRepository.findById(id)
                .orElseThrow(() -> new DocumentoNaoEncontradoException(id));
        return documentoMapper.toDto(entity);
    }
}
