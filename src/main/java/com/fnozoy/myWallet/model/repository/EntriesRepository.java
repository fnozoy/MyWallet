package com.fnozoy.myWallet.model.repository;

import com.fnozoy.myWallet.model.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntriesRepository extends JpaRepository<Entry, Long> {
}
