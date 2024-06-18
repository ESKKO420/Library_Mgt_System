package com.charles.librarymgt.dtos;

import com.charles.librarymgt.models.BorrowRecord;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Relation(collectionRelation = "items")
public class BorrowRecordDto {
    public Long id;
    public LocalDate borrowDate;
    public LocalDate returnDate;
    public BookDto book;
    public PatronDto patron;

    public BorrowRecordDto(BorrowRecord borrowRecord) {
        this.id = borrowRecord.getId();
        this.borrowDate = LocalDate.from(borrowRecord.getBorrowDate());
        this.returnDate = LocalDate.from(borrowRecord.getReturnDate());
        this.book = new BookDto(borrowRecord.getBook());
        this.patron = new PatronDto(borrowRecord.getPatron());
    }
}
