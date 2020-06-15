package org.example.core.service.oneton;

import org.example.core.model.oneton.Author;
import org.example.core.model.oneton.Book;

import java.util.List;

public interface AuthorService {
  List<Author> findAll();
  boolean saveAuthor(Integer id, String name);
  boolean deleteAuthor(Integer id);
  List<Book> getAllBooks(Integer id);
  List<Author> findByName(String name);
  List<Author> findByBookName(String bookName);

  List<Book> findAllBooks();
  boolean saveBook(Integer id, String name, Author author);
  boolean deleteBook(Integer id);
  boolean updateBook(Integer id, String name, Author author);
}
