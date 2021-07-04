package com.fnozoy.myWallet.service.dto.EntryDTO;

import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.enums.EntryCode;
import com.fnozoy.myWallet.model.enums.EntryStatusCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EntryDTO {
    private String description;
    private Integer month;
    private Integer year;
    private BigDecimal value;
    private EntryCode entryCode;
    private EntryStatusCode entryStatusCode;
    private LocalDate createDate;
    private User userId;

}
