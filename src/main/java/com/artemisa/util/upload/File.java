package com.artemisa.util.upload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;

public class File {

	public static boolean exist(FacesContext ctx,  String path ,String fileName)
	{		
		java.io.File f = new java.io.File(path + fileName);

		if(f.exists()){
			System.out.println("File existed");
			return true;
		}else{
			System.out.println("File not found!");
			return false;			  
		}
	}
	
	public static void delete(String path, String fileName) throws IOException
	{		
		java.io.File file = new java.io.File(path + fileName);
		 
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
	}
	
	public static void upload(String path, String fileName, InputStream stream) throws IOException
	{		
		OutputStream out = new FileOutputStream(new java.io.File(path + fileName));
		
		int read = 0;
		byte[] bytes = new byte[1024];
		 
		while ((read = stream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		 
		stream.close();
		out.flush();
		out.close();
	}	
}
