package com.charles.librarymgt.repository;

import com.charles.librarymgt.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long>{
}
