package org.example.server.service.validators;

import org.example.common.domain.Student;
import org.example.common.domain.exceptions.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class StudentValidator {
  public void validate(Student entity) throws ValidatorException {
    StringBuilder errorMessage = new StringBuilder();
    if (entity.getId() == null) errorMessage.append("Id is null");
    else if (entity.getId() < 0) errorMessage.append("Invalid id! ");
    if (entity.getName().equals("")) errorMessage.append("Invalid name! ");

    if (errorMessage.length() > 0) throw new ValidatorException(errorMessage.toString());
  }
}
