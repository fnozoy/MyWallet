package com.fnozoy.myWallet.service.impl;

import com.fnozoy.myWallet.api.dto.UserDTO;
import com.fnozoy.myWallet.exceptions.AuthenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import com.fnozoy.myWallet.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
public class UserServiceImplTest {

    UserRepository userRepository = mock(UserRepository.class);
    UserService userService = new UserServiceImpl(userRepository);

    @Test
    public void validateExistingEmailReturnVoid(){
        when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        userService.validateSingleEmail("whatever@email.com");
        Mockito.verify(userRepository).existsByEmail("whatever@email.com");
    }

    @Test
    public void emailAlreadyExistsThrowError(){
        when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThrows(BusinessRuleException.class, () -> userService.validateSingleEmail("whatever@email.com"));
    }

    @Test
    public void authenticateFailureDueToNotExistingEmail(){
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        UserDTO userdto = UserDTO.builder().email("whaterver@email.com").pswd("123456").build();
        Assertions.assertThrows(AuthenticationErrorException.class, () -> userService.authenticate(userdto));
    }

    @Test
    public void authenticateFailureDueToNotMatchingPassword(){
        User user = User.builder().email("whatever@email.com").name("JohnDo").pswd("123456").Id(1L).build();
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDTO userDTO = UserDTO.builder().email(user.getEmail()).name(user.getName()).pswd("654321").build();
        Assertions.assertThrows(AuthenticationErrorException.class, () -> userService.authenticate(userDTO));
    }

    @Test
    public void authenticateSuccessful(){

        User user = User.builder().email("whatever@email.com").name("JohnDo").pswd("123456").Id(1L).build();
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDTO userDTO = UserDTO.builder().email(user.getEmail()).name(user.getName()).pswd(user.getPswd()).build();
        UserDTO userAssert = userService.authenticate(userDTO);
        org.assertj.core.api.Assertions.assertThat(userAssert).isNotNull();
    }


    @Test
    public void signupUserWithSuccess(){
        User user = User.builder().email("whatever@email.com").name("JohnDo").pswd("123456").Id(1L).build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .pswd(user.getPswd())
                .build();
        UserDTO userAssert = userService.signupUser(userDTO);
        org.assertj.core.api.Assertions.assertThat(userAssert).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userAssert.getId()).isEqualTo(1L);
        org.assertj.core.api.Assertions.assertThat(userAssert.getEmail()).isEqualTo("whatever@email.com");
        org.assertj.core.api.Assertions.assertThat(userAssert.getName()).isEqualTo("JohnDo");
        org.assertj.core.api.Assertions.assertThat(userAssert.getPswd()).isEqualTo("123456");
    }

    @Test
    public void signupFailureDueToAlreadyExistingEmail(){
        when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        User user = User.builder().email("whatever@email.com").name("JohnDo").pswd("123456").Id(1L).build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .pswd(user.getPswd())
                .build();
        Assertions.assertThrows(BusinessRuleException.class, () -> userService.signupUser(userDTO));
    }
}
