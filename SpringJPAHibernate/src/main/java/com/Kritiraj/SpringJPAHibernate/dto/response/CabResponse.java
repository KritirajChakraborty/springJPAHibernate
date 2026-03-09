package com.Kritiraj.SpringJPAHibernate.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CabResponse {
    private String cabNumber;
    private String cabModel;
    private double perKMRate;
    private boolean available;
    private DriverResponse driver;
}
