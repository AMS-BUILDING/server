package com.ams.building.server.dao;

import com.ams.building.server.bean.AbsentDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsentDetailDAO extends JpaRepository<AbsentDetail, Long> {

    @Query("SELECT ad FROM AbsentDetail ad WHERE ad.name LIKE %?1% AND ad.identifyCard LIKE %?2% AND ad.absentType.id =?3 ORDER BY ad.id")
    Page<AbsentDetail> absentList(String name, String identifyCard, Long absentType, Pageable pageable);

    @Query("SELECT ad FROM AbsentDetail ad WHERE ad.name LIKE %?1% AND ad.identifyCard LIKE %?2%  ORDER BY ad.id")
    Page<AbsentDetail> absentListNotByAbsentType(String name, String identifyCard, Pageable pageable);

    @Query("SELECT ad FROM AbsentDetail ad WHERE  ad.identifyCard =?1 AND ad.absentType.id =?2")
    AbsentDetail getAbsentDetailByIdentifyCardAndAbsentType(String identifyCard, Long absentType);

}
