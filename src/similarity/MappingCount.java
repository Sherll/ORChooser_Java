package similarity;

public class MappingCount {
	int numclass;
	int nummethod;
	int numvariable;
	
	MappingCount()
	{
		numclass = 0;
		nummethod = 0;
		numvariable = 0;
	}
	
	public int getnumclass()
	{
		return this.numclass;
	}
	
	public int getnummethod()
	{
		return this.nummethod;
	}	
	
	public int getnumvariable()
	{
		return this.numvariable;
	}
	
	public void setnumclass(int numclass)
	{
		this.numclass = numclass;
	}
	
	public void setnummethod(int nummethod)
	{
		this.nummethod = nummethod;
	}	
	
	public void setnumvariable( int numvariable)
	{
		this.numvariable = numvariable;
	}
	
	public void increasenumclass()
	{
		this.numclass++;
	}
	
	public void increasenummethod()
	{
		this.nummethod++;
	}
	
	public void increasenumvariable()
	{
		this.numvariable++;
	}
	
	public void print()
	{
		System.out.println(this.getnumclass()+"\t");
		System.out.println(this.getnummethod()+"\t");
		System.out.println(this.getnumvariable()+"\t");
		System.out.println("\n");		
	}
}
