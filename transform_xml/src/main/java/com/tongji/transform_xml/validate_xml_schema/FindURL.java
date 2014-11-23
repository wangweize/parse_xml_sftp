package com.tongji.transform_xml.validate_xml_schema;

import java.io.File;
import java.io.IOException;

public class FindURL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File directory = new File(".");
		try {
			System.out.println(directory.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
