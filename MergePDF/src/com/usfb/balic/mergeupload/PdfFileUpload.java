package com.usfb.balic.mergeupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.usfb.balic.process.USFBCSVFile;


public class PdfFileUpload 
{
	
	final static Logger log = Logger.getLogger(PdfFileUpload.class);
	static String soucrceToMeregePDFList = "D:\\UjjivanUSFB\\Manual_bkp\\";
	static String distinationStoreMergePDF = "D:\\UjjivanUSFB\\MergeManualPDF\\";
	public static void main(String[] args) {
		
		mergePDFFiles(soucrceToMeregePDFList,distinationStoreMergePDF);
		System.out.println("done");
	}
	
	public static void mergePDFFiles(String sourceLocation, String distinationLocation) 
	{
		log.info("Enter into getAllDocumentForGivenPolicyNo() method");
		File root = new File(sourceLocation);
		
		File fil[] = root.listFiles();
		System.out.println(fil.length);
		String policyNo = "";
		for(File f:fil){
			
			policyNo = f.getName();//folder name as a policy number
			System.out.println(policyNo);
			File fileListRoot = new File(sourceLocation+"\\"+policyNo);
			File fileList[] = fileListRoot.listFiles();//Get all pdf files
			for(File listFiles:fileList){
				String listFileName = listFiles.getName();
				System.out.println(listFileName);
			}
			Document document = new Document();
			boolean flag=false;
			String fileName =null;
			try {
				 fileName = distinationLocation.concat("\\" + policyNo+ "_PDF.pdf");
				File file = new File(fileName);
				String mergedFile = file.getName();
				log.info("Merged File Name : " + fileName);
				PdfCopy copy = new PdfCopy(document, new FileOutputStream(fileName));
				document.open();
				PdfReader readInputPDF;
				int number_of_pages;

				if(fileList.length>0){
				for (File ff : fileList) 
				{
					
					// if file is not pdf then convert into pdf and send to merge
					System.out.println("PDF path which we are reading : "+ ff.getPath());
					
					String docName = ff.getName();
					
					USFBCSVFile.generateCSV(policyNo,docName,mergedFile);//Generate CSV file
					/*********insert doc details in database**********//*
					Date startDate = getCurrentDate();
					new TestMergeWithCSV().insertDocDtls(docName, mergedFile,startDate);
					*//*********End of insertion in database**********/
					readInputPDF = new PdfReader(ff.getPath());
					number_of_pages = readInputPDF.getNumberOfPages();
					log.info("Number of pages for reading pdf :" + number_of_pages);

					for (int page = 0; page < number_of_pages;) 
					{
						copy.addPage(copy.getImportedPage(readInputPDF, ++page));
					}
				}
				
				flag=true;
			}
			else
			{
				System.out.println("merged File is not present, policy No ="+policyNo);
				log.info("merged File is not present, policy No ="+policyNo);
			}
				document.close();

				// ftp call send src file path =fileName declared ablove in same			
				if(flag){
					new PdfFileUpload().ftpUploadFiles(fileName);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		

	}
	
	public void ftpUploadFiles(String inputFile) 
	{
		log.info("Enter into ftpUploadFiles method");
		System.out.println("Enter into ftpUploadFiles method");
		String hostname = "sft.bajajallianz.com";
		
		String userName = "balicin\\ujjivan.sfb";
		String password = "Gt147y$1g4T";
		FTPClient ftpClient = null;

		try {
				ftpClient = new FTPClient();
				log.info("New FTP Client");
				System.out.println("New FTP Client");
				ftpClient.connect(hostname);
				log.info("Connected");
				System.out.println("Connected");
				boolean login = ftpClient.login(userName, password);
				log.info("Logged IN");
				System.out.println("Logged IN");
				int reply = ftpClient.getReplyCode();
				log.info("Reply from ftp server :" + reply);
				System.out.print("\nFTP string getReplyCode : " + reply);
				if (login) 
				{
					log.info("After validation of login credentials :" + login);
					if (ftpClient.isConnected()) 
					{
						ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
						File file = new File(inputFile.trim());
						String uploadFile = "/Ujjivan SFB/" + file.getName();
						log.info("File name which is started to upload :"+ file.getName());
						InputStream is = new FileInputStream(file);
	
						boolean done = ftpClient.storeFile(uploadFile, is);	
						is.close();
						if (done) 
						{
							log.info("File uploaded on FTP Successfully done : "+ file.getName());
							System.out.println("File uploaded on FTP Successfully done : "+ file.getName());
						} 
						else 
						{
							log.error("Error while uploading the pdf in FTP server");
							System.out.println("Error in FTP Upload......");
						}
					}
				} 
				else 
				{
					System.out.println("Error in Login FTP Server....." + login);
					log.error("Error in Login FTP Server....." + login);
				}
			} catch (Exception e){
				System.out.println("exception catched");
				e.printStackTrace();
				log.error("Exception comes at ftp/ftp server issue");
		} finally{
				try 
				{
					if (ftpClient.isConnected())
					{
						ftpClient.logout();
						ftpClient.disconnect();
					}
				} 
				catch (IOException ex) 
				{
					ex.printStackTrace();
					log.error("File not available");
				}
			}
	}
}
