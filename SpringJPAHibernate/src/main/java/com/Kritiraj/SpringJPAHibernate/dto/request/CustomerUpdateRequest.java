package com.Kritiraj.SpringJPAHibernate.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CustomerUpdateRequest {


    private String name;
    private Integer age;
    @NotNull // this makes a field mandatory but we need to add @Valid to request api
    private String email;
}
