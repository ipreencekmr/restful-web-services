package com.home.restfulwebservices.todo;
import com.home.restfulwebservices.service.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoResource {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping(path = "/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable String username) {
        return todoRepository.findAll();
    }

    @GetMapping(path = "/users/{username}/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable String username, @PathVariable long id) {
        Integer todoId = Integer.parseInt(String.valueOf(id));
        Optional value = todoRepository.findById(todoId);
        if(value.isPresent()) {
            return new ResponseEntity<Todo>((Todo) value.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username,
                           @PathVariable long id,
                           @RequestBody Todo todo) {

        Integer todoId = Integer.parseInt(String.valueOf(id));

        if(!todoRepository.findById(todoId).isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Todo uTodo = todoRepository.findById(todoId).get();
        uTodo.setDescription(todo.getDescription());
        uTodo.setTargetDate(todo.getTargetDate());
        Todo updatedResource = todoRepository.save(uTodo);
        return new ResponseEntity<Todo>(updatedResource, HttpStatus.OK);
    }

    @PostMapping(path = "/users/{username}/todos")
    public ResponseEntity<Void> saveTodo(@PathVariable String username,
                                           @RequestBody Todo todo) {

        Todo createdResource = todoRepository.save(todo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdResource.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(path = "/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {

        Integer todoId = Integer.parseInt(String.valueOf(id));

        if(todoRepository.findById(todoId).isPresent()) {
            todoRepository.deleteById(todoId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
