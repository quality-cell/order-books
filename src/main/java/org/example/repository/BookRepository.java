package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookDto;
import org.example.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager em;

    public Book getBookById(Long bookId) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id = :bookId", Book.class);
        query.setParameter("bookId", bookId);

        return query.getResultStream().findFirst().orElse(null);
    }

    public List<Book> getAllBooks() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);

        return query.getResultList();
    }

    @Transactional
    public Book addBook(Book book) {
        em.persist(book);
        em.flush();

        return book;
    }

    @Transactional
    public Book updateBook(Book newBook) {
        Long bookId = newBook.getId();
        Book oldBook = getBookById(bookId);
        if (oldBook == null) {
            throw new RuntimeException("Книги с id = " + bookId + " не существует");
        }

        oldBook.setTitle(newBook.getTitle());
        oldBook.setAuthor(newBook.getAuthor());
        oldBook.setIsbn(newBook.getIsbn());

        em.merge(oldBook);
        return oldBook;
    }

    public boolean exitsBookById(Long bookId) {
        TypedQuery<Boolean> query = em.createQuery("select count(*) > 0 from Book b where b.id = :bookId", Boolean.class);
        query.setParameter("bookId", bookId);

        return query.getSingleResult();
    }

    @Transactional
    public void deleteBookById(Long bookId) {
        Query query = em.createQuery("delete from Book b where b.id = :bookId");
        query.setParameter("bookId", bookId);

        query.executeUpdate();
    }
}
