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
import java.util.Set;



public class SmdDuplicates {
	public static void main(String args[]){

		generateCode("D:\\Feature Dependant\\MUTE_workspace\\Testcases\\smd\\CE_SEAE_0x1_0x1_0x0_0x0_0x0_INF2_EAR_INF1_BV_0045.smd");
	}


	public static void main2(String[] args){
		
		ArrayList<String> smdList = new ArrayList<String>();
		HashMap<String,Integer> removeMap = new HashMap<String,Integer>();
		String line;
		String smdPath="D:\\Validation_Suites\\MUTE_5.0\\MUTE_workspace\\Testcases\\smd";
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File("C:\\Users\\M1037549\\Desktop\\simillarSmds.txt")));
			while((line=br.readLine())!=null){
				smdList.add(line);
			}
			br.close();
			for(int i=0;i<smdList.size()-1;i++){
				for(int j=i+1;j<smdList.size();j++){
					//System.out.println("a"+i+"-"+j);
					if((smdList.get(i).substring(0, smdList.get(i).lastIndexOf("_")).equals(smdList.get(j).substring(0, smdList.get(j).lastIndexOf("_"))))
							&&(generateCode(smdPath+"\\"+smdList.get(i)).equals(generateCode(smdPath+"\\"+smdList.get(j))))){
						System.out.println("b"+i+"-"+j);
						removeMap.put(smdList.get(j), i);
						smdList.remove(j--);
					}
				}
			}
				
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		String content="Retain,Remove\n";
		for(String key:removeMap.keySet()){
			
			System.out.println(key+"  <--  "+smdList.get(removeMap.get(key)));
			
		}
		
		for(int i=0;i<smdList.size();i++){
			content+=smdList.get(i);
			for(String key:removeMap.keySet()){
				if(removeMap.get(key)==i){
					content+=","+key+"\n";
				}
			}
			content+="\n";
		}
		try {
			File report=new File("C:\\Users\\M1037549\\Desktop\\smdlistredone.csv");
			FileWriter fr=new FileWriter(report);
			fr.write(content);
			fr.close();
			report.renameTo(new File("C:\\Users\\M1037549\\Desktop\\smdlistredone.xlsx"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
			System.out.println();
	
	}
	
	public static String generateCode(String file){
		String line, code="", prevState = "";
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File(file)));
			code = "";
			HashMap<String, Boolean> states=new HashMap<String, Boolean>();		
			while((line=br.readLine())!=null){
					if(line.trim().endsWith(":")){
						prevState = line.trim().split(":")[0];
						states.put(prevState, false);
						continue;	
					}
					if(line.contains("_EXT")){
						states.put(prevState, true);
					}
			}
			br.close();
			br=new BufferedReader(new FileReader(new File(file)));
			while((line=br.readLine())!=null){
				System.out.println(line);
				if(line.trim().startsWith("%")||line.trim()==null)
					continue;
				if(line.trim().endsWith(":")){
					if(states.get(line.trim().substring(0, line.lastIndexOf(":")))){
						while(!(line=br.readLine()).trim().endsWith(":"))
							System.out.println(line);;
					}
					continue;
				}
				if(line.contains("START_TC")||line.contains("LOG"))
					continue;
				if(line.contains("HCI_READ_LOCAL_VERSION_INFO")){
					while(!(line=br.readLine()).trim().endsWith(":"));
				}
				else{
					
					for(int j=0;j<states.keySet().size();j++){
						if(line.split((String) states.keySet().toArray()[j]).length>1)
							line=line.split((String) states.keySet().toArray()[j])[0]
									+line.split((String) states.keySet().toArray()[j])[1];
						
					}
					
					line = line.trim().replaceAll(" ", "");
					line = line.trim().replaceAll("\n", "");
					line = line.trim().replaceAll("\t", "");
					code+=line;
					
				}
			}
			br.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}
}
