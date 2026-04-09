package com.Kritiraj.SpringJPAHibernate.dto.response;

import com.Kritiraj.SpringJPAHibernate.model.Booking;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerStatisticsResponse {

    private String customerName;
    private int totalTrips;
    private double totalAmountSpent;
    private Booking lastBooking;
}
