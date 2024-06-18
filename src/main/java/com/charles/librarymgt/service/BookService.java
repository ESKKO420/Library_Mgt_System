package com.charles.librarymgt.service;


import com.charles.librarymgt.exception.NotFoundException;
import com.charles.librarymgt.request.CreateBookRequest;
import com.charles.librarymgt.request.UpdateBookRequest;
import org.springframework.stereotype.Service;
import com.charles.librarymgt.dtos.BookDto;
import com.charles.librarymgt.models.Book;
import com.charles.librarymgt.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PagedResourcesAssembler<BookDto> pagedResourcesAssembler;

    public PagedModel<EntityModel<BookDto>> getAllBooks(Pageable paging) {
        Page<Book> result = bookRepository.findAll(paging);
        return pagedResourcesAssembler.toModel(result.map(BookDto::new));
    }

    public BookDto getBook(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException(Book.class, id));
        return new BookDto(book);
    }

    public BookDto addBook(CreateBookRequest request) {
        var book = new Book(request.title, request.author, request.isbn, request.publicationYear);
        return new BookDto(bookRepository.save(book));
    }

    public BookDto updateBook(UpdateBookRequest request, Long id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException(Book.class, id));

        if (request.title != null && !book.getTitle().equals(request.title)) {
            book.setTitle(request.title);
        }
        if (request.author != null && !book.getAuthor().equals(request.author)) {
            book.setAuthor(request.author);
        }
        if (request.isbn != null && !book.getIsbn().equals(request.isbn)) {
            book.setIsbn(request.isbn);
        }
        if (request.publicationYear != null && !book.getPublicationYear().equals(request.publicationYear)) {
            book.setPublicationYear(request.publicationYear);
        }

        return new BookDto(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
