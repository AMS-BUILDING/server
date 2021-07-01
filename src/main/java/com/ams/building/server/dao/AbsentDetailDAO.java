package com.ams.building.server.dao;

import com.ams.building.server.bean.AbsentDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsentDetailDAO extends JpaRepository<AbsentDetail, Long> {

    @Query("SELECT ad FROM AbsentDetail ad WHERE ad.name LIKE CONCAT('%',:name,'%') AND ad.identifyCard LIKE CONCAT('%',:identifyCard,'%') AND ad.absentType.id =:absentType ORDER BY ad.id")
    Page<AbsentDetail> absentList(@Param("name") String name, @Param("identifyCard") String identifyCard, @Param("absentType") Long absentType, Pageable pageable);

    @Query("SELECT ad FROM AbsentDetail ad WHERE ad.name LIKE CONCAT('%',:name,'%') AND ad.identifyCard LIKE CONCAT('%',:identifyCard,'%')  ORDER BY ad.id")
    Page<AbsentDetail> absentListNotByAbsentType(@Param("name") String name, @Param("identifyCard") String identifyCard, Pageable pageable);

    @Query("SELECT ad FROM AbsentDetail ad WHERE  ad.identifyCard =?1 AND ad.absentType.id =?2")
    AbsentDetail getAbsentDetailByIdentityCardAndAbsentType(String identifyCard, Long absentType);

}
