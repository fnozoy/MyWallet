package com.fnozoy.myWallet.model.repository;

import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface EntriesRepository extends JpaRepository<Entry, Long> {

    @Query(value = "" +
            " select sum(e.value) " +
            " from  Entry e " +
            " join  e.user u " +
            " where u.Id = :id " +
            " and   e.entryCodeEnum = :entryCodeEnum" +
            " and   e.entryStatusEnum = :entryStatusEnum" +
            " group by u"
    )
    BigDecimal getBalanceByUserId(
            @Param("id") Long id,
            @Param("entryCodeEnum") EntryCodeEnum entryCodeEnum,
            @Param("entryStatusEnum") EntryStatusEnum entryStatusEnum
            );

}
