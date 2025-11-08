package org.example.repository;

import org.example.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = "author")
    @Query("select b from Book b where b.id = :bookId ")
    Book getBookById(Long bookId);

    @EntityGraph(attributePaths = "author")
    @Query("select b from Book b")
    List<Book> getAllBooks();
}
