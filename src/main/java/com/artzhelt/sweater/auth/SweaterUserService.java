package com.artzhelt.sweater.auth;

import com.artzhelt.sweater.domain.Role;
import com.artzhelt.sweater.domain.SweaterUser;
import com.artzhelt.sweater.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SweaterUserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SweaterUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public SweaterUser register(String username, String password) {
        assertUsernameUnique(username);
        String encodedPassword = passwordEncoder.encode(password);
        SweaterUser user = new SweaterUser(username, encodedPassword, true, Set.of(Role.USER));
        return repository.save(user);
    }

    private void assertUsernameUnique(String username) {
        if (repository.existsByUsername(username)) {
            throw new UsernameNotUniqueException(username);
        }
    }

    public SweaterUser save(SweaterUser user, Set<Role> newRoles, String newUsername) {
        user.setUsername(newUsername);
        user.getRoles().addAll(newRoles);
        return repository.save(user);
    }

    public Iterable<SweaterUser> findAll() {
        return repository.findAll();
    }

    public SweaterUser saveProfile(SweaterUser user, String newPassword) {
        if (newPassword != null) {
            newPassword = newPassword.trim();
            if (!newPassword.isEmpty()) {
               newPassword = passwordEncoder.encode(newPassword) ;
               user.setPassword(newPassword);
            }
        }
        return repository.save(user);
    }
}
