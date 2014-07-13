package mathengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileWriter1 {
	
	public FileWriter1(String fname) throws IOException
	{
		FileWriter f2;
		BufferedWriter bw;
		FileReader fr ;
		BufferedReader br;
		f2 = new FileWriter(fname,true);//append mode
		bw = new BufferedWriter(f2);//create file
		fr = new FileReader(fname);
		br = new BufferedReader(fr);
		
	}

	public void SaveKB(String string, String fname)
	{
		try {
			FileWriter f2 = new FileWriter(fname,true);//append mode
			BufferedWriter bw = new BufferedWriter(f2);//create file
			bw.write(string);
			//System.out.println((buffer.toString()));
			bw.newLine();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	public void AppendKB(String string, String fname)
	{
		FileWriter f2;
		BufferedWriter bw;
		try{
			  // Create file 
			  f2 = new FileWriter(fname,true);
			  bw = new BufferedWriter(f2);
			  bw.write(string);
			  bw.newLine();
			  //Close the output stream
			  bw.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	public void RecallKB(String fname) throws IOException{
		FileReader fr = new FileReader(fname);;
		BufferedReader br = new BufferedReader(fr);
		String s;
		while((s = br.readLine()) != null) {
				SaveKB(s,"result.txt");
		}
		fr.close();
		
		
	}
	
	public boolean FindKBbool(String str, String fname) throws IOException{
		FileReader fr = new FileReader(fname);
		BufferedReader br = new BufferedReader(fr);
		String s="";
		Boolean flag=false;
		while((s=br.readLine()) !=null) {
			
			System.out.println("check"+s);
			if(s.equals(str))
			{
				flag=true;
				break;
			}
		}
		fr.close();
		return flag;
	}

}
