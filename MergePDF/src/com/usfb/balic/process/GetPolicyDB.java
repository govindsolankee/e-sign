package com.usfb.balic.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.usfb.balic.connection.TestConnection;



public class GetPolicyDB {

	final static Logger log = Logger.getLogger(GetPolicyDB.class);
	
	public static List<String> getPolicyNo(){
		
		log.info("Enter into getPolicyNo() method of GetPolicyDB");
		Connection con = TestConnection.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		//String sql = "select * from ddt_1 a, balic.pdbdocument b where A.FOLDDOCINDEX = B.DOCUMENTINDEX and upper(field_7) ="+aaa;
		String sql ="SELECT policy_ref FROM ocp_policy_versions a," +
				"azbj_policy_bases_ext b,azbj_nbtab_activity_dtls c," +
				"ocp_policy_bases d WHERE a.contract_id = b.contract_id AND" +
				" a.top_indicator = 'Y' AND b.action_code <> 'D' AND " +
				"b.top_indicator = 'Y' AND d.action_code <> 'D' AND " +
				"d.top_indicator = 'Y' AND a.contract_id = d.contract_id " +
				"AND sign_card_no = c.application_no AND c.version_no = 1" +
				" AND a.version_no IN (1, 2) AND module_flag = 'USFB' " +
				"AND change_description = 'ISSUED' AND NOT EXISTS " +
				"(SELECT 1 FROM azbj_logistics_shipments WHERE" +
				"  policy_no = policy_ref)";
		//String sql = "select policyno from merge_pdf";
		String policyNo = null;
		List<String> listOfPolNo = new ArrayList<String>();
		try {
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()){
				policyNo = rs.getString("policy_ref");
				System.out.println("*******policyNo*******" +policyNo);
				log.info("*******policyNo*******" +policyNo);
				listOfPolNo.add(policyNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(con!=null){
					con.close();
				}
				if(rs!=null){
					rs.close();
				}
				if(st!=null){
					st.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		System.out.println("Size of the list is : --->"+listOfPolNo.size());
		
		return listOfPolNo;
	}
	
	
	public static void main(String[] args) {
		List<String> list =getPolicyNo();
		int count=0;
		for(String s:list){
			System.out.println(s);
			count++;
		}
		System.out.println(count);
	}
}
