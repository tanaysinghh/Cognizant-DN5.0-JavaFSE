package com.library.service;

import com.library.repository.BookRepository;

// BookService depends on BookRepository. The dependency is injected by Spring
// (setter injection is used here, wired in applicationContext.xml).
public class BookService {

    private BookRepository bookRepository;

    // setter used by Spring for dependency injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void showBook() {
        System.out.println("Book from repository: " + bookRepository.getBook());
    }
}
