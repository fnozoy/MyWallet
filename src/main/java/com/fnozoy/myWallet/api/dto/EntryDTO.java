package com.fnozoy.myWallet.api.dto;

import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import lombok.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public EntryDTO(Entry entry) {
        this.id = entry.getId();
        this.description = entry.getDescription();
        this.month = entry.getMonth();
        this.year = entry.getYear();
        this.value = entry.getValue();
        this.entryCode = entry.getEntryCodeEnum();
        this.entryStatus = entry.getEntryStatusEnum();
        this.userId = entry.getUser().getId();
    }

    public static Page<EntryDTO> convert(Page<Entry> entries) {
        return entries.map(EntryDTO::new);
    }
}
