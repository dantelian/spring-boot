package com.example.springbootsecurityjwt.config.sercurity;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class CustomUserDetailsService implements UserDetailsService {

//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        UserBuilder builder = withUsername(user.getUsername());
//        builder.password(user.getPassword());
//        builder.roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new));

        UserBuilder builder = withUsername("ddd");
        builder.password("$2a$10$HX2cnwrmT/AI2KCXKnIYuOW50Od7ST0NH5el81iT8TM14KJ.7k4zq"); // 123456
        builder.roles("ADMIN");

        return builder.build();
    }

}
