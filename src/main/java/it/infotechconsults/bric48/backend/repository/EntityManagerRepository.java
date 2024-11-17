package it.infotechconsults.bric48.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.infotechconsults.bric48.backend.rest.dto.FiltersDTO;

public interface EntityManagerRepository<E> {
    Page<E> search(FiltersDTO filters, Pageable pageable, Class<E> clazz);
}
