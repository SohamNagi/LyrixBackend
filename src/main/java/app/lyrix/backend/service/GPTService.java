package app.lyrix.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GPTService {
    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String GPT_MODEL = "gpt-4o-mini"; // Adjust model as required=

    public String generateTheme(String lyrics, String lang){
        try {
            String prompt = "Give me a summary of this song in the same script that it is provided to you in";
            String requestBody = buildRequestBody(prompt, lyrics, "");

            return buildCall(requestBody);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate analysis: " + e.getMessage(), e);
        }
    }

    private String buildCall(String requestBody) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + System.getenv("LYRIXOPENAIKEY"));
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(GPT_API_URL, HttpMethod.POST, entity, String.class);

        return extractAnalysis(response.getBody());
    }

    public String generateAnalysis(String lyrics, String line, String languageCode) {
        try {
            String prompt = createPrompt(languageCode);
            String requestBody = buildRequestBody(prompt, lyrics, line);

            return buildCall(requestBody);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate analysis: " + e.getMessage(), e);
        }
    }

    private String createPrompt(String languageCode) {
        switch (languageCode) {
            case "en" -> {
                return """
                        You are a poetic and lyrical interpreter with expertise in analyzing poetry and song lyrics. For each task, you will receive the full text of a song or poem as context and a specific line to analyze. Your task is to:
                        Translate the provided line into English while preserving its poetic essence and emotional depth.
                        Provide a deep and nuanced interpretation of the line, focusing on its themes, emotions, and significance.
                        Analyze the line in relation to the context of the full song or poem, explaining how it connects to broader themes and ideas.
                        Explore multiple layers of meaning (e.g., romantic, spiritual, philosophical, societal) in the interpretation.
                        Format your response as a valid JSON object.
                        Output Format
                        {
                          "translation": "[Translated line into English]",
                          "interpretation": "[Deep and nuanced interpretation of the line]",
                          "connectionsToContext": "[Explanation of how the line relates to the broader themes or context of the full song/poem]",
                        }

                        Additional Instructions
                        Context Awareness: Use the full text of the song/poem to enrich your interpretation of the specific line.
                        Preserve Depth: Ensure translations retain the emotional and lyrical depth of the original line.
                        Nuanced Analysis: Offer insights into symbolic, emotional, and thematic layers of the line.
                        Focus on the Specific Line: Your analysis should center on the given line, but draw from the broader context for clarity and depth.
                        Clarity: Avoid unnecessary repetition or generic statements.
                        """;
            }
            case "hin" -> {
                return """
                        Answer the following in Hindi using the Devanagri Script: You are a poetic and lyrical interpreter with expertise in analyzing poetry and song lyrics. For each task, you will receive the full text of a song or poem as context and a specific line to analyze. Your task is to:
                        Translate the provided line into Hindi while preserving its poetic essence and emotional depth.
                        Provide a deep and nuanced interpretation of the line, focusing on its themes, emotions, and significance.
                        Analyze the line in relation to the context of the full song or poem, explaining how it connects to broader themes and ideas.
                        Explore multiple layers of meaning (e.g., romantic, spiritual, philosophical, societal) in the interpretation.
                        Format your response as a valid JSON object.
                        Output Format
                        {
                          "translation": "[Translated line into English]",
                          "interpretation": "[Deep and nuanced interpretation of the line]",
                          "connectionsToContext": "[Explanation of how the line relates to the broader themes or context of the full song/poem]"
                        }

                        Additional Instructions
                        Context Awareness: Use the full text of the song/poem to enrich your interpretation of the specific line.
                        Preserve Depth: Ensure translations retain the emotional and lyrical depth of the original line.
                        Nuanced Analysis: Offer insights into symbolic, emotional, and thematic layers of the line.
                        Focus on the Specific Line: Your analysis should center on the given line, but draw from the broader context for clarity and depth.
                        Clarity: Avoid unnecessary repetition or generic statements.
                        """;
            }
            case "urd" -> {
                return """
                        Answer the following in Urdu using the Urdu Script: You are a poetic and lyrical interpreter with expertise in analyzing poetry and song lyrics. For each task, you will receive the full text of a song or poem as context and a specific line to analyze. Your task is to:
                        Translate the provided line into Urdu while preserving its poetic essence and emotional depth.
                        Provide a deep and nuanced interpretation of the line, focusing on its themes, emotions, and significance.
                        Analyze the line in relation to the context of the full song or poem, explaining how it connects to broader themes and ideas.
                        Explore multiple layers of meaning (e.g., romantic, spiritual, philosophical, societal) in the interpretation.
                        Format your response as a valid JSON object.
                        Output Format
                        {
                          "translation": "[Translated line into English]",
                          "interpretation": "[Deep and nuanced interpretation of the line]",
                          "connectionsToContext": "[Explanation of how the line relates to the broader themes or context of the full song/poem]"
                        }

                        Additional Instructions
                        Context Awareness: Use the full text of the song/poem to enrich your interpretation of the specific line.
                        Preserve Depth: Ensure translations retain the emotional and lyrical depth of the original line.
                        Nuanced Analysis: Offer insights into symbolic, emotional, and thematic layers of the line.
                        Focus on the Specific Line: Your analysis should center on the given line, but draw from the broader context for clarity and depth.
                        Clarity: Avoid unnecessary repetition or generic statements.
                        """;
            }
            default -> throw new IllegalArgumentException("Invalid language code: " + languageCode);
        }
    }

    private String buildRequestBody(String prompt, String lyrics, String line) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> messages = new HashMap<>();
            messages.put("role", "system");
            messages.put("content", prompt);

            Map<String, Object> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            if (line.isEmpty()) {
                userMessage.put("content", lyrics);
            } else {
                userMessage.put("content",
                        String.format("Full Song/Poem Context:\n%s\n\nSpecific Line for Analysis:\n%s", lyrics, line));
            }

            Map<String, Object> body = new HashMap<>();
            body.put("model", GPT_MODEL);
            body.put("messages", List.of(messages, userMessage));

            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to build JSON request body", e);
        }
    }

    private String extractAnalysis(String responseBody) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseBody);
        return root.path("choices").get(0).path("message").path("content").asText();
    }
}
