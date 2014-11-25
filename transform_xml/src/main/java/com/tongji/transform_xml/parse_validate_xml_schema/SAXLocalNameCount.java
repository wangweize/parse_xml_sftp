package com.tongji.transform_xml.parse_validate_xml_schema;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SAXLocalNameCount extends DefaultHandler {
	private Hashtable tags;
	private static int c = 0;
	private static int d = 0;
	private static int e = 0;
	private String content = "test";
	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	public void startDocument() throws SAXException {
		tags = new Hashtable();
	}

	public void endDocument() throws SAXException {
		Enumeration e = tags.keys();
		while (e.hasMoreElements()) {
			String tag = (String) e.nextElement();
			int count = ((Integer) tags.get(tag)).intValue();
			System.out.println("Local Name \"" + tag + "\" occurs " + count
					+ " times");
		}
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {

		// System.out.println(content);
		System.out.println(++c);
		String key = localName;
		Object value = tags.get(key);

		if (value == null) {
			tags.put(key, new Integer(1));
		} else {
			int count = ((Integer) value).intValue();
			count++;
			tags.put(key, new Integer(count));
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content = new String(ch, start, length).trim();
//		if (!content.isEmpty()) {
			System.out.print("h2-" + (++e));
			System.out.println(content);
//		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		// System.out.println("h" + (++d));

	}

	public void ignorableWhitespace(char buf[], int start, int length)
			throws SAXException {
		System.out.println("run here");
	}

	private static String convertToFileURL(String filename) {
		String path = new File(filename).getAbsolutePath();
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar, '/');
		}

		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return "file:" + path;
	}

	private static void usage() {
		System.err.println("Usage: SAXLocalNameCount <file.xml>");
		System.err.println("       -usage or -help = this message");
		System.exit(1);
	}

	private static class MyErrorHandler implements ErrorHandler {
		private PrintStream out;

		MyErrorHandler(PrintStream out) {
			this.out = out;
		}

		private String getParseExceptionInfo(SAXParseException spe) {
			String systemId = spe.getSystemId();

			if (systemId == null) {
				systemId = "null";
			}

			String info = "URI=" + systemId + " Line=" + spe.getLineNumber()
					+ ": " + spe.getMessage();

			return info;
		}

		public void warning(SAXParseException spe) throws SAXException {
			out.println("Warning: " + getParseExceptionInfo(spe));
		}

		public void error(SAXParseException spe) throws SAXException {
			String message = "Error: " + getParseExceptionInfo(spe);
			System.out.println(message);
			throw new SAXException(message);
		}

		public void fatalError(SAXParseException spe) throws SAXException {
			String message = "Fatal Error: " + getParseExceptionInfo(spe);
			System.out.println(message);
			throw new SAXException(message);
		}
	}

	public static void main(String[] args) {
		String filename = "post.xml";

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(true);
		try {
			SAXParser saxParser = spf.newSAXParser();
			try {
				saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			} catch (SAXNotRecognizedException x) {
				System.err
						.println("Error: JAXP SAXParser property not recognized: "
								+ JAXP_SCHEMA_LANGUAGE);

				System.err
						.println("Check to see if parser conforms to the JAXP spec.");
				System.exit(1);
			}
			saxParser.setProperty(JAXP_SCHEMA_SOURCE, new File("post.xsd"));
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(new SAXLocalNameCount());
			xmlReader.parse(convertToFileURL(filename));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
