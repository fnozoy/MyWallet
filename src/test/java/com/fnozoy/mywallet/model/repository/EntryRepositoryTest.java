package com.fnozoy.mywallet.model.repository;

import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import com.fnozoy.myWallet.model.repository.EntriesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
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
public class EntryRepositoryTest {

    @Autowired
    EntriesRepository entriesRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void verifyIfSaveEntryWorks(){
        User user = makeUser();
        Entry entry = makeEntry(user);
        testEntityManager.persist(entry);

        Entry entrySave = entriesRepository.save(entry);
        Assertions.assertThat(entrySave.getId()).isNotNull();
    }

    @Test
    public void verifyIfDeleteEntryWorks(){
        User user = makeUser();
        Entry entry = makeEntry(user);
        testEntityManager.persist(entry);

        entry = testEntityManager.find(Entry.class, entry.getId());
        entriesRepository.delete(entry);
        Entry entryNull = testEntityManager.find(Entry.class, entry.getId());
        Assertions.assertThat(entryNull).isNull();
    }

    @Test
    public void verifyIfFindByIdWorks(){
        User user = makeUser();
        Entry entry = makeEntry(user);
        testEntityManager.persist(entry);

        Optional<Entry> entryOptional = entriesRepository.findById(entry.getId());
        Assertions.assertThat(entryOptional).isNotEmpty();
    }

    @Test
    public void verifyIfUpdateWorks(){
        User user = makeUser();
        Entry entry = makeEntry(user);
        testEntityManager.persist(entry);

        entry.setYear(2020);
        Entry entryUpdated = entriesRepository.save(entry);
        Assertions.assertThat(entryUpdated.getYear()).isEqualTo(2020);
    }

    @Test
    public void verifyIfgetBalanceByUserIdWorks(){
        User user = makeUser();
        testEntityManager.persist(user);
        Entry entry = makeEntry(user);
        testEntityManager.persist(entry);
        Entry entry2 =makeEntry(user);
        entry2.setEntryCodeEnum(EntryCodeEnum.OUTCOME);
        entry2.setValue(new BigDecimal(1000));
        entry2.setDescription("EXPENSE");
        testEntityManager.persist(entry2);

        BigDecimal balance = entriesRepository.getBalanceByUserId(entry.getUser().getId(), EntryCodeEnum.INCOME, EntryStatusEnum.APPROVED);
        Assertions.assertThat(balance.compareTo(new BigDecimal("10000")) == 0);

        balance = entriesRepository.getBalanceByUserId(entry.getUser().getId(), EntryCodeEnum.OUTCOME, EntryStatusEnum.APPROVED);
        Assertions.assertThat(balance.compareTo(new BigDecimal("1000")) == 0);
    }

    public Entry makeEntry(User user){
        return Entry.builder()
                .month(6)
                .year(2021)
                .entryCodeEnum(EntryCodeEnum.INCOME)
                .entryStatusEnum(EntryStatusEnum.APPROVED)
                .description("SALARY")
                .value(BigDecimal.valueOf(10000))
                .user(user)
                .build();
    }
    public User makeUser(){
        return User.builder()
                .name("userid")
                .email("userid@email.com")
                .pswd("blablabla")
                .build();
    }
}
