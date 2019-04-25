package similarity;
import java.io.*;


public class ReadMapping {

	static SingleMapping[] mappingArray;
	static int length;
	
	public ReadMapping()
	{
		this.mappingArray = null;
		this.length = 0;
	}
	
	public SingleMapping[] return_array()
	{
		return mappingArray;
	}
	
	private static String[] Readfile(File mapping) throws IOException
	{
		InputStream In = new FileInputStream(mapping);
		int size = In.available();
		byte[] recordRead = new byte[size];
		for(int i = 0; i < size; i++)
		{
			recordRead[i] = (byte)In.read();
		}
		String s = new String(recordRead, 0, size);
		String[] s_split = s.split(":");
		length = s_split.length - 1;
		String[] s_split_after = manageString(s_split);
		In.close();
		return s_split_after;
	}
	
	private static String[] manageString(String[] split)
	{
		String[] s_split = new String[split.length - 1];
		String tmp = new String();
		tmp = split[0] + ":" + "\r\n";
		for(int i = 1; i < split.length; i++)
		{
			String[] ss_split = split[i].split("\r\n");
			
			for(int j = 1; j < ss_split.length - 1; j++)
			{
				tmp = tmp + ss_split[j] + "\r\n";
			}		
			if(i == split.length - 1)
			{
				tmp = tmp + ss_split[ss_split.length - 1] + "\r\n";
			}
			//System.out.println(tmp);
			s_split[i - 1] = tmp;
			tmp = ss_split[ss_split.length - 1] + ":\r\n";		
		}
		
//		for(int i = 0; i < split.length - 1; i++)
//		{
//			System.out.println(s_split[i]);
//		}
		return s_split;
	}
	
