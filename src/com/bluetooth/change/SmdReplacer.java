package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class SmdReplacer {

	public static void SmdChangerInSnf(String SmdFilePath,String SnfFilePath) {

		File smdfolder = new File("D:\\MUTE_TESTCASE\\MUTE_workspace\\Testcases\\smd");
		File[] listOfSmd = smdfolder.listFiles();

		int count = 0;
		for (File curSmd : listOfSmd) {
			count++;
			FileReader fr = null;
			File snffolder = new File("D:\\MUTE_TESTCASE\\MUTE_workspace\\Testcases\\snf");
			File[] listOfsnf = snffolder.listFiles();
			for (File curSnf : listOfsnf) {
				System.out.println(curSnf);
				try {

					fr = new FileReader(curSnf);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String replace = curSmd.getName() + count + "";
				
				String s,totalStr="";
				try {
					BufferedReader br = new BufferedReader(fr);
					 while ((s = br.readLine()) != null) {
				            totalStr += s+"\n";
				        }
				        totalStr = totalStr.replaceAll(curSmd.getName(), replace);
				        FileWriter fw = new FileWriter(curSnf);
				    fw.write(totalStr);
				    fw.close();
				    }
				 catch (Exception e) {

				}

			}
		}
	}
}
