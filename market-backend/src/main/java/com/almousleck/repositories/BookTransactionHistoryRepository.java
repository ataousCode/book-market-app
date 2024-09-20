package com.almousleck.repositories;

import com.almousleck.domain.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Long> {

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.user.id = :userId
          """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Long id);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.owner.id = :userId
          """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Long userId);

    @Query("""
          SELECT
          (COUNT(*) > 0) AS isBorrowed
          FROM BookTransactionHistory bookTransactionHistory
          WHERE bookTransactionHistory.user.id = :userId
          AND bookTransactionHistory.book.id = :bookId
          AND bookTransactionHistory.returnApproved = false
          """)
    boolean isAlreadyBorrowedByUser(Long bookId, Long userId);


    @Query("""
          SELECT transaction
          FROM BookTransactionHistory transaction
          WHERE transaction.user.id = :userId
          AND transaction.book.id = :bookId
          AND transaction.returned = false
          AND transaction.returnApproved = false
          """)
    Optional<BookTransactionHistory> findBookIdAndUserId(Long bookId, Long userId);

//    @Query("""
//          SELECT transaction
//          FROM BookTransactionHistory transaction
//          WHERE transaction.book.owner.id = :userId
//          AND transaction.book.id = :bookId
//          AND transaction.returned = true
//          AND transaction.returnApproved = false
//          """)
//    Optional<BookTransactionHistory> findByBookIdAndUserId(Long bookId, Long userId);

    @Query("""
          SELECT transaction
          FROM BookTransactionHistory transaction
          WHERE transaction.book.owner.id = :userId
          AND transaction.book.id = :bookId
          AND transaction.returned = true
          AND transaction.returnApproved = false
          """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(Long bookId, Long userId);


    //owner_id

}
