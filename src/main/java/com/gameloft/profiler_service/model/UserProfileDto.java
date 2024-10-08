package com.gameloft.profiler_service.model;

import com.gameloft.profiler_service.repository.model.Clan;
import com.gameloft.profiler_service.repository.model.Device;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class UserProfileDto {

    private String playerId;
    private String credential;
    private Integer level;
    private String country;
    private String language;
    private Instant birthDate;
    private Gender gender;
    private Integer totalSpent;
    private Integer totalRefund;
    private Integer totalTransactions;
    private Instant lastPurchase;
    private String xp;
    private Instant lastSession;
    private String customField;
    private ClanDto clan;
    private List<DeviceDto> devices;
    private Map<String, String> inventory;
    private List<CampaignDto> activeCampaigns;

    private static final ModelMapper modelMapper = new ModelMapper();

    public void setClan(Clan clan) {
        this.clan = modelMapper.map(clan, ClanDto.class);
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices.stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }
}
