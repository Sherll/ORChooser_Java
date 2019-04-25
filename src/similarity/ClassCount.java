package similarity;

import java.util.ArrayList;
import java.util.List;


public class ClassCount {
	int Numfunc;
	String Classname;
	int Numinnerclass;
	int Numvar;
	int pubV;
	int LOC;
	List<MethodCount> Methods;
	
	ClassCount()
	{
		Numfunc = 0;
		Classname = null;
		Numinnerclass = 0;
		Numvar = 0;
		pubV = 0;
		LOC = 0;
		Methods = new ArrayList<MethodCount>();
	}
	
	public int getNumfunc()
	{
		return Numfunc;
	}
	
	public String getClassname()
	{
		return Classname;
	}
	
	public int getNuminnerclass()
	{
		return Numinnerclass;
	}
	
	public int getNumvar()
	{
		return Numvar;
	}
	
	public int getpubV()
	{
		return pubV;
	}
	
	public int getLOC()
	{
		return LOC;
	}
	
	public MethodCount getMethod(int a)
	{
		return Methods.get(a);
	}
	
	public void setNumfunc(int a)
	{
		Numfunc = a;
	}
	
	public void setClassname(String a)
	{
		Classname = a;
	}
	
	public void setNuminnerclass(int a)
	{
		Numinnerclass = a;
	}
	
	public void setNumvar(int a)
	{
		Numvar = a;
	}
	
	public void setpubV(int a)
	{
		pubV = a;
	}

	public void setLOC(int a)
	{
		LOC = a;
	}
	
	public void increaseNumfunc()
	{
		Numfunc++;
	}
	
	public void increaseLOC()
	{
		LOC++;
	}
	
	public void increaseNuminnerclass()
	{
		Numinnerclass++;
	}
	
	public void increaseNumvar()
	{
		Numvar++;
	}
	
	public void increasepubV()
	{
		pubV++;
	}
	
	public void addMethod(MethodCount a)
	{
		Methods.add(a);
	}
	
	public void print()
	{
		System.out.println(Classname);
		System.out.println("Function:" + Numfunc + " Innerclass:" + Numinnerclass + " Variable:" + Numvar + " publicVar:" + pubV + "LOC:" + LOC);
		for(int i = 0; i < Numfunc; i++)
		{
			Methods.get(i).print();
		}
	}
}
