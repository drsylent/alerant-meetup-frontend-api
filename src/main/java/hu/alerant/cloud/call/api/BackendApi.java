package hu.alerant.cloud.call.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "message", url = "${api.message.url}", path = "/message")
public interface BackendApi {

    @GetMapping(value = "/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> getRecentMessages();

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    void newMessageArrived(@RequestBody String message);
}
