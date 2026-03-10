package com.Kritiraj.SpringJPAHibernate.repository;

import com.Kritiraj.SpringJPAHibernate.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    //Driver findByCabCabId(int cabId);

    //Or use HQL here
    //@Query("select d from Driver d where d.cab.cabId = :cabId")
    //Driver findDriverByCabId(@Param("cabId") int cabId)
    //Native SQL below
    @Query(value="select * from driver d where d.cab_id = :cabId", nativeQuery = true)
    Driver findDriverByCabId(@Param("cabId") int cabId);
}
