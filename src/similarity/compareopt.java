package similarity;

import java.io.*;

public class compareopt {

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
	
	private static String readfile(File filestream, int type) throws IOException
	{

		InputStream In = new FileInputStream(filestream);
		int size = In.available();
		byte[] recordRead = new byte[size];
		for(int i = 0; i < size; i++)
		{
			recordRead[i] = (byte)In.read();
		}
		String s = new String(recordRead, 0, size);
		In.close();
		return s;
	}
	
	private static double compareOptimize(String origin, String target)
	{
		File from = new File(origin);
		File to = new File(target);
		String[] target_file = null;
		String source_file = null;
		int count = 0;
		try {
			target_file = readfile(to);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			source_file = readfile(from, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < target_file.length; i++)
		{
			if(source_file.contains(target_file[i]))
			{
				count++;
			}
//			else
//			{
//				System.out.println(target_file[i]);
//			}
		}
		String[] split_source = source_file.split("\r\n");
		//System.out.println((double)target_file.length/split_source.length);
		//System.out.println(count/split_source.length);
		return ((double)count/split_source.length);
	}
	
	public double test(GetFiles files) throws ClassNotFoundException
	{
		
		String origin = files.getorigin();
		//System.out.println(origin);
		String result = files.getresult();
		//System.out.println(result);
		String target = files.gettarget();
		//System.out.println(target);
		String filename = files.getfilename();
		//System.out.println(filename);
		String _origin = files.get_origin();
		//System.out.println(_origin);
		String _target = files.get_target();
		//System.out.println(_target);

	//	System.out.println("Starting to compute scores");
		
		RecordClass before = new RecordClass();
		RecordClass after = new RecordClass();
		after.record_class(_target);
		before.record_class(_origin);
		
		GetDistance distance = new GetDistance();
		ReadMapping r = new ReadMapping();
		distance.getMap(before, after, r);
		
		ReadMapping.read_Mapping(filename);
		ReadCallgraph.modify_Callgraph(result, target);
		double optresult = compareOptimize(origin, target);
		//ReadMapping.print();
	//	System.out.println("Scores:\n" + optresult + "\n");
		
	//	new File(origin).delete();
		new File(target).delete();
	//	new File(_origin).delete();
		new File(_target).delete();
		new File(filename).delete();
		new File(result).delete();	
		return optresult;
	}
}
