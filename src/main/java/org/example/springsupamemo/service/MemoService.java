package org.example.springsupamemo.service;

import org.example.springsupamemo.dto.MemoFormDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 스프링 컨테이너에 싱클톤 빈으로 등록 -> 의존성 주입 -> final 필드 -> 생성자 주입 -> (Lombok) @RequiredArgsConstructor
public class MemoService {
    public void save(MemoFormDTO memoFormDTO) {
        System.out.println("MemoService.save");
    }

    public List<MemoFormDTO> findAll() {
        return List.of();
    }
}
