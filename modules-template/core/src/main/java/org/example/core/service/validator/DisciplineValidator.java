package org.example.core.service.validator;

import org.example.core.model.Discipline;
import org.springframework.stereotype.Component;

@Component
public class DisciplineValidator {
  public void validate(Discipline entity) throws ValidatorException {
    StringBuilder errorMessage = new StringBuilder();
    if (entity.getId() == null) errorMessage.append("Id is null\n");
    if (entity.getId() < 0) errorMessage.append("Id is negative\n");
    if (entity.getDescription() == null) errorMessage.append("Description is null\n");
    if (entity.getDescription().equals("")) errorMessage.append("Description is empty\n");

    if (errorMessage.length() > 0) throw new ValidatorException(errorMessage.toString());
  }
}
