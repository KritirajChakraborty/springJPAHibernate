package com.Kritiraj.SpringJPAHibernate.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CabUtilizationResponse {
       private int cabId;
       private String cabNumber;
       private String cabModel;
       private int totalTrips;
       private double totalDistance;
       private double totalRevenue;
}
