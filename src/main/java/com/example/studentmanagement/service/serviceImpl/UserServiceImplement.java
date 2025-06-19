package com.example.studentmanagement.service.serviceImpl;

import com.example.studentmanagement.exception.NotFoundException;
import com.example.studentmanagement.model.dto.response.UserResponse;
import com.example.studentmanagement.model.entity.postgres.User;
import com.example.studentmanagement.repository.postgres.UserRepository;
import com.example.studentmanagement.service.UserService;
import com.example.studentmanagement.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplement.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponse loadUserByUserId() throws UsernameNotFoundException {
        Integer userId = GetCurrentUser.userId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }
}
