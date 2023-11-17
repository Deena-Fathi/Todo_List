package com.example.to_dolist.data;

import java.util.Date;

public class Todo {
    String todo;

    Date date;

    boolean done;

    public Todo(String todo, Date date, boolean done) {
        this.todo = todo;
        this.date = date;
        this.done = done;
    }
}
