package com.Kritiraj.SpringJPAHibernate.transformer;

import com.Kritiraj.SpringJPAHibernate.dto.request.DriverRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.DriverResponse;
import com.Kritiraj.SpringJPAHibernate.model.Driver;

public class DriverTransformer {
    public static Driver driverRequestToDriver(DriverRequest driverRequest) {
        return Driver.builder()
                .name(driverRequest.getName())
                .age(driverRequest.getAge())
                .emailId(driverRequest.getEmailId())
                .build();
    }

    public static DriverResponse driverToDriverResponse(Driver driver) {
        return DriverResponse.builder()
                .driverId(driver.getDriverId())
                .name(driver.getName())
                .age(driver.getAge())
                .emailId(driver.getEmailId())
                .build();
    }
}

