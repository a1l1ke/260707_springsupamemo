package org.example.springsupamemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter // EL Parser 때문에 어차피 일반적 스타일의 get***가 필요해서 사용
@AllArgsConstructor
public class MemoViewDTO {
    private String memo;
    private ZonedDateTime createdDate;
}
