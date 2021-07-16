package com.ams.building.server.dao;

import com.ams.building.server.bean.StatusResidentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusResidentCardDAO extends JpaRepository<StatusResidentCard, Long> {
}
