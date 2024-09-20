package com.almousleck.service;

import com.almousleck.domain.Book;
import com.almousleck.domain.BookTransactionHistory;
import com.almousleck.domain.User;
import com.almousleck.exception.ResourceNotFoundException;
import com.almousleck.mapper.BookMapper;
import com.almousleck.mapper.BookSpecification;
import com.almousleck.repositories.BookRepository;
import com.almousleck.repositories.BookTransactionHistoryRepository;
import com.almousleck.request.BookRequest;
import com.almousleck.response.BookResponse;
import com.almousleck.response.BorrowedBookResponse;
import com.almousleck.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.almousleck.mapper.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;

    public Long save(BookRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();
    }


    public BookResponse findById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with the id: " + bookId));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());

        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();

        return new PageResponse<>(bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable);

        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();

        return new PageResponse<>(bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }


    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable  = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository
                .findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks
                .stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();

        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }


    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable  = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository
                .findAllReturnedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks
                .stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();

        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

}
