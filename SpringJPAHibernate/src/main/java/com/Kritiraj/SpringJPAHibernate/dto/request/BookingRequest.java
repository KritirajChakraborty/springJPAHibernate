package com.Kritiraj.SpringJPAHibernate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingRequest {

    private String pickup;
    private String destination;
    private double tripDistanceInKM;
}
