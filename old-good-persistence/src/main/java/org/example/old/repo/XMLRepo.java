package org.example.old.repo;


import org.example.old.domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class XMLRepo implements Repository {
  private final String filename;

  public XMLRepo(String filename) {
    this.filename = filename;
  }

  @Override
  public Optional<Student> findOne(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("id must not be null");
    }

    Map<Integer, Student> entities =
        StreamSupport.stream(loadData().spliterator(), false)
            .collect(Collectors.toMap(Student::getId, elem -> elem));

    return Optional.ofNullable(entities.get(id));
  }

  @Override
  public List<Student> findAll() {
    return StreamSupport.stream(loadData().spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public boolean save(Student entity) {
    if (entity == null) {
      throw new IllegalArgumentException("entity must not be null");
    }

    Map<Integer, Student> entities =
        StreamSupport.stream(loadData().spliterator(), false)
            .collect(Collectors.toMap(Student::getId, elem -> elem));
    Optional<Student> optional = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));

    if (optional.isPresent()) return false;

    saveToFile(entity);
    return true;
  }

  @Override
  public boolean delete(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("id must not be null");
    }
    Map<Integer, Student> entities =
        StreamSupport.stream(loadData().spliterator(), false)
            .collect(Collectors.toMap(Student::getId, elem -> elem));
    Optional<Student> optional = Optional.ofNullable(entities.remove(id));

    if (optional.isEmpty()) return false;

    deleteEntity(optional.get());
    return true;
  }

  @Override
  public boolean update(Student entity) {
    if (entity == null) {
      throw new IllegalArgumentException("entity must not be null");
    }

    Map<Integer, Student> entities =
        StreamSupport.stream(loadData().spliterator(), false)
            .collect(Collectors.toMap(Student::getId, elem -> elem));

    Optional<Student> optional =
        Optional.ofNullable(
            entities.computeIfPresent(entity.getId(), (k, v) -> entity) == null ? entity : null);
    if (optional.isPresent()) return false;

    updateEntity(entity);
    return true;
  }

  private void deleteEntity(Student entity) {
    try {
      Document xmlFileDocument =
          DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
      Element root = xmlFileDocument.getDocumentElement();

      NodeList children = root.getChildNodes();

      IntStream.range(0, children.getLength())
          .mapToObj(children::item)
          .filter(node -> node instanceof Element)
          .filter(
              element -> ((Element) element).getAttribute("Id").equals(entity.getId().toString()))
          .forEach(root::removeChild);

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(new DOMSource(xmlFileDocument), new StreamResult(new File(filename)));
    } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
      e.printStackTrace();
    }
  }

  private void updateEntity(Student entity) {
    try {
      Document xmlFileDocument =
          DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
      Element root = xmlFileDocument.getDocumentElement();

      NodeList children = root.getChildNodes();

      IntStream.range(0, children.getLength())
          .mapToObj(children::item)
          .filter(node -> node instanceof Element)
          .filter(
              element -> ((Element) element).getAttribute("Id").equals(entity.getId().toString()))
          .forEach(element -> root.replaceChild(objectToXMLNode(entity, xmlFileDocument), element));

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(new DOMSource(xmlFileDocument), new StreamResult(new File(filename)));
    } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
      e.printStackTrace();
    }
  }
  /**
   * Loads the data from the XML document
   *
   * @return Iterable of all entitites
   */
  private Iterable<Student> loadData() {

    HashSet<Student> newEntities = new HashSet<>();

    try {
      Document xmlFileDocument =
          DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
      Element root = xmlFileDocument.getDocumentElement();
      NodeList childrenNodes = root.getChildNodes();

      return IntStream.range(0, childrenNodes.getLength())
          .mapToObj(index -> childrenNodes.item(index))
          .filter(node -> node instanceof Element)
          .map(node -> studentObjectFromXMLFile((Element) node))
          .collect(Collectors.toSet());

    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return newEntities;
  }
  /**
   * Saves an entity to the XML document
   *
   * @param entitiesToSave the entities to be saved on the file
   */
  private void saveToFile(Collection<Student> entitiesToSave) {
    try {
      Document xmlFileDocument =
          DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
      Element root = xmlFileDocument.getDocumentElement();
      entitiesToSave.forEach(entity -> root.appendChild(objectToXMLNode(entity, xmlFileDocument)));
      Transformer transformer = TransformerFactory.newInstance().newTransformer();

      transformer.transform(new DOMSource(xmlFileDocument), new StreamResult(new File(filename)));
    } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves an entity to the XML document
   *
   * @param entity the entity to be saved on the list
   */
  private void saveToFile(Student entity) {
    try {
      Document xmlFileDocument =
          DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
      Element root = xmlFileDocument.getDocumentElement();
      Node entityNode = objectToXMLNode(entity, xmlFileDocument);
      root.appendChild(entityNode);
      Transformer transformer = TransformerFactory.newInstance().newTransformer();

      transformer.transform(new DOMSource(xmlFileDocument), new StreamResult(new File(filename)));
    } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }

  public static Student studentObjectFromXMLFile(Element element) {
    Student student = new Student();

    Integer id = Integer.parseInt(element.getAttribute("Id"));
    student.setId(id);

    student.setName(getTextFromTagName(element, "name"));

    return student;
  }

  private static String getTextFromTagName(Element element, String tagName) {
    Node node = element.getElementsByTagName(tagName).item(0);
    return node.getTextContent();
  }

  public Node objectToXMLNode(Student student, Document document) {
    Element studentElement = document.createElement("student");
    studentElement.setAttribute("Id", student.getId().toString());
    appendChildWithTextToNode(document, studentElement, "name", student.getName());
    return studentElement;
  }

  private void appendChildWithTextToNode(
      Document document, Node parentNode, String tagName, String textContent) {

    Element element = document.createElement(tagName);
    element.setTextContent(textContent);
    parentNode.appendChild(element);
  }
}
