package com.fnozoy.myWallet.service;

import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.enums.EntryStatusCode;

import java.util.List;

public interface EntryService {

    Entry create(Entry entry);
    Entry update(Entry entry);
    void delete(Entry entry);
    List<Entry> search(Entry entryFilter);
    void updateStatus(Entry entry, EntryStatusCode entryStatusCode);
    void validate(Entry entry);
}
