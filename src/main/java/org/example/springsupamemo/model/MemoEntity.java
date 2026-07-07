package org.example.springsupamemo.model;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
public class MemoEntity {
    private UUID id;
    private String memo;
    private ZonedDateTime createdDate;
}
