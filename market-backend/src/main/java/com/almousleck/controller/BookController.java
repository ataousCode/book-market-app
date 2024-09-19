package com.almousleck.controller;

import com.almousleck.request.BookRequest;
import com.almousleck.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Long> saveBook(@Valid @RequestBody BookRequest request,
                                         Authentication authentication) {
         return ResponseEntity.ok(bookService.save(request, authentication));
    }
}
