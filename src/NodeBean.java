import java.util.ArrayList; 
import java.lang.Math;


public class NodeBean {
	
	public String data;
	public NodeBean right, left;
	public ArrayList<NodeBean> child;
	boolean marked;
	public boolean isFunc;
	
	public NodeBean()
	{
		right = null;
		left = null;
		child = new ArrayList<NodeBean>();
		marked = false;
		isFunc = false;
	}
	
	public NodeBean(NodeBean n)
	{
		data = n.data;
		child = new ArrayList<NodeBean>();
		for(int i = 0; i<n.child.size(); i++)
		{
			child.add(new NodeBean(n.child.get(i)));
		}
		marked = false;
		isFunc = n.isFunc;
	//	condensed = n.condensed;
	}
	

	
	ArrayList<String> getLeaves()		//reserved for StaticFormula
	{
		ArrayList<String> lev = new ArrayList<String>();
		if(child.size() == 0)
		{
			try{
				Double.parseDouble(data);
			}
			catch( NumberFormatException e)
			{
				lev.add(data);
			}
			return lev;
		}
		else
		{
			for(NodeBean n : child)
			{
				lev.addAll(n.getLeaves());
			}
			return lev;
		}
	}
	
	boolean isConstant()
	{
		try
		{
			Double.parseDouble(data);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
	
	void clearMarks()
	{
		marked = false;
        for (NodeBean aChild : child) {
            aChild.clearMarks();
        }
	}
	
	public String dfs()
	{
		if(left != null && right != null)
		{
			return data + "("+left.dfs()+" "+right.dfs()+")";			
		}
		else
			return data;
	}
	
	public String DisplayDepthFirst()
	{
		if(child.size() > 0)
		{
			String str = data+"(";
            for (NodeBean aChild : child) {
                str += aChild.DisplayDepthFirst() + " ";
            }
			return str+")";			
		}
		else
			return data;
	}
	
	public String infix()
	{
		if(child.size() > 0)
		{
			if(!isFunc)
			{
				String str = "";
				if(data.equals("-"))
					str = "-";
				for(int i =0; i<child.size()-1; i++)
				{
					if(child.get(i).child.size() > 0)
						str+="("+child.get(i).infix()+")"+data;
					else
						str+=child.get(i).infix()+data;
				}
				if(child.get(child.size()-1).child.size() > 0)
					str+="("+child.get(child.size()-1).infix()+")";
				else
					str+=child.get(child.size()-1).infix();
				return str;
			}
			else
			{
				String str = data+":(";
				for(int i= 0; i<child.size();i++)
				{
					str += child.get(i).infix();
					if(i<child.size()-1)
						str += ',';
				}
				str += ')';
				return str;
			}
		}
		else
			return data;
	}



	boolean condensed;
	

	
	public void show_condensed()
	{
		if(!condensed)
			Parser.condense(this);
		System.out.println(data+" ("+child.size()+")");
		for(NodeBean n: child)
		{
			n.show_condensed();
		}
		if(child.size() > 0)
			System.out.println("end of NodeBean");
	}
	
	public boolean equals(NodeBean n)
	{
		Parser.condense(this);
		Parser.condense(n);
		
		if( !data.equals( n.data ) || child.size() != n.child.size() )
			return false;
		
		if(child.size() == 0)
			return true;
		
		boolean[] found = new boolean[child.size()];
		
		for(int i = 0; i<child.size(); ++i)
		{
			for(int j = 0; j<child.size(); ++j)
			{
				if( child.get(i).equals( n.child.get(j) ) && !found[j]  )
				{
					found[j] = true;
					break;
				}
			}
		}
		
		for(boolean b: found)
		{
			if(!b)
				return false;
		}
		return true;
		
	}
	
	private boolean sequals(NodeBean n)
	{
		Parser.condense(this);
		Parser.condense(n);
		
		if( !data.equals( n.data ))
			return false;
		
		if(child.size() == 0)
			return true;
		
		boolean[] found = new boolean[n.child.size()];
		
		for(int i = 0; i<child.size(); ++i)
		{
			for(int j = 0; j<n.child.size(); ++j)
			{
				if( child.get(i).equals( n.child.get(j) ) && !found[j]  )	//TODO: Think later equal? sequal?
				{
					found[j] = true;
					break;
				}
			}
		}
		
		for(boolean b: found)
		{
			if(!b)
				return false;
		}
		return true;
		
	}
	
	public boolean sub(NodeBean n)
	{
		if(sequals(n))
				return true;
		else
		{
            for (NodeBean aChild : child) {
                if (aChild.sub(n))
                    return true;
            }
			return false;
		}
	}
	
	
}
