package org.example.web;


import lombok.extern.slf4j.Slf4j;
import org.example.core.service.oneton.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CodeDriver {
  @Autowired
  private AuthorService authorService;

  public void run(){

  }
}
