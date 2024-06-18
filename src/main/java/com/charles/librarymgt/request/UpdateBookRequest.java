package com.charles.librarymgt.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {

    public String title;
    public String author;
    public String isbn;
    public String publicationYear;
}
