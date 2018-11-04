package com.bluetooth.change;

import java.util.ArrayList;

public class CommandVariants {

	public String CommandName;
	public ArrayList<String> variants;
	
	public CommandVariants(String name){
		CommandName=name;
		variants=new ArrayList<String>();
	}

	@Override
	public String toString() {
		return "CommandVariants [CommandName=" + CommandName + ", variants=" + variants + "]";
	}
	
}
