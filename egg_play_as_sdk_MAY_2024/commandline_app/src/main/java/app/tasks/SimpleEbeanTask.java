package app.tasks;

import java.util.*;

import models.*;

import io.ebean.*;

public class SimpleEbeanTask implements Task {
    
    @Override
    public boolean run(Database database) {
        List<Book> books = DB.find(Book.class)
                                     .select("id, title, author")
                                     .findList();

        for (Book book : books) {
            System.out.println("TRACER book: " + book.toString());
        }

        return true;
    }
}
