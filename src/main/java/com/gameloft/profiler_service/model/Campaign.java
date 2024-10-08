package com.gameloft.profiler_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class Campaign {

    private String game;
    private String name;
    private double priority;
    private MatchersDto configuration;
    private boolean enabled;
    private Instant startDate;
    private Instant endDate;
    private Instant lastUpdated;
}
