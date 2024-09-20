package com.almousleck.service;

import com.almousleck.domain.Book;
import com.almousleck.domain.User;
import com.almousleck.mapper.BookMapper;
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
    private final BookMapper bookMapper;

    public Long save(BookRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();
    }













}
