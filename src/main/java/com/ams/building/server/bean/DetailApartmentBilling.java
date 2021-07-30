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
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detail_apartment_billing")
public class DetailApartmentBilling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_billing_id", referencedColumnName = "id")
    private ApartmentBilling apartmentBilling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_detail_sub_service_id", referencedColumnName = "id")
    private ReasonDetailSubService reasonDetailSubService;

    @Column(name = "sub_service_name")
    private String subServiceName;

    @Column(name = "sub_service_price")
    private Double subServicePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_card_id", referencedColumnName = "id")
    private VehicleCard vehicleCard;

    @Column(name = "vehicle_name")
    private String vehicleName;

    @Column(name = "vehicle_price")
    private Double vehiclePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", referencedColumnName = "id")
    private ResidentCard residentCard;

    @Column(name = "resident_code")
    private String residentCode;

    @Column(name = "resident_price")
    private Double residentPrice;

    @Column(name = "billing_month")
    private String billingMonth;

}
