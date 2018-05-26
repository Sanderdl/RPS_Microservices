package com.sanderdl.service;


import com.sanderdl.domain.Role;
import com.sanderdl.domain.User;
import com.sanderdl.exception.ResourceNotFoundException;
import com.sanderdl.messaging.Status;
import com.sanderdl.messaging.UserAppGateway;
import com.sanderdl.model.JwtUser;
import com.sanderdl.model.RoleName;
import com.sanderdl.repository.RoleRepository;
import com.sanderdl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserAppGateway userAppGateway;

    private BCryptPasswordEncoder passwordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
        user.getRoles().add(userRole);

        User savedUser =  userRepository.save(user);
        userAppGateway.notifyServices(savedUser, Status.CREATED);

        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById (Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
        }else {

            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                    .collect(Collectors.toList());

            return new JwtUser(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        }
    }
}
