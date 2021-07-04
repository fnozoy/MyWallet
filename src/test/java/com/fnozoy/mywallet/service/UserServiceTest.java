package com.fnozoy.mywallet.service;

import com.fnozoy.myWallet.exceptions.AutenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import com.fnozoy.myWallet.service.UserService;
import com.fnozoy.myWallet.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;

import static org.mockito.Mockito.*;

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    UserRepository userRepository = mock(UserRepository.class);;
    UserService userService = new UserServiceImpl(userRepository);
    //UserServiceImpl userService = spy(UserServiceImpl.class);


    @Test
    public void validateExistingEmailReturnVoid(){
        when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        userService.validateSingleEmail("whatever@email.com");
    }

    @Test
    public void emailAlreadyExistsThrowError(){
        when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThrows(BusinessRuleException.class, () -> {
            userService.validateSingleEmail("whatever@email.com");
        });
    }

    @Test
    public void autenticateFailureDueToUnexistingEmail(){
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(AutenticationErrorException.class, () -> {
            userService.autenticate("whatever@email.com", "123465");
        });
    }

    @Test
    public void autenticateFailureDueToNotMatchingPassword(){
        User user = User.builder().email("whatever@email.com").name("JohnDo").pswd("123456").Id(1l).build();
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        Assertions.assertThrows(AutenticationErrorException.class, () -> {
            userService.autenticate("whatever@email.com", "wrongpassword");
        });
    }

    @Test
    public void autenticateSuccessfull(){

        User user = User.builder().email("whatever@email.com").name("JohnDo").pswd("123456").Id(1l).build();
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        User userAssert = userService.autenticate("whatever@email.com", "123456");
        org.assertj.core.api.Assertions.assertThat(userAssert).isNotNull();
    }


    @Test
    public void signupUserWithSuccess(){
        User user = User.builder().email("whatever@email.com").name("JohnDo").pswd("123456").Id(1l).build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userAssert = userService.signupUser(user);
        org.assertj.core.api.Assertions.assertThat(userAssert).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userAssert.getId()).isEqualTo(1l);
        org.assertj.core.api.Assertions.assertThat(userAssert.getEmail()).isEqualTo("whatever@email.com");
        org.assertj.core.api.Assertions.assertThat(userAssert.getName()).isEqualTo("JohnDo");
        org.assertj.core.api.Assertions.assertThat(userAssert.getPswd()).isEqualTo("123456");
    }

    @Test
    public void signupFailureDueToAlreadyExistingEmail(){
        when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        User user = User.builder().email("whatever@email.com").name("JohnDo").pswd("123456").Id(1l).build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        Assertions.assertThrows(BusinessRuleException.class, () -> {
            userService.signupUser(user);
        });
    }
}
