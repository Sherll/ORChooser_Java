package similarity;

import java.io.*;

public class ReadCallgraph {

	private static String[] readfile(File filestream) throws IOException
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
//		String[] sss = s.split("\n");
//		System.out.println(sss.length);
		return s_split;
	}
	
	private static void writefile(File filestream, String str) throws IOException
	{
		OutputStream Out = new FileOutputStream(filestream);
		byte[] bytes = str.getBytes();
		Out.write(bytes);
		Out.close();
	}
	
	private static String findClass(String search)
	{
		
		
		//manage array;
		int first = search.indexOf("[");
		int last = search.lastIndexOf("[");
		if(first == 0 && last != 0 && search.length() - 1 > last + 2)
		{
			search = search.substring(last+2, search.length() - 1);
		}
		String target = ReadMapping.findClass(search);
//		if(first != 0 && last != 0)
//		 {
//			target = target.substring(0, target.length() - 1);
//		 }
		if(first == 0 && last != 0 && search.length() - 1 > last + 2)
		{
			target = "L" + target;
			for(int i = 0; i <= last; i++)
			{
				target = "[" + target;
			}
			target = target + ";";
		}
		return target;
	}
	
	private static String change_OriginClass(String dict)
	{
		String target = null;
		String[] classname = dict.split(":");
		classname = classname[1].split(" ");
		//System.out.println(classname[0]+" " + classname[1]);
		classname[0] = findClass(classname[0]);
		classname[1] = findClass(classname[1]);
		target = classname[0] + " " + classname[1] + "\r\n";
		//System.out.println(target);
		return target;
	}
	
	private static String findMethod(String search, String classname)
	{
		//method name might be repeated. The function might be override.
		String target = null;
		String methodname = search.substring(0, search.indexOf("("));
		/******************how about h()***************************/
		String arg_type = search.substring(search.indexOf("(")+1, search.indexOf(")"));
		String[] arg_types = arg_type.split(",");
		if(arg_type.length() == 0)
		{
			arg_types = null;
		}
		else
		{
			for(int i = 0; i < arg_types.length; i++)
			{
				arg_types[i] = findClass(arg_types[i]);
			}
		}
		//System.out.println(classname + " " + methodname + " " + arg_types);
		target = ReadMapping.findMethod(classname, methodname, arg_types);
		
		return target;
	}
	
	private static String change_OriginMethod(String dict)
	{
		String target = null;
		String[] split = dict.split(" ");
		String[] split_first = split[0].split(":");
		String class1 = findClass(split_first[1]);
		String method1 = findMethod(split_first[2], class1);
		
		String[] split_second = split[1].split(":");
		String class2 = findClass(split_second[0].substring(3, split_second[0].length()));
		String method2 = findMethod(split_second[1], class2);
		
		target = class1 + ":" + method1 + " " + split_second[0].substring(0,3) + class2 + ":" + method2 + "\r\n";
		return target;
	}
	
	public static void modify_Callgraph(String origin, String target)
	{
		File from = new File(origin);
		File to = new File(target);
		String[] split = null;
		String tmp = "";
		try {
			split = readfile(from);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tmp = tmp + "M:" + change_OriginMethod(split[split.length - 1]);
		for(int i = 0; i < split.length; i++)
		{
			if(split[i].indexOf("C") == 0)
			{
				tmp = tmp + "C:" + change_OriginClass(split[i]);
			}
			else
			{
				tmp = tmp + "M:" + change_OriginMethod(split[i]);
			}
			//System.out.println(split[i]);
		}
		//String[] haha = tmp.split("\r\n");
		//System.out.println(haha.length);
		//System.out.println(tmp);
		try {
			writefile(to, tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] arg)
//	{
//		String origin = "origin.txt";
//		String result = "Tetris_out.txt";
//		String target = "target.txt";
//		String filename = "mapping.map";
//		ReadMapping testmapping = new ReadMapping();
//		ReadMapping.read_Mapping(filename);
//		//testmapping.print();
//		modify_Callgraph(result, target);
//	}
}
