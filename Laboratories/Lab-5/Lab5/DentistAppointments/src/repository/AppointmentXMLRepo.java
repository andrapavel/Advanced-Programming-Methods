
package repository;

import domain.Appointment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class AppointmentXMLRepo extends FileRepository<Appointment, Integer>
{

    public AppointmentXMLRepo(String filename)
    {
        super(filename);
    }

    @Override
    public void readFromFile()
    {
        try
        {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList appointmentList = doc.getElementsByTagName("appointment");

            for (int temp = 0; temp < appointmentList.getLength(); temp++)
            {
                Element appointmentElement = (Element) appointmentList.item(temp);
                int id = Integer.parseInt(appointmentElement.getAttribute("id"));
                String description = appointmentElement.getElementsByTagName("description").item(0).getTextContent();
                String date = appointmentElement.getElementsByTagName("date").item(0).getTextContent();

                Appointment appointment = new Appointment(id, description, date);
                repo.put(id, appointment);
            }
        }
        catch (Exception e)
        {
            //throw new RuntimeException("Error reading from the XML file: " + e.getMessage(), e);
        }
    }

    @Override
    public void writeToFile()
    {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("appointments");
            doc.appendChild(rootElement);

            for (Appointment appointment : repo.values())
            {
                Element appointmentElement = doc.createElement("appointment");
                appointmentElement.setAttribute("id", String.valueOf(appointment.getID()));

                Element descriptionElement = doc.createElement("description");
                descriptionElement.appendChild(doc.createTextNode(appointment.getDescription()));
                appointmentElement.appendChild(descriptionElement);

                Element dateElement = doc.createElement("date");
                dateElement.appendChild(doc.createTextNode(appointment.getDate()));
                appointmentElement.appendChild(dateElement);

                rootElement.appendChild(appointmentElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));

            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error writing to the XML file: " + e.getMessage(), e);
        }
    }
}
