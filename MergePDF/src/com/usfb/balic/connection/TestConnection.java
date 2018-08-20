package com.usfb.balic.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

	public static  Connection getConnection()
	{
		Connection conn=null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");		
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@10.3.5.113:1521:OPUSUAT7","CUSTOMER","cust_uat7_2o17");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.3.3.171:1521:PRODN1","IT_40471","aug_2018");
			System.out.println("Connection is :"+conn);		
		}catch(Exception e)
		{
			e.printStackTrace(); 
		}
		return conn;
	}
	public static void main(String args[]){
	getConnection();}
}
