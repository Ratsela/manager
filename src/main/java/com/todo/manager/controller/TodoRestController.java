package com.todo.manager.controller;

import com.todo.manager.enums.TodoCategory;
import com.todo.manager.enums.TodoStatus;
import com.todo.manager.model.Todo;
import com.todo.manager.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/todo")
public class TodoRestController {

    private final TodoRepository todoRepository;
    private final Logger logger = LoggerFactory.getLogger(TodoRestController.class);

    @Autowired
    public TodoRestController(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<Todo> createTodo(@RequestParam String title,
                                            @RequestParam String category,
                                            @RequestParam String description,
                                            @RequestParam int day,
                                            @RequestParam int month,
                                            @RequestParam int year){
        logger.info("Creating a todo ------->>>>>>>>>>>>>>");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        Instant dueDate = Instant.ofEpochMilli(calendar.getTimeInMillis());

        Todo todo = new Todo(title,TodoCategory.valueOf(category),description, dueDate,TodoStatus.INCOMPLETE);
        return ResponseEntity.ok(todoRepository.save(todo));
    }

    @RequestMapping(value = "/search/category/{category}",method = RequestMethod.GET)
    public ResponseEntity<List<Todo>> searchByCategory(@PathVariable TodoCategory category){
        List<Todo> todoList = todoRepository.findByCategory(category);
        return ResponseEntity.ok(todoList);
    }

    @RequestMapping(value = "/search/title/{title}",method = RequestMethod.GET)
    public ResponseEntity<List<Todo>> searchByTitle(@PathVariable String title){
        List<Todo> todoList = todoRepository.findByTitleIgnoreCase(title);
        return ResponseEntity.ok(todoList);
    }

    @RequestMapping(value = "/load/{id}",method = RequestMethod.GET)
    public ResponseEntity<Todo> loadTodo(@PathVariable long id){
        Optional<Todo> todo = todoRepository.findById(id);
        return todo.map(ResponseEntity::ok).orElse(null);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Page<Todo> listAll(Pageable pageable){

        logger.info("Loading all todos <<<<<>>>>> {}",todoRepository.findAll().get(0));
        return todoRepository.findAll(pageable);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ResponseEntity deleteTodo(@PathVariable long id){
        Optional<Todo> todo = todoRepository.findById(id);
        todo.ifPresent(todoRepository::delete);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ResponseEntity<Todo> editTodo(@RequestParam long id,
                                         @RequestParam String title,
                                         @RequestParam TodoCategory category,
                                         @RequestParam String description,
                                         @RequestParam int day,
                                         @RequestParam int month,
                                         @RequestParam int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        Instant dueDate = Instant.ofEpochMilli(calendar.getTimeInMillis());

        Optional<Todo> todo = todoRepository.findById(id);
        Todo updated = null;
        if(todo.isPresent()){
            updated = todo.get();
            updated.setTitle(title);
            updated.setCategory(category);
            updated.setDescription(description);
            updated.setDueDate(dueDate);
            todoRepository.save(updated);
        }
        return ResponseEntity.ok(updated);
    }

    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    public ResponseEntity<Todo> markAsComplete(@RequestParam long todoId){
        Optional<Todo> todo = todoRepository.findById(todoId);
        Todo updated = null;
        if(todo.isPresent()){
            updated = todo.get();
            updated.setStatus(TodoStatus.COMPLETE);
            todoRepository.save(updated);
        }
        return ResponseEntity.ok(updated);
    }
}
