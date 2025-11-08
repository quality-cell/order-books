package org.example.repository;

import org.example.entity.BookOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    @EntityGraph(attributePaths = {"book.author", "client"})
    @Query("select bo from BookOrder bo")
    List<BookOrder> getAll();

    @EntityGraph(attributePaths = {"book.author", "client"})
    @Query("select bo from BookOrder bo " +
            "where bo.id = :bookOrderId")
    BookOrder getBookOrderById(Long bookOrderId);
}
