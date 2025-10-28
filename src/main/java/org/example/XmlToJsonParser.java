package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlToJsonParser {
    public static List<Employee> parseXML(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    int id = Integer.parseInt(element.getElementsByTagName("идентификатор").item(0).getTextContent());
                    String firstName = element.getElementsByTagName("имя").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("фамилия").item(0).getTextContent();
                    String country = element.getElementsByTagName("страна").item(0).getTextContent();
                    int age = Integer.parseInt(element.getElementsByTagName("возраст").item(0).getTextContent());

                    Employee employee = new Employee(id, firstName, lastName, country, age);
                    employees.add(employee);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
    public static void writeString(String json, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String listToJson(List<Employee> employees) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(employees);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
