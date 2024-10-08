package com.gameloft.profiler_service.controller;

import com.gameloft.profiler_service.model.UserProfileDto;
import com.gameloft.profiler_service.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@Tag(name = "Profiler API")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Get client configuration with active campaigns")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get client configuration with success"),
            @ApiResponse(responseCode = "400", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Parse campaign exception"),
    })
    @GetMapping(path = "/get_client_config/{player_id}")
    public ResponseEntity<UserProfileDto> getClientConfig(@PathVariable("player_id") String playerId) {

        return ResponseEntity.ok(profileService.getUserProfile(playerId));
    }
}
