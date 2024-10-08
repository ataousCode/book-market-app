package com.almousleck.controller;

import com.almousleck.request.BookRequest;
import com.almousleck.response.BookResponse;
import com.almousleck.response.BorrowedBookResponse;
import com.almousleck.response.PageResponse;
import com.almousleck.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {
    private final BookService bookService;

    @PostMapping()
    public ResponseEntity<Long> saveBook(@Valid @RequestBody BookRequest request,
                                         Authentication authentication) {
         return ResponseEntity.ok(bookService.save(request, authentication));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(bookService.findAllBooks(page, size, authentication));
    }


    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, authentication));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, authentication));
    }


    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, authentication));
    }

    @PatchMapping("/shareable/{bookId}")
    public ResponseEntity<Long> updateSharableStatus(@PathVariable Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.updateSharableStatus(bookId, authentication));
    }

    @PatchMapping("/archived/{bookId}")
    public ResponseEntity<Long> updateArchivedStatus(@PathVariable Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.updateArchivedStatus(bookId, authentication));
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Long> borrowBook(@PathVariable Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.borrowBook(bookId, authentication));
    }

    @PatchMapping("/borrow/return/{bookId}")
    public ResponseEntity<Long> returnBorrowBook(@PathVariable Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.returnBorrowBook(bookId, authentication));
    }

    @PatchMapping("/borrow/return/approve/{bookId}")
    public ResponseEntity<Long> approveReturnBorrowBook(@PathVariable Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.approveReturnBorrowBook(bookId, authentication));
    }

    @PostMapping(value = "/cover/{bookId}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("bookId") Long bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication authentication) {
        bookService.uploadBookCoverPicture(bookId, file, authentication);
        return ResponseEntity.accepted().build();
    }
















}
