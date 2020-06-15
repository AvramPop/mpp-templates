package org.example.web.converter;

import org.example.core.model.Discipline;
import org.example.web.dto.DisciplineDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisciplineConverter {
  public Discipline convertDtoToModel(DisciplineDto dto) {
    return Discipline.builder()
        .description(dto.getDescription())
        .id(dto.getId())
        .build();
  }

  public DisciplineDto convertModelToDto(Discipline discipline) {
    return DisciplineDto.builder()
        .id(discipline.getId())
        .description(discipline.getDescription())
        .build();
  }

  public List<DisciplineDto> convertModelsToDtos(Collection<Discipline> disciplines) {
    return disciplines.stream().map(this::convertModelToDto).collect(Collectors.toList());
  }
}
