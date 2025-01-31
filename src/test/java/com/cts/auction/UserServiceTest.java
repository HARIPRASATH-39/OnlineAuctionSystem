package com.cts.auction;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.rmi.server.LoaderHandler;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.auction.DisplayDTO.LoginDisplayDTO;
import com.cts.auction.DisplayDTO.UserDisplayDTO;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Exception.UserNotFoundException;
import com.cts.auction.Repository.UserRepository;
import com.cts.auction.Service.UserServiceImpl;
import com.cts.auction.Validation.UserDTO;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private UserEntity userEntity;
    private UserDisplayDTO userdisplayDTO;
    

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder()
                .username("testuser")
                .email("testuser@example.com")
                .password("password")
                .wallet_amount(100.0)
                .build();

        userEntity = UserEntity.builder()
                .username("testuser")
                .email("testuser@example.com")
                .password("encodedPassword")
                .wallet_amount(100.0)
                .build();
        
        userdisplayDTO= UserDisplayDTO.builder()
        		.username("testuser")
        		.email("testuser@example.com")
        		.build();
        
       
    }

    @Test
    void testSignup_UserAlreadyRegistered() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(userEntity);

        String result = userService.signup(userDTO);

        assertEquals("The email address is already registered.", result);
    }

    @Test
    void testSignup_NewUser() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");

        String result = userService.signup(userDTO);

        assertEquals("Account created successfuly", result);
    }



    @Test
    void testFindAllUsers() {
        userService.findAllUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserById_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        UserDisplayDTO result = userService.findUserById(1);

        assertNotNull(result);
        assertEquals(userdisplayDTO, result);
    }

    @Test
    void testFindUserById_Failure() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUserById(1);
        });

        assertEquals("No user by ID: 1", exception.getMessage());
    }

    @Test
    void testAddAmount_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        String result = userService.addAmount(1, 50.0);

        assertEquals("Amount added successfull", result);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        String result = userService.deleteUser(1);

        assertEquals("User Removed", result);
        verify(userRepository, times(1)).delete(userEntity);
    }
}
