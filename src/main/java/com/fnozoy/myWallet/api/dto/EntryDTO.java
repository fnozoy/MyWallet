package com.fnozoy.myWallet.api.dto;

import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.enums.EntryCode;
import com.fnozoy.myWallet.model.enums.EntryStatusCode;
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
    private String description;
    private Integer month;
    private Integer year;
    private BigDecimal value;
    private EntryCode entryCode;
    private EntryStatusCode entryStatusCode;
    private LocalDate createDate;
    private Long userId;

}
