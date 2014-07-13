package mathengine;

import java.util.ArrayList;

public class Plan {
	
	StaticFormula action;
	ArrayList<Goal> subgoals = new ArrayList<Goal>();
	
	public Plan(StaticFormula f)
	{
		action = new StaticFormula(f);
	}
}
