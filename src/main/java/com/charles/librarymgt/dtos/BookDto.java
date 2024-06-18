package com.charles.librarymgt.dtos;

import com.charles.librarymgt.models.Book;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "items")
public class BookDto {
    public Long id;
    public String title;
    public String author;
    public String isbn;
    public String publicationYear;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.publicationYear = book.getPublicationYear();
    }
}
