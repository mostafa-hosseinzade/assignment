package com.assignment.user.service;

import com.assignment.user.dao.UserRepository;
import com.assignment.user.dto.UserDto;
import com.assignment.user.exceptions.UserNotFoundException;
import com.assignment.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() throws Exception {
        userService = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findById() throws Exception {
        Long userId = 1L;

        Assertions.assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("user not found!!!");
    }

    @Test
    void save() throws Exception {
        User user = User.builder()
                .id(1L)
                .fullname("Test Testy")
                .email("test@test.test")
                .username("test")
                .build();

        userService.create(user.getTransferObject());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User captureUser = userArgumentCaptor.getValue();

        Assertions.assertThat(captureUser).isEqualTo(user);
    }

    @Test
    void update() {
    }

    @Test
    void findByEmail() {
    }
}