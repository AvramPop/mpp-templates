package org.example.core.model.oneton;

import lombok.*;
import org.example.core.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="author")
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraphs({
    @NamedEntityGraph(name = "allAuthorsWithBooks",
        attributeNodes = @NamedAttributeNode(value = "books")),
    @NamedEntityGraph(name = "allAuthorsWithName",
        attributeNodes = @NamedAttributeNode(value = "books")),
    @NamedEntityGraph(name = "allAuthorsWithBooksWithName",
        attributeNodes = @NamedAttributeNode(value = "books"))
})
public class Author extends BaseEntity<Integer> {
  private String name;

  @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Book> books;

  @Override
  public String toString(){
    return "Author{" +
        "id='" + getId() + '\'' +
        "name='" + name + '\'' +
        ", books=" + books.stream().map(Book::getName).reduce("", (a, b) -> a + " " + b) +
        '}';
  }
}