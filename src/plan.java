import java.util.ArrayList;

public class plan {
	
	formula_static action;
	ArrayList<GoalNode> subgoals = new ArrayList<GoalNode>();
	
	public plan(formula_static f)
	{
		action = new formula_static(f);
	}
}
