package com.caiodorn.imgarena.golftournamentapi.rest;

import com.caiodorn.imgarena.golftournamentapi.repository.GolfTournamentRepository;
import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import com.caiodorn.imgarena.golftournamentapi.service.mapper.LocalDateTimeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GolfTournamentControllerITest {

    private static final String RESOURCE_PATH = "/v1/golf-tournament";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GolfTournamentRepository repository;

    @Autowired
    private LocalDateTimeMapper localDateTimeMapper;

    @BeforeEach
    void cleanup() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("sourceId=A Tests")
    class SourceIdATests {
        @Test
        void shouldReturn201_whenPOST_givenValidRequest() throws Exception {
            String requestBody = "{\n" +
                    "\t\"tournamentId\": \"174638\",\n" +
                    "\t\"tournamentName\": \"Women's Open Championship\",\n" +
                    "\t\"forecast\": \"fair\",\n" +
                    "\t\"courseName\": \"Sunnydale Golf Course\",\n" +
                    "\t\"countryCode\": \"GB\",\n" +
                    "\t\"startDate\": \"09/07/21\",\n" +
                    "\t\"endDate\": \"13/07/21\",\n" +
                    "\t\"roundCount\": \"4\"\n" +
                    "}";

            mockMvc.perform(post(RESOURCE_PATH)
                    .header("sourceId", "A")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$").doesNotExist());

            GolfTournamentEntity expected = GolfTournamentEntity.builder()
                    .tournamentName("Women's Open Championship")
                    .externalId("174638")
                    .country("GB")
                    .courseName("Sunnydale Golf Course")
                    .startDateTime(LocalDateTime.of(2021, 7, 9, 0, 0 ,0))
                    .endDateTime(LocalDateTime.of(2021, 7, 13, 0, 0 ,0))
                    .forecast("fair")
                    .numberOfRounds(4)
                    .sourceId(SourceId.A)
                    .build();

            assertPersisted(expected, SourceId.A);
        }

        @Test
        void shouldReturn400_whenPOST_givenMissingRequiredFieldValues() throws Exception {
            String requestBody = "{\n" +
                    "\t\"tournamentId\": null,\n" +
                    "\t\"tournamentName\": null,\n" +
                    "\t\"forecast\": null,\n" +
                    "\t\"courseName\": null,\n" +
                    "\t\"countryCode\": null,\n" +
                    "\t\"startDate\": \"\",\n" +
                    "\t\"endDate\": \"\",\n" +
                    "\t\"roundCount\": null" +
                    "}";

            mockMvc.perform(post(RESOURCE_PATH)
                    .header("sourceId", "A")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.violations", hasSize(8)));
        }

        @Test
        void shouldReturn400_whenPOST_givenMissingRequiredFields() throws Exception {
            String requestBody = "{\"tournamentId\": \"174638\"}";

            mockMvc.perform(post(RESOURCE_PATH)
                    .header("sourceId", "A")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.violations", hasSize(7)));
        }
    }

    @Nested
    @DisplayName("sourceId=B Tests")
    class SourceIdBTests {
        @Test
        void shouldReturn201_whenPOST_givenValidRequest() throws Exception {
            String requestBody = "{\n" +
                    "    \"tournamentUUID\":\"southWestInvitational\",\n" +
                    "    \"golfCourse\":\"Happy Days Golf Club\",\n" +
                    "    \"competitionName\":\"South West Invitational\",\n" +
                    "    \"hostCountry\":\"United States Of America\",\n" +
                    "    \"epochStart\":\"1638349200\",\n" +
                    "    \"epochFinish\":\"1638468000\",\n" +
                    "    \"rounds\":\"2\",\n" +
                    "    \"playerCount\":\"35\"\n" +
                    "}";

            mockMvc.perform(post(RESOURCE_PATH)
                    .header("sourceId", "B")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$").doesNotExist());

            GolfTournamentEntity expected = GolfTournamentEntity.builder()
                    .tournamentName("South West Invitational")
                    .externalId("southWestInvitational")
                    .country("United States Of America")
                    .courseName("Happy Days Golf Club")
                    .startDateTime(localDateTimeMapper.fromSeconds(1638349200L))
                    .endDateTime(localDateTimeMapper.fromSeconds(1638468000L))
                    .numberOfRounds(2)
                    .playerCount(35)
                    .sourceId(SourceId.B)
                    .build();

            assertPersisted(expected, SourceId.B);
        }

        @Test
        void shouldReturn400_whenPOST_givenMissingRequiredFieldValues() throws Exception {
            String requestBody = "{\n" +
                    "    \"tournamentUUID\": null,\n" +
                    "    \"golfCourse\": null,\n" +
                    "    \"competitionName\": null,\n" +
                    "    \"hostCountry\": null,\n" +
                    "    \"epochStart\": null,\n" +
                    "    \"epochFinish\": null,\n" +
                    "    \"rounds\": null,\n" +
                    "    \"playerCount\": null\n" +
                    "}";

            mockMvc.perform(post(RESOURCE_PATH)
                    .header("sourceId", "B")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.violations", hasSize(8)));
        }

        @Test
        void shouldReturn400_whenPOST_givenMissingRequiredFields() throws Exception {
            String requestBody = "{ \"playerCount\": \"5\"}";

            mockMvc.perform(post(RESOURCE_PATH)
                    .header("sourceId", "B")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.violations", hasSize(7)));
        }
    }

    void assertPersisted(GolfTournamentEntity expected, SourceId sourceId) {
        repository.findByExternalIdAndSourceId(expected.getExternalId(), expected.getSourceId()).ifPresentOrElse(actual -> {
            assertNotNull(actual);
            assertNotNull(actual.getId());
            assertEquals(expected.getCountry(), actual.getCountry());
            assertEquals(expected.getCourseName(), actual.getCourseName());
            assertEquals(expected.getTournamentName(), actual.getTournamentName());
            assertEquals(expected.getNumberOfRounds(), actual.getNumberOfRounds());
            assertEquals(expected.getStartDateTime(), actual.getStartDateTime());
            assertEquals(expected.getEndDateTime(), actual.getEndDateTime());

            if (SourceId.A.equals(sourceId)) {
                assertNull(actual.getPlayerCount());
                assertEquals(expected.getForecast(), actual.getForecast());
            } else if (SourceId.B.equals(sourceId)) {
                assertNull(actual.getForecast());
                assertEquals(expected.getPlayerCount(), actual.getPlayerCount());
            }
        }, () -> fail("Should have returned persisted object."));

    }

}
