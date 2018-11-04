package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

public class AddressChange {
	
	public static void main(String[] args) {
		File smdDir = new File("D:\\AE_Converted_Suites\\SVN_Checkout_finally\\MUTE_workspace\\Testcases\\smd");
		File[] files =smdDir.listFiles();
		int counter = 0;
		boolean res= false;
		for(File smd:files){
			res=false;
			try {
				BufferedReader br = new BufferedReader(new FileReader(smd));
				Object o[] = br.lines().toArray();
				String list[] = Arrays.copyOf(o, o.length, String[].class);
				
				for(int i=0;i<list.length;i++){
					if(list[i].contains("GET")&&list[i].contains("INF_1")&&list[i-1].contains("ADDRESS")){
						for(i=i+1;i<list.length;i++){
							if(list[i].contains("CONSTRUCT")){
								res=true;
								
							}else if(list[i].contains("COMPARE")){
								break;
							}
						}
						if((res==true||((i<list.length)&&list[i].contains("COMPARE"))))
							break;
					}
				}
				if(res==true)
					System.out.println((++counter)+". "+smd.getName());
				
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			
			
		}
		
	}

}
