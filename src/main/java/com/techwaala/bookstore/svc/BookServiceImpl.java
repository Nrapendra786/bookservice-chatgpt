package com.techwaala.bookstore.svc;

import com.techwaala.bookstore.dtos.BookResponse;
import com.techwaala.bookstore.dtos.CreateBookRequest;
import com.techwaala.bookstore.dtos.UpdateBookRequest;
import com.techwaala.bookstore.entity.Book;
import com.techwaala.bookstore.exceptions.BookNotFoundException;
import com.techwaala.bookstore.exceptions.DuplicateIsbnException;
import com.techwaala.bookstore.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponse createBook(CreateBookRequest request) {
        validateIsbnUnique(request.getIsbn());

        Book bookToCreate = mapToEntity(request);
        Book savedBook = bookRepository.save(bookToCreate);

        return toBookResponse(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        Book book = findBookByIdOrThrow(id);
        return toBookResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toBookResponse)
                .toList();
    }

    @Override
    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book existingBook = findBookByIdOrThrow(id);

        applyUpdates(existingBook, request);

        Book updatedBook = bookRepository.save(existingBook);
        return toBookResponse(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book bookToDelete = findBookByIdOrThrow(id);
        bookRepository.delete(bookToDelete);
    }

    // -----------------------
    // Helper methods
    // -----------------------

    private Book findBookByIdOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    private void validateIsbnUnique(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new DuplicateIsbnException(isbn); // renamed for clarity
        }
    }

    private void validateIsbnChange(Book existingBook, String newIsbn) {
        boolean isbnChanged = !existingBook.getIsbn().equals(newIsbn);
        boolean isbnAlreadyInUse = bookRepository.existsByIsbn(newIsbn);

        if (isbnChanged && isbnAlreadyInUse) {
            // Throwing a "duplicate" style exception to match the condition
            throw new DuplicateIsbnException(newIsbn);
        }
    }

    private Book mapToEntity(CreateBookRequest request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .isbn(request.getIsbn())
                .build();
    }

    private void applyUpdates(Book book, UpdateBookRequest request) {
        validateIsbnChange(book, request.getIsbn());

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setIsbn(request.getIsbn());
    }

    private BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .isbn(book.getIsbn())
                .build();
    }
}


