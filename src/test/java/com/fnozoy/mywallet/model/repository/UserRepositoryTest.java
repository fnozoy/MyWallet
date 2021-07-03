package com.fnozoy.mywallet.model.repository;

import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {
    /*
    integration test over entities
     */
    @Autowired
    UserRepository userRepository;

    @Test
    public void verifyIfEmailAlreadyExists(){
        //scenario
        User user = User.builder().name("userid").email("userid@email.com").build();
        userRepository.save(user);

        //action
        boolean result = userRepository.existsByEmail("userid@email.com");

        //verify
        Assertions.assertThat(result).isTrue();
    }

}
