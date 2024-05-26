package services;

import io.ebean.DB;

import models.*;

import javax.inject.Inject;
import jakarta.persistence.*;
import java.util.*;

public class BookService {

    @Inject
    public BookService() {
    }

    // This is not the "Play ethos" but we just want to test Ebean.
    // (In Play, we want to be async and query the DB on a different thread.)
    public List<Book> getBooks() {
        List<Book> books = DB.find(Book.class).orderBy("id").findList();
        return books;
    }
}
