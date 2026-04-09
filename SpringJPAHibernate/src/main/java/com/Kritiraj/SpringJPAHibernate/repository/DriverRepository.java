package com.Kritiraj.SpringJPAHibernate.repository;

import com.Kritiraj.SpringJPAHibernate.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    //Driver findByCabCabId(int cabId);

    //Or use HQL here
    //@Query("select d from Driver d where d.cab.cabId = :cabId")
    //Driver findDriverByCabId(@Param("cabId") int cabId)
    //Native SQL below
    @Query(value="select * from driver d where d.cab_id = :cabId", nativeQuery = true)
    Optional<Driver> findDriverByCabId(@Param("cabId") int cabId);

    //direction will not work because :direction is not treated as SQL reserved word ASC/DESC rather value,
    //here best option for native query is to use EntityManager but input should be validated else prone to SQL injection
    @Query(value="select * from driver order by :sortby :direction limit :limit offset :offset",nativeQuery = true)
    List<Driver> findDriversBasedOnPaginationWithNativeQuery(@Param("offset")int offset,@Param("limit") int limit, @Param("sortby") String sortBy, @Param("direction") String direction);

    @Query(value= """
            select d.*
            from driver d
            join booking b
            on d.driver_id = b.driver_id
            group by b.driver_id
            order by count(b.driver_id) desc
            limit 1
            """, nativeQuery = true)
    Driver findMostActiveDriver();


    //this is not needed as while fetching the driver we fethced the cab object which has available field
    /*
    @Query("select d.cab.available from Driver d where d.driverId = :driverId")
    Boolean isCabAvailable(@Param("driverId") int driverId);
     */
}
