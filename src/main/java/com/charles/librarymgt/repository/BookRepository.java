package com.charles.librarymgt.repository;

import com.charles.librarymgt.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}
