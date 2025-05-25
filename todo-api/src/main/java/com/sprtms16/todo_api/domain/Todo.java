package com.sprtms16.todo_api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Todo {

    // Getter와 Setter 메서드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private boolean completed = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    // 생성자
    public Todo(String title) {
        this.title = title;
    }

}
