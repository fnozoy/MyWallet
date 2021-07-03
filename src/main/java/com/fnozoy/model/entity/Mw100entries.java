package com.fnozoy.model.entity;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="mw010entries", schema="wallet")
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


    public Long getMw100_entry_k() {
        return mw100_entry_k;
    }

    public void setMw100_entry_k(Long mw100_entry_k) {
        this.mw100_entry_k = mw100_entry_k;
    }

    public String getMw100_entry_x() {
        return mw100_entry_x;
    }

    public void setMw100_entry_x(String mw100_entry_x) {
        this.mw100_entry_x = mw100_entry_x;
    }

    public Integer getMw100_month_c() {
        return mw100_month_c;
    }

    public void setMw100_month_c(Integer mw100_month_c) {
        this.mw100_month_c = mw100_month_c;
    }

    public Integer getMw100_year_c() {
        return mw100_year_c;
    }

    public void setMw100_year_c(Integer mw100_year_c) {
        this.mw100_year_c = mw100_year_c;
    }

    public BigDecimal getMw100_value_a() {
        return mw100_value_a;
    }

    public void setMw100_value_a(BigDecimal mw100_value_a) {
        this.mw100_value_a = mw100_value_a;
    }

    public EntryCode getMw100_entry_c() {
        return mw100_entry_c;
    }

    public void setMw100_entry_c(EntryCode mw100_entry_c) {
        this.mw100_entry_c = mw100_entry_c;
    }

    public EntryStatusCode getMw100_status_c() {
        return mw100_status_c;
    }

    public void setMw100_status_c(EntryStatusCode mw100_status_c) {
        this.mw100_status_c = mw100_status_c;
    }

    public LocalDate getWm100_create_y() {
        return wm100_create_y;
    }

    public void setWm100_create_y(LocalDate wm100_create_y) {
        this.wm100_create_y = wm100_create_y;
    }

    public com.fnozoy.model.entity.Mw001user getMw001user() {
        return Mw001user;
    }

    public void setMw001user(com.fnozoy.model.entity.Mw001user mw001user) {
        Mw001user = mw001user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mw100entries)) return false;
        Mw100entries that = (Mw100entries) o;
        return getMw100_entry_k().equals(that.getMw100_entry_k()) && getMw100_entry_x().equals(that.getMw100_entry_x()) && getMw100_month_c().equals(that.getMw100_month_c()) && getMw100_year_c().equals(that.getMw100_year_c()) && getMw100_value_a().equals(that.getMw100_value_a()) && getMw100_entry_c() == that.getMw100_entry_c() && getMw100_status_c() == that.getMw100_status_c() && getWm100_create_y().equals(that.getWm100_create_y()) && getMw001user().equals(that.getMw001user());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMw100_entry_k(), getMw100_entry_x(), getMw100_month_c(), getMw100_year_c(), getMw100_value_a(), getMw100_entry_c(), getMw100_status_c(), getWm100_create_y(), getMw001user());
    }

    @Override
    public String toString() {
        return "Mw100entries{" +
                "mw100_entry_k=" + mw100_entry_k +
                ", mw100_entry_x='" + mw100_entry_x + '\'' +
                ", mw100_month_c=" + mw100_month_c +
                ", mw100_year_c=" + mw100_year_c +
                ", mw100_value_a=" + mw100_value_a +
                ", mw100_entry_c=" + mw100_entry_c +
                ", mw100_status_c=" + mw100_status_c +
                ", wm100_create_y=" + wm100_create_y +
                ", Mw001user=" + Mw001user +
                '}';
    }
}
