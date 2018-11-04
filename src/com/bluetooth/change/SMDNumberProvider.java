package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SMDNumberProvider {
	
	
	public static int max=1;
	public static String sourcePath = "D:\\CheckInToday\\Master_Testsuite\\MUTE_workspace\\Testcases\\smd";
	public static String checkPaths[] = {"D:\\Validation_Suites\\MUTE_5.0\\MUTE_workspace\\Testcases\\smd"};
	public static String snfPath = "D:\\CheckInToday\\Master_Testsuite\\MUTE_workspace\\Testcases\\snf";
	
	public static void SmdChangerInSnf(ArrayList<String> listOfNewFileNames, ArrayList<String> listOfOldFileNames) {

		FileReader fr = null;
		File snffolder = new File(snfPath);
		File[] listOfsnf = snffolder.listFiles();
		for (File curSnf : listOfsnf) {
			try {

				fr = new FileReader(curSnf);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String s, totalStr = "";
			try {
				
				BufferedReader br = new BufferedReader(fr);
				while ((s = br.readLine()) != null) {
					for (int i = 0; i < listOfOldFileNames.size(); i++) {
						if (s.contains(listOfOldFileNames.get(i))) {
							
							s = listOfNewFileNames.get(i);
							break;
						}

					}
					totalStr += s + "\n";
				}
				FileWriter fw = new FileWriter(curSnf);
				fw.write(totalStr);
				fw.close();
				
			} catch (Exception e) {
				System.out.println("Unsuccessful change in snf");

			}

		}

	}
	
	
	public static void main(String[] args) {
		
		File sourceDir=new File(sourcePath);
		ArrayList<File> listOfSmd=new ArrayList<File>(Arrays.asList(sourceDir.listFiles()));
		
		ArrayList<String> listOfNewFileNames = new ArrayList<String>();
		ArrayList<String> listOfOldFileNames = new ArrayList<String>();
		
		HashMap<String,Integer> cmdMap = new HashMap<String,Integer>();
 		
		String cmdName = "";
		
		int flag=0;
		
		String content="";
		
		for(File f:listOfSmd){
			cmdName=f.getName().split("_")[0]+"_"+f.getName().split("_")[1];
			listOfOldFileNames.add(f.getName());
			flag=0;
			for(int i=0;i<checkPaths.length;i++){
				File checkDir=new File(checkPaths[i]);
				ArrayList<File> listOfOldSmd=new ArrayList<File>(Arrays.asList(checkDir.listFiles()));
//				if(listOfOldSmd.contains(f)){
//					System.out.println("Duplicate Found. File deleted : "+f.getName());
//					f.delete();
//					break;
//				}
//				else 
				if(cmdMap.containsKey(cmdName)){
					String name=f.getName();
					String[] nameArr = name.split("_");
					nameArr[nameArr.length - 1] = "" + (cmdMap.get(cmdName)+1);
					cmdMap.put(cmdName, (cmdMap.get(cmdName)+1));
					while (nameArr[nameArr.length - 1].length() != 4)
						nameArr[nameArr.length - 1] = "0" + nameArr[nameArr.length - 1];
					nameArr[nameArr.length - 1] += ".smd";
					name = nameArr[0];
					for (int j = 1; j < nameArr.length; j++) {
						name += "_" + nameArr[j];
					}
					listOfNewFileNames.add(name);
					break;
				}
				else{
					for(File cf:listOfOldSmd){
						//System.out.println(cf.getName()+"								"+cf.getAbsolutePath());
						if(cmdName.equals(cf.getName().split("_")[0]+"_"+cf.getName().split("_")[1])){
							int number = Integer.parseInt(cf.getName().split("_")[cf.getName().split("_").length - 1].split("\\.")[0]);
							max=(max>number)?max:number;
						
						}
						
					}
					flag=1;
				}
			}
			
			if(flag==1){
				cmdMap.put(cmdName, max);
				max=1;
				System.out.println(cmdName+"-->"+cmdMap.get(cmdName));
				content+=cmdName+","+cmdMap.get(cmdName);
				String name=f.getName();
				String[] nameArr = name.split("_");
				nameArr[nameArr.length - 1] = "" + (cmdMap.get(cmdName)+1);
				cmdMap.put(cmdName, (cmdMap.get(cmdName)+1));
				while (nameArr[nameArr.length - 1].length() != 4)
					nameArr[nameArr.length - 1] = "0" + nameArr[nameArr.length - 1];
				nameArr[nameArr.length - 1] += ".smd";
				name = nameArr[0];
				for (int j = 1; j < nameArr.length; j++) {
					name += "_" + nameArr[j];
				}
				listOfNewFileNames.add(name);
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
		
		for (int i = 0; i < listOfOldFileNames.size(); i++)
			System.out.println(listOfNewFileNames.get(i) + "	-->	" + listOfOldFileNames.get(i));
		
		
		SmdChangerInSnf(listOfNewFileNames, listOfOldFileNames);
		while (!listOfOldFileNames.isEmpty()) {
			for (int i = 0; i < listOfOldFileNames.size(); i++) {
//				System.out.println(listOfNewFileNames.get(i) + "-->" + listOfOldFileNames.get(i));

				File oldfile = new File(sourcePath + "\\" + listOfOldFileNames.get(i));
				File newfile = new File(sourcePath + "\\" + listOfNewFileNames.get(i));

				if (oldfile.renameTo(newfile)) {
//					System.out.println("Rename successful");
					listOfOldFileNames.remove(i);
					listOfNewFileNames.remove(i);
				} else {
//					System.out.println("Rename failed for " + listOfOldFileNames.get(i));
				}
			}

		}
		
	}

}
