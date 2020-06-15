package org.example.core.model;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
  @Id
  private ID id;
}
