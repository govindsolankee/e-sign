package com.bajaj.utility;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


public class utilityClass {

	public static void CreateDirectoryStructure(String path)
	{
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("already directory exist");
			}
		}

	}

	public  void checkFileAtributes(String path) {
	
		try {
			Path file = Paths.get(path);
			BasicFileAttributes attr;
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			System.out.println("creationTime     = " + attr.creationTime());
			String date=new SimpleDateFormat("dd/MM/yyyy").format(file);
			System.out.println(date);
			System.out.println("lastAccessTime   = " + attr.lastAccessTime());
			System.out.println("lastModifiedTime = " + attr.lastModifiedTime());
        	System.out.println("isDirectory      = " + attr.isDirectory());
			System.out.println("isOther          = " + attr.isOther());
			System.out.println("isRegularFile    = " + attr.isRegularFile());
			System.out.println("isSymbolicLink   = " + attr.isSymbolicLink());
			System.out.println("size             = " + attr.size());
		} catch (IOException e) {
			System.out.println("Exception caught"+e.toString());
			e.printStackTrace();
		}
	}
	public static Boolean isNullorEmpty(String stringName)
	{
		if(stringName!= null && !stringName.isEmpty())
		{
			return true;
		}
		else {

			return false;	
		}	
	}
public	void deleteDirectoryStructure(String SRC_FOLDER) {
		
		File directory = new File(SRC_FOLDER);

    	//make sure directory exists
    	if(!directory.exists()){
    		System.out.println("Directory does not exist.");
            System.exit(0);
         }
    	else{   
             try{
          
    		delete(directory);

           }
    	catch(IOException e){
    		System.out.println("exception caught"+e.toString());
            e.printStackTrace();
            System.exit(0);
           }
        }
     }
    public  void delete(File file) throws IOException{
   	if(file.isDirectory()){
   		//directory is empty, then delete it
    		if(file.list().length==0){
    		   file.delete();
    		   System.out.println("Directory is deleted : "+file.getAbsolutePath());
    		   }
    		else{
      //list all the directory contents
        	   String files[] = file.list();
   
        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp);

        	      //recursive delete
        	     delete(fileDelete);
        	   }

        	   //check the directory again, if empty then delete it
        	   if(file.list().length==0){
           	     file.delete();
        	     System.out.println("Directory is deleted : "
                                                  + file.getAbsolutePath());
        	   }
    		}

    	}else{
    		//if file, then delete it
    		file.delete();
    		System.out.println("File is deleted : " + file.getAbsolutePath());
    	}
    }
    
    
    public  boolean equalLists(List<String> a, List<String> b){     
        // Check for sizes and nulls

        if (a == null && b == null) return false;


        if ((a == null && b!= null) || (a != null && b== null) || (a.size() != b.size()))
        {
            return false;
        }

        // Sort and compare the two lists          
        Collections.sort(a);
        Collections.sort(b);      
        return a.equals(b);
    }
}
