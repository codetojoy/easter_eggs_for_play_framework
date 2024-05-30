package app.tasks;

import models.Book;

import com.google.inject.Inject;
import io.ebean.DB;
import java.util.List;

public class SimpleEbeanTask implements Task {

    @Inject
    public SimpleEbeanTask() {
    }
    
    @Override
    public boolean run() {
        emitIdentity();

        List<Book> books = DB.find(Book.class)
                                     .select("id, title, author")
                                     .findList();

        for (Book book : books) {
            System.out.println("TRACER book: " + book.toString());
        }

        return true;
    }
}
