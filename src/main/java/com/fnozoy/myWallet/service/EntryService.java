package com.fnozoy.myWallet.service;

import com.fnozoy.myWallet.api.dto.EntryDTO;
import com.fnozoy.myWallet.model.entity.Entry;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EntryService {

    EntryDTO create(EntryDTO entryDTO);
    EntryDTO update(EntryDTO entryDTO);
    void delete(Long id);
    List<EntryDTO> search(EntryDTO entryFilter);
    void updateStatus(EntryDTO entryDTO);

    Optional<Entry> findById(Long id);

    BigDecimal getBalanceByUserId(Long id);
}
