package com.gameloft.profiler_service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class CampaignDto {

    private String game;
    private String name;
    private double priority;
}
