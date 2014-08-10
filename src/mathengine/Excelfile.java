import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
             /*
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class Excelfile {

public void evaluate(String fname) throws IOException
{

	Engine r = Engine.instance();
	
	ArrayList<Integer> tofindindex=new ArrayList<Integer>();
	ArrayList<String> tofind=new ArrayList<String>();
	ArrayList<String> knownval=new ArrayList<String>();
	ArrayList<String> knownvar=new ArrayList<String>();
	ArrayList<String> formula=new ArrayList<String>();
	ArrayList<String> ans;
	
	FileInputStream fis = new FileInputStream(fname);
	Workbook wb = new HSSFWorkbook(fis);
	
	for(int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) 
	{

	    Sheet sheet=wb.getSheetAt(sheetNum);
	    ArrayList<String> list=new ArrayList<String>();
	    if(sheet.getLastRowNum()>sheet.getFirstRowNum())
	    {
		    int size=sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();
			for(int i=0;i<size;i++)
			{
				list.add(sheet.getRow(sheet.getFirstRowNum()).getCell(i).toString());
				//System.out.println("SDS"+list);
			}
	    }
		for(Row row:sheet)
		{
			tofind.clear();
			tofindindex.clear();
			knownvar.clear();
			knownval.clear();
			
		    for(int i=0;i<row.getPhysicalNumberOfCells();++i)
		    {
		        Cell sell = row.getCell(i);
		        
		
		        if(sell.getCellType() == Cell.CELL_TYPE_FORMULA)
		        {
		           formula.add(sell.getCellFormula().toString());
		           System.out.println("F"+formula);
		           /*if(totalCellValue.startsWith("Total Royalty"))
		           {
		               key = totalCell.getStringCellValue().toString().trim();
		
		           }*/
/*		        }
		        else if(sell.toString().contains("???"))
		        {
		        	//System.out.println("II"+i);
		        	tofind.add(sheet.getRow(sheet.getFirstRowNum()).getCell(i).toString());
		        	tofindindex.add(i);
		            System.out.println("to find"+tofind);
		            System.out.println("to find index"+tofindindex);
		
		        }
		
		
		        else if(sell.getCellType() == Cell.CELL_TYPE_NUMERIC)
		        {
		        	//System.out.println("II"+i);
		        	knownval.add(sell.toString());
		        	knownvar.add(list.get(i));
		        	 System.out.println("knownvar"+knownvar);
		            System.out.println("knownval"+knownval);
		           
		
		        }    
		      }
		    ans = r.KnowledgeQuery(knownvar, knownval, tofind);
		//    r.getAllSteps();
		    
		    System.out.println("solved");
		    for(int ind = 0; ind< ans.size(); ind++)
		    {
		    	row.getCell( tofindindex.get(ind) ).setCellValue( Double.parseDouble(ans.get(ind)) );
		    	System.out.println(ans.get(ind));
		    }
		    
		}
		
		FileOutputStream fos = new FileOutputStream(fname);
		
	      wb.write(fos);
		
	//	System.out.println(n.infix());
		//StaticFormula f=new StaticFormula(DynamicFormula.get(0));
		
	}
}


}
*/