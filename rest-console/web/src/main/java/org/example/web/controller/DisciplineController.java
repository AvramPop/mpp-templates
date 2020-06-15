package org.example.web.controller;

import org.example.core.model.Discipline;
import org.example.core.service.DisciplineService;
import org.example.web.converter.DisciplineConverter;
import org.example.web.dto.DisciplineDto;
import org.example.web.dto.DisciplinesDto;
import org.example.web.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DisciplineController {
  public static final Logger logger = LoggerFactory.getLogger(DisciplineController.class);

  @Autowired
  private DisciplineService disciplineService;

  @Autowired private DisciplineConverter disciplineConverter;


  @RequestMapping(value = "/disciplines/all", method = RequestMethod.GET)
  public DisciplinesDto getDisciplines() {
    logger.trace("getDisciplines -- method entered");
    List<Discipline> disciplines = disciplineService.findAll();
    logger.trace("getDisciplines: disciplines={}", disciplines);
    return new DisciplinesDto(disciplineConverter.convertModelsToDtos(disciplines));
  }

  @RequestMapping(value = "/disciplines/add", method = RequestMethod.POST)
  public ResponseDto addDiscipline(@RequestBody DisciplineDto disciplineDto) {
    logger.trace("addDiscipline -- method entered. Data: {}", disciplineDto.toString());
    if(disciplineService.saveDiscipline(disciplineDto.getId(), disciplineDto.getDescription())) {
      logger.trace("addDiscipline -- successful. Added discipline: {}", disciplineDto.toString());
      return new ResponseDto("200");
    } else {
      logger.trace("addDiscipline -- failed");
      return new ResponseDto("404");
    }
  }

  @RequestMapping(value = "/disciplines/up", method = RequestMethod.PUT)
  public ResponseDto updateDiscipline(@RequestBody DisciplineDto disciplineDto) {
    logger.trace("updateDiscipline -- method entered. Data: {}", disciplineDto.toString());
    if(disciplineService.updateDiscipline(disciplineDto.getId(), disciplineDto.getDescription())) {
      logger.trace("updateDiscipline -- successful. Updated discipline: {}", disciplineDto.toString());
      return new ResponseDto("200");
    } else {
      logger.trace("updateDiscipline -- failed");
      return new ResponseDto("404");
    }
  }

  @RequestMapping(value = "/disciplines/delete/{id}", method = RequestMethod.DELETE)
  public ResponseDto deleteDiscipline(@PathVariable Integer id) {
    logger.trace("deleteDiscipline -- method entered. Id: {}", id);
    if(disciplineService.deleteDiscipline(id)) {
      logger.trace("deleteDiscipline -- successful. Deleted discipline with id: {}", id);
      return new ResponseDto("200");
    } else {
      logger.trace("deleteDiscipline -- failed");
      return new ResponseDto("404");
    }
  }
}
