import java.util.ArrayList;


public class GoalNode {
		
	public String tag;
	public ArrayList<GoalNode> subgoal=new ArrayList<GoalNode>();
	public PlanBean chosenPlan;
	public boolean achieved;
	public String Answer;
	public ArrayList<PlanBean> APL = new ArrayList<PlanBean>();
	
	GoalNode(String str)
	{
		tag = str;
		Answer = null;
		achieved = false;
	}
	
}
