package com.gameloft.profiler_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameloft.profiler_service.exception.CampaignException;
import com.gameloft.profiler_service.exception.ProfileException;
import com.gameloft.profiler_service.model.CampaignDto;
import com.gameloft.profiler_service.model.MatchersDto;
import com.gameloft.profiler_service.model.UserProfileDto;
import com.gameloft.profiler_service.repository.UsersRepository;
import com.gameloft.profiler_service.model.Campaign;
import com.gameloft.profiler_service.repository.model.Inventory;
import com.gameloft.profiler_service.repository.model.User;
import com.gameloft.profiler_service.specification.UserSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gameloft.profiler_service.model.Constants.CASH;
import static com.gameloft.profiler_service.model.Constants.COINS;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final String USER_NOT_FOUND = "User not found";
    private final UsersRepository usersRepository;
    private final CampaignService campaignService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final UserSpecification userSpecification;

    @Transactional
    public UserProfileDto getUserProfile(String playerId) {

        return usersRepository.findById(playerId)
                .map(user -> {
                    UserProfileDto userProfileDto = getUserProfileDto(user);
                    setInventory(userProfileDto, user.getInventory());
                    setActiveCampaigns(userProfileDto);
                    return userProfileDto;
                })
                .orElseThrow(() -> new ProfileException(USER_NOT_FOUND));
    }

    private void setInventory(UserProfileDto userProfileDto, Inventory inventory) {

        try {
            JsonNode node = objectMapper.readTree(inventory.getItems());
            Map<String, String> map = new HashMap<>();
            map.put(CASH, inventory.getCash().toString());
            map.put(COINS, inventory.getCoins().toString());
            for (JsonNode elementNode : node) {
                elementNode.fieldNames().forEachRemaining(field -> {
                    String value = elementNode.get(field).asText();
                    map.put(field, value);
                });
            }
            userProfileDto.setInventory(map);
        } catch (JsonProcessingException e) {
            throw new ProfileException(String.format("Could not parse inventory json because: %s", e.getMessage()));
        }
    }

    private void setActiveCampaigns(UserProfileDto userProfileDto) {

        List<Campaign> campaigns = campaignService.getCampaigns();
        List<CampaignDto> activeCampaigns = new ArrayList<>();
        campaigns.forEach(campaign -> {
            MatchersDto matchersDto = campaign.getConfiguration();
            if (userSpecification.matchesCampaign(userProfileDto, matchersDto)) {
                activeCampaigns.add(modelMapper.map(campaign, CampaignDto.class));
            }
        });
        userProfileDto.setActiveCampaigns(activeCampaigns);
    }

    private UserProfileDto getUserProfileDto(User user) {

        modelMapper.typeMap(User.class, UserProfileDto.class)
                .addMappings(modelMapper -> {
                    modelMapper.map(User::getId, UserProfileDto::setPlayerId);
                    modelMapper.map(User::getClan, UserProfileDto::setClan);
                    modelMapper.map(User::getDevices, UserProfileDto::setDevices);
                });

        return modelMapper.map(user, UserProfileDto.class);
    }

}
