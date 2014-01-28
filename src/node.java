import java.util.ArrayList; 
import java.lang.Math;


public class node {
	
	public String data;
	public node right, left;
	public ArrayList<node> child;
	boolean marked;
	public boolean isFunc;
	
	public node()
	{
		right = null;
		left = null;
		child = new ArrayList<node>();
		marked = false;
		isFunc = false;
	}
	
	public node(node n)
	{
		data =  new String(n.data);
		child = new ArrayList<node>();
		for(int i = 0; i<n.child.size(); i++)
		{
			child.add(new node(n.child.get(i)));
		}
		marked = false;
		isFunc = n.isFunc;
	//	condensed = n.condensed;
	}
	
	public node(String given)
	{
		child = new ArrayList<node>();
		marked = false;
		isFunc = false;
		given = this.bracketless(given);
		data = new String(given);
		boolean found = false;
		int br = 0;
		right = null;
		left = null;
		for(int i = 0; i  < given.length() && !found; i++)
		{
			char ch = given.charAt(i);
			if( ch == '(' )
				br++;
			else if(ch == ')')
				br--;
			else if(br == 0)
			{
				switch(ch)
				{
					case '+':
						if(i==0)
                                                        break;
						String temp1 = given.substring(0, i);
						String temp2 = given.substring(i+1, given.length());
						left = new node(temp1);
						right = new node(temp2);
						char arr[] = new char[1];
						arr[0] = ch;
						data = new String(arr);
						found = true;
						break;
					case '-':
						if(i==0)
							break;
						temp1 = given.substring(0, i);
						temp2 = given.substring(i+1, given.length());
						left = new node(temp1);
						
						right = new node("-"+temp2);
						arr = new char[1];
						arr[0] = '+';
						data = new String(arr);
						found = true;
						break;
				}
			}
		}
				
		for(int i = 0; i  < given.length() && !found; i++)
		{
			char ch = given.charAt(i);
			if( ch == '(' )
				br++;
			else if(ch == ')')
				br--;
			else if(br == 0)
			{
				switch(ch)
				{
					case '*':
						String temp1 = given.substring(0, i);
						String temp2 = given.substring(i+1, given.length());
						left = new node(temp1);
						right = new node(temp2);
						char arr[] = new char[1];
						arr[0] = ch;
						data = new String(arr);
						found = true;
						break;
				}
			}
		}
		
		
		for(int i = 0; i  < given.length() && !found; i++)
		{
			char ch = given.charAt(i);
			if( ch == '(' )
				br++;
			else if(ch == ')')
				br--;
			else if(br == 0)
			{
				switch(ch)
				{
					case '/':
						String temp1 = given.substring(0, i);
						String temp2 = given.substring(i+1, given.length());
						left = new node(temp1);
						right = new node(temp2);
						char arr[] = new char[1];
						arr[0] = ch;
						data = new String(arr);
						found = true;
						break;
				}
			}
		}
		
		for(int i = 0; i  < given.length() && !found; i++)
		{
			char ch = given.charAt(i);
			if( ch == '(' )
				br++;
			else if(ch == ')')
				br--;
			else if(br == 0)
			{
				switch(ch)
				{
					case '^':
						String temp1 = given.substring(0, i);
						String temp2 = given.substring(i+1, given.length());
						left = new node(temp1);
						right = new node(temp2);
						char arr[] = new char[1];
						arr[0] = ch;
						data = new String(arr);
						found = true;
						break;
				}
			}
		}
		
		if(data.charAt(0) == '-')
		{
			String temp = data.substring(1);
			right = new node(temp);
			data = "-";
		}
	
		//TODO: modify this loop for function calls using : operator
		for(int i = 0; i  < given.length() && !found; i++)
		{
			char ch = given.charAt(i);
			if( ch == '(' )
				br++;
			else if(ch == ')')
				br--;
			else if(br == 0)
			{
				switch(ch)
				{
					case ':':
						isFunc = true;
						String temp1 = given.substring(0, i);
						String temp2 = given.substring(i+1, given.length());
						data = new String(temp1);
						ArrayList<String> parameters = parameterSplit(bracketless(temp2));
						for(String str: parameters)
						{
							child.add(new node(str));
						}
						found = true;
						break;
				}
			}
		}
		
		condense();
		data = data.trim();
	}
	
