package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SMDFinderAndMover {

	public static String sourcePath = "D:\\Ayaz_CCO_AEN_DSU\\Master_Testsuite\\MUTE_workspace\\Testcases\\smd";
	public static String destPath = "D:\\Anshul\\smd";
	public static String snfPath = "D:\\CheckInToday\\Master_Testsuite\\MUTE_workspace\\Testcases\\snf";
	
	public static void main(String[] args) {
		
		File snfDir=new File(snfPath);
		ArrayList<File> listOfSnf=new ArrayList<File>(Arrays.asList(snfDir.listFiles()));
		
		File sourceDir=new File(sourcePath);
		ArrayList<File> listOfSmd=new ArrayList<File>(Arrays.asList(sourceDir.listFiles()));
		
		for(File snf:listOfSnf){
			String line;
			try {
				BufferedReader br=new BufferedReader(new FileReader(snf));
				while((line=br.readLine())!=null)
				{
					System.out.println("Found2");
					if(line.contains(".smd")){
						System.out.println("Found1");
						if(listOfSmd.contains(new File(sourcePath+"\\"+line.trim()))){
							System.out.println("Found");
							File from=new File(sourcePath+"\\"+line.trim());
							File to=new File(destPath+"\\"+line.trim());
							String l,content="";
							BufferedReader fr=new BufferedReader(new FileReader(from));
							while((l=fr.readLine())!=null)
								content+=l+"\n";
							FileWriter fw=new FileWriter(to);
							fw.write(content);
							fr.close();
							fw.close();
						}
					}
				}
				br.close();
			} catch (IOException e) {		
				e.printStackTrace();
			}
			
			
		}
		
		

	}

}
