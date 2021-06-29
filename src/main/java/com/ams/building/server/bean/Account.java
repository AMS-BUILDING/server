package com.ams.building.server.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender", columnDefinition = "false")
    private Boolean gender;

    @Column(name = "password")
    private String password;

    @Column(name = "identity_card", unique = true)
    private String identityCard;

    @Column(name = "image_link")
    private String image;

    @Column(name = "dob")
    private String dob;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "home_town")
    private String homeTown;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "start_time")
    private Date startDate;

    @Column(name = "end_time")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    public Account(@NonNull Long id) {
        this.id = id;
    }
}
