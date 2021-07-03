package com.fnozoy.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="mw001user", schema="wallet")
public class Mw001user {

/*
    CREATE TABLE wallet.mw001user
    (
        mw001_user_k bigint NOT NULL DEFAULT nextval('wallet.mw001user_mw001_id_k_seq'::regclass),
        mw001_name_x character varying(150) COLLATE pg_catalog."default" NOT NULL,
        mw001_email_c character varying(100) COLLATE pg_catalog."default" NOT NULL,
        mw001_password_c character varying(250) COLLATE pg_catalog."default" NOT NULL,
        mw001_create_y date NOT NULL DEFAULT now(),
        CONSTRAINT mw001user_pkey PRIMARY KEY (mw001_user_k)
    )
 */
    @Id
    @Column(name="mw001_user_k")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mw001_user_k;

    @Column(name="mw001_name_x")
    private String mw001_name_x;

    @Column(name="mw001_email_c")
    private String mw001_email_c;

    @Column(name="mw001_password_c")
    private String mw001_password_c;


    public Long getMw001_user_k() {
        return mw001_user_k;
    }

    public void setMw001_user_k(Long mw001_user_k) {
        this.mw001_user_k = mw001_user_k;
    }

    public String getMw001_name_x() {
        return mw001_name_x;
    }

    public void setMw001_name_x(String mw001_name_x) {
        this.mw001_name_x = mw001_name_x;
    }

    public String getMw001_email_c() {
        return mw001_email_c;
    }

    public void setMw001_email_c(String mw001_email_c) {
        this.mw001_email_c = mw001_email_c;
    }

    public String getMw001_password_c() {
        return mw001_password_c;
    }

    public void setMw001_password_c(String mw001_password_c) {
        this.mw001_password_c = mw001_password_c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mw001user)) return false;
        Mw001user mw001user = (Mw001user) o;
        return getMw001_user_k().equals(mw001user.getMw001_user_k()) && getMw001_name_x().equals(mw001user.getMw001_name_x()) && getMw001_email_c().equals(mw001user.getMw001_email_c()) && getMw001_password_c().equals(mw001user.getMw001_password_c());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMw001_user_k(), getMw001_name_x(), getMw001_email_c(), getMw001_password_c());
    }

    @Override
    public String toString() {
        return "Mw001user{" +
                "mw001_user_k=" + mw001_user_k +
                ", mw001_name_x='" + mw001_name_x + '\'' +
                ", mw001_email_c='" + mw001_email_c + '\'' +
                ", mw001_password_c='" + mw001_password_c + '\'' +
                '}';
    }
}
