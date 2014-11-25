package com.tongji.transform_xml.parse_validate_xml_schema;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class ProductXML {
	public static void main(String[] args) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		// 构建XML中的节点
		Element root = doc.createElement("font");
		Element nameElement = doc.createElement("name");
		Text nameValue = doc.createTextNode("san");
		Element sizeElement = doc.createElement("size");
		sizeElement.setAttribute("unit", "px");
		Text sizeValue = doc.createTextNode("14");
		// 按顺序添加各个节点
		doc.appendChild(root);
		root.appendChild(nameElement);
		nameElement.appendChild(nameValue);
		root.appendChild(sizeElement);
		sizeElement.appendChild(sizeValue);

		Transformer t = TransformerFactory.newInstance().newTransformer();
		// 设置换行和缩进
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(
				new File("text.xml"))));

	}
}
