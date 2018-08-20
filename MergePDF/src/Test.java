import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Test {

	static final String hostname_omni="balicdocs";
	static final String username_omni="UJIVAN_DOCS";
	static final String password_omni="Ox8Wna59K#u1Bef";
	static final String sourcepath="";
	static int port = 21;
	final static  String source="D:\\UjjivanUSFB\\destinationImages_cropped\\";
	public static void main(String[] args) throws Exception {
		
		/*
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter year for check the leap year or not :");
		int year = sc.nextInt();
		if((year%4==0) && (year%100!=0)){
			System.out.println("This is a leap year");
		}
		else if(year%100==0){
			System.out.println("This is a not leap year");
		}
		else if(year%400==0){
			System.out.println("This is a not leap year");
		}
		else{
			System.out.println("This is a not a leap year");
		}
		*/

		File f2=new File(source);
		File files[] = f2.listFiles();
		ArrayList<String> list = new ArrayList<String>();
		HashSet<String> set = new HashSet<String>();
		for(File  file:files){
			String changefname=file.getName();
			String[] splitname=changefname.split("_");
			//System.out.println(splitname[0]);
			if(!list.contains(splitname[0])) {
				list.add(splitname[0]);
			}
		}
		File f = new File("D:\\content3.txt");
	  	  BufferedReader b = new BufferedReader(new FileReader(f));
	  	  String readLine = "";
	  	  while ((readLine = b.readLine()) != null) {
	            //System.out.println(readLine);	            
	            if(!list.contains(readLine)){
	            	System.out.println(readLine);
	            }	
	  	  }
	  }
	}

