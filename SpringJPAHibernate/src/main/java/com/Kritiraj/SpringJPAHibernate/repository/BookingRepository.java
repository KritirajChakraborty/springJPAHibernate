package com.Kritiraj.SpringJPAHibernate.repository;

import com.Kritiraj.SpringJPAHibernate.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query(value = "select exists(select * from booking where customer_id = :customerId)", nativeQuery = true)
    Long existsByCustomerId(@Param("customerId") int customerId);
}
