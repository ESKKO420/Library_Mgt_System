package com.charles.librarymgt.repository;

import com.charles.librarymgt.models.Book;
import com.charles.librarymgt.models.BorrowRecord;
import com.charles.librarymgt.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowBookRepository extends JpaRepository<BorrowRecord, Long> {

    BorrowRecord findBorrowRecordByBookAndPatron(Book book, Patron patron);
}
