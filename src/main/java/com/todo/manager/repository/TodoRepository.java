package com.todo.manager.repository;

import com.todo.manager.enums.TodoCategory;
import com.todo.manager.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<Todo> findByCategory(TodoCategory category);
    List<Todo> findByTitleIgnoreCase(String title);
    Page<Todo> findAll(Pageable pageable);
}
