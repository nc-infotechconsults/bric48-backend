package it.infotechconsults.bric48.backend.service;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.mapper.UserMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.UserRepository;
import it.infotechconsults.bric48.backend.rest.dto.UserDTO;
import it.infotechconsults.bric48.backend.rest.dto.UserResponseDTO;
import it.infotechconsults.bric48.backend.rest.dto.UserSessionDTO;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService extends AuditService<UserDTO, UserResponseDTO, User, String> {

    public UserService(UserRepository repository, EntityManagerRepository<User> eRepository, UserMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = User.class;
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

    public Optional<UserSessionDTO> sessionByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);
        return Optional.ofNullable(user.isPresent() ? ((UserMapper)mapper).entityToSession(user.get()) : null);
    }

    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        Specification<User> spec = (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(criteriaBuilder.lower(root.get("email")), username.toLowerCase());
        if (repository.count(spec) > 1)
            throw new UsernameNotFoundException("Multiple user found with the username: " + username);

        return repository.findBy(spec, t -> t.first());
    }
    
}
