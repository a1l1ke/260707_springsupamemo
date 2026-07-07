package org.example.springsupamemo.dto;

// DTO -> Data Transfer Object
public record MemoFormDTO(String memo) {
    // 1. 만들어진 시간? 식별자? 등을 붙이면 안 되나요? - 가능은 한데, 굳이? (DTO는 가볍게)
    // 2. DTO 구조를 가져갈 경우 특정 레이어에서 변경사항이 생기면 메서드를 건들 필요가 X.
}
