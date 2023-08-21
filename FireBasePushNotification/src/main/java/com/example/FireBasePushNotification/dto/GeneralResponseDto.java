package com.example.FireBasePushNotification.dto;


import com.example.FireBasePushNotification.enums.ResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponseDto<T> implements Serializable {

    private ResponseStatusEnum status;
    private String message;
    private transient T data;

    public GeneralResponseDto(ResponseStatusEnum status, String message) {
        this.status = status;
        this.message = message;
    }

    public GeneralResponseDto(ResponseStatusEnum status) {
        this.status = status;
    }


}
