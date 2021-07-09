package com.fnozoy.mywallet.service.impl;

import com.fnozoy.myWallet.api.dto.EntryDTO;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import com.fnozoy.myWallet.model.repository.EntriesRepository;
import com.fnozoy.myWallet.model.repository.UserRepository;
import com.fnozoy.myWallet.service.impl.EntryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class EntryServiceImplTest {


    UserRepository userRepository = mock(UserRepository.class);;
    EntriesRepository entriesRepository = mock(EntriesRepository.class);;
    EntryServiceImpl entryServiceImpl = new EntryServiceImpl(entriesRepository, userRepository);

        @Test
    public void validateCreateEntryWithSuccess() {
        User user = makeUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Entry entry = makeEntry(user);
        EntryDTO entryDTO = makeEntryDTO(user);
        when(entriesRepository.save(any(Entry.class))).thenReturn(entry);

        EntryDTO entryDTOAssert = entryServiceImpl.create(entryDTO);

        org.assertj.core.api.Assertions.assertThat(entryDTOAssert).isNotNull();
    }

    @Test
    public void validateCreateEntryWithInvalidUser() {
        User user = makeUser();
        Entry entry = makeEntry(user);
        EntryDTO entryDTO = makeEntryDTO(user);

        when(entriesRepository.save(any(Entry.class))).thenReturn(entry);

        Assertions.assertThrows(BusinessRuleException.class, () -> entryServiceImpl.create(entryDTO));
    }

    @Test
    public void validateCreateEntryWithInvalidMonth() {
        User user = makeUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Entry entry = makeEntry(user);
        EntryDTO entryDTO = makeEntryDTO(user);
        entryDTO.setMonth(13); //should be any other on entryService.validate()

        when(entriesRepository.save(any(Entry.class))).thenReturn(entry);

        Assertions.assertThrows(BusinessRuleException.class, () -> entryServiceImpl.create(entryDTO));
    }

    @Test
    public void validateUpdateEntryWithFailure() {
        User user = makeUser();
        Entry entry = makeEntry(user);
        EntryDTO entryDTO = makeEntryDTO(user);

        when(entriesRepository.save(any(Entry.class))).thenReturn(entry);

        Assertions.assertThrows(BusinessRuleException.class, () -> entryServiceImpl.update(entryDTO) );
    }

    @Test
    public void validateUpdateEntryWithSuccess() {
        User user = makeUser();
        Entry entry = makeEntry(user);
        EntryDTO entryDTO = makeEntryDTO(user);
        when(entriesRepository.findById(777L)).thenReturn(Optional.of(entry));
        when(entriesRepository.save(any(Entry.class))).thenReturn(entry);

        EntryDTO entryDTOAssert = entryServiceImpl.update(entryDTO);

        org.assertj.core.api.Assertions.assertThat(entryDTOAssert).isNotNull();
    }

    @Test
    public void validateUpdateStatusWithSuccess() {
        User user = makeUser();
        Entry entry = makeEntry(user);
        EntryDTO entryDTO = makeEntryDTO(user);
        when(entriesRepository.findById(777L)).thenReturn(Optional.of(entry));
        when(entriesRepository.save(any(Entry.class))).thenReturn(entry);

        entryServiceImpl.updateStatus(entryDTO);

        Mockito.verify(entriesRepository).save(entry);
    }

    @Test
    public void validateUpdateStatusWithFailure() {
        User user = makeUser();
        Entry entry = makeEntry(user);
        EntryDTO entryDTO = makeEntryDTO(user);
        when(entriesRepository.save(any(Entry.class))).thenReturn(entry);

        Assertions.assertThrows(BusinessRuleException.class, () -> entryServiceImpl.updateStatus(entryDTO) );
    }

    @Test
    void validateDeleteEntryWithSuccess() {
        User user = makeUser();
        Entry entry = makeEntry(user);
        when(entriesRepository.findById(1L)).thenReturn(Optional.of(entry));

        entryServiceImpl.delete(1L);

        Mockito.verify(entriesRepository).deleteById(1L);
    }

    @Test
    void validateDeleteEntryWithFailure() {
        doNothing().when(entriesRepository).deleteById(1L);

        Assertions.assertThrows( BusinessRuleException.class, () -> entryServiceImpl.delete(1L) );
    }

    @Test
    void validateSearchWithSuccess() {
        User user = makeUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Entry entry = makeEntry(user);
        Entry entry2 = makeEntry(user);
        entry2.setId(778L);
        EntryDTO entryDTO = makeEntryDTO(user);
        List<Entry> list = new ArrayList<>();
        list.add(entry);
        list.add(entry2);

        when(entriesRepository.findAll(any(Example.class))).thenReturn(list);

        List<EntryDTO> listDTO = entryServiceImpl.search(entryDTO);
        org.assertj.core.api.Assertions.assertThat(listDTO.size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(listDTO.get(0).getId()).isEqualTo(777L);
        org.assertj.core.api.Assertions.assertThat(listDTO.get(1).getId()).isEqualTo(778L);
    }


    @Test
    void validateSearchWithFailure() {
        User user = makeUser(); //do not mock
        Entry entry = makeEntry(user);
        Entry entry2 = makeEntry(user);
        entry2.setId(778L);
        EntryDTO entryDTO = makeEntryDTO(user);
        List<Entry> list = new ArrayList<Entry>();
        list.add(entry);
        list.add(entry2);

        when(entriesRepository.findAll(any(Example.class))).thenReturn(list);

        Assertions.assertThrows(BusinessRuleException.class, () ->  entryServiceImpl.search(entryDTO));
    }

    @Test
    void validateGetBalanceByUserIdWithSuccess() {
        User user = makeUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(entriesRepository.getBalanceByUserId(1L, EntryCodeEnum.INCOME, EntryStatusEnum.APPROVED)).thenReturn(new BigDecimal(1000));
        when(entriesRepository.getBalanceByUserId(1L, EntryCodeEnum.OUTCOME, EntryStatusEnum.APPROVED)).thenReturn(new BigDecimal(100));

        BigDecimal balance = entryServiceImpl.getBalanceByUserId(1L);

        org.assertj.core.api.Assertions.assertThat(balance.compareTo(new BigDecimal("900")) == 0);
    }

    @Test
    void validateGetBalanceByUserIdWithFailure() {
        User user = makeUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(entriesRepository.getBalanceByUserId(1L, EntryCodeEnum.INCOME, EntryStatusEnum.APPROVED)).thenReturn(new BigDecimal(1000));
        when(entriesRepository.getBalanceByUserId(1L, EntryCodeEnum.OUTCOME, EntryStatusEnum.APPROVED)).thenReturn(new BigDecimal(100));

        Assertions.assertThrows(BusinessRuleException.class, () -> {
            entryServiceImpl.getBalanceByUserId(11111111L);
        });
    }

    public User makeUser(){
        User user = User.builder()
                .Id(1L)
                .name("userid")
                .email("userid@email.com")
                .pswd("blablabla")
                .build();
        return user;
    }

    public Entry makeEntry(User user){
        Entry entry = Entry.builder()
                .Id(777L)
                .month(06)
                .year(2021)
                .entryCodeEnum(EntryCodeEnum.INCOME)
                .entryStatusEnum(EntryStatusEnum.PENDING)
                .description("SALARY")
                .value(BigDecimal.valueOf(10000))
                .user(user)
                .build();
        return entry;
    }

    public EntryDTO makeEntryDTO(User user){
        EntryDTO entryDTO = EntryDTO.builder()
                .id(777L)
                .month(06)
                .year(2021)
                .entryCode(EntryCodeEnum.INCOME)
                .entryStatus(EntryStatusEnum.APPROVED)
                .description("SALARY")
                .value(BigDecimal.valueOf(10000))
                .userId(1L)
                .build();
        return entryDTO;
    }

}