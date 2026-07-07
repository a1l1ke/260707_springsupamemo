package org.example.springsupamemo.service;

import lombok.RequiredArgsConstructor;
import org.example.springsupamemo.dto.MemoFormDTO;
import org.example.springsupamemo.dto.MemoViewDTO;
import org.example.springsupamemo.model.MemoEntity;
import org.example.springsupamemo.repository.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 스프링 컨테이너에 싱클톤 빈으로 등록 -> 의존성 주입 -> final 필드 -> 생성자 주입 -> (Lombok) @RequiredArgsConstructor
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    public void save(MemoFormDTO memoFormDTO) {
//        System.out.println("MemoService.save");
        // Builder 패턴 사용 시 원하는 것만 지정해서 인스턴스를 생성 가능
        MemoEntity memo = MemoEntity.builder()
                .memo(memoFormDTO.memo())
                .build();
        memoRepository.save(memo);
    }

    public List<MemoViewDTO> findAll() {
        return memoRepository.findAll()
                .stream().map(
                        memoEntity -> new MemoViewDTO(memoEntity.getMemo())
                )
                .toList();
    }
}
