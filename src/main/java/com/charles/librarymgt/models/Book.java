package com.charles.librarymgt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import jakarta.persistence.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books",schema= "public")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Book(String title){
        this.title = title;
    }


}
