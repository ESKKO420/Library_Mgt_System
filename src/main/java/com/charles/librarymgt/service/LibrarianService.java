package com.charles.librarymgt.service;

import com.charles.librarymgt.dtos.BorrowRecordDto;
import com.charles.librarymgt.exception.NotFoundException;
import com.charles.librarymgt.models.Book;
import com.charles.librarymgt.models.Patron;
import com.charles.librarymgt.request.CreateLibrarianRequest;
import com.charles.librarymgt.request.UpdateLibrarianRequest;
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
    private BorrowBookRepository borrowRepository;

    @Autowired
    private PagedResourcesAssembler<LibrarianDto> pagedResourcesAssembler;

    public PagedModel<EntityModel<LibrarianDto>> getAllLibrarians(Pageable paging) {
        Page<Librarian> result = librarianRepository.findAll(paging);

        return pagedResourcesAssembler.toModel(result.map(LibrarianDto::new));
    }

    public LibrarianDto getLibrarian(Long id) {
        return new LibrarianDto(librarianRepository.findById(id).orElseThrow(() -> new NotFoundException(Librarian.class, id)));
    }

    public LibrarianDto createLibrarian(CreateLibrarianRequest request) {
        var librarian = new Librarian(request.email, request.name, request.password);
        return new LibrarianDto(librarianRepository.save(librarian));
    }

    public LibrarianDto updateLibrarian(UpdateLibrarianRequest request, Long id) {
        var user = librarianRepository.findById(id).orElseThrow(() -> new NotFoundException(Librarian.class, id));

        if (request.email != null && !user.getEmail().equals(request.email)) {
            user.setEmail(request.email);
        }
        if (request.name != null && !user.getName().equals(request.name)) {
            user.setName(request.name);
        }
        if (request.password != null && !user.getPassword().equals(request.password)) {
            user.setPassword(request.password);
        }

        return new LibrarianDto(librarianRepository.save(user));
    }

    public void deleteLibrarian(Long id) {
        librarianRepository.deleteById(id);
    }

    public BorrowRecordDto borrowBook(Long bookId, Long patronId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(Book.class, bookId));
        var patron = patronRepository.findById(patronId).orElseThrow(() -> new NotFoundException(Patron.class, patronId));

        var borrowRecord = borrowRepository.findBorrowRecordByBookAndPatronAndReturnDateNull(book, patron);
        if (borrowRecord == null) {
            var newRecord = new BorrowRecord(book, patron);
            newRecord.setBorrowDate(LocalDate.now().atStartOfDay());

            return new BorrowRecordDto(borrowRepository.save(newRecord));
        }
        return null;
    }

    public BorrowRecordDto returnBook(Long bookId, Long patronId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(Book.class, bookId));
        var patron = patronRepository.findById(patronId).orElseThrow(() -> new NotFoundException(Patron.class, patronId));

        var borrowRecord = borrowRepository.findBorrowRecordByBookAndPatronAndReturnDateNull(book, patron);
        if (borrowRecord != null) {
            borrowRecord.setReturnDate(LocalDate.now().atStartOfDay());
            return new BorrowRecordDto(borrowRepository.save(borrowRecord));
        }
        return null;
    }
}
