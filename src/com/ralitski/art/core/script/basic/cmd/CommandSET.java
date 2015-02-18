package com.ralitski.art.core.script.basic.cmd;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.core.script.Program;
import com.ralitski.art.core.script.ValueList;
import com.ralitski.art.core.script.basic.Command;
import com.ralitski.art.core.script.exception.VariableNotFoundException;

public class CommandSET implements Command {

	@Override
	public String name() {
		return "SET";
	}

	@Override
	public void execute(Program program, String arguments) {
		String[] a = arguments.split(" ");
		ValueList vars = program.getData().getObject("$VARIABLES");
		if(a.length == 3) {
			//x y color
			String x = a[0];
			String y = a[1];
			String color = a[2];
			int xInt = getInt(x, vars);
			int yInt = getInt(y, vars);
			int colorInt = getInt(color, vars);
			doExecute(program, xInt, yInt, colorInt);
		} else if(a.length == 5) {
			//x y r g b
			String x = a[0];
			String y = a[1];
			String r = a[2];
			String g = a[3];
			String b = a[4];
			int xInt = getInt(x, vars);
			int yInt = getInt(y, vars);
			int rInt = getInt(r, vars);
			int gInt = getInt(g, vars);
			int bInt = getInt(b, vars);
			int color = 0xFF000000 | (rInt << 16) | (gInt << 8) | bInt;
			doExecute(program, xInt, yInt, color);
		} else if(a.length == 6) {
			//x y r g b a
			String x = a[0];
			String y = a[1];
			String r = a[2];
			String g = a[3];
			String b = a[4];
			String alpha = a[5];
			int xInt = getInt(x, vars);
			int yInt = getInt(y, vars);
			int rInt = getInt(r, vars);
			int gInt = getInt(g, vars);
			int bInt = getInt(b, vars);
			int aInt = getInt(alpha, vars);
			doExecute(program, xInt, yInt, (aInt << 24) | (rInt << 16) | (gInt << 8) | bInt);
		}
	}
	
	private void doExecute(Program program, int x, int y, int color) {
		ValueList vars = program.getData().getObject("$SYS");
		Object o = vars.get("canvas");
		if(o instanceof ArtCanvas) {
			ArtCanvas canvas = (ArtCanvas)o;
			canvas.setColor(x, y, color);
		} else {
			//what did you do?
			throw new VariableNotFoundException("Art Canvas not found.");
		}
	}
	
	private int getInt(String var, ValueList vars) {
		int i;
		try {
			i = Integer.parseInt(var);
		} catch (NumberFormatException e) {
			if(vars.has(var)) {
				i = vars.getInt(var);
			} else {
				throw new VariableNotFoundException("Variable \"" + var + "\" not found");
			}
		}
		return i;
	}
	
}
