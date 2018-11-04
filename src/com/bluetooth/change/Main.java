package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Set;

public class Main {

	public static String smdPath = "D:\\MUTE_workspace\\Testcases\\smd";
	public static String snfPath = "D:\\MUTE_workspace\\Testcases\\snf";

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

	public static void main1(String args[]) {
		File folder = new File(smdPath);
		File[] listOfFiles = folder.listFiles();

		int count = 0;
		ArrayList<String> listOfNewFileNames = new ArrayList<String>();
		ArrayList<String> listOfOldFileNames = new ArrayList<String>();
		String cmdPrevName = "";
		for (File curFile : listOfFiles) {

			String name = curFile.getName();
			listOfOldFileNames.add(name);
			try {
				int number = Integer.parseInt(name.split("_")[name.split("_").length - 1].split("\\.")[0]);
			} catch (Exception e) {
				System.out.println(e);
				listOfNewFileNames.add(name);
				continue;
			}

			String cmdCurName = name.split("_")[1];
			if (cmdCurName.equals(cmdPrevName))
				count++;
			else {
				cmdPrevName = cmdCurName;
				count = 1;
			}
			String[] nameArr = name.split("_");
			nameArr[nameArr.length - 1] = "" + count;
			while (nameArr[nameArr.length - 1].length() != 4)
				nameArr[nameArr.length - 1] = "0" + nameArr[nameArr.length - 1];
			nameArr[nameArr.length - 1] += ".smd";
			name = nameArr[0];
			for (int i = 1; i < nameArr.length; i++) {
				name += "_" + nameArr[i];
			}
			listOfNewFileNames.add(name);

		}

		for (int i = 0; i < listOfOldFileNames.size(); i++)
			System.out.println(listOfNewFileNames.get(i) + "	-->	" + listOfOldFileNames.get(i));

		SmdChangerInSnf(listOfNewFileNames, listOfOldFileNames);
		while (!listOfOldFileNames.isEmpty()) {
			for (int i = 0; i < listOfOldFileNames.size(); i++) {
//				System.out.println(listOfNewFileNames.get(i) + "-->" + listOfOldFileNames.get(i));

				File oldfile = new File(smdPath + "\\" + listOfOldFileNames.get(i));
				File newfile = new File(smdPath + "\\" + listOfNewFileNames.get(i));

				if (oldfile.renameTo(newfile)) {
//					System.out.println("Rename succesful");
					listOfOldFileNames.remove(i);
					listOfNewFileNames.remove(i);
				} else {
//					System.out.println("Rename failed for " + listOfOldFileNames.get(i));
				}
			}

		}
	}
	
	public static void main(String[] args) {
		
		double a = 295.04;
		byte c = (byte)a;
		System.out.println(4);
	}

}
