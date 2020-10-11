package com.pplenty.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yusik on 2020/10/11.
 */
@DisplayName("돈 공유 테스트")
@AutoConfigureMockMvc
@SpringBootTest
class SharingControllerTest {

    private static final long USER_ID = 314;
    private static final long OTHER_ID = 315;
    private static final long ROOM_ID = 333;

    @Autowired
    private MockMvc mvc;

    @DisplayName("뿌리기 테스트")
    @Test
    void shareMoney() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/sharing")
                .header("X-USER-ID", USER_ID)
                .header("X-ROOM-ID", ROOM_ID)
                .content("{\n" +
                        "  \"amount\": 10000,\n" +
                        "  \"numberOfTarget\": 3\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("받기 테스트")
    @Test
    void takeMoney() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/sharing/{token}", "ABC")
                .header("X-USER-ID", OTHER_ID)
                .header("X-ROOM-ID", ROOM_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    @DisplayName("조회 테스트")
    @Test
    void takenStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/sharing/{token}", "ABC")
                .header("X-USER-ID", USER_ID)
                .header("X-ROOM-ID", ROOM_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}