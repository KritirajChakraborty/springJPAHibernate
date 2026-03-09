package com.Kritiraj.SpringJPAHibernate.dto.response;

import com.Kritiraj.SpringJPAHibernate.Enum.TripStatus;
import lombok.*;


import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingResponse {

    private String pickup;
    private String destination;
    private double tripDistanceInKM;
    private TripStatus tripStatus;
    private double billAmount;
    private Date bookedAt;
    private Date lastUpdateAt;
    private CustomerResponse customer;
    private CabResponse cab;
}
