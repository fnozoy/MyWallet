package com.fnozoy.mywallet.service;

import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import com.fnozoy.myWallet.service.UserService;
import com.fnozoy.myWallet.service.impl.UserServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    UserRepository userRepository;
    UserService userService;


    @Before("")
    public void setUp(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void validateExistingEmailReturnVoid(){
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
//        doNothing().when(userService).validateSingleEmail(anyString());
//        userService.validateSingleEmail("useridxx@email.com");
        verify(userService, times(1)).validateSingleEmail("useridxx@email.com");
    }


    @Test
    public void emailAlreadyExistsThrowError(){
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThrows(BusinessRuleException.class, () -> {
            userService.validateSingleEmail("userid@email.com");
        });
    }

}
