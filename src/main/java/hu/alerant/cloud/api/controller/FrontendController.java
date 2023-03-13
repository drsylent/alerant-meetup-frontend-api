package hu.alerant.cloud.api.controller;

import hu.alerant.cloud.api.dto.FrontendRequest;
import hu.alerant.cloud.api.dto.FrontendResponse;
import hu.alerant.cloud.service.FrontendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/frontend")
@Slf4j
public class FrontendController {

    private final FrontendService service;

    public FrontendController(FrontendService service) {
        this.service = service;
    }

    @GetMapping(value = "/test",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FrontendResponse test(@RequestParam int leftNumber, @RequestParam int rightNumber) {
        return localCalculation("test", new FrontendRequest("", leftNumber, rightNumber));
    }

    @PostMapping(value = "/local",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FrontendResponse local(@RequestBody FrontendRequest request) {
        return localCalculation("local", request);
    }

    @PostMapping(value = "/store",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FrontendResponse store(@RequestBody FrontendRequest request) {
        logRequest("store", request);
        int result = service.calculate(request.leftNumber(), request.rightNumber());
        service.storeNewMessage(request.message());
        List<String> recentMessages = service.retrieveRecentMessages();
        FrontendResponse response = new FrontendResponse(recentMessages, result);
        logResponse("store", response);
        return response;
    }

    private FrontendResponse localCalculation(String endpointName, FrontendRequest request) {
        logRequest(endpointName, request);
        int result = service.calculate(request.leftNumber(), request.rightNumber());
        List<String> recentMessages = Collections.singletonList(request.message());
        FrontendResponse response = new FrontendResponse(recentMessages, result);
        logResponse(endpointName, response);
        return response;
    }

    private static void logRequest(String endpointName, FrontendRequest request) {
        log.info("Request arrived on /training/{} endpoint with message: {}, leftNumber: {}, rightNumber: {}",
                endpointName, request.message(), request.leftNumber(), request.rightNumber());
    }

    private static void logResponse(String endpointName, FrontendResponse response) {
        log.info("Sending back response on /training/{} endpoint with data: number of recentMessages {}, result: {}",
                endpointName, response.recentMessages().size(), response.result());
    }
}
