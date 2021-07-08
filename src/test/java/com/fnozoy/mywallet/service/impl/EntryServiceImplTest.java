package com.fnozoy.mywallet.service.impl;

import com.fnozoy.myWallet.model.repository.EntriesRepository;
import com.fnozoy.myWallet.service.impl.EntryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class EntryServiceImplTest {

    @SpyBean
    EntryServiceImpl service;
    @MockBean
    EntriesRepository repository;
    //TODO: make TESTS on Entry Service impl

/*
    @Test
    public void verifyIfCreateWorks() {
        Entry entry = EntryRepositoryTest.saveEntry();

        User user = User.builder().name("userid").email("userid@email.com").pswd("blablabla").build();
       // TestEntityManager testEntityManager.persist(user);
        Entry entry = Entry.builder().month(6).year(2021).entryCodeEnum(EntryCodeEnum.INCOME).entryStatusCode(EntryStatusCode.APPROVED).description("SALARY").value(BigDecimal.valueOf(10000)).user(user).build();

        Entry entry = Entry.builder().month(6).year(2021).entryCodeEnum(EntryCodeEnum.INCOME).description("SALARY").value(BigDecimal.valueOf(10000)).build();
        when(entriesRepository.save(any(Entry.class))).thenReturn(entry);
        Entry entryAssert = entryServiceImpl.create(entry);
        Assertions.assertThat(entryAssert).isNotNull();
    }
*/
    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void search() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void findById() {
    }

    @Test
    void getBalanceByUserId() {
    }

}