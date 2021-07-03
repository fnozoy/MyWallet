package com.fnozoy.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="mw010entries", schema="wallet")
@Data
@Builder
public class Mw100entries {
/*
    CREATE TABLE wallet.mw100entries
    (
        mw100_entry_k bigint NOT NULL DEFAULT nextval('wallet.mw100entries_mw100_entry_k_seq'::regclass),
        mw100_entry_x character varying(100) COLLATE pg_catalog."default" NOT NULL,
        mw100_month_c integer NOT NULL,
        mw100_year_c integer NOT NULL,
        mw100_value_a numeric(16,2) NOT NULL,
        mw100_entry_c character varying(20) COLLATE pg_catalog."default" NOT NULL,
        mw100_status_c character varying(20) COLLATE pg_catalog."default" NOT NULL,
        mw100_mw001_user_k bigint NOT NULL,
        wm100_create_y date NOT NULL DEFAULT now(),
        CONSTRAINT mw100entries_pkey PRIMARY KEY (mw100_entry_k),
        CONSTRAINT mw100entries_mw100_mw001_user_k_fkey FOREIGN KEY (mw100_mw001_user_k)
            REFERENCES wallet.mw001user (mw001_user_k) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
        CONSTRAINT mw100entries_mw100_entry_c_check CHECK (mw100_entry_c::text = ANY (ARRAY['INCOME'::character varying::text, 'OUTCOME'::character varying::text])) NOT VALID,
        CONSTRAINT mw100entries_mw100_status_c_check CHECK (mw100_status_c::text = ANY (ARRAY['PENDING'::character varying::text, 'CANCELED'::character varying::text, 'APPROVED'::character varying::text])) NOT VALID
    )
*/
    @Id
    @Column(name="mw100_entry_k")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mw100_entry_k;

    @Column(name="mw100_entry_x")
    private String mw100_entry_x;

    @Column(name="mw100_entry_x")
    private Integer mw100_month_c;

    @Column(name="mw100_year_c")
    private Integer mw100_year_c;

    @Column(name="mw100_value_a")
    private BigDecimal mw100_value_a;

    @Column(name="mw100_entry_c")
    @Enumerated(value=EnumType.STRING)
    private EntryCode mw100_entry_c;

    @Column(name="mw100_status_c")
    @Enumerated(value=EnumType.STRING)
    private EntryStatusCode mw100_status_c;

    @Column(name="wm100_create_y")
    @Convert(converter= Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate wm100_create_y;

    @ManyToOne
    @JoinColumn(name="mw100_mw001_user_k")
    private Mw001user Mw001user;

}
