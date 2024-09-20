package com.almousleck.mapper;

import com.almousleck.domain.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> withOwnerId(Long ownerId) {
        return (root, _, cb) -> cb.equal(root.get("owner").get("id"), ownerId);
    }
}
