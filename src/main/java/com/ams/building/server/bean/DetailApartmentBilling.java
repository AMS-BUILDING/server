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
    @JoinColumn(name = "subservice_id", referencedColumnName = "id")
    private SubService subService;

    @Column(name = "sub_service_name")
    private String subServiceName;

    @Column(name = "sub_service_price")
    private Double subServicePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_card_id", referencedColumnName = "id")
    private ResidentCard residentCard;

    @Column(name = "quantity_residenrial_card")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_card_id", referencedColumnName = "id")
    private VehicleCard vehicleCard;

    @Column(name = "vechicle_name")
    private String vehicleName;

    @Column(name = "vechicle_price")
    private String vehiclePrice;

    @Column(name = "billing_month")
    private String billingMonth;

}
