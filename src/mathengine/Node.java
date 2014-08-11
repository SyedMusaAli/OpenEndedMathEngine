package mathEngine;

import java.util.ArrayList;


class Node {
	
	public String data;
	public Node right, left;
	public ArrayList<Node> child;
	boolean marked;
	public boolean isFunc;
	
	public Node()
	{
		right = null;
		left = null;
		child = new ArrayList<Node>();
		marked = false;
		isFunc = false;
	}
	
	public Node(Node n)
	{
		data = n.data;
		child = new ArrayList<Node>();
		for(int i = 0; i<n.child.size(); i++)
		{
			child.add(new Node(n.child.get(i)));
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
			for(Node n : child)
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
        for (Node aChild : child) {
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
            for (Node aChild : child) {
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
		for(Node n: child)
		{
			n.show_condensed();
		}
		if(child.size() > 0)
			System.out.println("end of Node");
	}
	
	public boolean equals(Node n)
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
	
	private boolean sequals(Node n)
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
	
	public boolean sub(Node n)
	{
		if(sequals(n))
				return true;
		else
		{
            for (Node aChild : child) {
                if (aChild.sub(n))
                    return true;
            }
			return false;
		}
	}
	
	
}
