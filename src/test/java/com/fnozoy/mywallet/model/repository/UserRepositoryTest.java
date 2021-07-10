package com.fnozoy.mywallet.model.repository;

import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

/* demands spring boot on application context
@SpringBootTest
*/
@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test") uses application-test.properties
@ActiveProfiles("test")
//@DataJpaTest must rollback the database
@DataJpaTest
//@AutoConfigureTestDatabase do not mess DB config
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserRepositoryTest {
    /*
    integration test over entities
     */
    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void verifyIfEmailAlreadyExists(){
        User user = makeUser();
        testEntityManager.persist(user);
        boolean result = userRepository.existsByEmail("user@email.com");
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void verifyEmailDoesNotExist(){
        userRepository.deleteAll();
        boolean result = userRepository.existsByEmail("doesnotexist@email.com");
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void persistUserOnDB(){
        User user = makeUser();
        User userSave = userRepository.save(user);
        boolean result = userRepository.existsByEmail("user@email.com");
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void getUserByEmailWithSuccess(){
        User user = makeUser();
        User userSave = userRepository.save(user);
        Optional<User> userFound = userRepository.findByEmail("user@email.com");
        Assertions.assertThat(userFound.isPresent()).isTrue();

    }

    @Test
    public void getUserByEmailWithUserNotFound(){
        User user = makeUser();
        User userSave = userRepository.save(user);
        Optional<User> userFound = userRepository.findByEmail("NoTfOuNd@eM4i1.C0M");
        Assertions.assertThat(userFound.isPresent()).isFalse();

    }
    public User makeUser() {
        return User.builder()
                .name("User Name")
                .email("user@email.com")
                .pswd("123456")
                .build();
    }
}
