package org.example.springsupamemo.repository;

import lombok.RequiredArgsConstructor;
import org.example.springsupamemo.model.MemoEntity;
import org.example.springsupamemo.util.SupabaseUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemoRepository {
    private final SupabaseUtil supabaseUtil;

    public void save(MemoEntity memoEntity) {
        supabaseUtil.save(memoEntity);
    }

    public List<MemoEntity> findAll() {
        return supabaseUtil.getAll();
    }
}
