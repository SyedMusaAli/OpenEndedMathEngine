import java.util.ArrayList;


public class GoalNode {
		
	public String tag;
	public ArrayList<GoalNode> subgoal=new ArrayList<GoalNode>();
	public plan chosenPlan;
	public boolean achieved;
	public String Answer;
	public ArrayList<plan> APL = new ArrayList<plan>();
	
	GoalNode(String str)
	{
		tag = str;
		Answer = null;
		achieved = false;
	}
	
}
