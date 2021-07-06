package com.fnozoy.myWallet.model.repository;

import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.enums.EntryCode;
import com.fnozoy.myWallet.model.enums.EntryStatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface EntriesRepository extends JpaRepository<Entry, Long> {

    @Query(value = "" +
            " select sum(e.value) " +
            " from Entry e " +
            " join e.user u " +
            " where u.Id = :id " +
            " and   e.entryCode = :entryCode" +
            " and   e.entryStatusCode = :entryStatusCode" +
            " group by u"
    )
    BigDecimal getBalanceByUserId(
            @Param("id") Long id,
            @Param("entryCode") EntryCode entryCode,
            @Param("entryStatusCode") EntryStatusCode entryStatusCode
            );

}
