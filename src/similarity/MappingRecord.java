package similarity;

public class MappingRecord {   //In fact, it indicates class file.
	
	MappingSingle mappingClass;
	MappingMethods mappingMethods;
	
	MappingRecord()
	{
		mappingClass = new MappingSingle();
		mappingMethods = new MappingMethods();
	}
	
	MappingRecord(int a)
	{
		mappingClass = new MappingSingle();
		mappingMethods = new MappingMethods(a);
	}
	
	public void setmappingClass(String a, String b)
	{
		mappingClass.setmapping(a, b);
	}
	
	public MappingMethods getmappingMethods()
	{
		return mappingMethods;
	}
	
	public String returnClassOrigin()
	{
		return mappingClass.getorigin();
	}
	
	public String returnClassTarget()
	{
		return mappingClass.gettarget();
	}
	
	public void print()
	{
		mappingClass.print(1);
		mappingMethods.print();
	}
	
	public String getprint()
	{
		String result = "";
		result = result + mappingClass.getprint(1) + mappingMethods.getprint();
		return result;
	}
	
	
	public class MappingMethods{
		
		MappingSingle[] mappingMethods;
		int length;
		
		MappingMethods()
		{
			length = 0;
			mappingMethods = null;
		}
		
		MappingMethods(int a)
		{
			length = a;
			mappingMethods = new MappingSingle[a];
			for(int i = 0; i < a; i++)
			{
				mappingMethods[i] = new MappingSingle();
			}
		}
		
		public int getlength()
		{
			return length;
		}
		
		public MappingSingle getmethod(int a)
		{
			return mappingMethods[a];
		}
		
		public void setmethod(int a, String b, String c)
		{
			mappingMethods[a].setmapping(b, c);
		}
		
		public void setlength(int a)
		{
			length = a;
		}
		
		public void print()
		{
			for(int i = 0; i < length; i++)
			{
				mappingMethods[i].print();
			}
		}
		
		public String getprint()
		{
			String result = "";
			for(int i = 0; i < length; i++)
			{
				result = result + mappingMethods[i].getprint();
			}
			return result;
		}
		
	}
	
	
	public class MappingSingle {
		String origin;
		String target;
		
		MappingSingle()
		{
			origin = null;
			target = null;
		}
		
		public void setorigin(String a)
		{
			origin = a;
		}
		
		public void settarget(String a)
		{
			target = a;
		}
		
		public void setmapping(String a, String b)
		{
			origin = a;
			target = b;
		}
		
		public String getorigin()
		{
			return origin;
		}
		
		public String gettarget()
		{
			return target;
		}
		
		public void print(int a)
		{
			System.out.println(origin + " -> " + target + ":");
		}
		
		public void print()
		{
			System.out.println("    " + origin + " -> " + target);
		}
		
		public String getprint(int a)
		{
			String result = origin + " -> " + target + ":" + "\r\n";
			return result;
		}
		
		public String getprint()
		{
			String result = "    " + origin + " -> " + target + "\r\n";
			return result;
		}
	}
	
}
