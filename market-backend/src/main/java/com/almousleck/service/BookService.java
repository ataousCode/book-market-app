package com.almousleck.service;

import com.almousleck.repositories.BookRepository;
import com.almousleck.request.BookRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public Long save(BookRequest request, Authentication authentication) {
        return null;
    }
}
