package org.example.core.model.oneton;

import lombok.*;
import org.example.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="book")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraphs({
    @NamedEntityGraph(name = "booksWithAuthor",
        attributeNodes = @NamedAttributeNode(value = "author")),
})
public class Book extends BaseEntity<Integer> {
  private String name;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "aid")
  private Author author;

  @Override
  public String toString(){
    return "Book{" +
        "id='" + getId() + '\'' +
        "name='" + name + '\'' +
        ", author=" + author +
        '}';
  }
}