package com.Kritiraj.SpringJPAHibernate;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cab {

    @Id
    private int cabId;
    private String cabNumber;
    private String cabModel;
    private double perKMRate;
    private boolean available;
}
