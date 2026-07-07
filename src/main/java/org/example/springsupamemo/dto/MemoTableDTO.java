package org.example.springsupamemo.dto;

import org.example.springsupamemo.model.MemoEntity;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

public record MemoTableDTO(
        String id,
        String memo,
//        String createdAt
        String created_at
) {
    public MemoEntity toEntity() {
        System.out.println("id = " + id);
        System.out.println("memo = " + memo);
//        System.out.println("createdAt = " + createdAt);
        System.out.println("created_at = " + created_at);
        return MemoEntity.builder()
                .id(UUID.fromString(id))
                .memo(memo)
                .createdDate(
                        Instant.parse(created_at)
                                .atZone(ZonedDateTime.now().getZone()))
                .build();
    }
}
