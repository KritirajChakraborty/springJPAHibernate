package com.Kritiraj.SpringJPAHibernate.repository;

import com.Kritiraj.SpringJPAHibernate.model.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CabRepository extends JpaRepository<Cab,Integer> {

//    @Query(value = "select * from cab where available = true limit 1", nativeQuery = true)

    //Better Query (for randomness)
    @Query(value="select * from cab where available = true order by rand() limit 1",nativeQuery = true)
    Cab findAvailableCab();



}
