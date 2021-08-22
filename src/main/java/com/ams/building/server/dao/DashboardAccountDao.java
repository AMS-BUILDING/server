package com.ams.building.server.dao;

import com.ams.building.server.bean.Account;
import com.ams.building.server.response.DashboardResponseTotal;
import com.ams.building.server.response.DashboardTypeAccountResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardAccountDao extends CrudRepository<Account, Long> {


    @Query(value = "SELECT month (a.created_date) AS date ,count(a.id) AS total FROM account a WHERE a.enabled = 1  " +
            "   and year(a.created_date) = ?1   " +
            "   Group by month (a.created_date) ", nativeQuery = true)
    List<DashboardResponseTotal> monthlyAccount(String year);

    @Query(nativeQuery = true,
            value = "SELECT t.type_name as type,count(ac.id) as total FROM apartment a" +
                    " join room_number r " +
                    " on a.room_number_id = r.id " +
                    " join type_apartment t" +
                    " on r.type_apartment_id = t.id" +
                    " join account ac " +
                    " on a.account_id = ac.id" +
                    " where ac.enabled = 1" +
                    " group by t.type_name;")
    List<DashboardTypeAccountResponse> typeApartmentAccount();

}
