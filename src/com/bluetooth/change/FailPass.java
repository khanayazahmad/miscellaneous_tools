package com.bluetooth.change;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FailPass {
	
	public static void main(String args[]){
	
		ArrayList<String> checkList = new ArrayList<String>();
		ArrayList<File> checkDocs = new ArrayList<File>();
		boolean fail = false;
		int flag=0;
		File checkDir[] = {new File("D:\\logs\\flog")};
		for(int i=0;i<checkDir.length;i++){
			ArrayList<File> listOfLogs = new ArrayList<File>(Arrays.asList(checkDir[i].listFiles()));
		
			for(File log:listOfLogs){
				if(log.getName().contains("SMM")){
					System.out.println(log.getName());
					checkDocs.add(log);
				}
			}
		}
		
		
		String line,content="";
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File("D:\\list.csv")));
			while((line=br.readLine())!= null&&line.contains("snf")){
				System.out.println(line);
				checkList.add(line);
			}
			br.close();
			for(int i=0;i<checkList.size();i++){

				for(int j=0;j<checkDocs.size();j++){
					fail=false;
					if(content.contains(checkList.get(i)+",RUN,PASSED,"))
						break;

					br=new BufferedReader(new FileReader((checkDocs.get(j))));
					do{
						
						boolean reset_status = true;
						while((line=br.readLine())!=null&&!line.contains(checkList.get(i))){
							if(line.contains("LE_INIT_5.0_MT_INF1_MT_INF2.snf]")){
								while((line=br.readLine())!=null&&!line.contains("C_SEM_ALL_INF2_BV_")){
									if(line.contains("FAILED")||line.contains("INCONCLUSIVE")){
										reset_status=false;
										break;
									}
								}
								if(line.contains("PASSED")){
									reset_status=true;
								}
							}
						}
						if(line!=null){
							System.out.println("Found "+checkList.get(i)+" in "+checkDocs.get(j).getName());
							String space = line.substring(line.indexOf(']'), line.indexOf(checkList.get(i)));
							line=br.readLine();
							if((line=br.readLine())!=null&&reset_status==false&&(line).contains("FAILED")){
								if(!content.contains(checkList.get(i))){
									System.out.println(checkList.get(i)+",DID NOT RUN,STUCK,\n");
									content += checkList.get(i)+",DID NOT RUN,STUCK,\n";
									fail=true;
								}
								continue;
							}
							
							if(line!=null&&(line.contains("LC.snf")||line.contains("AEC.snf")||line.contains("PRIVACY_AE.snf")||line.contains("PRIVACY.snf"))){
								while((line=br.readLine())!=null&&!line.contains("C_RLS"));
								if(line.contains("FAILED")||line.contains("INCONCLUSIVE")){
									String spc=line.substring(line.indexOf("]"),line.lastIndexOf("[")+1);
									while((line=br.readLine())!=null&&!line.contains(spc)&&!line.contains(space));
								}
							}

							
							if(!line.contains(space+"LE_INIT_5.0_MT_INF1_MT_INF2.snf]")){
								System.out.println(line+" 3");
								while((line=br.readLine())!=null&&!line.contains(space+"LE_INIT_5.0_MT_INF1_MT_INF2.snf]")&&(line.contains(".snf")||
										line.contains("PASSED")||line.contains("FAILED")||line.contains("INCONCLUSIVE")||(line!=null&&line.trim().equals("")))){
//									System.out.println("Coming in main loop\n"+line);
									if(line!=null&&(line.contains("LC.snf")||line.contains("AEC.snf")||line.contains("PRIVACY_AE.snf")||line.contains("PRIVACY.snf"))){
										while((line=br.readLine())!=null&&!line.contains("C_RLS"));
										if(line.contains("FAILED")||line.contains("INCONCLUSIVE")){
											String spc=line.substring(line.indexOf("]"),line.lastIndexOf("[")+1);
											while((line=br.readLine())!=null&&!line.contains(spc)&&!line.contains(space));
										}
									}
								
									if(line.contains(space))break;
									
									System.out.println("Before fail: "+line);
									if(line!=null&&line.contains("FAILED")||line.contains("INCONCLUSIVE")){
										System.out.println("Failed "+checkList.get(i));
										fail=true;
										if(content.contains(checkList.get(i)+",DID NOT RUN,STUCK,")){
											System.out.println("content.replace(checkList.get(i)+,DID NOT RUN,STUCK, checkList.get(i)+RUN,FAILED)");
											content = content.replace(checkList.get(i)+",DID NOT RUN,STUCK,", checkList.get(i)+",RUN,FAILED,");
										}else if(!(content.contains(checkList.get(i)+",RUN,PASSED,"))&&!(content.contains(checkList.get(i)+",RUN,FAILED,\n"))){
											System.out.println("content += checkList.get(i)+,RUN,FAILED\n");
											content += checkList.get(i)+",RUN,FAILED,\n";
											
											
											flag=1;
										}
										break;
									}
			
								}
								if(line!=null&&(line.contains("FAILED")||line.contains("INCONCLUSIVE"))&&!content.contains(checkList.get(i)+",RUN,PASSED")&&flag==1&&reset_status==false){
												System.out.println("content.replace(checkList.get(i)+RUN,FAILED, checkList.get(i)+,DID NOT RUN,STUCK)");
												content = content.replace(checkList.get(i)+",RUN,FAILED,", checkList.get(i)+",DID NOT RUN,STUCK");

								}

							
									
							if(line==null||fail==false){
								System.out.println(checkList.get(i)+" passed in "+checkDocs.get(j));
								
								if(content.contains(checkList.get(i)+",DID NOT RUN,STUCK,")){
									System.out.println("c1");
									content = content.replace(checkList.get(i)+",DID NOT RUN,STUCK,", checkList.get(i)+",RUN,PASSED,"+checkDocs.get(j).getName());
								}else if(content.contains(checkList.get(i)+",RUN,FAILED,")){
									System.out.println("c2");
									content = content.replace(checkList.get(i)+",RUN,FAILED,", checkList.get(i)+",RUN,PASSED,"+checkDocs.get(j).getName());
								}else{
									content += checkList.get(i)+",RUN,PASSED,"+checkDocs.get(j).getName()+"\n";
									System.out.println("c3");
								}
								break;
							}

						}
						
						}
					}while((line)!=null&&fail==true);
					
				}
				br.close();
				if(!content.contains(checkList.get(i)))
					content += checkList.get(i)+",DID NOT RUN,NO LOGS EXIST\n";
			}
			
			FileWriter fw = new FileWriter("C:\\Users\\M1037549\\Desktop\\output_ae.csv");
			fw.write(content);
			fw.close();

			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
