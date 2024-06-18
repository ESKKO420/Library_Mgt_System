package com.charles.librarymgt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

//@Setter
//@Getter
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String author;
    private String title;
    private String publicationYear;
    private String isbn;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BorrowRecord> borrowRecords;

    public Book(String author, String title, String publicationYear, String isbn) {
        this.author = author;
        this.title = title;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }
}