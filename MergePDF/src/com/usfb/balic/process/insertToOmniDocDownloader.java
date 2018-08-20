package com.usfb.balic.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.usfb.balic.connection.TestConnection;
public class insertToOmniDocDownloader {
	
	
	public void insertDataIntoOmniDocDownloader(String DocName,String FIELD_NAME,String FIELD_VALUE)  throws SQLException, ClassNotFoundException{

		TestConnection testConnection = new TestConnection();
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		int maxrecord=0;

		String insertTableSQL = "INSERT INTO balic.AZBJ_OMNI_DOC_DOWNLOADER"
			+ " (FOLDER_PATH,SOURCE_MODULE,DATACLASS,DOC_NAME,FIELD_NAME,FIELD_VALUE, DOC_UPLOAD_FLAG,DOC_DOWNLOAD_FLAG,RECORDNO,DOC_UPLOAD_DT,ENTRY_DATE) VALUES"
			+ "(?,?,?,?,?,?,?,?,?,?,?)";

		String record_nb="SELECT max(RECORDNO) +1 from balic.AZBJ_OMNI_DOC_DOWNLOADER";
		System.out.println(insertTableSQL);

		try{
			//dbConnection = testConnection.getConnection();

			Class.forName("oracle.jdbc.driver.OracleDriver");		
			//dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.3.5.113:1521:OPUSUAT7","CUSTOMER","cust_uat7_2o17");
			dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.3.3.171:1521:PRODN1","IT_CI3944","bajaj!1234");
			System.out.println("Connection is :"+dbConnection);		
			preparedStatement = dbConnection.prepareStatement(record_nb);

			ResultSet rs = preparedStatement.executeQuery(record_nb);

			while(	rs.next())
			{
				maxrecord=rs.getInt(1);
			}
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
     		preparedStatement.setString(1, "UJIVAN_DOCS");
			preparedStatement.setString(2, "USFB");
			preparedStatement.setString(3, "PolicyDocs");
			preparedStatement.setString(4, DocName);
			preparedStatement.setString(5, FIELD_NAME);
			preparedStatement.setString(6, FIELD_VALUE);
			preparedStatement.setString(7, "Y");
			preparedStatement.setString(8, "N");
			//preparedStatement.setString(9, " (SELECT max(RECORDNO) +1  from balic.AZBJ_OMNI_DOC_DOWNLOADER)");
			preparedStatement.setInt(9, maxrecord);
			preparedStatement.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
		System.out.println("Record is inserted into balic.AZBJ_OMNI_DOC_DOWNLOADER table!");
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}
		finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	public static  ArrayList<String> getStatusFromOmniDocDownloader(ArrayList <String>  dbDoclist,String policyNo)
	{
		TestConnection testConnection = new TestConnection();
		Connection dbConnection = testConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs=null;


		String insertTableSQL = "select * from balic.AZBJ_OMNI_DOC_DOWNLOADER where FIELD_NAME='PolicyNo' and FIELD_VALUE=?";
		try {
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, policyNo);

			rs = preparedStatement.executeQuery();
			while(rs.next()){
				String DOC_UPLOAD_FLAG= rs.getString("DOC_UPLOAD_FLAG");
				String DOC_DOWNLOAD_FLAG =rs.getString("DOC_DOWNLOAD_FLAG");
				String DOWNLOAD_REMARKS =rs.getString("DOWNLOAD_REMARKS");
				String DOC_NAME=rs.getString("DOC_NAME");
				if(DOC_NAME!=null && !DOC_NAME.isEmpty()&& DOC_UPLOAD_FLAG.equalsIgnoreCase("Y") && DOC_DOWNLOAD_FLAG.equalsIgnoreCase("P") && DOWNLOAD_REMARKS.equalsIgnoreCase("Document Downloaded Successfully"))
				{
					System.out.println("DOC_UPLOAD_FLAG,DOC_UPLOAD_FLAG,DOC_UPLOAD_FLAG :"+ DOC_UPLOAD_FLAG+ " "+ DOWNLOAD_REMARKS + " "+DOC_UPLOAD_FLAG);		

					if(DOC_NAME.equalsIgnoreCase("PROPOSAL FORM")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("AADHAAR CARD STANDARD")|| DOC_NAME.equalsIgnoreCase("PAN CARD"))
						dbDoclist.add("KYC");

					if(DOC_NAME.equalsIgnoreCase("PROP_M645") || DOC_NAME.equalsIgnoreCase("OTH_M645")||DOC_NAME.equalsIgnoreCase("CUSTOMER DECLARATION FORM")){
						dbDoclist.add("CDF");
					}
					if(DOC_NAME.equalsIgnoreCase("RECENT_PHOTOGRAPH")){
						//dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("RECEIPT")){
						//dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("BI")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("D032")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("PROP_M585")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("TOP20_M105")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M329")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("KYC_M286")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M234")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M299")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("AML_M423")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("KYC_M726")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M200")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M202")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M328")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("PHOTO_M543")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M294")){
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("TOP20_M108")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("m073")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("OTH_M505")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("AML_M516")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M084")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("KYC_M533")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("OTH_M622")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("KYC docs")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("OTH_M504")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("OTH_M686")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M331")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M330")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M253")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("AML_M563")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M295")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M121")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("KYC_M689")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("AML_M517")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("M186")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					if(DOC_NAME.equalsIgnoreCase("OTH_M533")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
					
					if(DOC_NAME.equalsIgnoreCase("M017")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}

					if(DOC_NAME.equalsIgnoreCase("OTH_M017")){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
						dbDoclist.add(DOC_NAME);
					}
				

				}
				Set<String> hs = new HashSet<String>();
				hs.addAll(dbDoclist);
				dbDoclist.clear();
				dbDoclist.addAll(hs);
			}	

			
			System.out.println(dbDoclist);
			return dbDoclist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(dbConnection!=null){
					dbConnection.close();
				}
				if(rs!=null){
					rs.close();
				}
				if(preparedStatement!=null){
					preparedStatement.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return dbDoclist;




	}
	public static  ArrayList<String> getListOfFromOmniDocDownloader(ArrayList <String>  dbDoclist)
	{
		TestConnection testConnection = new TestConnection();
		Connection dbConnection = testConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs=null;


		String insertTableSQL = "select distinct DOC_NAME from balic.AZBJ_OMNI_DOC_DOWNLOADER  where source_module='USFB' and DATACLASS='PolicyDocs' ";
		try {
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			

			rs = preparedStatement.executeQuery();
			while(rs.next()){
				
				String DOC_NAME=rs.getString("DOC_NAME");
				System.out.println("DOC_NAME ::: "+DOC_NAME);
				dbDoclist.add(DOC_NAME);
			}	

			
			System.out.println(dbDoclist);
			return dbDoclist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(dbConnection!=null){
					dbConnection.close();
				}
				if(rs!=null){
					rs.close();
				}
				if(preparedStatement!=null){
					preparedStatement.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return dbDoclist;




	}

}
