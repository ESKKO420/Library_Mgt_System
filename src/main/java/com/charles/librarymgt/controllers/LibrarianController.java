package com.charles.librarymgt.controllers;

import com.charles.librarymgt.dtos.LibrarianDto;
import com.charles.librarymgt.models.BorrowRecord;
import com.charles.librarymgt.models.Librarian;
import com.charles.librarymgt.repository.BookRepository;
import com.charles.librarymgt.repository.BorrowBookRepository;
import com.charles.librarymgt.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import com.charles.librarymgt.repository.LibrarianRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LibrarianController {

    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowBookRepository borrowBookRepository;

    @Autowired
    private PagedResourcesAssembler<LibrarianDto> pagedResourcesAssembler;


    @GetMapping("/librarians")
    PagedModel<EntityModel<LibrarianDto>> index(
            @PageableDefault(page = 0, size = Integer.MAX_VALUE, sort = {"email"}) Pageable paging) {
        Page<Librarian> result = librarianRepository.findAll(paging);

        return pagedResourcesAssembler.toModel(result.map(LibrarianDto::new));
    }

    @GetMapping("/librarians/{id}")
    LibrarianDto show(@PathVariable Long id) {
        return new LibrarianDto(librarianRepository.findById(id).get());
    }

    @PostMapping("/librarians")
    LibrarianDto store(@RequestBody Librarian librarian) {
        return new LibrarianDto(librarianRepository.save(librarian));
    }

    @PutMapping("/librarians/{id}")
    LibrarianDto update(@RequestBody Librarian newLibrarian, @PathVariable Long id) {
        var user = librarianRepository.findById(id).get();

        if (!user.getEmail().equals(newLibrarian.getEmail())) {
            user.setEmail(newLibrarian.getEmail());
        }
        if (!user.getName().equals(newLibrarian.getName())) {
            user.setName(newLibrarian.getName());
        }
        if (!user.getPassword().equals(newLibrarian.getPassword())) {
            user.setPassword(newLibrarian.getPassword());
        }

        return new LibrarianDto(librarianRepository.save(user));
    }

    @DeleteMapping("/librarians/{id}")
    void delete(@PathVariable Long id) {
        librarianRepository.deleteById(id);
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    BorrowRecord borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        var patron = patronRepository.findById(patronId).get();
        var book = bookRepository.findById(bookId).get();

        var borrowRecord = borrowBookRepository.findBorrowRecordByBookAndPatron(book, patron);
        if (borrowRecord == null) {
            var newRecord = new BorrowRecord(book, patron);
            newRecord.setBorrowDate(LocalDate.now().atStartOfDay());

            return borrowBookRepository.save(newRecord);
        }
        return null;

    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    BorrowRecord returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        var patron = patronRepository.findById(patronId).get();
        var book = bookRepository.findById(bookId).get();

        var borrowRecord = borrowBookRepository.findBorrowRecordByBookAndPatron(book, patron);
        if (borrowRecord != null) {
            borrowRecord.setReturnDate(LocalDate.now().atStartOfDay());
            return borrowBookRepository.save(borrowRecord);
        }
        return null;
    }
}
