package com.gameloft.profiler_service.service.client;

import com.gameloft.profiler_service.exception.CampaignException;
import com.gameloft.profiler_service.model.Campaign;
import com.gameloft.profiler_service.model.DoesNotHaveDto;
import com.gameloft.profiler_service.model.HasDto;
import com.gameloft.profiler_service.model.LevelDto;
import com.gameloft.profiler_service.model.MatchersDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class CampaignClient {

    private static final String COULD_NOT_RETRIEVE_THE_CAMPAIGNS = "Could not retrieve the campaigns";
    private final WebClient webClient;

    public CampaignClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Campaign> getMockRestCallResponse() {

        List<Campaign> campaigns = new ArrayList<>();
        try {
            //TODO: if the rest call is failing we should throw exception (random API is called just for testing purpose)
            webClient.get()
                    .uri("/users/1")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            HasDto hasDto = new HasDto(Arrays.asList("US", "RO", "CA"), List.of("item_1"));
            DoesNotHaveDto doesNotHaveDto = new DoesNotHaveDto(
                    //List.of("IT"),
                    List.of("item_4"));
            LevelDto levelDto = new LevelDto(1, 3);
            MatchersDto matchersDto = new MatchersDto(levelDto, hasDto, doesNotHaveDto);

            Campaign campaign = new Campaign("mygame", "mycampaign", 10.5,
                    matchersDto,
                    true,
                    Instant.ofEpochSecond(1728046142),
                    Instant.ofEpochSecond(1730724542), Instant.ofEpochSecond(1699102142));
            campaigns.add(campaign);
        } catch (Exception e) {
            throw new CampaignException(COULD_NOT_RETRIEVE_THE_CAMPAIGNS);
        }

        return campaigns;
    }

}
