package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MetadataCorrector {
	
	public static void main(String args[]){
		
		File csv = new File("C:\\Users\\M1037549\\Desktop\\SNFIM.csv");
		try {
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String record, fname, tb;
			while((record=br.readLine()) != null){
				
				fname = record.split(",")[0];
				tb = record.split(",")[1];

				File snf = new File("D:\\Validation_Suites\\MUTE_5.0\\MUTE_workspace\\Testcases\\snf\\"+fname);
				BufferedReader snfbr = new BufferedReader(new FileReader(snf));
				String line,content="";
				System.out.println("Changing "+fname);
				while((line=snfbr.readLine())!=null){
					if(line.contains("% Purpose")||line.contains("%Purpose")){
						line = "%Test Purpose"+line.split("Purpose")[1];
					}
					if(line.contains("Tester Behavior")&&line.contains("--NA--")){
						line = line.split("--NA--")[0]+tb;
					}
					content += line+"\n";
					
				}
				FileWriter fw = new FileWriter(snf);
				fw.write(content);
				fw.close();
				snfbr.close();
			}
			br.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
