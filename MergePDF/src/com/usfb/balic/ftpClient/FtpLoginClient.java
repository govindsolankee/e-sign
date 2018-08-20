package com.usfb.balic.ftpClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import sun.net.ftp.FtpLoginException;
import com.bajaj.utility.utilityClass;

public class FtpLoginClient {



	FTPClient ftpClient = null;
	static final String hostname = "10.2.100.57";
	static final String username = "Balicin\\Ge.CFT1";
	static final String pass = "p35uRy6R5HR5";
	static final String ftpSource ="/FileDumps/";
	static final String ftpDestination ="/FileDumpsBackups/";
	static final String destination="/image/";
	static final String destinationOfCsv="/csv/";
	static final String hostname_omni="balicscan";
	static final String username_omni="omniexport";
	static final String password_omni="omn!export03";
	static int port= 21;
	Boolean flag=false;
	public FtpLoginClient(String host, int port, String username, String password) throws Exception{

		ftpClient = new FTPClient();
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		int reply;
		if(utilityClass.isNullorEmpty(host))
		{
			ftpClient.connect(host,port);
			System.out.println("FTP URL is:"+ftpClient.getDefaultPort());
			reply = ftpClient.getReplyCode();      
			System.out.println("reply:::::::::::::::::::"+reply);

			if (!FTPReply.isPositiveCompletion(reply)){
				ftpClient.disconnect();

				throw new FtpLoginException("Exception in connecting to FTP Server");
			}
			if(utilityClass.isNullorEmpty(username) && utilityClass.isNullorEmpty(password)){
				ftpClient.login(username, password);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();   
			}
		}  }

	public  void downloadFTPFile(String source, String destination,ArrayList <String> policyNoList) throws URISyntaxException, IOException {

		try {

			if(ftpClient.isConnected()){

				System.out.println( "ftp connected");

				FTPFile[] ftpFiles = ftpClient.listFiles(source);
				System.out.println(ftpFiles.length);
				if (ftpFiles != null && ftpFiles.length > 0) {

					System.out.println("length greater");
					for (FTPFile file : ftpFiles) {
						if (!file.isFile() ) {

							continue;
						}
						for(String policyNo :policyNoList)
						{
							String changefname =file.getName();	
							System.out.println(changefname);
							String[] splitname=changefname.split("_");
							if (policyNo.equalsIgnoreCase(splitname[0])) 
							{
								String dest=destination+splitname[0];
								utilityClass.CreateDirectoryStructure(dest);
								OutputStream output;

								output =new BufferedOutputStream(new FileOutputStream(destination+ file.getName()));

								if(utilityClass.isNullorEmpty(source+file.getName())&& output!=null){
									boolean success=ftpClient.retrieveFile(source+file.getName(), output);

									if(success)
									{
										System.out.println("sucessfully retrived file"+success);
									} }
								output.close();
							}
						} }


				}
			}

		}
		catch (IOException e) {
			System.out.println("exception caught"+e.toString());
			e.printStackTrace();
		}

	}

}




