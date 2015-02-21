package com.ralitski.art.core.script.basic.cmd;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.core.script.Program;
import com.ralitski.art.core.script.ValueList;
import com.ralitski.art.core.script.basic.CommandExecutor;
import com.ralitski.art.core.script.basic.var.Variable;
import com.ralitski.art.core.script.exception.VariableNotFoundException;

public class CommandSETExecutor implements CommandExecutor {
	
	private Variable x;
	private Variable y;
	private Variable color;
	
	public CommandSETExecutor(Variable x, Variable y, Variable color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	@Override
	public void execute(Program program) {
		// TODO Auto-generated method stub
		ValueList obj = program.getData().getObject("$VARIABLES");
		ValueList vars = program.getData().getObject("$SYS");
		int x = (int)Math.floor(this.x.get(obj));
		int y = (int)Math.floor(this.y.get(obj));
		int color = (int)Math.floor(this.color.get(obj));
		Object o = vars.get("canvas");
		if(o instanceof ArtCanvas) {
			ArtCanvas canvas = (ArtCanvas)o;
			canvas.setColor(x, y, color);
		} else {
			//what did you do?
			throw new VariableNotFoundException("Art Canvas not found.");
		}
	}

	@Override
	public String getPlainText() {
		return "SET " + x + " " + y + " " + color;
	}

}
