package mathengine;

import java.io.PrintStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;


public class xmlFile {
	
	PrintStream ps;
	DocumentBuilder builder;
	Document Kb;
	StreamResult result;
	Element rootElement;
	DOMSource source;
	TransformerFactory transformerFactory;
	Transformer transformer;
	Element el;
	
	public xmlFile(String fname) throws Exception
	{
		DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
		ps = new PrintStream(fname);
		builder = factory.newDocumentBuilder();
		Kb = builder.newDocument(); //create file
		result = new StreamResult(ps);
		rootElement = Kb.createElement("KB");
	    Kb.appendChild(rootElement);
	}
	
	public void xmlWrite(String s,String category) throws Exception
	{	
	    source = new DOMSource(Kb);
	    transformerFactory = TransformerFactory.newInstance();
	    transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");    
		el = Kb.createElement(category);
		el.setTextContent(s);
	        //add given string to root elemen
	}
	
	public void xSave() throws TransformerException{
		// The actual output to a file goes here
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
		ps.close();
		
	}
	
	public ArrayList<String> xmlRead(String fname,String tag) throws Exception
	{
		Document doc=builder.parse(fname);
		doc.getDocumentElement().normalize();
		NodeList nodel=doc.getElementsByTagName(tag);
		ArrayList<String> ans = new ArrayList<String>();
		
		for(int i =0; i < nodel.getLength(); i++)
		{
			ans.add( nodel.item(i).getNodeValue() );
		}
		
		return ans;
	
		
		/*
		  Document doc = builder.parse(fname);
		  doc.getDocumentElement().normalize();
		  System.out.println("Root element " + doc.getDocumentElement().getNodeName());
		  NodeList nodeLst = doc.getElementsByTagName(tag);
		  System.out.println("Information of all "+ tag);

		  for (int i = 0; i < nodeLst.getLength(); i++) {
		    Node fstNode = nodeLst.item(i);
		    
		    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
		  
		           Element fstElmnt = (Element) fstNode;
		      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("known");
		      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		      NodeList fstNm = fstNmElmnt.getChildNodes();
		      System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
		    }
		    }*/

	}
}
