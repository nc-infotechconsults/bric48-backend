package it.infotechconsults.bric48.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findBy(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email),
                arg0 -> arg0.first());
    }

}
