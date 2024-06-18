package com.charles.librarymgt.service;

import com.charles.librarymgt.dtos.BorrowRecordDto;
import org.springframework.stereotype.Service;
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

@Service
public class LibrarianService {
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

    public PagedModel<EntityModel<LibrarianDto>> getAllLibrarians(Pageable paging) {
        Page<Librarian> result = librarianRepository.findAll(paging);

        return pagedResourcesAssembler.toModel(result.map(LibrarianDto::new));
    }

    public LibrarianDto getLibrarian(Long id) {
        return new LibrarianDto(librarianRepository.findById(id).get());
    }

    public LibrarianDto createLibrarian(Librarian librarian) {
        return new LibrarianDto(librarianRepository.save(librarian));
    }

    public LibrarianDto updateLibrarian(Librarian newLibrarian, Long id) {
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

    public void deleteLibrarian(Long id) {
        librarianRepository.deleteById(id);
    }

    public BorrowRecordDto borrowBook(Long bookId, Long patronId) {
        var patron = patronRepository.findById(patronId).get();
        var book = bookRepository.findById(bookId).get();

        var borrowRecord = borrowBookRepository.findBorrowRecordByBookAndPatronAndReturnDateNull(book, patron);
        if (borrowRecord == null) {
            var newRecord = new BorrowRecord(book, patron);
            newRecord.setBorrowDate(LocalDate.now().atStartOfDay());

            return new BorrowRecordDto(borrowBookRepository.save(newRecord));
        }
        return null;
    }

    public BorrowRecordDto returnBook(Long bookId, Long patronId) {
        var patron = patronRepository.findById(patronId).get();
        var book = bookRepository.findById(bookId).get();

        var borrowRecord = borrowBookRepository.findBorrowRecordByBookAndPatronAndReturnDateNull(book, patron);
        if (borrowRecord != null) {
            borrowRecord.setReturnDate(LocalDate.now().atStartOfDay());
            return new BorrowRecordDto(borrowBookRepository.save(borrowRecord));
        }
        return null;
    }
}
