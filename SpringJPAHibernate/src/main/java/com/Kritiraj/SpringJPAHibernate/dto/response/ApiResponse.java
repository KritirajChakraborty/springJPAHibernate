package com.Kritiraj.SpringJPAHibernate.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T>{

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success (T data, int status, String message) {

        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .status(status)
                .data(data)
                .build();
    }
}
