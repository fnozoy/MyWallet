package com.fnozoy.myWallet.service;

import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.enums.EntryStatusCode;

import java.util.List;
import java.util.Optional;

public interface EntryService {

    Entry create(Entry entry);
    Entry update(Entry entry);
    void delete(Long id);
    List<Entry> search(Entry entryFilter);
    void updateStatus(Entry entry, EntryStatusCode entryStatusCode);
    void validate(Entry entry);
    Optional<Entry> findById(Long id);
}
