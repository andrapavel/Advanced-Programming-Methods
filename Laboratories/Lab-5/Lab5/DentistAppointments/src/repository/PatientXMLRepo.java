
package repository;

import domain.Patient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PatientXMLRepo extends FileRepository<Patient, Integer>
{
    public PatientXMLRepo(String filename)
    {
        super(filename);
    }

    @Override
    public void readFromFile()
    {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList patientList = doc.getElementsByTagName("patient");

            for (int temp = 0; temp < patientList.getLength(); temp++)
            {
                Node patientNode = patientList.item(temp);

                if (patientNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element patientElement = (Element) patientNode;
                    String idString = patientElement.getElementsByTagName("id").item(0).getTextContent();

                    int id = Integer.parseInt(idString);
                    String name = patientElement.getElementsByTagName("name").item(0).getTextContent();
                    String disease = patientElement.getElementsByTagName("dental_condition").item(0).getTextContent();

                    Patient patient = new Patient(id, name, disease);
                    repo.put(id, patient);
                }
            }
        }
        catch (Exception e)
        {
            //throw new RuntimeException("Error reading from file", e);
        }
    }

    @Override
    public void writeToFile()
    {
        try (FileWriter fileWriter = new FileWriter(filename))
        {
            fileWriter.write("<patients>\n");
            for (Patient patient : repo.values()) {
                writePatientToXml(fileWriter, patient);
            }
            fileWriter.write("</patients>");
        }
        catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }

    private void writePatientToXml(FileWriter fileWriter, Patient patient) throws IOException
    {
        fileWriter.write("  <patient>\n");
        writeElementToXml(fileWriter, "id", String.valueOf(patient.getID()));
        writeElementToXml(fileWriter, "name", patient.getName());
        writeElementToXml(fileWriter, "dental_condition", patient.getDentalCondition());
        fileWriter.write("  </patient>\n");
    }

    private void writeElementToXml(FileWriter fileWriter, String elementName, String elementValue) throws IOException
    {
        fileWriter.write("    <" + elementName + ">" + elementValue + "</" + elementName + ">\n");
    }
}
/*
   DocumentBuilderFactory:
        DocumentBuilderFactory is an abstract class that provides a factory for obtaining DocumentBuilder instances.
        It follows the factory design pattern, allowing us to obtain instances of DocumentBuilder without directly creating them.
        The newInstance method is typically used to get an instance of DocumentBuilderFactory.

    DocumentBuilder:
        DocumentBuilder is an interface in the Java API for processing XML documents.
        It provides methods to parse XML content and create a Document object.
        Instances of DocumentBuilder are obtained from a DocumentBuilderFactory.

    Document:
        Document is an interface representing the entire XML document.
        It provides methods to navigate and manipulate the structure of the document, such as getting elements, attributes, and text content.
        The parse method of DocumentBuilder returns a Document object.

    Node:
        A Node is a fundamental interface in the DOM API.
        It represents a node in the XML tree structure, such as an element, attribute, or text node.
        Common types of nodes include Element, Attribute, Text, Comment, etc.
        Nodes can have child nodes, attributes, and other properties.

    NodeList:
        A NodeList is an ordered collection of nodes.
        It is often used to represent a list of child nodes of an element or the result of a query.
        Methods like getElementsByTagName return a NodeList containing nodes that match a specific tag name.
        It provides an ordered sequence of nodes that can be iterated over.

 */