	ArrayList<String> parameterSplit(String str)
	{
		ArrayList<String> arr = new ArrayList<String>();
		int anchor = 0, br=0;
		for(int i = 0; i  < str.length(); i++)
		{
			char ch = str.charAt(i);
			if( ch == '(' )
				br++;
			else if(ch == ')')
				br--;
			else if(br == 0)
			{
				if(ch == ',')
				{
					arr.add(str.substring(anchor, i));
					anchor = i+1;
				}
			}
		}
		arr.add(str.substring(anchor));
		return arr;
	}
	
	ArrayList<String> getLeaves()		//reserved for formula_static
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
			for(node n : child)
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
		for(int i = 0; i<child.size();i++)
		{
			child.get(i).clearMarks();
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
	
	public String dfs2()
	{
		if(child.size() > 0)
		{
			String str = data+"(";
			for(int i =0; i<child.size(); i++)
			{
				str+=child.get(i).dfs2()+" ";
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
	
	public void simplify_solve()			//simplifies all solvable child nodes
	{
		if(child.size() == 0)				//leaf nodes would be values, so no simplification needed 
		{
			return;
		}
		else if(child.size() == 1 && !data.equals("-") && data.charAt(0) != '&')		//remove redundancies like +(y)  except when like -(y) 
		{
			data = child.get(0).data;
			child = child.get(0).child;
		}
		
		double t;
		
		try
		{
			t = solve();	//try solving
		}
		catch(NumberFormatException e)				//if not solvable
		{
			for(int i = 0; i<child.size(); i++)		//for every child
			{
				child.get(i).simplify_solve();		//recursion
				child.get(i).solveFlexible();
			}
			solveFlexible();
			return;
		}
		
		data = String.valueOf(t);				//if solvable, replace data with value
		child.clear();							//empty child array
	}
	
	public void solveFlexible()			//TODO: make for (x/y)/(a/d)
	{
		if(data.length() == 0)
			return;
		double ans, l, r;
		boolean modified = false;
		
		switch( data.charAt(0) )
		{
		case '+':
			ans= 0;
			for(int i = 0; i<child.size();)		//sum the "solve()" of all child (recursion)  
			{
				try
				{
					ans += child.get(i).solve();
					child.remove(i);
					modified = true;
				}
				catch(NumberFormatException e)				//if not solvable
				{
					i++;
				}
			}
			if(modified)
				child.add(new node( Double.toString(ans)));	
		case '*':
			ans= 1;
			for(int i = 0; i<child.size();)		//sum the "solve()" of all child (recursion)  
			{
				try
				{
					ans *= child.get(i).solve();
					child.remove(i);
					modified = true;
				}
				catch(NumberFormatException e)				//if not solvable
				{
					i++;
				}
			}
			if(modified)
				child.add(new node( Double.toString(ans)));
		}
	}
	
	public double solve()			//TODO: make for (x/y)/(a/d)
	{
		if(data.length() == 0)
			return 0;
		if(child.size() == 0)
			return Double.parseDouble(data);
		double ans, l, r;
		switch( data.charAt(0) )
		{
		case '+':
			ans= 0;
			for(int i = 0; i<child.size(); i++)		//sum the "solve()" of all child (recursion)  
			{
				ans += child.get(i).solve();
			}
			return ans;
		case '-':
			return -1*child.get(0).solve();			//return the negative of child's value		
		case '*':
			ans= 1;
			for(int i = 0; i<child.size(); i++)		//product of all child's solve	  
			{
				ans *= child.get(i).solve();
			}
			return ans;
		case '/':
			l = child.get(0).solve();
			r = child.get(1).solve();  
			return l / r;
		case '^':
			return Math.pow(child.get(0).solve(),child.get(1).solve());
		default:
			return Double.parseDouble(data);
				
		}
	}
	
	String bracketless(String s)
	{
		boolean neg = false;
		int b = 1, i;
		if(s.charAt(0) == '-' && s.charAt(1) == '(')
		{
			neg = true;
			i = 2;
		}
		else if( s.charAt(0) != '(')
			return s;
		else
			i = 1;
		
		for(; i< s.length() && b>0; i++)
		{
			if( s.charAt(i) == '(')
				b++;
			else if( s.charAt(i) == ')')
				b--;
		}
		
		if(i == s.length())
		{
			if(!neg)
				return s.substring(1, i-1);
			char[] temp = new char[s.length()-1];
			
			int x ,y;
			
			for(x = 0, y =2; y<i-1; x++, y++)
			{
				//System.out.println("got: "+s.charAt(y));
				if( s.charAt(y) == '-' )
				{
					temp[x] = '+';
				}
				else if( s.charAt(y) == '+')
				{
					temp[x] = '-';
				}
				else if(y == 2)
				{
					temp[x++]= '-';
					temp[x] = s.charAt(y);
				}
				else
					temp[x] = s.charAt(y);
				//System.out.println("wrote: "+temp[x]);
			}
			return new String(temp, 0, x);
		}
		return s;
	}
	//TODO: Musa
	/*
	MathMethod(String given)
	{
		if(given.charAt(0) == '(')
			return given;
		for(int i = 0; i<given.length(); i++)
		{
			switch(given.charAt(i))
			{
				case '+':
				case '-':
				case '*':
				case '/':
					return given;
				case '('
				{
					
				}
			}
		}
	}*/
	
	private boolean condensed;
	
	public void condense()
	{
		if(condensed)			//return if it has been condensed before
			return;
		else
			condensed = true;		
		
		if(data.charAt(0) != '&')		//exception for specials
		{
			
			node temp = this;			
			if(temp.left != null)		//if there exists something on the left, that'll be the first child.
				child.add(left);
			
			if(data.charAt(0) == '/' || data.charAt(0) == '^' && right != null)
				child.add(right);
			else
			{
			
				//ASSUMPTION: there won't be anything like 1/2/3
				//TODO: make it for (x/y)/(a/d)
				
				if(temp.right!=null)			//iterate through all right node who have the same operator, and gather their nodes in child.
				{
					while(temp.right.data.equals(data) )
					{
							temp = temp.right;			//keep moving right
							child.add(temp.left);		//keep adding left node to child
					}
					child.add(temp.right);				//when u reach last node of this operator type. add the right to child
				}
				
				for( int i = 0; i < child.size();)		//iterate thorough the number of children
				{
					node n = child.get(i);              //get ith child
					if(n.data.equals(data) )            //same operator
					{
						child.add(i,n.left);            //keep adding the same operator operands of left and right
						child.add(i+1,n.right);
						child.remove(i+2);
					}
					else
						++i;							//else move on
				}
			}
		}
		
												//condense each child of it
		for(node n: child)
		{
			n.condense();
		}
	}
	
	public void show_condensed()
	{
		if(!condensed)
			condense();
		System.out.println(data+" ("+child.size()+")");
		for(node n: child)
		{
			n.show_condensed();
		}
		if(child.size() > 0)
			System.out.println("end of node");
	}
	
	public boolean equals(node n)
	{
		condense();
		n.condense();
		
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
	
	private boolean sequals(node n)
	{
		condense();
		n.condense();
		
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
	
	public boolean sub(node n)
	{
		if(sequals(n))
				return true;
		else
		{
			for(int i = 0; i<child.size(); ++i)
			{
				if( child.get(i).sub(n) )
					return true;
			}
			return false;
		}
	}
	
	
}
