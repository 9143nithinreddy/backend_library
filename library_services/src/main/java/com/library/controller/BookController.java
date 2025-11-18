package com.library.controller;

import com.library.model.Book;
import com.library.repository.BookRepository;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepo;

    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    // Add book (ADMIN only)
    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        bookRepo.save(book);
        return "Book added successfully!";
    }

    // View all books
    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    // Search by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Book book = bookRepo.findById(id).orElse(null);

        if (book == null) {
            return ResponseEntity.status(404).body("Book not found");
        }

        return ResponseEntity.ok(book);
    }

}
