package org.example.core.service.oneton;

import lombok.extern.slf4j.Slf4j;
import org.example.core.model.oneton.Author;
import org.example.core.model.oneton.Book;
import org.example.core.repository.oneton.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
  @Autowired
  private AuthorRepository authorRepository;

  @Override
  public List<Author> findAll(){
    log.trace("findAll -- method entered");
    List<Author> authors = authorRepository.findAllWithBooks();
    log.trace("findAll: result = {}", authors);
    return authors;
  }

  @Override
  public boolean saveAuthor(Integer id, String name){
    log.trace("saveAuthor -- method entered. Params: id = {}, name = {}", id, name);
    if(authorRepository.existsById(id)){
      log.trace("saveAuthor -- method failed. Author with same id already in db.");
      return false;
    }
    Author author = Author.builder().name(name).books(new ArrayList<>()).build();
    author.setId(id);
    authorRepository.save(author);
    log.trace("saveAuthor -- method finished successfully. Added author {}", author);
    return true;
  }

  @Override
  public boolean deleteAuthor(Integer id){
    log.trace("deleteAuthor -- method entered. Params: id = {}", id);
    if(id == null || id < 0){
      log.trace("deleteAuthor -- method failed. Invalid id.");
      throw new IllegalArgumentException("Invalid id!");
    }
    if(!authorRepository.existsById(id)){
      log.trace("deleteAuthor -- method failed. Author with id not in db.");
      return false;
    }
    authorRepository.deleteById(id);
    return true;
  }

  @Override
  public List<Book> getAllBooks(Integer id){
    log.trace("getAllBooks -- method entered");
    Optional<Author> authorOptional = authorRepository.findById(id);
    if(authorOptional.isPresent()){
      log.trace("findAll: result = {}", authorOptional.get().getBooks());
      return authorOptional.get().getBooks();
    } else{
      log.trace("findAll failed. no author found");
      return new ArrayList<>();
    }
  }

  @Override
  public List<Author> findByName(String name){
    log.trace("findByName -- method entered. Data = {}", name);
    List<Author> authors = authorRepository.findAllByNameCustom(name);
    log.trace("findByName: result = {}", authors);
    return authors;
  }

  @Override
  public List<Author> findByBookName(String bookName){
//    log.trace("findByBookName -- method entered. Data = {}", bookName);
//    List<Author> authors = authorRepository.findAll().stream().filter(author ->
//        author.getBooks().stream().map(Book::getName).anyMatch(name -> name.equals(bookName))).collect(Collectors.toList());
//    log.trace("findByBookName: result = {}", authors);
//    return authors;
    return authorRepository.findAllAuthorsWithBookName(bookName);
  }

  @Override
  public List<Book> findAllBooks(){
    return authorRepository.findAllWithBooks().stream().map(Author::getBooks).distinct().flatMap(Collection::stream).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public boolean saveBook(Integer id, String name, Author author){
    Book book = Book.builder().name(name).author(author).build();
    book.setId(id);
    authorRepository.findAllWithBooks()
        .stream()
        .filter(streamAuthor -> streamAuthor.getId().equals(author.getId()))
        .findFirst()
        .get()
        .getBooks()
        .add(book);
    return true;
  }

  @Override
  @Transactional
  public boolean deleteBook(Integer id){
    Optional<Author> parent = authorRepository.findAllWithBooks()
        .stream()
        .filter(author ->
          author.getBooks().stream().anyMatch(book -> book.getId().equals(id))
        )
        .findFirst();
    parent.get().getBooks().removeIf(book -> book.getId().equals(id));
    return true;
  }

  @Override
  @Transactional
  public boolean updateBook(Integer id, String name, Author author){
    Optional<Author> parent = authorRepository.findAllWithBooks()
        .stream()
        .filter(streamAuthor ->
            streamAuthor.getBooks().stream().anyMatch(book -> book.getId().equals(id))
        )
        .findFirst();
    Book book = parent.get().getBooks().stream().filter(streamBook -> streamBook.getId().equals(id)).findFirst().get();
    book.setName(name);
    book.setAuthor(author);
    return true;
  }
}
