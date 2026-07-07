package org.example.springsupamemo.util;

import org.example.springsupamemo.dto.MemoSupabaseDTO;
import org.example.springsupamemo.model.MemoEntity;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class SupabaseUtil {
    private final String supabaseUrl;
    private final String supabaseSecret;

    public SupabaseUtil() {
        this.supabaseUrl = System.getenv("SUPABASE_URL");
        this.supabaseSecret = System.getenv("SUPABASE_SECRET");
        System.out.println("supabaseUrl = " + supabaseUrl.substring(0, 10) + "*".repeat(supabaseUrl.length() - 10));
        System.out.println("supabaseSecret = " + supabaseSecret.substring(0, 10) + "*".repeat(supabaseSecret.length() - 10));
    }

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

    public void save(MemoEntity memoEntity) {
        MemoSupabaseDTO dto = new MemoSupabaseDTO(memoEntity.getMemo());
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("%s/%s".formatted(supabaseUrl, "rest/v1/memo")))
                .headers(
                        "apikey", supabaseSecret,
                        "Authorization", "Bearer %s".formatted(supabaseSecret),
                        "Content-Type", "application/json",
                        "Prefer", "return=minimal"
                )
                .POST(HttpRequest.BodyPublishers.ofString(
                        mapper.writeValueAsString(dto))
                )
                .build();
        try {
            client.send(request, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MemoEntity> getAll() {
        HttpRequest request = HttpRequest
                .newBuilder()
                // 그대로 쓰고...
                .uri(URI.create("%s/%s".formatted(supabaseUrl, "rest/v1/memo")))
                .headers(
                        "apikey", supabaseSecret,
                        "Authorization", "Bearer %s".formatted(supabaseSecret)
//                        , "Content-Type", "application/json",
//                        "Prefer", "return=minimal"
                )
//                .POST(HttpRequest.BodyPublishers.ofString(
//                        mapper.writeValueAsString(dto))
//                )
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, handler);
            System.out.println("response = " + response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
