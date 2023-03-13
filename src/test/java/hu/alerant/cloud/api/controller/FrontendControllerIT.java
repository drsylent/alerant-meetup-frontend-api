package hu.alerant.cloud.api.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import hu.alerant.cloud.call.api.MessageApiMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.util.StreamUtils.copyToString;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MessageApiMock.class})
class FrontendControllerIT {

    @Autowired
    private WireMockServer messageApi;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws IOException {
        MessageApiMock.setupMockResponses(messageApi);
    }

    @Test
    void test() throws Exception {
        mockMvc.perform(get("/frontend/test").queryParam("leftNumber", "1").queryParam("rightNumber", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recentMessages[0]").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1+2));
    }

    @Test
    void local() throws Exception {
        mockMvc.perform(post("/frontend/local").contentType(MediaType.APPLICATION_JSON_VALUE).content(loadRequestJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recentMessages[0]").value("test5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1+2));
    }

    @Test
    void store() throws Exception {
        mockMvc.perform(post("/frontend/store").contentType(MediaType.APPLICATION_JSON_VALUE).content(loadRequestJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recentMessages[0]").value("test5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recentMessages[1]").value("test4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recentMessages[2]").value("test3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recentMessages[3]").value("test2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recentMessages[4]").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1+2));
    }

    private String loadRequestJson() throws IOException {
        return copyToString(
                MessageApiMock.class.getClassLoader().getResourceAsStream("request/example-request.json"),
                defaultCharset());
    }
}