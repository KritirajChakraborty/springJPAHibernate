package com.Kritiraj.SpringJPAHibernate.dto.response;

import com.Kritiraj.SpringJPAHibernate.model.Cab;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MostActiveDriverResponse {

    private int driverId;
    private String name;
    private int age;
    private String emailId;
    private int cabId;
    private long totalTrips;
}


