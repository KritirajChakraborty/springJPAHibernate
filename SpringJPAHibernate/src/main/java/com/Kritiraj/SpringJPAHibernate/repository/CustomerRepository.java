package com.Kritiraj.SpringJPAHibernate.repository;

import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// CustomerRepository is a Spring Data JPA repository interface.
//
// By extending JpaRepository, we automatically get many built-in
// database operations like:
// save(), findById(), findAll(), deleteById() etc.
//
// Spring Data JPA automatically generates the implementation of
// this interface at runtime, so we do not need to write any SQL
// or implementation code ourselves.
//
// Hibernate acts as the ORM provider that actually interacts
// with the database and executes SQL queries.
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    List<Customer> findByGender(Gender gender);

    List<Customer> findByGenderAndAge(Gender gender, int age);


    //custom query using Query and HQL
    @Query("select c from Customer c where c.gender = :gender and c.age > :age")
    List<Customer> findByGenderAndAgeGreaterThan(@Param("gender") Gender gender, @Param("age") int age);

    //Sl-1
    @Query("select c from Customer c")
    List<Customer> findBasedOnPagination(Pageable pageable);


    //using native query and SQL
    //@Query(value = "select * from customer where gender = :gender and age > :age", nativeQuery = true)
    //List<Customer> findByGenderAndAgeGreaterThan(@Param("gender") Gender gender, @Param("age") int age);
    //ALWAYS USE HQL instead of native queries. only when we need complex DB logic then go for native sql
//  important :- JPQL/HQL works on java entities and fields and it is db independent
//  where as native sql works in db and tables directly thus in hql we use alias for c for Customer

}
