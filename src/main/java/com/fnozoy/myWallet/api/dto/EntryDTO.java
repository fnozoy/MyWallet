package com.fnozoy.myWallet.api.dto;

import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
public class EntryDTO {
    private Long id;
    private String description;
    private Integer month;
    private Integer year;
    private BigDecimal value;
    private EntryCodeEnum entryCode;
    private EntryStatusEnum entryStatus;
    private LocalDate createDate;
    private Long userId;

}
