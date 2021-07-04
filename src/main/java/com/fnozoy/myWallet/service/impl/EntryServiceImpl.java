package com.fnozoy.myWallet.service.impl;

import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.enums.EntryStatusCode;
import com.fnozoy.myWallet.model.repository.EntriesRepository;
import com.fnozoy.myWallet.service.EntryService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class EntryServiceImpl implements EntryService {

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
        return entriesRepository.save(entry);
    }

    @Override
    @Transactional
    public void delete(Entry entry) {
        Objects.requireNonNull(entry.getId());
        entriesRepository.delete(entry);
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
        if(entry.getEntryCode() == null)  {
            throw new BusinessRuleException("Entry type was not informed.");
        }


    }
}
