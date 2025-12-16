package com.techwaala.bookstore.svc;


import com.techwaala.bookstore.dtos.BookResponse;
import com.techwaala.bookstore.dtos.CreateBookRequest;
import com.techwaala.bookstore.dtos.UpdateBookRequest;

import java.util.List;

public interface BookService {

    BookResponse createBook(CreateBookRequest request);

    BookResponse getBookById(Long id);

    List<BookResponse> getAllBooks();

    BookResponse updateBook(Long id, UpdateBookRequest request);

    void deleteBook(Long id);
}

