package com.home.restfulwebservices.todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoResource {

    @Autowired
    private TodoHardCodedService todoService;

    @GetMapping(path = "/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable String username) {
        return todoService.findAll();
    }

    @GetMapping(path = "/users/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username, @PathVariable long id) {
        return todoService.findById(id);
    }

    @PutMapping(path = "/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username,
                           @PathVariable long id,
                           @RequestBody Todo todo) {
        Todo updatedTodo = todoService.save(todo);
        return new ResponseEntity<Todo>(updatedTodo, HttpStatus.OK);
    }

    @PostMapping(path = "/users/{username}/todos")
    public ResponseEntity<Void> saveTodo(@PathVariable String username,
                                           @RequestBody Todo todo) {
        Todo createdResource = todoService.save(todo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdResource.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(path = "/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {
        Todo todo = todoService.deleteById(id);
        if(todo != null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
