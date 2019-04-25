package similarity;

import java.io.*;

public class compareshr {
	static MappingCount usageresult = new MappingCount();
	
	private static String[] readfile(File usage) throws IOException
	{
		InputStream In = new FileInputStream(usage);
		int size = In.available();
		byte[] recordRead = new byte[size];
		for(int i = 0; i < size; i++)
		{
			recordRead[i] = (byte)In.read();
		}
		String s = new String(recordRead, 0, size);
		String[] s_split = s.split(":\r");
		In.close();
		/********for debug***************/
//		String[] sss = s.split("\n");
//		System.out.println(sss.length);
		return s_split;
	}
	
	public static void usage_count(String filename)
	{
		File usage = new File(filename);
		String[] ssplit = null;
		try {
			ssplit = readfile(usage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usageresult.setnumclass(ssplit.length - 1);
		for(int i = 1; i < ssplit.length; i++)
		{
			String[] s_ssplit = ssplit[i].split("\r\n");
			for(int j = 0; j < s_ssplit.length; j++)
			{
				if(s_ssplit[j].indexOf(":") != -1) 
				{
					usageresult.increasenummethod();
				}
				else
				{
					usageresult.increasenumvariable();
				}
			}
		}
		usageresult.setnumvariable(usageresult.getnumvariable() - usageresult.getnumclass() + 1);
	}
	
	public static void print()
	{
		usageresult.print();
	}
	
//	public static void main(String[] arg)
//	{
//		String filename = "Tetris0.usage";
//		usage_count(filename);
//		usageresult.print();
//	}
	
}
