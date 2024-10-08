package com.gameloft.profiler_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchersDto {

    private LevelDto level;
    private HasDto has;
    @JsonProperty("does_not_have")
    private DoesNotHaveDto doesNotHave;

}
