package com.gameloft.profiler_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoesNotHaveDto {

//    TODO:
//    @JsonProperty("country")
//    private List<String> countries;
    private List<String> items;
}
