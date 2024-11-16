package it.infotechconsults.bric48.backend.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.enums.LogicOperator;
import it.infotechconsults.bric48.backend.enums.QueryOperation;
import it.infotechconsults.bric48.backend.mapper.UserMapper;
import it.infotechconsults.bric48.backend.repository.UserRepository;
import it.infotechconsults.bric48.backend.rest.dto.FilterCriteriaDTO;
import it.infotechconsults.bric48.backend.rest.dto.FiltersDTO;
import it.infotechconsults.bric48.backend.rest.dto.UserDTO;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService extends AuditService<UserDTO, User, User, String> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void checkSave(UserDTO dto) throws Exception {
        if (repository.exists((root, query, criteriaBuilder) -> criteriaBuilder
                .equal(criteriaBuilder.lower(root.get("email")), dto.getEmail().toLowerCase())))
            throw new EntityExistsException();
    }

    @Override
    protected void checkUpdate(String id, UserDTO dto, User entity) throws Exception {
        if (repository.exists((root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("email")), dto.getEmail().toLowerCase()),
                criteriaBuilder.not(
                        criteriaBuilder.equal(criteriaBuilder.lower(root.get("id")),
                                id.toLowerCase())))))
            throw new EntityExistsException();
    }

    public Optional<User> findByUsername(String username) {
        try {
            Page<User> userDetail = search(
                    FiltersDTO.builder()
                            .criterias(
                                    Collections.singletonList(
                                            FilterCriteriaDTO.builder()
                                                    .field("email")
                                                    .operation(QueryOperation.EQUAL_IGNORECASE)
                                                    .value(username)
                                                    .build()))
                            .operator(LogicOperator.AND)
                            .build(),
                    Pageable.unpaged());

            if (userDetail.getSize() > 1)
                throw new UsernameNotFoundException("Multiple user found with the username: " + username);

            // Converting UserInfo to UserDetails
            return userDetail.get().findFirst();
        } catch (Exception e) {
            log.error("Error happen: {}", e.getMessage());
            log.debug("Detail stacktrace: ", e);
            throw new UsernameNotFoundException("Error happen during search for username: " + username);
        }
    }

}