	public static void read_Mapping(String filename)
	{
		File mapping = new File(filename);
		String[] ssplit = null;
		try {
			ssplit = Readfile(mapping);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mappingArray = new SingleMapping[length];

		for(int i = 0; i < ssplit.length; i++)
		{
			mappingArray[i] = new SingleMapping();
			mappingArray[i].set_Single(ssplit[i]);
		}
	}
	
	public int returnClasslen()
	{
		return length;
	}
	
	public static String findClass(String search)
	{
		String origin = null;
		//System.out.println("target1:" + search.hashCode());
		for(int i = 0; i < length; i++)
		{
			//System.out.println("target2:" + mappingArray[i].returnclassname_target().hashCode());
			if(search.equals(mappingArray[i].returnclassname_target()))
			{
				origin = mappingArray[i].returnclassname_origin();
			}
		}
		if(origin == null)
		{
			origin = search;
		}
		//System.out.println("origin:" + origin);
		return origin;
	}
	
	public static int correctClass()
	{
		int numclass = 0;
		for(int i = 0; i < length; i++ )
		{
			if(mappingArray[i].returnclassname_origin().equals(mappingArray[i].returnclassname_target()))
			{
				numclass++;
			}
		}
		return numclass;
	}
	
	public static int correctMethod()
	{
		int nummethod = 0;
		for(int i = 0; i < length; i++ )
		{
			nummethod += mappingArray[i].correctMethod();
		}
		return nummethod;
	}
	
	public static int correctVariable()
	{
		int numvariable = 0;
		for(int i = 0; i < length; i++)
		{
			numvariable += mappingArray[i].correctVariable();
		}
		return numvariable;
	}
	
	
	public void print()
	{
		for(int i = 0; i < length; i++)
		{
			mappingArray[i].print();
		}
	}
	
	public static String findMethod(String classname, String methodname, String[] arg_types)
	{
		String origin = null;
		int indexOfclass = -1;
		String target = null;
		for(int i = 0; i < length; i++)
		{
			if(classname.equals(mappingArray[i].returnclassname_origin()))
			{
				indexOfclass = i;
				break;
			}
		}
		if(indexOfclass == -1)
		{
			target = methodname + "(";
			if(arg_types != null)
			{
				for(int i = 0; i < arg_types.length - 1; i++)
				{
					target = target + arg_types[i] + ",";
				}
				target = target + arg_types[arg_types.length - 1] + ")";
			}
			else
			{
				target = target + ")";
			}
		}
		else
		{
			target = mappingArray[indexOfclass].findMethod(methodname, arg_types);
			//System.out.println(target);
			if(target == null)
			{
				target = methodname + "(";
				if(arg_types != null)
				{
					for(int i = 0; i < arg_types.length - 1; i++)
					{
						target = target + arg_types[i] + ",";
					}
					target = target + arg_types[arg_types.length - 1] + ")";
				}
				else
				{
					target = target + ")";
				}
			}
		}
		//System.out.println(indexOfclass);
		return target;
	}
	
	public static class MappingString{
		String origin; //origin to illustrate the original name;
		String target; //target to illustrate the changed name;
		int type; //0 to illustrate class; 1 to illustrate method; 2 to illustrate variable; -1 is the initial state. 
		
		MappingString()
		{
			this.origin = null;
			this.target = null;
			this.type = -1;
		}
		
		MappingString(String nametxt)
		{
			String[] s_nametxt = nametxt.split("->");
			this.origin = s_nametxt[0].substring(0 , s_nametxt[0].length() - 1);
			this.target = s_nametxt[1]; // before has a blank space
			if(nametxt.indexOf(":") != -1)
			{
				type = 0;
				this.target = s_nametxt[1].substring(1,s_nametxt[1].length() - 1); // blank space
			}
			else if(nametxt.indexOf("(") != -1)
			{
				type = 1;
				this.origin = s_nametxt[0].split(" ")[5];
				this.target = s_nametxt[1].substring(1, s_nametxt[1].length()); // blank space
			}
			else
			{
				type = 2;
				//this.origin = s_nametxt[0].substring(4 , s_nametxt[0].length() - 1);
				this.target = s_nametxt[1].substring(1, s_nametxt[1].length()); // blank space
			}
		}
		
		public String return_target()
		{
			return target;
		}
		
		public String return_origin()
		{
			return origin;
		}
		
		private int return_type()
		{
			return type;
		}
		
		public void set_String(String nametxt)
		{
			String[] s_nametxt = nametxt.split("->");
			//this.origin = s_nametxt[0].split(" ")[1]; 
			this.origin = s_nametxt[0].substring(0 , s_nametxt[0].length() - 1);
			this.target = s_nametxt[1];
			if(nametxt.indexOf(":") != -1)
			{
				type = 0;
				this.target = s_nametxt[1].substring(1, s_nametxt[1].length());
			}
			else if(nametxt.indexOf("(") != -1)
			{
				type = 1;
				String[] tmp = s_nametxt[0].split(" ");
				this.origin = tmp[tmp.length - 1];
				this.target = s_nametxt[1].substring(1, s_nametxt[1].length());
			}
			else
			{
				type = 2;
				//this.origin = s_nametxt[0].substring(4 , s_nametxt[0].length() - 1);
				this.target = s_nametxt[1].substring(1, s_nametxt[1].length());
			}
		}
		
		public void print()
		{
			System.out.println(this.origin + "->" + this.target + ":" + this.type);
		}
	}
	
	public static class SingleMapping{
		MappingString classname;
		MappingString[] variablename;
		int numvariable;
		MappingString[] methodname;
		int nummethod;
		
		SingleMapping()
		{
			this.classname = null;
			this.variablename = null;
			this.numvariable = 0;
			this.methodname = null;
			this.nummethod = 0;
		}
		
		SingleMapping(String classname, int numvariable, int nummethod)
		{
			this.classname = new MappingString(classname);
			this.variablename = new MappingString[numvariable];
			this.numvariable = numvariable;
			this.methodname = new MappingString[nummethod];
			this.nummethod = nummethod;
		}
		
		public void set_Single(String singleClass)
		{
			String[] split = singleClass.split("\r\n");
			for(int i = 0; i < split.length; i++)
			{
				if(i == 0) this.classname = new MappingString(split[i]);
				else if(split[i].indexOf("(") != -1)
				{
					nummethod++;
				}
				else
				{
					numvariable++;
				}
			}
			this.methodname = new MappingString[nummethod];
			this.variablename = new MappingString[numvariable];
			int m = 0;   // to record the index of methodname array
			int v = 0;   // to record the index of variablename array
			for(int i = 1; i < split.length; i++)
			{
				if(split[i].indexOf("(") != -1)
				{
					this.methodname[m] = new MappingString();
					this.methodname[m].set_String(split[i]);
					m++;
				}
				else
				{
					this.variablename[v] = new MappingString();
					this.variablename[v].set_String(split[i]);
					v++;
				}
			}
				
		}
		
		public String findMethod(String methodname, String[] arg_types)
		{
			String target = null;
			for(int i = 0; i < this.nummethod; i++)
			{
				//System.out.println(this.methodname[i].return_target());
				if(methodname.equals(this.methodname[i].return_target()))
				{
					String pretarget = this.methodname[i].return_origin();
					target = pretarget;
					int first = pretarget.indexOf("(");
					int last = pretarget.indexOf(")");
					pretarget = pretarget.substring(first + 1, last);
					if(pretarget.length() == 0 && arg_types == null)
					{
						return target;
					}
					String[] target_types = pretarget.split(",");
					if(compareStringArray(target_types, arg_types))
					{
						return target;
					}
				}
			}
			//System.out.println("Somethings not correct here!");
			return target;			
		}
		
		private boolean compareStringArray(String[] strarray1, String[] strarray2)
		{
			if(strarray1.length == 0 || strarray2 == null)
			{
				return false;
			}
			
			else if(strarray1.length != strarray2.length)
				return false;
			else
			{
				for(int i = 0; i < strarray1.length; i++)
				{
					int j = 0;
					for(; j < strarray2.length; j++)
					{
						if(strarray1[i].equals(strarray2[j]))
						{
							break;
						}
					}
					if(j == strarray2.length) return false;
				}
				
				for(int i = 0; i < strarray2.length; i++)
				{
					int j = 0;
					for(; j < strarray1.length; j++)
					{
						if(strarray2[i].equals(strarray1[j]))
						{
							break;
						}
					}
					if(j == strarray2.length) return false;
				}
			}
			return true;
		}
		
		public String returnclassname_target()
		{
			return this.classname.return_target();
		}
		
		public String returnclassname_origin()
		{
			return this.classname.return_origin();
		}
		
		public int correctMethod()
		{
			int numMethod = 0;
			for(int i = 0; i < this.nummethod; i++)
			{
				String origin = this.methodname[i].return_origin();
				String target = this.methodname[i].return_target();
				origin = origin.substring(0, origin.indexOf("("));
				
				if(origin.equals(target))
				{
					numMethod++;
				}
			}
			return numMethod;
		}
		
		public int correctVariable()
		{
			int numVariable = 0;
			for(int i = 0; i < this.numvariable; i++)
			{
				String origin = this.variablename[i].return_origin();
				String target = this.variablename[i].return_target();
				origin = origin.split(" ")[1];
				if(origin.equals(target))
				{
					numVariable++;
				}
			}
			return numVariable;
		}
		
		public void print()
		{
			this.classname.print();
//			for(int i = 0; i < numvariable; i++)
//			{
//				this.variablename[i].print();
//			}
			for(int i = 0; i < nummethod;i++)
			{
				this.methodname[i].print();
			}
			
		}
	
		public MappingString getmethodname(int a)
		{
			return methodname[a];
		}
		
		public int getnummethod()
		{
			return nummethod;
		}
	}
	
//	public static void main(String[] arg)
//	{
//		String filename = "Tetris0.map";
//		ReadMapping testmapping = new ReadMapping();
//		ReadMapping.read_Mapping(filename);
//		testmapping.print();
//	}
}
