package com.Kritiraj.SpringJPAHibernate.repository;

import com.Kritiraj.SpringJPAHibernate.model.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabRepository extends JpaRepository<Cab,Integer> {
}
