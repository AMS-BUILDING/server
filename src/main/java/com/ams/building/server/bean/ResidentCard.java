package com.ams.building.server.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resident_card")
public class ResidentCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", columnDefinition = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_resident_card_id", columnDefinition = "id")
    private StatusResidentCard statusResidentCard;

    @Column(name = "resident_card_code", unique = true)
    private String cardCode;

    @Column(name = "price")
    private Double price;

    @Column(name = "billing_month")
    private String billingMonth;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "is_use", columnDefinition = "1")
    private Integer isUse;

    @PrePersist
    public void prePersist() {
        if (startDate == null) {
            startDate = new Date(System.currentTimeMillis());
        }
    }

}
