package com.Kritiraj.SpringJPAHibernate;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//instead of manually writing 2 constructor and 10 getter and setters we can use lombok annotation
//so in future if we add 20 attributes,40 getters and setters will not be written, Lombok handles this
@NoArgsConstructor   //this is for default constructor with no args
@AllArgsConstructor  //this is for all args constructor
@Getter
@Setter
@Entity              // this is for letting JPA know to create a DB table for this class
//@Table(name="customer_info")
public class Customer {

    @Id   // this is the annotation to let JPA know this is the primary key
    private int customerId;
    //@Column(name="first_name)
    private String name;
    private int age;
    private String email;
    Gender gender;
}

//if we say add another attribute to entity here, JPA will create a new column in DB
//but JPA cannot delete attribute/column in DB
//to change the table name of a entity we use @Table(name="name") but here if there already
     //has a table JPA will not rename it but create a new table (*written in comments above)
//to change the column name of a entity we use @Column(name="name") but here if there already
     //has a column JPA will not rename it but create a new column (*written in comments above)

//Generally in real life we don't use JPA to write the structure of SQL tables, for that we have other things