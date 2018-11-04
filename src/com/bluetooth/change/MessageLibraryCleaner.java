package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MessageLibraryCleaner {

public static void main(String args[]){
		
		
		ArrayList<CommandVariants> cmdVar = new ArrayList<CommandVariants>();
		File library = new File("D:\\Master_Test_final_final_1\\Master_Testsuite_final\\MUTE_workspace\\Messages\\LE_cmd_msg.mlb");
		File usedVariants = new File("C:\\Users\\M1037549\\Documents\\usedVariants.txt");
		String line, fileContents = "";
		String var[];
		int i;
		try {
			BufferedReader buv=new BufferedReader(new FileReader(usedVariants));
			while((line = buv.readLine())!=null){
				if(line.contains("_")){
					CommandVariants cv = new CommandVariants(line);
					line = buv.readLine();
					var = line.split(" ");
					for(String v:var)
						cv.variants.add(v.trim());
					cv.variants.remove(0);
					cmdVar.add(cv);
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(CommandVariants cv:cmdVar)
			System.out.println(cv.toString());
		
		
		
		
		
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(library));
			boolean flag = false;
			for(i=0;i<cmdVar.size();i++){
				br = new BufferedReader(new FileReader(library));
				flag = false;
				while((line=br.readLine())!=null&&!(line.contains(cmdVar.get(i).CommandName)));
				
				if(line!=null){
					fileContents+=line+"\n";
					while((line=br.readLine())!=null&&!(line.contains(cmdVar.get(i).CommandName+":")))
							fileContents += line+"\n";
					fileContents += line+"\n";
					System.out.println(line);
					while((line=br.readLine())!=null&&!line.trim().endsWith(":")){
						if(line.trim().startsWith("%")){
							flag=false;
							continue;
						}
						if(line.contains(":")&&!line.trim().endsWith(":")){
							fileContents+=line+"\n";
							flag=false;
							continue;
						}
						if(line.contains(",")){
							if(cmdVar.get(i).variants.contains(line.split(",")[0].trim())){
								fileContents+=line+"\n";
								flag=true;
							}
							else{
								flag=false;
							}
							continue;
						}
						fileContents+=line+"\n";
						
					}
					
					
				}
					
					
			}
			
			
			
			System.out.println(fileContents);
//			FileWriter fw = new FileWriter("D:\\Master_Test_final_final_1\\Master_Testsuite_final\\MUTE_workspace\\Messages\\test.txt");
//			fw.write(fileContents);
//			fw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
