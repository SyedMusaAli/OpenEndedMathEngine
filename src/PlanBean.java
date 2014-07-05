import java.util.ArrayList;

public class PlanBean {
	
	StaticFormula action;
	ArrayList<GoalNode> subgoals = new ArrayList<GoalNode>();
	
	public PlanBean(StaticFormula f)
	{
		action = new StaticFormula(f);
	}
}
