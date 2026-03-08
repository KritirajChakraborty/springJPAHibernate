package com.Kritiraj.SpringJPAHibernate.dto.request;

import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerRequest {

    private String name;
    private int age;
    private String email;
    private Gender gender;
}
