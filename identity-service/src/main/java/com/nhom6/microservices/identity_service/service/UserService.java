package com.nhom6.microservices.identity_service.service;

import com.nhom6.microservices.identity_service.dto.request.UserCreationRequest;
import com.nhom6.microservices.identity_service.dto.request.UserUpdateRequest;
import com.nhom6.microservices.identity_service.dto.respone.UserResponse;
import com.nhom6.microservices.identity_service.entity.User;
import com.nhom6.microservices.identity_service.enums.Role;
import com.nhom6.microservices.identity_service.exception.AppException;
import com.nhom6.microservices.identity_service.exception.ErrorCode;
import com.nhom6.microservices.identity_service.mapper.UserMapper;
import com.nhom6.microservices.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest userCreationRequest) {


        if(userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user =userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        log.info("In method getAllUsers");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) {
        log.info("In method getUserById");
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo(){
        var securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id, UserUpdateRequest userUpdateRequest) {

        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        userMapper.updateUser(user, userUpdateRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public String deleteUser(String id) {
        userRepository .deleteById(id);
        return "User has been deleted";
    }
}
