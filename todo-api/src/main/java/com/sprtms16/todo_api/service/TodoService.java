package com.sprtms16.todo_api.service;

import com.sprtms16.todo_api.domain.Todo;
import com.sprtms16.todo_api.dto.TodoRequest;
import com.sprtms16.todo_api.dto.TodoResponse;
import com.sprtms16.todo_api.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional
    public TodoResponse createTodo(TodoRequest request) {
        Todo todo = new Todo(request.getTitle());
        Todo savedTodo = todoRepository.save(todo);
        return convertToResponse(savedTodo);
    }

    // ID로 Todo 조회
    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        return convertToResponse(todo);
    }

    // 모든 Todo 조회
    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Todo 업데이트
    @Transactional
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }

        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }

        Todo updatedTodo = todoRepository.save(todo);
        return convertToResponse(updatedTodo);
    }

    // Todo 삭제
    @Transactional
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    // 완료 상태별 Todo 조회
    public List<TodoResponse> getTodosByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // Entity를 Response DTO로 변환
    private TodoResponse convertToResponse(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setCompleted(todo.isCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        return response;
    }
}
