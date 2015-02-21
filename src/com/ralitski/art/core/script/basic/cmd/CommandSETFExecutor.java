package com.ralitski.art.core.script.basic.cmd;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.core.script.Program;
import com.ralitski.art.core.script.ValueList;
import com.ralitski.art.core.script.basic.CommandExecutor;
import com.ralitski.art.core.script.basic.var.Variable;
import com.ralitski.art.core.script.exception.VariableNotFoundException;

public class CommandSETFExecutor implements CommandExecutor {
	
	private Variable x;
	private Variable y;
	private Variable r;
	private Variable g;
	private Variable b;
	private Variable a;
	
	public CommandSETFExecutor(Variable x, Variable y, Variable r, Variable g, Variable b) {
		this(x, y, r, g, b, null);
	}
	
	public CommandSETFExecutor(Variable x, Variable y, Variable r, Variable g, Variable b, Variable a) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	@Override
	public void execute(Program program) {
		// TODO Auto-generated method stub
		ValueList obj = program.getData().getObject("$VARIABLES");
		ValueList vars = program.getData().getObject("$SYS");
		int x = (int)Math.floor(this.x.get(obj));
		int y = (int)Math.floor(this.y.get(obj));
		Object o = vars.get("canvas");
		if(o instanceof ArtCanvas) {
			ArtCanvas canvas = (ArtCanvas)o;
			if(a != null) {
				//with alpha
				canvas.setColor(x, y, r.get(obj), g.get(obj), b.get(obj), a.get(obj));
			} else {
				//full alpha
				canvas.setColor(x, y, r.get(obj), g.get(obj), b.get(obj));
			}
		} else {
			//what did you do?
			throw new VariableNotFoundException("Art Canvas not found.");
		}
	}

	@Override
	public String getPlainText() {
		return a != null ? "SET " + x + " " + y + " " + r + " " + g + " " + b + " " + a : "SET " + x + " " + y + " " + r + " " + g + " " + b;
	}

}
