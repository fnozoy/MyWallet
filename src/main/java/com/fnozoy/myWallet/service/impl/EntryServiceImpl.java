package com.fnozoy.myWallet.service.impl;

import com.fnozoy.myWallet.api.dto.EntryDTO;
import com.fnozoy.myWallet.exceptions.AuthenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import com.fnozoy.myWallet.model.repository.EntriesRepository;
import com.fnozoy.myWallet.model.repository.UserRepository;
import com.fnozoy.myWallet.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private final EntriesRepository entriesRepository;

    @Autowired
    private final UserRepository userRepository;

    public EntryServiceImpl(EntriesRepository entriesRepository, UserRepository userRepository) {
        this.entriesRepository = entriesRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public EntryDTO create(EntryDTO entryDTO) {

        validate(entryDTO);

        Entry entry = DTOToEntry(entryDTO);
        entry.setEntryStatusEnum(EntryStatusEnum.PENDING);
        entry.setCreateDate(LocalDate.now());
        entry = entriesRepository.save(entry);

        entryDTO = entryToDTO(entry);

        return entryDTO;
    }

    @Override
    @Transactional
    public EntryDTO update(EntryDTO entryDTO) {
        Entry entry = entriesRepository.findById(entryDTO.getId())
                .orElseThrow(() -> new BusinessRuleException("Entry does not exist."));

        validate(entryDTO);

        entry.setEntryStatusEnum(EntryStatusEnum.PENDING);
        entry.setEntryCodeEnum(entryDTO.getEntryCode());
        entry.setYear(entryDTO.getYear());
        entry.setMonth(entryDTO.getMonth());
        entry.setDescription(entryDTO.getDescription());
        entry.setValue(entryDTO.getValue());

        entry = entriesRepository.save(entry);

        entryDTO = entryToDTO(entry);

        return entryDTO;
    }

    @Override
    @Transactional
    public void updateStatus(EntryDTO entryDTO) {
        Entry entry = entriesRepository.findById(entryDTO.getId())
                .orElseThrow(() -> new BusinessRuleException("Entry does not exist."));
        entry.setEntryStatusEnum(entryDTO.getEntryStatus());
        entriesRepository.save(entry);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Entry entry = entriesRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Entry does not exist."));
        entriesRepository.deleteById(id);
    }

    @Override
    public List<EntryDTO> search(EntryDTO entryDTO) {
        //TODO implements pageable
        if(entryDTO.getUserId() == null)  {
            throw new BusinessRuleException("User is not informed.");
        }

        User user = userRepository.findById(entryDTO.getUserId())
                .orElseThrow( () -> new BusinessRuleException("User does not exist."));

        Entry entryFilter = DTOToEntry(entryDTO);
        Example<Entry> example = Example.of(entryFilter,
                ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        List<Entry> listEntry = entriesRepository.findAll(example);
        List<EntryDTO> listEntryDTO = new ArrayList<>();
        listEntry.forEach(entry->
                listEntryDTO.add(entryToDTO(entry))
        );

        return listEntryDTO;

    }

    @Override
    public BigDecimal getBalanceByUserId(Long id){

        if(id == null)  {
            throw new BusinessRuleException("User was not informed.");
        }

        User user = userRepository.findById(id)
                .orElseThrow( () -> new BusinessRuleException("User does not exist."));

        BigDecimal balanceIncome = entriesRepository.getBalanceByUserId(id, EntryCodeEnum.INCOME, EntryStatusEnum.APPROVED);
        BigDecimal balanceOutcome = entriesRepository.getBalanceByUserId(id, EntryCodeEnum.OUTCOME, EntryStatusEnum.APPROVED);
        if (balanceIncome == null){
            balanceIncome = BigDecimal.ZERO;
        }
        if (balanceOutcome == null){
            balanceOutcome = BigDecimal.ZERO;
        }
        return balanceIncome.subtract(balanceOutcome);
    }
    @Override
    public EntryDTO getEntryById(Long id){

        if(id == null)  {
            throw new BusinessRuleException("Entry was not informed.");
        }

        Entry entry = entriesRepository.findById(id)
                .orElseThrow( () -> new BusinessRuleException("Entry not found"));

        EntryDTO entryDTO = entryToDTO(entry);

        return entryDTO;
    }

    public void validate(EntryDTO entry) {
        if(entry.getDescription() == null || entry.getDescription().trim().equals("")){
            throw new BusinessRuleException("Fill up the description.");
        }
        if(entry.getMonth() == null || entry.getMonth() < 1 || entry.getMonth() > 12)  {
            throw new BusinessRuleException("Inform a valid month.");
        }
        if(entry.getYear() == null || entry.getYear().toString().length() != 4)  {
            throw new BusinessRuleException("Inform a valid year.");
        }
        if(entry.getUserId() == null)  {
            throw new BusinessRuleException("User is invalid.");
        }
        if(entry.getValue() == null || entry.getValue().compareTo(BigDecimal.ZERO) < 1)  {
            throw new BusinessRuleException("Value is invalid.");
        }
        if(entry.getEntryCode() == null || (entry.getEntryCode()!= EntryCodeEnum.INCOME && entry.getEntryCode()!= EntryCodeEnum.OUTCOME) )  {
            throw new BusinessRuleException("Entry type is invalid.");
        }
    }

    private Entry DTOToEntry(EntryDTO entryDTO){
        User user = new User();
        user.setId(entryDTO.getUserId());

        userRepository.findById(user.getId()).orElseThrow( () -> new BusinessRuleException("User does not exist."));

        return Entry.builder()
                .description(entryDTO.getDescription())
                .month(entryDTO.getMonth())
                .year(entryDTO.getYear())
                .value(entryDTO.getValue())
                .user(user)
                .entryCodeEnum(entryDTO.getEntryCode())
                .entryStatusEnum(entryDTO.getEntryStatus())
                .build();
    }

    private EntryDTO entryToDTO(Entry entry){

        return EntryDTO.builder()
                .id(entry.getId())
                .description(entry.getDescription())
                .month(entry.getMonth())
                .year(entry.getYear())
                .value(entry.getValue())
                .userId(entry.getUser().getId())
                .entryCode(entry.getEntryCodeEnum())
                .entryStatus(entry.getEntryStatusEnum())
                .createDate(LocalDate.now())
                .build();
    }


}
