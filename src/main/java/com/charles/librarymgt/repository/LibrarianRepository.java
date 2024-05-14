package com.charles.librarymgt.repository;

import com.charles.librarymgt.models.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long>{
}
