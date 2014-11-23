package com.tongji.transform_xml.validate_xml_schema;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SchemaValidator implements ErrorHandler {
	public void warning(SAXParseException se) throws SAXException {
		System.err.println("[Warning] " + getLocationString(se) + ":"
				+ se.getMessage());
	}

	public void error(SAXParseException se) throws SAXException {
		System.err.println("[Error] " + getLocationString(se) + ": "
				+ se.getMessage());
	}

	public void fatalError(SAXParseException se) throws SAXException {
		System.err.println("[Fatal Error] " + getLocationString(se) + ": "
				+ se.getMessage());
	}

	private String getLocationString(SAXParseException se) {
		StringBuffer str = new StringBuffer();
		String systemId = se.getSystemId();
		if (systemId != null) {
			str.append(systemId);
			str.append(" ");
		}
		str.append(se.getLineNumber());
		str.append(':');
		str.append(se.getColumnNumber());
		return str.toString();
	}
	public static void main(String[] args) {
		SchemaFactory sf=SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");//XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI
		File schemaFile=new File("post.xsd");
		try {
			Schema schema = sf.newSchema(schemaFile);
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			dbf.setSchema(schema);
			DocumentBuilder db=dbf.newDocumentBuilder();
			Document doc=db.parse("post.xml");
			System.out.println("validate"+doc.getNodeName());
//			Validator validator=schema.newValidator();
//			validator.setErrorHandler(new SchemaValidator());
//			Source source=new StreamSource(new File("post.xml"));
//			validator.validate(source);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
