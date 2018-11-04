package com.bluetooth.change;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LastNumberFinder {

	public static int max=1;
	public static String sourcePath = "D:\\FreshCheckout\\MUTE_workspace\\Testcases\\smd";
	public static String checkPaths[] = {"D:\\FreshCheckout\\MUTE_workspace\\Testcases\\smd"};
	public static String snfPath = "D:\\Validation_Suites\\MUTE_5.0\\MUTE_workspace\\Testcases\\snf";
	public static void main(String args[]){
		
		
		
		File sourceDir=new File(sourcePath);
		ArrayList<File> listOfSmd=new ArrayList<File>(Arrays.asList(sourceDir.listFiles()));
		
		ArrayList<String> listOfNewFileNames = new ArrayList<String>();
		ArrayList<String> listOfOldFileNames = new ArrayList<String>();
		
		HashMap<String,Integer> cmdMap = new HashMap<String,Integer>();
 		
		String cmdName = "";
		
		int flag=0;
		
		String content="";
		
		for(File f:listOfSmd){
			cmdName=(f.getName().split("_")[1].contains("0x")||f.getName().split("_")[0].equals("CONFIG")||f.getName().split("_")[0].equals("DELAY"))?f.getName().split("_")[0]:f.getName().split("_")[0]+"_"+f.getName().split("_")[1];
			//listOfOldFileNames.add(f.getName());
			flag=0;
			for(int i=0;i<checkPaths.length;i++){
				File checkDir=new File(checkPaths[i]);
				ArrayList<File> listOfOldSmd=new ArrayList<File>(Arrays.asList(checkDir.listFiles()));
				
				if(!cmdMap.containsKey(cmdName)){
					
					for(File cf:listOfOldSmd){
						//System.out.println(cf.getName()+"								"+cf.getAbsolutePath());
						
						String cmdName2=(cf.getName().split("_")[1].contains("0x")||cf.getName().split("_")[0].equals("CONFIG")||cf.getName().split("_")[0].equals("DELAY"))?cf.getName().split("_")[0]:cf.getName().split("_")[0]+"_"+cf.getName().split("_")[1];
						if(cmdName.equals(cmdName2)){
							try{
								int number = Integer.parseInt(cf.getName().split("_")[cf.getName().split("_").length - 1].split("\\.")[0]);
							
								max=(max>number)?max:number;
							}catch(NumberFormatException e){
								max=max;
								continue;
							}
						
						}
						
					}
					flag=1;
				}
			}
			
			if(flag==1){
				cmdMap.put(cmdName, max);
				max=1;
				System.out.println(cmdName+"-->"+cmdMap.get(cmdName));
				content+=cmdName+","+cmdMap.get(cmdName)+"\n";
				
			}
		}
		
		try {
			File report=new File("C:\\Users\\M1037549\\Desktop\\smdnumbers.csv");
			FileWriter fr=new FileWriter(report);
			fr.write(content);
			fr.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
