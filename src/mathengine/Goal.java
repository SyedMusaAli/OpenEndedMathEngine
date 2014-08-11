package mathEngine;

import java.util.ArrayList;


class Goal {
		
	public String tag;
	public ArrayList<Goal> subgoal=new ArrayList<Goal>();
	public Plan chosenPlan;
	public boolean achieved;
	public String Answer;
	public ArrayList<Plan> APL = new ArrayList<Plan>();
	
	Goal(String str)
	{
		tag = str;
		Answer = null;
		achieved = false;
	}
	
}
