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
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicle_card")
public class VehicleCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", columnDefinition = "id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", columnDefinition = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_vehicle_card_id", columnDefinition = "id")
    private StatusVehicleCard statusVehicleCard;

    @Column(name = "vehicle_type_name")
    private String vehicleName;

    @Column(name = "vehicle_branch")
    private String vehicleBranch;

    @Column(name = "license_plate", unique = true)
    private String licensePlate;

    @Column(name = "vehicle_color")
    private String vehicleColor;

    @Column(name = "billing_month")
    private String billingMonth;

    @Column(name = "start_date")
    private Date startDate;

}
