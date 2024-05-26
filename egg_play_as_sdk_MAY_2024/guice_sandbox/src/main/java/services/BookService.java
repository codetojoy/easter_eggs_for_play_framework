package services;

import models.*;

import javax.inject.Inject;
import java.util.*;

public class BookService {

    @Inject
    public BookService() {
    }

    public List<Book> getBooks() {
        Book book = new Book();
        book.setId(5150);
        book.setTitle("Example title");
        book.setAuthor("Michael Lewis");
        List<Book> books = List.of(book);
        return books;
    }
}
