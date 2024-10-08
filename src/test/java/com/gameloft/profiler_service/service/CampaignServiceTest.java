package com.gameloft.profiler_service.service;

import com.gameloft.profiler_service.model.Campaign;
import com.gameloft.profiler_service.model.DoesNotHaveDto;
import com.gameloft.profiler_service.model.HasDto;
import com.gameloft.profiler_service.model.LevelDto;
import com.gameloft.profiler_service.model.MatchersDto;
import com.gameloft.profiler_service.service.client.CampaignClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    private CampaignService campaignService;
    @Mock
    private CampaignClient mockCampaignClient;

    @BeforeEach
    void setUp() {
        campaignService = new CampaignService(mockCampaignClient);
    }

    @Test
    void test_getCampaigns_withTwoValidCampaigns() {
        //given
        Mockito.when(mockCampaignClient.getMockRestCallResponse()).thenReturn(generateCampaigns());

        //when
        List<Campaign> campaigns = campaignService.getCampaigns();

        //then
        Assertions.assertThat(campaigns).hasSize(2);
        Assertions.assertThat(campaigns.getFirst().getGame()).isEqualTo("mygame");
        Assertions.assertThat(campaigns.getFirst().getName()).isEqualTo("mycampaign");

        Assertions.assertThat(campaigns.getLast().getGame()).isEqualTo("mygame");
        Assertions.assertThat(campaigns.getLast().getName()).isEqualTo("campaign3");
    }

    @Test
    void test_getCampaigns_withoutValidCampaigns() {
        //given
        Mockito.when(mockCampaignClient.getMockRestCallResponse()).thenReturn(generateExpiredCampaigns());

        //when
        List<Campaign> campaigns = campaignService.getCampaigns();

        //then
        Assertions.assertThat(campaigns).isEmpty();
    }

    private List<Campaign> generateCampaigns() {

        List<Campaign> campaigns = new ArrayList<>();

        HasDto hasDto = new HasDto(Arrays.asList("US", "RO", "CA"), List.of("item_1"));
        DoesNotHaveDto doesNotHaveDto = new DoesNotHaveDto(List.of("item_4"));
        LevelDto levelDto = new LevelDto(1, 3);
        MatchersDto matchersDto = new MatchersDto(levelDto, hasDto, doesNotHaveDto);

        Campaign campaign1 = new Campaign("mygame", "mycampaign", 10.5,
                matchersDto, true,
                Instant.ofEpochSecond(1728046142),  // 2024-09-04T00:29:02Z
                Instant.ofEpochSecond(1730724542),  // 2024-10-04T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign1);

        Campaign campaign2 = new Campaign("mygame", "campaign2", 8.0,
                matchersDto, true,
                Instant.ofEpochSecond(1729046142),  // 2024-09-12T00:29:02Z
                Instant.ofEpochSecond(1731724542),  // 2024-10-12T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign2);

        Campaign campaign3 = new Campaign("mygame", "campaign3", 9.5,
                matchersDto, true,
                Instant.ofEpochSecond(1726046142),  // 2024-09-11T00:29:02Z
                Instant.ofEpochSecond(1728724542),  // 2024-10-12T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign3);

        Campaign campaign4 = new Campaign("mygame", "campaign4", 7.0,
                matchersDto, true,
                Instant.ofEpochSecond(1725046142),  // 2024-08-01T00:29:02Z
                Instant.ofEpochSecond(1727724542),  // 2024-08-30T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign4);

        Campaign campaign5 = new Campaign("mygame", "campaign5", 9.0,
                matchersDto, true,
                Instant.ofEpochSecond(1724046142),  // 2024-07-20T00:29:02Z
                Instant.ofEpochSecond(1726724542),  // 2024-08-18T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign5);

        Campaign campaign6 = new Campaign("mygame", "campaign6", 6.5,
                matchersDto, true,
                Instant.ofEpochSecond(1723046142),  // 2024-07-08T00:29:02Z
                Instant.ofEpochSecond(1725724542),  // 2024-08-06T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign6);

        return campaigns;
    }

    private List<Campaign> generateExpiredCampaigns() {

        List<Campaign> campaigns = new ArrayList<>();

        HasDto hasDto = new HasDto(Arrays.asList("US", "RO", "CA"), List.of("item_1"));
        DoesNotHaveDto doesNotHaveDto = new DoesNotHaveDto(List.of("item_4"));
        LevelDto levelDto = new LevelDto(1, 3);
        MatchersDto matchersDto = new MatchersDto(levelDto, hasDto, doesNotHaveDto);

        Campaign campaign2 = new Campaign("mygame", "campaign2", 8.0,
                matchersDto, true,
                Instant.ofEpochSecond(1729046142),  // 2024-09-12T00:29:02Z
                Instant.ofEpochSecond(1731724542),  // 2024-10-12T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign2);

        Campaign campaign4 = new Campaign("mygame", "campaign4", 7.0,
                matchersDto, true,
                Instant.ofEpochSecond(1725046142),  // 2024-08-01T00:29:02Z
                Instant.ofEpochSecond(1727724542),  // 2024-08-30T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign4);

        Campaign campaign5 = new Campaign("mygame", "campaign5", 9.0,
                matchersDto, true,
                Instant.ofEpochSecond(1724046142),  // 2024-07-20T00:29:02Z
                Instant.ofEpochSecond(1726724542),  // 2024-08-18T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign5);

        Campaign campaign6 = new Campaign("mygame", "campaign6", 6.5,
                matchersDto, true,
                Instant.ofEpochSecond(1723046142),  // 2024-07-08T00:29:02Z
                Instant.ofEpochSecond(1725724542),  // 2024-08-06T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign6);

        return campaigns;
    }


}
