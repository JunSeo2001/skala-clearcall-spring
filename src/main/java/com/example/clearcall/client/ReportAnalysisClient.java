package com.example.clearcall.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReportAnalysisClient {

    private final FastApiClient fastApiClient;

    public String generateReport(String title, String query, String authorization) {
        Map<String, Object> body = new HashMap<>();
        body.put("query", query);
        body.put("title", title);
        body.put("limit", 10);
        body.put("search_all", true);
        body.put("score_threshold", 0.15);
        body.put("enhance", true);
        body.put("rerank", true);

        Map<String, Object> responseBody = fastApiClient.sendPostRequest("/api/v1/analysis/report", body, authorization);

        if (responseBody != null && "success".equals(responseBody.get("status"))) {
            return (String) responseBody.get("report");
        }
        return "";
    }
}
