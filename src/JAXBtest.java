import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import generated.*;

public class JAXBtest {
	public static void validate(String schemaFilename, String filename) throws Exception {
		Source xmlFile = new StreamSource(new File(filename));
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;
		try {
			schema = schemaFactory.newSchema(new File(schemaFilename));
		} catch (SAXException e) {
			System.out.println(schemaFilename + " contains errors");
			System.out.println(e.getLocalizedMessage());
			return;
		}
		
		Validator validator = schema.newValidator();
		try {
			validator.validate(xmlFile);
			System.out.println(filename + " is valid");
		} catch (SAXException e) {
			System.out.println(filename + " is not valid");
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static MailBox unmarshal(String filename) throws JAXBException {
		File file = new File(filename);
		JAXBContext jaxbContext = JAXBContext.newInstance(MailBox.class);
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		MailBox MailBox = (MailBox) jaxbUnmarshaller.unmarshal(file);
		return MailBox;
	}
	
	public static void marshal(String filename, MailBox MailBox) throws JAXBException {
		File file = new File(filename);
		JAXBContext jaxbContext = JAXBContext.newInstance(MailBox.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(MailBox, file);
	}
	
	public static XMLGregorianCalendar getXmlDate(String date) throws DatatypeConfigurationException {
	    return DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
	}
	
	public static void main(String[] args) throws Exception {
		validate("xml/example.xsd", "xml/example.xml");
		
		MailBox MailBox = unmarshal("xml/example.xml");
		
		ObjectFactory factory = new ObjectFactory();
		MailBox.Message message = factory.createMailBoxMessage();
		message.setFrom("Max");
		message.setTo("Alex");
		message.setSubject("Travel");
		message.setText("R U going w/ us?");
		message.setDate(getXmlDate("2016-12-01"));
		MailBox.getMessage().add(message);
		
		marshal("xml/example.new.xml", MailBox);
		validate("xml/example.xsd", "xml/example.new.xml");
	}
}
