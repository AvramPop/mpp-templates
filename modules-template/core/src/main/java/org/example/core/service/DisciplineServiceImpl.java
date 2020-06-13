package org.example.core.service;

import org.example.core.model.Discipline;
import org.example.core.repository.DisciplineRepository;
import org.example.core.service.validator.DisciplineValidator;
import org.example.core.service.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineServiceImpl implements DisciplineService {
  public static final Logger logger = LoggerFactory.getLogger(DisciplineServiceImpl.class);

  @Autowired private DisciplineRepository disciplineRepository;
  @Autowired private DisciplineValidator disciplineValidator;

  @Override
  public List<Discipline> findAll() {
    logger.trace("findAll -- method entered");
    List<Discipline> disciplines = disciplineRepository.findAll();
    logger.trace("findAll: result = {}", disciplines);
    return disciplines;
  }

  @Override
  @Transactional
  public boolean updateDiscipline(Integer id, String newDescription) {
    logger.trace(
        "updateDiscipline -- method entered. Params: id = {}, description = {}", id, newDescription);
    try {
      disciplineValidator.validate(new Discipline(id, newDescription));
      Optional<Discipline> repositoryDiscipline = disciplineRepository.findById(id);
      if (repositoryDiscipline.isPresent()) {
        repositoryDiscipline.get().setDescription(newDescription);
      } else {
        logger.error("updateDiscipline -- method failed. Discipline with id does not exist in db.");
        return false;
      }
      logger.trace("updateDiscipline -- method finished successfully. Updated discipline {}", repositoryDiscipline.get());
      return true;
    } catch(ValidatorException exception) {
      logger.error("updateDiscipline -- method failed due to to validating exception. Message = {}", exception.getMessage());
      return false;
    }
  }

  @Override
  public boolean saveDiscipline(Integer id, String description) {
    logger.trace(
        "saveDiscipline -- method entered. Params: id = {}, description = {}", id, description);
    if(disciplineRepository.existsById(id)) {
      logger.error("saveDiscipline -- method failed. Discipline with same id already in db.");
      return false;
    }
    Discipline discipline = new Discipline(id, description);
    try {
      disciplineValidator.validate(discipline);
      disciplineRepository.save(discipline);
      logger.trace("saveDiscipline -- method finished successfully. Added discipline {}", discipline);
      return true;
    } catch (ValidatorException exception) {
      logger.error("saveDiscipline -- method failed due to to validating exception. Message = {}", exception.getMessage());
      return false;
    }
  }

  @Override
  public boolean deleteDiscipline(Integer id) {
    logger.trace(
        "deleteDiscipline -- method entered. Params: id = {}", id);
    if (id == null || id < 0){
      logger.error("deleteDiscipline -- method failed. Invalid id.");
      return false;
    }
    if(!disciplineRepository.existsById(id)) {
      logger.trace("deleteDiscipline -- method failed. Discipline with id not in db.");
      return false;
    }
    disciplineRepository.deleteById(id);
    return true;
  }
}
