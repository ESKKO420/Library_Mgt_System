package com.charles.librarymgt.controllers;

import com.charles.librarymgt.dtos.BorrowRecordDto;
import com.charles.librarymgt.dtos.LibrarianDto;
import com.charles.librarymgt.models.Librarian;
import com.charles.librarymgt.request.CreateLibrarianRequest;
import com.charles.librarymgt.request.UpdateLibrarianRequest;
import com.charles.librarymgt.service.LibrarianService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LibrarianController {

    @Autowired
    private LibrarianService librarianService;

    @GetMapping("/librarians")
    PagedModel<EntityModel<LibrarianDto>> index(
            @PageableDefault(page = 0, size = Integer.MAX_VALUE, sort = {"email"}) Pageable paging) {

        return librarianService.getAllLibrarians(paging);
    }

    @GetMapping("/librarians/{id}")
    LibrarianDto show(@PathVariable Long id) {
        return librarianService.getLibrarian(id);
    }

    @PostMapping("/librarians")
    LibrarianDto store(@RequestBody @Valid CreateLibrarianRequest request) {
        return librarianService.createLibrarian(request);
    }

    @PutMapping("/librarians/{id}")
    LibrarianDto update(@RequestBody @Valid UpdateLibrarianRequest request, @PathVariable Long id) {
        return librarianService.updateLibrarian(request, id);
    }

    @DeleteMapping("/librarians/{id}")
    void delete(@PathVariable Long id) {
        librarianService.deleteLibrarian(id);
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    BorrowRecordDto borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return librarianService.borrowBook(bookId, patronId);

    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    BorrowRecordDto returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return librarianService.returnBook(bookId, patronId);
    }
}
