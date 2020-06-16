package org.example.core.repository;

import org.example.core.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface MovieAppRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
