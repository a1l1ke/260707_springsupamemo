package org.example.springsupamemo.repository;

import org.example.springsupamemo.model.MemoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemoRepository {
    public void save(MemoEntity memoEntity) {

    }

    public List<MemoEntity> findAll() {
        return List.of();
    }
}
