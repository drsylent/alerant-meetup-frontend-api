package hu.alerant.cloud.service;

import hu.alerant.cloud.call.api.BackendApi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

class FrontendServiceTest {

    private static final BackendApi api = Mockito.mock(BackendApi.class);
    private static final FrontendService service = new FrontendService(api);

    @Test
    void calculate() {
        int result = service.calculate(1, 1);

        MatcherAssert.assertThat(result, Matchers.is(0));
    }

    @Test
    void createNewMessage() {
        String message = "message";

        service.storeNewMessage(message);

        Mockito.verify(api, Mockito.atLeastOnce()).newMessageArrived(message);
    }

    @Test
    void retrieveRecentMessages() {
        String message = "message";
        Mockito.when(api.getRecentMessages()).thenReturn(Collections.singletonList(message));

        List<String> result = service.retrieveRecentMessages();

        MatcherAssert.assertThat(result, Matchers.contains(message));
    }
}