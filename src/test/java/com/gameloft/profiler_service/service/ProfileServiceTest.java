package com.gameloft.profiler_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameloft.profiler_service.exception.ProfileException;
import com.gameloft.profiler_service.model.Campaign;
import com.gameloft.profiler_service.model.DoesNotHaveDto;
import com.gameloft.profiler_service.model.HasDto;
import com.gameloft.profiler_service.model.LevelDto;
import com.gameloft.profiler_service.model.MatchersDto;
import com.gameloft.profiler_service.model.UserProfileDto;
import com.gameloft.profiler_service.repository.UsersRepository;
import com.gameloft.profiler_service.repository.model.Clan;
import com.gameloft.profiler_service.repository.model.Device;
import com.gameloft.profiler_service.repository.model.Inventory;
import com.gameloft.profiler_service.repository.model.User;
import com.gameloft.profiler_service.specification.UserSpecification;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    private static final String USER_NOT_FOUND = "User not found";
    private ProfileService profileService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ModelMapper modelMapper = new ModelMapper();
    @Mock
    UsersRepository mockUsersRepository;
    @Mock
    CampaignService mockCampaignService;
    @Mock
    UserSpecification mockUserSpecification;

    @BeforeEach
    void setUp() {
        profileService = new ProfileService(mockUsersRepository, mockCampaignService, objectMapper, modelMapper, mockUserSpecification);
    }

    @Test
    void test_getUserProfile_withSuccess() {
        //given
        String playerId = "97983be2-98b7-11e7-90cf-082e5f28d836";
        User user = generateUser(playerId);

        //when
        Mockito.when(mockUsersRepository.findById(playerId)).thenReturn(Optional.of(user));
        List<Campaign> campaigns = generateCampaigns();
        Mockito.when(mockCampaignService.getCampaigns()).thenReturn(campaigns);
        Mockito.when(mockUserSpecification.matchesCampaign(Mockito.any(UserProfileDto.class), Mockito.any(MatchersDto.class))).thenReturn(true);
        UserProfileDto userProfileDto = profileService.getUserProfile(playerId);

        //then
        Assertions.assertThat(userProfileDto).isNotNull();
        Assertions.assertThat(userProfileDto.getPlayerId()).isEqualTo(playerId);
        Mockito.verify(mockUsersRepository, Mockito.times(1)).findById(playerId);
        Assertions.assertThat(userProfileDto.getInventory().get("coins")).isEqualTo("123");
        Assertions.assertThat(userProfileDto.getInventory().get("cash")).isEqualTo("123");
        Assertions.assertThat(userProfileDto.getInventory().get("item_1")).isEqualTo("1");
        Assertions.assertThat(userProfileDto.getInventory().get("item_34")).isEqualTo("3");
        Assertions.assertThat(userProfileDto.getInventory().get("item_55")).isEqualTo("2");
        Assertions.assertThat(userProfileDto.getActiveCampaigns().size()).isEqualTo(1);
        Assertions.assertThat(userProfileDto.getActiveCampaigns().getFirst().getGame()).isEqualTo(campaigns.getFirst().getGame());
        Assertions.assertThat(userProfileDto.getActiveCampaigns().getFirst().getName()).isEqualTo(campaigns.getFirst().getName());
        Assertions.assertThat(userProfileDto.getActiveCampaigns().getFirst().getPriority()).isEqualTo(campaigns.getFirst().getPriority());
    }

    @Test
    void test_getUserProfile_withSuccessWithoutActiveCampaigns() {
        //given
        String playerId = "97983be2-98b7-11e7-90cf-082e5f28d836";
        User user = generateUser(playerId);

        //when
        Mockito.when(mockUsersRepository.findById(playerId)).thenReturn(Optional.of(user));
        List<Campaign> campaigns = generateCampaigns();
        Mockito.when(mockCampaignService.getCampaigns()).thenReturn(campaigns);
        Mockito.when(mockUserSpecification.matchesCampaign(Mockito.any(UserProfileDto.class), Mockito.any(MatchersDto.class))).thenReturn(false);
        UserProfileDto userProfileDto = profileService.getUserProfile(playerId);

        //then
        Assertions.assertThat(userProfileDto).isNotNull();
        Assertions.assertThat(userProfileDto.getPlayerId()).isEqualTo(playerId);
        Mockito.verify(mockUsersRepository, Mockito.times(1)).findById(playerId);
        Assertions.assertThat(userProfileDto.getInventory().get("coins")).isEqualTo("123");
        Assertions.assertThat(userProfileDto.getInventory().get("cash")).isEqualTo("123");
        Assertions.assertThat(userProfileDto.getInventory().get("item_1")).isEqualTo("1");
        Assertions.assertThat(userProfileDto.getInventory().get("item_34")).isEqualTo("3");
        Assertions.assertThat(userProfileDto.getInventory().get("item_55")).isEqualTo("2");
        Assertions.assertThat(userProfileDto.getActiveCampaigns().size()).isEqualTo(0);
    }

    @Test
    void test_getUserProfile_throwProfileException() {
        //given
        String playerId = "97983be2-98b7-11e7-90cf-082e5f28d836";

        //when
        Mockito.when(mockUsersRepository.findById(playerId)).thenReturn(Optional.empty());

        //then
        Assertions.assertThatThrownBy(() -> profileService.getUserProfile(playerId))
                .isInstanceOf(ProfileException.class)
                .hasMessageContaining(USER_NOT_FOUND);
    }

    @SneakyThrows
    @Test
    void test_getUserProfile_throwJsonProcessingException() {
        //given
        ObjectMapper mockObjectMapper = Mockito.mock(ObjectMapper.class);
        profileService = new ProfileService(mockUsersRepository, mockCampaignService, mockObjectMapper, modelMapper, mockUserSpecification);
        String playerId = "97983be2-98b7-11e7-90cf-082e5f28d836";
        User user = generateUser(playerId);

        //when
        Mockito.when(mockUsersRepository.findById(playerId)).thenReturn(Optional.of(user));
        Mockito.when(mockObjectMapper.readTree(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        //then
        Assertions.assertThatThrownBy(() -> profileService.getUserProfile(playerId))
                .isInstanceOf(ProfileException.class)
                .hasMessageContaining("Could not parse inventory json because");
    }

    @SneakyThrows
    @Test
    void test_getUserProfile_throwMappingException() {
        //given
        String playerId = "97983be2-98b7-11e7-90cf-082e5f28d836";
        User user = generateUser(playerId);
        user.setClan(null);

        //when
        Mockito.when(mockUsersRepository.findById(playerId)).thenReturn(Optional.of(user));

        //then
        Assertions.assertThatThrownBy(() -> profileService.getUserProfile(playerId))
                .isInstanceOf(MappingException.class)
                .hasMessageContaining("ModelMapper mapping errors");
    }

    private static User generateUser(String playerId) {

        User user = new User();
        user.setId(playerId);
        user.setCredential("apple_credential");
        user.setLevel(3);
        user.setCountry("CA");
        user.setLanguage("fr");
        user.setGender("male");
        Device device = new Device();
        device.setId(123456);
        device.setModel("apple iphone 11");
        device.setCarrier("vodafone");
        device.setFirmware("123");
        user.setDevices(List.of(device));
        Inventory inventory = new Inventory();
        inventory.setCoins(123);
        inventory.setCash(123);
        inventory.setItems("""
                [{"item_1" : 1}, {"item_34" : 3}, {"item_55" : 2}]
                """);
        user.setInventory(inventory);
        Clan clan = new Clan();
        clan.setId(123456);
        clan.setName("Hello world clan");
        user.setClan(clan);
        return user;
    }

    private List<Campaign> generateCampaigns() {

        List<Campaign> campaigns = new ArrayList<>();

        HasDto hasDto = new HasDto(Arrays.asList("US", "RO", "CA"), List.of("item_1"));
        DoesNotHaveDto doesNotHaveDto = new DoesNotHaveDto(List.of("item_4"));
        LevelDto levelDto = new LevelDto(1, 3);
        MatchersDto matchersDto = new MatchersDto(levelDto, hasDto, doesNotHaveDto);

        Campaign campaign = new Campaign("mygame", "mycampaign", 10.5,
                matchersDto, true,
                Instant.ofEpochSecond(1728046142),  // 2024-09-04T00:29:02Z
                Instant.ofEpochSecond(1730724542),  // 2024-10-04T00:29:02Z
                Instant.ofEpochSecond(1699102142)); // 2023-11-04T00:29:02Z
        campaigns.add(campaign);

        return campaigns;
    }

}
