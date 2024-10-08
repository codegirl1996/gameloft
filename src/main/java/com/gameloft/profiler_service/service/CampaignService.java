package com.gameloft.profiler_service.service;

import com.gameloft.profiler_service.model.Campaign;
import com.gameloft.profiler_service.service.client.CampaignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignClient campaignClient;

    public List<Campaign> getCampaigns() {
        List<Campaign> campaigns = campaignClient.getMockRestCallResponse();
        Instant now = Instant.now();

        return campaigns.stream()
                .filter(campaign -> now.isAfter(campaign.getStartDate()) && now.isBefore(campaign.getEndDate()))
                .toList();
    }
}
