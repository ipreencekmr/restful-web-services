package com.home.restfulwebservices.todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoHardCodedService {

    private static List<Todo> todos = new ArrayList<Todo>();

    private static int idCounter = 0;

    static {
        todos.add(new Todo(++idCounter,
                "in28Minutes",
                "Learn to Dance",
                 new Date(),
                false));
        todos.add(new Todo(++idCounter,
                "in28Minutes",
                "Learn Microservices",
                new Date(),
                false));
        todos.add(new Todo(++idCounter,
                "in28Minutes",
                "Learn React",
                new Date(),
                false));
    }

    public List<Todo> findAll() {
        return todos;
    }
}
