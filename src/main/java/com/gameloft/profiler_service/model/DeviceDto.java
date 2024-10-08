package com.gameloft.profiler_service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DeviceDto {

    private String model;
    private String carrier;
    private String firmware;
}
