package com.ams.building.server.dao;

import com.ams.building.server.bean.TypeApartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeApartmentDAO extends JpaRepository<TypeApartment, Long> {
}
