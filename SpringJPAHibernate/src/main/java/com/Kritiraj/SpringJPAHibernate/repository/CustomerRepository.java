package com.Kritiraj.SpringJPAHibernate.repository;

import com.Kritiraj.SpringJPAHibernate.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
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
}
