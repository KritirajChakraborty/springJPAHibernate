package com.Kritiraj.SpringJPAHibernate.model;


import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//instead of manually writing 2 constructor and 10 getter and setters we can use lombok annotation
//so in future if we add 20 attributes,40 getters and setters will not be written, Lombok handles this
@NoArgsConstructor   //this is for default constructor with no args
@AllArgsConstructor  //this is for all args constructor
@Getter
@Setter
@Entity              // this is for letting JPA know to create a DB table for this class
//@Table(name="customer_info")
@Builder
public class Customer {

    @Id   // this is the annotation to let JPA know this is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //this is auto incrementing for PK so user dont need to enter id
    private int customerId;
    //@Column(name="first_name)
    private String name;
    private int age;
    private String email;
    //@Enumerated(EnumType.STRING)
    private Gender gender;

    //this is how we create oneToMany relationship in java where one represents the
    @OneToMany(cascade= CascadeType.ALL) // ALL will propagate all properties from parent to child entity(create,save,remove etc) you can add specific cascading too
    @JoinColumn(name="customer_id") // this creates foreign key in bookings table with reference to the PK of customer
            // if we dont use the name="customer_id" then JPA will create a column name as bookings_customer_id
            //basically name of the foreign key variable + name of the primary key of the table it is referenced to.
    List<Booking> bookings = new ArrayList<>();


}
//1
//if we say add another attribute to entity here, JPA will create a new column in DB
//but JPA cannot delete attribute/column in DB
//to change the table name of a entity we use @Table(name="name") but here if there already
     //has a table JPA will not rename it but create a new table (*written in comments above)
//to change the column name of a entity we use @Column(name="name") but here if there already
     //has a column JPA will not rename it but create a new column (*written in comments above)

//Generally in real life we don't use JPA to write the structure of SQL tables, for that we have other things

//2
//Some important points to remember:
//when we establish a one-2-many or many-2-one relationship then
//FK is always stored in the Many table as it references the PK of one table

//3
//Cascading: here the customer table has a parent relationship to the booking table which is child
//so for instance if we delete a customer, we want to delete all bookings also associated with that customer
//we achieve that my adding cascadeType.ALL in OneToMany annotation