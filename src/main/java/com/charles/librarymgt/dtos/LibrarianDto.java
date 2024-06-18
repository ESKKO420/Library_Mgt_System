package com.charles.librarymgt.dtos;

import com.charles.librarymgt.models.Book;
import com.charles.librarymgt.models.Librarian;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "items")
public class LibrarianDto {
    public Long id;
    public String email;
    public String name;

    public LibrarianDto(Librarian librarian) {
        this.id = librarian.getId();
        this.email = librarian.getEmail();
        this.name = librarian.getName();
    }
}
