package com.gameloft.profiler_service.service.client;

import com.gameloft.profiler_service.exception.CampaignException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ExtendWith(MockitoExtension.class)
class CampaignClientTest {

    private CampaignClient campaignClient;
    @Mock
    private WebClient mockWebClient;

    @BeforeEach
    void setUp() {
        campaignClient = new CampaignClient(mockWebClient);
    }

    @Test
    void test_getMockRestCallResponse_throwCampaignException() {
        //given

        //when
        Mockito.when(mockWebClient.get()).thenThrow(WebClientResponseException.class);

        //then
        Assertions.assertThatThrownBy(() -> campaignClient.getMockRestCallResponse())
                .isInstanceOf(CampaignException.class)
                .hasMessageContaining("Could not retrieve the campaigns");
    }

}
