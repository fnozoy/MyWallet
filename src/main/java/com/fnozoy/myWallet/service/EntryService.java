package com.fnozoy.myWallet.service;

import com.fnozoy.myWallet.api.dto.EntryDTO;
import java.math.BigDecimal;
import java.util.List;

public interface EntryService {

    EntryDTO create(EntryDTO entryDTO);
    EntryDTO update(EntryDTO entryDTO);
    EntryDTO getEntryById(Long id);

    void delete(Long id);
    List<EntryDTO> search(EntryDTO entryFilter);
    void updateStatus(EntryDTO entryDTO);
    BigDecimal getBalanceByUserId(Long id);
}
