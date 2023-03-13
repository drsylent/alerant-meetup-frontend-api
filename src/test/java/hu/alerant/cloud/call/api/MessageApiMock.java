package hu.alerant.cloud.call.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

@TestConfiguration
public class MessageApiMock {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockServer() {
        return new WireMockServer(18080);
    }

    public static void setupMockResponses(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/message"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.CREATED.value())));

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/message/recent"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        MessageApiMock.class.getClassLoader().getResourceAsStream("response/mock-message.json"),
                                        defaultCharset()))));
    }
}
