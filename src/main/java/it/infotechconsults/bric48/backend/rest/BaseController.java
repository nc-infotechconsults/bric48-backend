package it.infotechconsults.bric48.backend.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.infotechconsults.bric48.backend.mapper.BaseMapper;
import it.infotechconsults.bric48.backend.rest.dto.FiltersDTO;
import it.infotechconsults.bric48.backend.service.BaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseController<R, RS, E, ID> {

    protected final BaseService<R, RS, E, ID> service;
    protected final BaseMapper<R, E, RS> mapper;

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public RS create(@Valid @RequestBody R dto) throws Exception {
        return mapper.entityToResponse(service.save(dto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public RS update(@PathVariable("id") ID id, @Valid @RequestBody R dto) throws Exception {
        return mapper.entityToResponse(service.update(id, dto));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public RS patch(@PathVariable("id") ID id, @Valid @RequestBody R dto) throws Exception {
        return mapper.entityToResponse(service.patch(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") ID id) throws Exception {
        service.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public RS getById(@PathVariable("id") ID id) throws Exception {
        return mapper.entityToResponse(service.getById(id));
    }

    @PostMapping("/search")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<RS> search(@RequestBody FiltersDTO filters,
            @RequestParam(name = "unpaged", required = false, defaultValue = "false") Boolean unpaged, Pageable pageable)
            throws Exception {
        return mapper.pageEntityToPageRespoonse(service.search(filters, unpaged ? Pageable.unpaged(pageable.getSort()) : pageable));
    }

}
