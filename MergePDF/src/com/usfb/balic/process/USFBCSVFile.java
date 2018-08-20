package com.usfb.balic.process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class USFBCSVFile {

public static void generateCSV(String policyNo, String fileName,String mergedFileName){
		
		String timestamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		String Date_Format = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(Date_Format);
		String dtformat = sdf.format(new Date());

		File destDir = new File("D:\\UjjivanUSFB\\CSV");
		if (!destDir.exists()) {
			destDir.mkdir();
		}

		File fil = null;
		FileWriter fw = null;

		fil = new File(destDir.getAbsolutePath() + "\\" + dtformat
				+ "_" + "USFB.csv");
		System.out.println(destDir.getAbsolutePath() + "\\"+ dtformat + "_" + "USFB.csv");

		if (!fil.exists()) {
			try {
				fil.createNewFile();
			} catch (Exception e) {

			}
			
			try {
				fw = new FileWriter(fil, true);
				fw.write("Policy Number,File Name,Merged FName,DateTime");
				fw.write("\n".toCharArray());
				String metadata = policyNo + "," + fileName + ","
						+ mergedFileName + "," + timestamp;
				fw.write(metadata.toCharArray());

			} catch (Exception e) {

			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				fw = new FileWriter(fil, true);
				fw.write("\n".toCharArray());
				String metadata = policyNo + "," + fileName + ","
						+ mergedFileName + "," + timestamp;
				fw.write(metadata.toCharArray());
			} catch (Exception e) {

			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
