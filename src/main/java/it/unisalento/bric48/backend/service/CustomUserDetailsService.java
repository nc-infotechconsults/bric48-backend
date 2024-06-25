package it.unisalento.bric48.backend.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.unisalento.bric48.backend.domain.Admin;
import it.unisalento.bric48.backend.domain.Worker;
import it.unisalento.bric48.backend.repositories.AdminRepository;
import it.unisalento.bric48.backend.repositories.WorkerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Admin admin = adminRepository.findByEmail(email);

        if (admin == null) {

            final Optional<Worker> workerOpt = workerRepository.findByEmail(email);

            Worker worker = new Worker();

            if(workerOpt.isPresent()){
                worker = workerOpt.get();
            }

            if(worker == null){
                throw new UsernameNotFoundException(email);
            }else{
                return org.springframework.security.core.userdetails.User
                .withUsername(worker.getEmail())
                .password(worker.getPassword())
                .roles("SECURITY_MANAGER")
                .build();
            }

        }

        return org.springframework.security.core.userdetails.User
                .withUsername(admin.getEmail())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }
}

