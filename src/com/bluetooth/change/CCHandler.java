package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;


import java.util.ArrayList;

public class CCHandler {

	public static void main(String[] args) {
		
		try {
			BufferedReader listFile = new BufferedReader(new FileReader(new File("C:\\Users\\M1037549\\Desktop\\listOfCC.csv")));
			Object o[] = listFile.lines().toArray();
			String list[] = Arrays.copyOf(o, o.length, String[].class);
			listFile.close();
			
			for(String scenarioName:list){
				
				String names[] = {scenarioName.replace("BV", "RAND_BV"),
						scenarioName.replace("BV", "RPAP_BV"),
						scenarioName.replace("BV", "RPAR_BV"),
						scenarioName.replace("BV", "NRPA_BV"),
				};
				
				for(String subName:names){
					
					BufferedReader br = new BufferedReader(new FileReader(new File("D:\\AE_Converted_Suites\\SVN_Checkout_finally\\MUTE_workspace\\Testcases\\snf\\"+subName)));
					o = br.lines().toArray();
					String smds[] = Arrays.copyOf(o, o.length, String[].class);
					br.close();
										
					for(String smd:smds){
						if(smd.trim().startsWith("%")||!smd.trim().contains(".smd")) continue;
						
						br = new BufferedReader(new FileReader(new File("D:\\AE_Converted_Suites\\SVN_Checkout_finally\\MUTE_workspace\\Testcases\\smd\\"+smd)));
						o = br.lines().toArray();
						ArrayList<String> lines = new ArrayList<String>(Arrays.asList(Arrays.copyOf(o, o.length, String[].class)));
						br.close();
						boolean write = false;
						for(int i=0;i<lines.size();i++){
							if(lines.get(i).trim().startsWith("%")) continue;
							
							if(lines.get(i).contains("GET")){
								int k = i;
								for(int j=i+1;j<lines.size();j++){
									if(lines.get(j).contains("COMPARE"))
										k = j;
									if(lines.get(j).contains("GET"))
										break;
								}
								
								ArrayList<String> labels = new ArrayList<String>();
								
								for(int j=i+1;j<k;j++){
									if(lines.get(j).contains("0x")){
										if(!labels.contains(lines.get(j))){
											labels.add(lines.get(j));
											
										}
										lines.remove(j--);
										k--;										
									}
								}
								if(labels.size()!=0){
									lines.addAll(i, labels);
									write = true;
								}
							}
							
							
						}
												
						if(write){
							
							o = lines.toArray();
							FileWriter fw=new FileWriter(new File("D:\\AE_Converted_Suites\\SVN_Checkout_finally\\MUTE_workspace\\Testcases\\smd\\"+smd));
							fw.write(String.join("\r\n", Arrays.copyOf(o, o.length, String[].class))+"\r\n");
							fw.close();
						}

					}
					
				}
				
				
			}
			
			
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		

	}

}
