package com.gameloft.profiler_service.specification;

import com.gameloft.profiler_service.model.MatchersDto;
import com.gameloft.profiler_service.model.UserProfileDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserSpecification {

    public boolean matchesCampaign(UserProfileDto user, MatchersDto matchersDto) {

        boolean levelMatches = matchersDto.getLevel().getMin() <= user.getLevel() &&
                matchersDto.getLevel().getMax() >= user.getLevel();

        boolean countryMatches = matchersDto.getHas().getCountries().contains(user.getCountry());

        boolean hasItemsMatch = matchersDto.getHas().getItems().stream()
                .allMatch(item -> user.getInventory().containsKey(item));

        boolean doesNotHaveItemsMatch = matchersDto.getDoesNotHave().getItems().stream()
                .noneMatch(item -> user.getInventory().containsKey(item));

        return levelMatches && countryMatches && hasItemsMatch && doesNotHaveItemsMatch;
    }
}

