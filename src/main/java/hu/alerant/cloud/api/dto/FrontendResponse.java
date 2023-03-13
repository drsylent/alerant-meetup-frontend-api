package hu.alerant.cloud.api.dto;

import java.util.List;

public record FrontendResponse(List<String> recentMessages, int result) {
}
