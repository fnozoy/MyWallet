package com.fnozoy.mywallet.model.repository;

import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserRepositoryTest {
    /*
    integration test over entities
     */
    @Autowired
    UserRepository userRepository;

    @Test
    public void verifyIfEmailAlreadyExists(){
        //scenario
        User user = User.builder().name("userid").email("userid@email.com").pswd("blablabla").build();
        userRepository.save(user);


        //action
        boolean result = userRepository.existsByEmail("userid@email.com");

        //verify
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void verifyEmailDoesNotExist(){
        //scenario

        //action
        boolean result = userRepository.existsByEmail("doesnotexist@email.com");

        //verify
        Assertions.assertThat(result).isFalse();
    }

}
