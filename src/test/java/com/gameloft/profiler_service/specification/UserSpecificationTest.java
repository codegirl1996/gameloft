package com.gameloft.profiler_service.specification;

import com.gameloft.profiler_service.model.DoesNotHaveDto;
import com.gameloft.profiler_service.model.HasDto;
import com.gameloft.profiler_service.model.LevelDto;
import com.gameloft.profiler_service.model.MatchersDto;
import com.gameloft.profiler_service.model.UserProfileDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class UserSpecificationTest {

    private UserSpecification userSpecification;

    @BeforeEach
    void setUp() {
        userSpecification = new UserSpecification();
    }

    @Test
    void test_matchesCampaign_allConditionsAreValid() {
        //given

        //when
        boolean isMatchesCampaign = userSpecification.matchesCampaign(generateUserProfileDto(), generateMatchersDto());

        //then
        Assertions.assertThat(isMatchesCampaign).isTrue();
    }

    @Test
    void test_matchesCampaign_withFourLevelMatchesFalse() {
        //given
        UserProfileDto userProfileDto = generateUserProfileDto();
        userProfileDto.setLevel(4);

        //when
        boolean isMatchesCampaign = userSpecification.matchesCampaign(userProfileDto, generateMatchersDto());

        //then
        Assertions.assertThat(isMatchesCampaign).isFalse();
    }

    @Test
    void test_matchesCampaign_withLevelZeroMatchesFalse() {
        //given
        UserProfileDto userProfileDto = generateUserProfileDto();
        userProfileDto.setLevel(0);

        //when
        boolean isMatchesCampaign = userSpecification.matchesCampaign(userProfileDto, generateMatchersDto());

        //then
        Assertions.assertThat(isMatchesCampaign).isFalse();
    }

    @Test
    void test_matchesCampaign_withCountryMatchesFalse() {
        //given
        UserProfileDto userProfileDto = generateUserProfileDto();
        userProfileDto.setCountry("UK");

        //when
        boolean isMatchesCampaign = userSpecification.matchesCampaign(userProfileDto, generateMatchersDto());

        //then
        Assertions.assertThat(isMatchesCampaign).isFalse();
    }

    @Test
    void test_matchesCampaign_withHasItemsMatchFalse() {
        //given
        UserProfileDto userProfileDto = generateUserProfileDto();
        Map<String, String> map = new HashMap<>();
        map.put("coins", "123");
        map.put("cash", "123");
        map.put("item_5", "1");
        userProfileDto.setInventory(map);

        //when
        boolean isMatchesCampaign = userSpecification.matchesCampaign(userProfileDto, generateMatchersDto());

        //then
        Assertions.assertThat(isMatchesCampaign).isFalse();
    }

    @Test
    void test_matchesCampaign_withDoesNotHaveItemsMatchFalse() {
        //given
        UserProfileDto userProfileDto = generateUserProfileDto();
        Map<String, String> map = new HashMap<>();
        map.put("coins", "123");
        map.put("cash", "123");
        map.put("item_4", "1");
        map.put("item_1", "2");
        userProfileDto.setInventory(map);

        //when
        boolean isMatchesCampaign = userSpecification.matchesCampaign(userProfileDto, generateMatchersDto());

        //then
        Assertions.assertThat(isMatchesCampaign).isFalse();
    }

    private UserProfileDto generateUserProfileDto() {

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setLevel(3);
        userProfileDto.setCountry("CA");
        Map<String, String> map = new HashMap<>();
        map.put("coins", "123");
        map.put("cash", "123");
        map.put("item_1", "1");
        map.put("item_34", "3");
        map.put("item_55", "2");
        userProfileDto.setInventory(map);
        return userProfileDto;
    }

    private MatchersDto generateMatchersDto() {

        HasDto hasDto = new HasDto(Arrays.asList("US", "RO", "CA"), List.of("item_1"));
        DoesNotHaveDto doesNotHaveDto = new DoesNotHaveDto(//Arrays.asList("IT"),
                List.of("item_4"));
        LevelDto levelDto = new LevelDto(1, 3);
        return new MatchersDto(levelDto, hasDto, doesNotHaveDto);
    }


}
