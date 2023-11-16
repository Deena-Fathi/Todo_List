package com.example.to_dolist.data;

import java.util.Date;

public class Todo {
    Date date;

    String todo;

    boolean done;

    public Todo(Date date, String todo, boolean done) {
        this.date = date;
        this.todo = todo;
        this.done = done;
    }
}
