package org.basak.educore.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.basak.educore.model.entity.User;
import org.basak.educore.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserDetails implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = Optional.ofNullable(userService.findByEmail(email));
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return convertToUserDetails(userOpt.get());
    }

    public UserDetails loadUserById(UUID userId) throws UsernameNotFoundException {
        Optional<User> userOpt = Optional.ofNullable(userService.findById(userId));
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
        return convertToUserDetails(userOpt.get());
    }

    private UserDetails convertToUserDetails(User user) {
        String role = "ROLE_" + user.getProfileType().getName().toUpperCase(); // örn: ROLE_SUPERADMIN
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}