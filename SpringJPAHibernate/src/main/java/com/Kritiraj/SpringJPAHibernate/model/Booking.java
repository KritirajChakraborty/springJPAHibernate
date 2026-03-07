package com.Kritiraj.SpringJPAHibernate.model;


import com.Kritiraj.SpringJPAHibernate.Enum.TripStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Booking {

    @Id
    private int bookingId;
    private String pickup;
    private String destination;
    private double tripDistanceInKM;
    private TripStatus tripStatus;
    private double billAmount;
    @CreationTimestamp
    private Date bookedAt;
    @UpdateTimestamp
    private Date lastUpdateAt;

}
