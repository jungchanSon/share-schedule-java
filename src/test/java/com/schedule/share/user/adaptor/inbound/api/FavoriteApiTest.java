package com.schedule.share.user.adaptor.inbound.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schedule.share.common.util.JwtUtil;
import com.schedule.share.user.adaptor.inbound.api.dto.FavoriteRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.mapper.FavoriteDTOMapper;
import com.schedule.share.user.adaptor.inbound.api.mapper.FavoriteDTOMapperImpl;
import com.schedule.share.user.application.port.inbound.FavoriteCommand;
import com.schedule.share.user.application.port.inbound.FavoriteQuery;
import com.schedule.share.user.application.service.user.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FavoriteApi.class)
@Import({JwtUtil.class, FavoriteDTOMapperImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavoriteApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteCommand favoriteCommand;

    @MockBean
    private FavoriteQuery favoriteQuery;

    @Autowired
    private FavoriteDTOMapper favoriteDTOMapper;

    @MockBean
    private FavoriteService favoriteService;

    @Autowired
    private JwtUtil jwtUtil;

    private String accessToken;
    private long userId;

    private final long scheduleId = 110;
    private final long calendarId = 120;
    private final boolean isAllDay = true;
    private String scheduleStartDatetime;
    private String scheduleEndDatetime;

    @BeforeEach
    void initAccessToken() {
        accessToken = jwtUtil.generateAccessToken(130);
        userId = jwtUtil.getUserId(accessToken);
        scheduleStartDatetime = LocalDateTime.now().toString();
        scheduleEndDatetime = LocalDateTime.now().plusHours(3).toString();
    }

    @Test
    void create() throws Exception {

        //given
        FavoriteRequestDTO.Favorite request = FavoriteRequestDTO.Favorite.builder()
                .scheduleId(scheduleId)
                .calendarId(calendarId)
                .isAllday(isAllDay)
                .scheduleStartDatetime(LocalDateTime.parse(scheduleStartDatetime))
                .scheduleEndDatetime(LocalDateTime.parse(scheduleEndDatetime))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        String body = mapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/favorites")
                        .header("access_token", accessToken)
                        .header("content-type", "application/json")
                        .content(body))
                .andExpect(status().isOk());
        
        then(favoriteCommand).should()
                .create(userId, favoriteDTOMapper.toVo(request));
    }

    @Test
    void getFavorite() throws Exception {
        long favoriteId = 100L;

        //when
        mockMvc.perform(get("/favorites/{favoriteId}", favoriteId)
                        .header("access_token", accessToken))
                .andExpect(status().isOk());

        then(favoriteQuery).should()
                .get(userId, favoriteId);
    }

    @Test
    void getList() throws Exception {
        //when
        mockMvc.perform(get("/favorites")
                        .header("access_token", accessToken))
                .andExpect(status().isOk());

        then(favoriteQuery).should()
                .list(userId);
    }

    @Test
    void test_delete() throws Exception {
        long favoriteId = 100L;

        //when
        mockMvc.perform(delete("/favorites/{id}", 100L)
                        .header("access_token", accessToken))
                .andExpect(status().isOk());

        then(favoriteCommand).should()
                .delete(userId, favoriteId);
    }

    @Test
    void bulkDelete() throws Exception {
        FavoriteRequestDTO.BulkDelete request = FavoriteRequestDTO.BulkDelete
                .builder()
                .list(List.of(1L, 2L, 3L))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        String body = mapper.writeValueAsString(request);

        mockMvc.perform(delete("/favorites")
                        .header("access_token", accessToken)
                        .header("content-type", "application/json")
                        .content(body))
                .andExpect(status().isOk());

        then(favoriteCommand).should()
                .delete(userId, request.list());
    }
}