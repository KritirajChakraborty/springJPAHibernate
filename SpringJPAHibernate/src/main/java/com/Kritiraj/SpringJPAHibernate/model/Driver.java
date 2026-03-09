package com.Kritiraj.SpringJPAHibernate.model;

import com.Kritiraj.SpringJPAHibernate.model.Booking;
import com.Kritiraj.SpringJPAHibernate.model.Cab;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;
    private String name;
    private int age;
    @Column(unique = true,nullable = false)
    private String emailId;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="driver_id")
    List<Booking> bookings = new ArrayList<>();

    @OneToOne(cascade=CascadeType.ALL)  //in 1-1 relationships, FK is added on the table where JoinColumn(Driver Entity) is called
    //unlike 1-many where FK is always created on the many table
    //here Driver and Cab can have multi directional mapping since it is one-one but this is avoided in real
    //life production code. Always prefer unidirectional mapping
    @JoinColumn(name="cab_id")
    Cab cab;
}
