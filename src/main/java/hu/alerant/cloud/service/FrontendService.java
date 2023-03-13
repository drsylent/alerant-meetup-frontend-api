package hu.alerant.cloud.service;

import hu.alerant.cloud.call.api.BackendApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FrontendService {

    private final BackendApi api;

    public FrontendService(BackendApi api) {
        this.api = api;
    }

    public int calculate(int leftNumber, int rightNumber) {
        log.info("Calculating: {} - {}", leftNumber, rightNumber);
        return leftNumber - rightNumber;
    }

    public void storeNewMessage(String message) {
        log.info("Creating new message on API: {}", message);
        api.newMessageArrived(message);
    }

    public List<String> retrieveRecentMessages() {
        log.info("Retrieving recent recentMessages from API");
        return api.getRecentMessages();
    }
}
