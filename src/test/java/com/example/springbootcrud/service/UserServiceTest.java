package com.example.springbootcrud.service;

import com.example.springbootcrud.model.User;
import com.example.springbootcrud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        List<User> users = Arrays.asList(
                new User(1L, "John", "john@example.com", 25),
                new User(2L, "Jane", "jane@example.com", 30)
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        User user = new User(1L, "John", "john@example.com", 25);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertEquals("John", result.getName());
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        User user = new User(null, "John", "john@example.com", 25);
        when(userRepository.save(any(User.class))).thenReturn(new User(1L, "John", "john@example.com", 25));

        User result = userService.createUser(user);
        assertNotNull(result.getId());
    }

    @Test
    void searchUsers_ShouldReturnPaginatedUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        List<User> users = Arrays.asList(new User(1L, "John", "john@example.com", 25));
        Page<User> userPage = new PageImpl<>(users);
        when(userRepository.findByNameContainingIgnoreCase("John", pageable)).thenReturn(userPage);

        Page<User> result = userService.searchUsers("John", pageable);
        assertEquals(1, result.getTotalElements());
    }
}
