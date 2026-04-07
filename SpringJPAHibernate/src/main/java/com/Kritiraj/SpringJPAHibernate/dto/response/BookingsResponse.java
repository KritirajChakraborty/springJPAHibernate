package com.Kritiraj.SpringJPAHibernate.dto.response;

import com.Kritiraj.SpringJPAHibernate.Enum.TripStatus;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingsResponse {

    private String pickup;
    private String destination;
    private double tripDistanceInKM;
    private TripStatus tripStatus;
    private double billAmount;
    private Date bookedAt;
    private Date lastUpdateAt;

}
