package com.sprtms16.todo_api.repository;

import com.sprtms16.todo_api.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 완료 상태별 Todo 목록 조회
    List<Todo> findByCompleted(boolean completed);

    // 제목에 특정 키워드가 포함된 Todo 목록 조회
    List<Todo> findByTitleContaining(String keyword);

    // 특정 날짜 이후에 생성된 Todo 목록 조회
    List<Todo> findByCreatedAtAfter(LocalDateTime date);
}
