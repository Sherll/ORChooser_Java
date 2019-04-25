package similarity;

import java.io.*;

public class RecordClass {
	
	ClassCount[] classArray;
	int length;
	
	RecordClass()
	{
		classArray = null;
		length = 0;
	}
	
	private String[] readfile(File filestream) throws IOException
	{
		InputStream In = new FileInputStream(filestream);
		int size = In.available();
		byte[] recordRead = new byte[size];
		for(int i = 0; i < size; i++)
		{
			recordRead[i] = (byte)In.read();
		}
		String s = new String(recordRead, 0, size);
		String[] s_split = s.split("\r\n");
		In.close();
	
		/********for debug***************/
//		System.out.println(s);
//		String[] sss = s.split("\n");
//		System.out.println(sss.length);
		return s_split;
	}
	
	public void record_class(String filename)
	{
		File filestream = new File(filename);
		String[] split = null;
		try {
			split = readfile(filestream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < split.length; i++)
		{
			//System.out.println(split[i]);
			if(split[i].contains("Classfile"))
			{
				length++;
			}
		}
		classArray = new ClassCount[length];
//		int i = 0;
		int indexofArray = -1;
		int flag = -1; // use a flag to illustrate the area of classfile. 0 for classname, 
						//1 for innerclass, 2 for Numvar, 3 code area.
		for(int i = 0; i < split.length; i++)
		{
			if(split[i].contains("Classfile") && split[i].contains(".class"))
			{
				indexofArray++;
				classArray[indexofArray] = new ClassCount();
				flag = 0;
				String a = split[i].substring(split[i].lastIndexOf("/") + 1, split[i].length() - 6);
				classArray[indexofArray].setClassname(a);
			}
			else if(split[i].contains("InnerClasses:"))
			{
				flag = 1;
			}
			else if(split[i].equals("Constant pool:"))
			{
				flag = 2;
			}
			else if(split[i].equals("{"))
			{
				flag = 3;
			}
			else if(flag == 1 && split[i].contains("#") && split[i].contains("//class"))
			{
				classArray[indexofArray].increaseNuminnerclass();
			}
			else if(flag == 2 && split[i].contains("NameAndType") && !split[i].contains("("))
			{
				classArray[indexofArray].increaseNumvar();
			}
			else if(flag == 3)
			{
				if(split[i].contains("public") && !split[i].contains("("))
				{
					classArray[indexofArray].increasepubV();
				}
				else if((split[i].contains(");") && !split[i].contains("//")) || split[i].contains(") throws"))
				{
					classArray[indexofArray].increaseNumfunc();
				}
			}
		}
		
		//getting methods for every class.
		int mi = 0;
		indexofArray = -1;
		while(mi < split.length)
		{
			if(split[mi].contains("Classfile") && split[mi].contains(".class"))
			{
				indexofArray++;
//				while(mi < split.length && !split[mi].contains(");")) // Be careful as the class may with no function.
//				{
//					mi++;
//				}
			}
			
			if((split[mi].contains(");")&& !split[mi].contains("//") && !split[mi].contains("=")) || split[mi].contains(") throws"))
			{
				MethodCount single = new MethodCount();
				
				//get necessary information from the title of a function.
				String[] s_split = split[mi].split(" ");
				String a = null;
				for(int m = 0; m < s_split.length; m++)
				{
					if(s_split[m].contains("("))
					{
						a = s_split[m];
						break;
					}
				}
				a = a.substring(0, a.indexOf("("));
				single.setFunctionname(a);
				//System.out.println("");
				a = "";
				for(int m = 0; m < s_split.length; m++)
				{
					if(s_split[m].contains("("))
					{
						break;
					}
					else if(s_split[m].equals(""))
					{
						continue;
					}
					else
					{
						a = a + s_split[m] + " ";
					}
				}
				if(!a.equals(""))
				{
					a = a.substring(0, a.length() - 1); 
				}
				single.setReturntype(a);
				a = split[mi].substring(split[mi].indexOf("(") + 1, split[mi].indexOf(")"));
				String[] a_split = a.split(", ");
				
				if(a.equals(""))
				{
					single.setTotalparameter(0);
				}
				else
				{
					single.setTotalparameter(a_split.length);
				}
				//System.out.println(a_split[1]);
				single.setParatype(a_split);
				mi++;
				while(mi < split.length && !((split[mi].contains(");")&& !split[mi].contains("//") && !split[mi].contains("=")) || split[mi].contains(") throws")) && !split[mi].contains("Classfile"))
				{
					String[] ss_split = split[mi].split(": ");
					//System.out.println(split[mi]);
					
					if(ss_split[0].length() == 10 && ss_split.length != 1)
					{
						String[] sss_split = ss_split[1].split(" ");
						//System.out.println(sss_split[0]);
						single.setInstruction(sss_split[0]);
						single.increaseLOC();
						classArray[indexofArray].increaseLOC();
					}
					mi++;
				}			
				classArray[indexofArray].addMethod(single);
			}
			else
			{
				mi++;
			}
			
			//get instructions and LOC
				//instructions			
			//classArray[indexofArray].addMethod(single);
			//mi++;
		}
		//System.out.println(length);
//		for(int j = 0; j < length; j++)
//		{
//			//System.out.println("j:" + j);
//			classArray[j].print();
//		}
	}
	
	public int getlength()
	{
		return length;
	}
	
	public ClassCount getclass(int index)
	{
		return classArray[index];
	}
//	public static void main(String[] arg)
//	{
//		String origin = "_origin.txt";
//		RecordClass rc = new RecordClass();
//		rc.record_class(origin);
//	}
}
