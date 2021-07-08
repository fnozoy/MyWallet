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
        //User user = User.builder().name("userid").email("userid@email.com").pswd("blablabla").build();
        //testEntityManager.persist(user);
        Entry entry = Entry.builder().month(6).year(2021).entryCodeEnum(EntryCodeEnum.INCOME).description("SALARY").value(BigDecimal.valueOf(10000)).build();
        Entry entrySave = entriesRepository.save(entry);
        Assertions.assertThat(entrySave.getId()).isNotNull();
    }

    @Test
    public void verifyIfDeleteEntryWorks(){
        Entry entry = Entry.builder().month(6).year(2021).entryCodeEnum(EntryCodeEnum.INCOME).description("SALARY").value(BigDecimal.valueOf(10000)).build();
        testEntityManager.persist(entry);
        entry = testEntityManager.find(Entry.class, entry.getId());
        entriesRepository.delete(entry);
        Entry entryNull = testEntityManager.find(Entry.class, entry.getId());
        Assertions.assertThat(entryNull).isNull();

    }

    @Test
    public void verifyIfFindByIdWorks(){
        Entry entry = Entry.builder().month(6).year(2021).entryCodeEnum(EntryCodeEnum.INCOME).description("SALARY").value(BigDecimal.valueOf(10000)).build();
        testEntityManager.persist(entry);
        Optional<Entry> entryOptional = entriesRepository.findById(entry.getId());
        Assertions.assertThat(entryOptional).isNotEmpty();
    }

    @Test
    public void verifyIfUpdateWorks(){
        Entry entry = Entry.builder().month(6).year(2021).entryCodeEnum(EntryCodeEnum.INCOME).description("SALARY").value(BigDecimal.valueOf(10000)).build();
        testEntityManager.persist(entry);
        entry.setYear(2020);
        Entry entryUpdated = entriesRepository.save(entry);
        Assertions.assertThat(entryUpdated.getYear()).isEqualTo(2020);
    }

    @Test
    public void verifyIfgetBalanceByUserIdWorks(){
        User user = User.builder().name("userid").email("userid@email.com").pswd("blablabla").build();
        testEntityManager.persist(user);
        Entry entry = Entry.builder().month(6).year(2021).entryCodeEnum(EntryCodeEnum.INCOME).entryStatusEnum(EntryStatusEnum.APPROVED).description("SALARY").value(BigDecimal.valueOf(10000)).user(user).build();
        testEntityManager.persist(entry);
        entry = Entry.builder().month(6).year(2021).entryCodeEnum(EntryCodeEnum.OUTCOME).entryStatusEnum(EntryStatusEnum.APPROVED).description("EXPENSE").value(BigDecimal.valueOf(1000)).user(user).build();
        testEntityManager.persist(entry);
        BigDecimal balance = entriesRepository.getBalanceByUserId(entry.getUser().getId(), EntryCodeEnum.INCOME, EntryStatusEnum.APPROVED);
        Assertions.assertThat(balance.compareTo(new BigDecimal("10000")) == 0);
        balance = entriesRepository.getBalanceByUserId(entry.getUser().getId(), EntryCodeEnum.OUTCOME, EntryStatusEnum.APPROVED);
        Assertions.assertThat(balance.compareTo(new BigDecimal("1000")) == 0);
    }

}
