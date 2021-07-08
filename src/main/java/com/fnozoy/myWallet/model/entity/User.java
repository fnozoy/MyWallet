package com.fnozoy.myWallet.model.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="mw001user", schema="wallet")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

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
    private Long Id;

    @Column(name="mw001_name_x")
    private String name;

    @Column(name="mw001_email_c")
    private String email;

    @Column(name="mw001_password_c")
    private String pswd;

}
