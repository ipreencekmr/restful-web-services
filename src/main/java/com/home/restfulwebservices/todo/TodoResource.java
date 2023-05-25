package com.home.restfulwebservices.todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoResource {

    @Autowired
    private TodoHardCodedService todoService;

    @GetMapping(path = "/users/{username}/todos")
    public List<Todo> getAllTodos(String username) {
        return todoService.findAll();
    }
}
