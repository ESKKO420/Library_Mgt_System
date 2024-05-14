package com.charles.librarymgt.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

//@Getter
@Data
@NoArgsConstructor
//@Setter
@Entity
@Table(name = "borrow_records", schema = "public")
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime returnDate;

    @CreationTimestamp
    private LocalDateTime borrowDate;


    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public BorrowRecord(Book book, Patron patron) {
        this.book = book;
        this.patron = patron;
    }
}
