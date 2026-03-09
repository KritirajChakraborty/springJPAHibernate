package com.Kritiraj.SpringJPAHibernate.transformer;

import com.Kritiraj.SpringJPAHibernate.dto.request.CabRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CabResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.DriverResponse;
import com.Kritiraj.SpringJPAHibernate.model.Cab;

public class CabTransformer {
    public static Cab cabRequestToCab(CabRequest cabRequest) {
        return Cab.builder()
                .cabModel(cabRequest.getCabModel())
                .cabNumber(cabRequest.getCabNumber())
                .perKMRate(cabRequest.getPerKMRate())
                .available(true)
                .build();
    }

    public static CabResponse cabToCabResponse(Cab cab, DriverResponse driverResponse) {
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber())
                .cabModel(cab.getCabModel())
                .perKMRate(cab.getPerKMRate())
                .driver(driverResponse)
                .available(cab.isAvailable())
                .build();
    }
}
