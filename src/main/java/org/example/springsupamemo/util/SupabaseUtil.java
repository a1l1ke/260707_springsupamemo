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

    public void save(MemoEntity memoEntity) {
        /*
            curl -X POST '{...}/rest/v1/memo' \
            -H "apikey: SUPABASE_KEY" \
            -H "Authorization: Bearer SUPABASE_KEY" \
            -H "Content-Type: application/json" \
            -H "Prefer: return=minimal" \
            -d '{ "some_column": "someValue", "other_column": "otherValue" }'
        * */
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        MemoSupabaseDTO dto = new MemoSupabaseDTO(memoEntity.getMemo());
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("%s.%s".formatted(supabaseUrl, "rest/v1/memo")))
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
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            client.send(
                    request,
                    handler
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MemoEntity> getAll() {
        return List.of();
    }
}
