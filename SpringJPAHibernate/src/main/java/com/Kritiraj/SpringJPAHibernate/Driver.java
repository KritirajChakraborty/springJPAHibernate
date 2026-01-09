package com.Kritiraj.SpringJPAHibernate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Driver {

    @Id
    private int driverId;
    private String name;
    private int age;
    private String emailId;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="driver_id")
    List<Booking> bookings = new ArrayList<>();
}
