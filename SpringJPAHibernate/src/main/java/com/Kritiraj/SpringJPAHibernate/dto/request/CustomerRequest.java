package com.Kritiraj.SpringJPAHibernate.dto.request;

import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CustomerRequest {

    private String name;
    private int age;
    private String email;
    private Gender gender;
}
