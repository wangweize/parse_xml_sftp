package com.tongji.transform_xml.validate_xml_schema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;

public class TestMain {
	public static void main(String[] args) {
		try {
			String strLang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(strLang);
			File directory = new File(".");
			try {
				System.out.println(directory.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputStream isSchema = ClassLoader.getSystemClassLoader()
					.getResourceAsStream("post.xsd");
			StreamSource ss = new StreamSource(isSchema);
			Schema schema = factory.newSchema(ss);

			Validator validator = schema.newValidator();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputStream isXML = ClassLoader.getSystemClassLoader()
					.getResourceAsStream("post.xml");
			Document document = db.parse(isXML);

			DOMSource source = new DOMSource(document);
			validator.validate(source);
			System.out.println("Result : Valid!");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Result : Invalid!");
		}
	}
}
