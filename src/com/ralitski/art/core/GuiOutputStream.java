package com.ralitski.art.core;

import java.io.IOException;
import java.io.OutputStream;

public class GuiOutputStream extends OutputStream {
	
	private Controller controller;

	public GuiOutputStream(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void write(int b) throws IOException {
		char c = (char)b;
		controller.getGui().log(""+c);
	}

}
