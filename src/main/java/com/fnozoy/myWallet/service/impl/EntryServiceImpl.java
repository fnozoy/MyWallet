package com.fnozoy.myWallet.service.impl;

import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.enums.EntryCode;
import com.fnozoy.myWallet.model.enums.EntryStatusCode;
import com.fnozoy.myWallet.model.repository.EntriesRepository;
import com.fnozoy.myWallet.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntriesRepository entriesRepository;

    public EntryServiceImpl(EntriesRepository entriesRepository) {
        this.entriesRepository = entriesRepository;
    }

    @Override
    @Transactional
    public Entry create(Entry entry) {
        validate(entry);
        entry.setEntryStatusCode(EntryStatusCode.PENDING);
        return entriesRepository.save(entry);
    }

    @Override
    @Transactional
    public Entry update(Entry entry) {
        Objects.requireNonNull(entry.getId());
        validate(entry);
        return entriesRepository.save(entry);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entriesRepository.deleteById(id);
    }

    @Override
    public List<Entry> search(Entry entryFilter) {
        Example example = Example.of(entryFilter,
                ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return entriesRepository.findAll(example);

    }

    @Override
    @Transactional
    public void updateStatus(Entry entry, EntryStatusCode entryStatusCode) {
        entry.setEntryStatusCode(entryStatusCode);
        entriesRepository.save(entry);
    }

    @Override
    public Optional<Entry> findById(Long Id){
        return entriesRepository.findById(Id);
    }

    @Override
    public BigDecimal getBalanceByUserId(Long id){
        BigDecimal balanceIncome = entriesRepository.getBalanceByUserId(id, EntryCode.INCOME, EntryStatusCode.APPROVED);
        BigDecimal balanceOutcome = entriesRepository.getBalanceByUserId(id, EntryCode.OUTCOME, EntryStatusCode.APPROVED);
        if (balanceIncome == null){
            balanceIncome = BigDecimal.ZERO;
        }
        if (balanceOutcome == null){
            balanceOutcome = BigDecimal.ZERO;
        }
        return balanceIncome.subtract(balanceOutcome);
    };

    @Override
    public void validate(Entry entry) {
        if(entry.getDescription() == null || entry.getDescription().trim().equals("")){
            throw new BusinessRuleException("Fill up the description.");
        }
        if(entry.getMonth() == null || entry.getMonth() < 1 || entry.getMonth() > 12)  {
            throw new BusinessRuleException("Inform a valid month.");
        }
        if(entry.getYear() == null || entry.getYear().toString().length() != 4)  {
            throw new BusinessRuleException("Inform a valid year.");
        }
        if(entry.getUser() == null || entry.getUser().getId() == null)  {
            throw new BusinessRuleException("User is invalid.");
        }
        if(entry.getValue() == null || entry.getValue().compareTo(BigDecimal.ZERO) < 1)  {
            throw new BusinessRuleException("Value is invalid.");
        }
        if(entry.getEntryCode() == null || (entry.getEntryCode()!= EntryCode.INCOME && entry.getEntryCode()!= EntryCode.OUTCOME) )  {
            throw new BusinessRuleException("Entry type is invalid.");
        }


    }
}
