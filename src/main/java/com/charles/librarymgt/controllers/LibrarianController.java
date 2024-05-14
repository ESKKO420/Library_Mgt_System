package com.charles.librarymgt.controllers;

import com.charles.librarymgt.models.BorrowRecord;
import com.charles.librarymgt.models.Librarian;
import com.charles.librarymgt.repository.BookRepository;
import com.charles.librarymgt.repository.BorrowBookRepository;
import com.charles.librarymgt.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.charles.librarymgt.repository.LibrarianRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LibrarianController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private BorrowBookRepository borrowBookRepository;

    @Autowired
    private PatronRepository patronRepository;


    @GetMapping("/librarians")
    List<Librarian> index() {
        return librarianRepository.findAll();
    }

    @GetMapping("/librarians/{id}")
    Librarian show(@PathVariable Long id) {
        return librarianRepository.findById(id).get();
    }

    @PostMapping("/librarians")
    Librarian store(@RequestBody Librarian librarian) {
        return librarianRepository.save(librarian);
    }

    @PutMapping("/librarians/{id}")
    Librarian update(@RequestBody Librarian newLibrarian, @PathVariable Long id) {
        var user = librarianRepository.findById(id).get();

        if (!user.getName().equals(newLibrarian.getName())) {
            user.setName(newLibrarian.getName());
        }
        if (!user.getPassword().equals(newLibrarian.getPassword())) {
            user.setPassword(newLibrarian.getPassword());
        }

        return librarianRepository.save(user);
    }

    @DeleteMapping("/librarians/{id}")
    void delete(@PathVariable Long id) {
        librarianRepository.deleteById(id);
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    BorrowRecord borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        var patron = patronRepository.findById(patronId).get();
        var book = bookRepository.findById(bookId).get();

        var borrowRecord = new BorrowRecord(book, patron);

        return borrowBookRepository.save(borrowRecord);

    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    BorrowRecord returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        var patron = patronRepository.findById(patronId).get();
        var book = bookRepository.findById(bookId).get();

        var borrowRecord = borrowBookRepository.findBorrowRecordByBookAndPatron(book, patron);
        if (borrowRecord != null) {
            borrowRecord.setReturnDate(LocalDateTime.now());
            return borrowBookRepository.save(borrowRecord);
        }
        return null;
    }
}
