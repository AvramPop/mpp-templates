package org.example.core.repository.oneton;

import org.example.core.model.oneton.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
  @Query("select distinct a from Author a where a.name=:name")
  @EntityGraph(value = "allAuthorsWithName", type =
      EntityGraph.EntityGraphType.LOAD)
  List<Author> findAllByNameCustom(@Param("name") String name);

  @Query("select distinct a from Author a")
  @EntityGraph(value = "allAuthorsWithBooks", type =
      EntityGraph.EntityGraphType.LOAD)
  List<Author> findAllWithBooks();

  @Query("select distinct a from Author a where :bookName in (select b.name from a.books b)")
  @EntityGraph(value = "allAuthorsWithBooksWithName", type =
      EntityGraph.EntityGraphType.LOAD)
  List<Author> findAllAuthorsWithBookName(@Param("bookName")String bookName);
}
