package com.ralitski.art.core;

import com.ralitski.art.core.cmd.CommandMark;

//https://github.com/XyPhoGR/Art/commit/1430699742d53cefd59b978759085a64878bf7de
public class CmdGui {
	
	@CommandMark(name = "gui",
			aliases = {"g"},
			usage = "[code|script]",
			help = {"Opens a GUI.","The optional \"code\" and \"script\" arguments open GUIs listing the loaded artists."})
	public void onGui(String alias, String args, Controller controller) {
		if(args.equals("code")) {
			openCode(controller);
		} else if(args.equals("script")) {
			openScript(controller);
		} else {
			open(controller);
		}
	}
	
	private void openCode(Controller controller) {
		
	}
	
	private void openScript(Controller controller) {
		
	}
	
	private void open(Controller controller) {
		
	}

}
