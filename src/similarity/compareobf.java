package similarity;

import java.io.*;

public class compareobf {
	static MappingCount mappingresult = new MappingCount();
	
	
	private static String[] readfile(File mapping) throws IOException {
		InputStream In = new FileInputStream(mapping);
		int size = In.available();
		byte[] recordRead = new byte[size];
		for(int i = 0; i < size; i++)
		{
			recordRead[i] = (byte)In.read();
		}
		String s = new String(recordRead, 0, size);
		String[] s_split = s.split(":");
		In.close();
		
		/********for debug***************/
//		String[] sss = s.split("\n");
//		System.out.println(sss.length);
		return s_split;
	}
	
	public static void mapping_count(String filename)
	{
		File mapping = new File(filename);
		String[] ssplit = null;
		try {
			ssplit = readfile(mapping);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mappingresult.setnumclass(ssplit.length - 1);
		for(int i = 1; i < ssplit.length;  i++)
		{
			String[] s_ssplit = ssplit[i].split("\r");
			for(int j = 0; j < s_ssplit.length; j++)
			{
				if(s_ssplit[j].indexOf("-") == -1) continue;
				//System.out.println(s_ssplit[j] + "\t" + j);
				if(s_ssplit[j].indexOf("(") != -1)
				{
					mappingresult.increasenummethod();
				}
				else
				{
					mappingresult.increasenumvariable();
				}
			}
		}
		mappingresult.setnumvariable(mappingresult.getnumvariable() - mappingresult.getnumclass() + 1);
		correctMapping();
	}
	
	private static void correctMapping()
	{
		mappingresult.setnumclass(mappingresult.getnumclass() - ReadMapping.correctClass());
		mappingresult.setnummethod(mappingresult.getnummethod() - ReadMapping.correctMethod());
		mappingresult.setnumvariable(mappingresult.getnumvariable() - ReadMapping.correctVariable());
	}
	
	public static void print()
	{
		mappingresult.print();
	}
	
//	public static void main(String[] arg) 
//	{
//		String filename = "Tetris0.map";
//		mapping_count(filename);
//		mappingresult.print();
//	}
	
}
