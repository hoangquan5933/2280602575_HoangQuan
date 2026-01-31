package com.example.bookapp.service;

import com.example.bookapp.model.Book;
import org.springframework.stereotype   .Service;

import java.util.*;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    public BookService() {
        books.add(new Book(1L, "Spring Boot", "Huy Cường"));
        books.add(new Book(2L, "Spring Boot V2", "Anh Sơn"));
    }

    public List<Book> getAll() {
        return books;
    }

    public Book getById(Long id) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst().orElse(null);
    }

    public void add(Book book) {
        book.setId((long) (books.size() + 1));
        books.add(book);
    }

    public void update(Book book) {
        Book old = getById(book.getId());
        old.setTitle(book.getTitle());
        old.setAuthor(book.getAuthor());
    }

    public void delete(Long id) {
        books.removeIf(b -> b.getId().equals(id));
    }
}
