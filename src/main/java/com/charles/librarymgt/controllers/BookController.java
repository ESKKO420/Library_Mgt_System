package com.charles.librarymgt.controllers;

import com.charles.librarymgt.models.Book;
import com.charles.librarymgt.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public Book getBooks(){
        bookRepository.save(new Book("title"));
        return bookRepository.findById(1L).get();
    }
}
