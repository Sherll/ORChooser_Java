package StepByStep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import similarity.GetFiles;
import similarity.compareopt;

public class InstructorPool {

	String[] instructor = null;
	boolean[] mark = null;
	public InstructorPool()
	{
		//0~3 is necessary.
		instructor = new String[19];
		mark = new boolean[19];
		/*************************to be changed**************************/
		instructor[0] = "-injars in/compress.jar\r\n";
		instructor[1] = "-outjars out/Tetris_out.jar\r\n";
		instructor[2] = "-libraryjars <java.home>/lib/rt.jar\r\n";
		instructor[7] = "-dontshrink\r\n";
		instructor[4] = "-dontoptimize\r\n";
		instructor[5] = "-dontobfuscate\r\n";
		instructor[6] = "-dontskipnonpubliclibraryclasses\r\n";
		instructor[3] = "-keepclasseswithmembers class * {\r\npublic static void main(java.lang.String[]);\r\n}\r\n";
		instructor[8] = "-dontskipnonpubliclibraryclassmembers\r\n";
		instructor[9] = "-target 1.7\r\n";  //use 1.7 for testing temporarily.
		instructor[10] = "-forceprocessing\r\n";
		instructor[11] = "-keep class ErsBlock {*; }\r\n";
		instructor[12] = "-optimizationpasses 5\r\n";
		instructor[13] = "-allowaccessmodification\r\n";
		instructor[14] = "-mergeinterfacesaggressively\r\n";
		instructor[15] = "-useuniqueclassmembernames\r\n";
		instructor[16] = "-overloadaggressively\r\n";
		instructor[17] = "-repackageclasses ''\r\n";
		instructor[18] = "-keepparameternames\r\n";		
		for(int i = 0; i < 19; i++)
		{
			mark[i] = false;
		}
	}
	
	public String generateCfg1(int index, int len, RecordResult a)
	{
		for(int i = 0; i < 19; i++)
		{
			mark[i] = false;
		}
		instructor[1] = "-outjars out/Tetris_out" + index + ".jar\r\n";
		
		String result = instructor[0] + instructor[1] + instructor[2] + instructor[3] + "-dontusemixedcaseclassnames\r\n" + "-keep class jnt.scimark2.*\r\n";
		mark[0] = mark[1] = mark[2] = mark[3] = true;
		/***********test for 6**************/
//		result = result + instructor[4] + instructor[5] + instructor[7];
		/***********test for 5**************/
//		result = result + instructor[4] + instructor[6] + instructor[7];

		Random ra = new Random();
		
		for(int i = 0; i < len; i++)
		{
			int num = ra.nextInt(15) + 4;
			while(mark[num] && !isDone())
			{
				num = ra.nextInt(15) + 4;
			}
			mark[num] = true;
			//System.out.println(num);
		}
		if(mark[4] != true)
		{
			result = result + instructor[4];
		}
		if(mark[5] != true)
		{
			result = result + instructor[5];
		}
		if(mark[6] != true)
		{
			result = result + instructor[6];
		}
		if(mark[7] != true)
		{
			result = result + instructor[7];
		}
		int m = 0;
		for(int i = 4; i < 19; i++)
		{
			if(mark[i] == true)
			{
				if(i !=4 && i != 5 && i != 7 && i != 6)
				{
					result = result + instructor[i];
     			}
				a.setSingleConfig(m, i);
				m++;
			}
		}
		//System.out.println(result);
		String fileCfg = "Tetris" + index + ".pro";
		OutputStream Out = null;
		try {
			Out = new FileOutputStream(fileCfg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bytes = result.getBytes();
		try {
			Out.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return fileCfg;
	}

	private static String readfile(File mapping) throws IOException 
	{
		InputStream In = new FileInputStream(mapping);
		int size = In.available();
		byte[] recordRead = new byte[size];
		for(int i = 0; i < size; i++)
		{
			recordRead[i] = (byte)In.read();
		}
		String s = new String(recordRead, 0, size);
		return s;
	}
	
	public String generateCfg2(int index, RecordResult a)
	{
		instructor[1] = "-outjars out/Tetris_out" + index + ".jar\r\n";
		String oldinstructor = "-outjars out/Tetris_out" + (index - 1) + ".jar\r\n";
		String oldresult = instructor[0] + instructor[1] + instructor[2] + instructor[3] + "-dontusemixedcaseclassnames\r\n"
				+ instructor[4] + instructor[5] + instructor[6] + instructor[7];
		String result = "";
		
		String lastone_str = "Tetris" + (index -  1) + ".pro";
		String SourceFile = "in/scimark2lib.jar";
		String DestinationFile = "out/Tetris_out" + index + ".jar";
		double[] record = new double[19];
		for(int i = 0; i < 19; i++)
		{
			record[i] = 100;
		}
		//read the previous one.
		if(index != 0)
		{
			File lastone = new File(lastone_str);
			if(lastone.exists())
			{
				try {
					oldresult = readfile(lastone);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			oldresult = oldresult.replaceAll(oldinstructor, instructor[1]);
		}
		
		for(int i = 4; i < 19;i++)
		{
			File DDir = new File(DestinationFile);
			File SDir = new File(SourceFile);
			result = "";
			
			if(DDir.exists())
			{
				DDir.delete();
			}

			if(i !=4 && i != 5 && i != 6 && i !=7)
				result = oldresult + instructor[i];
			else
				result = oldresult.replaceAll(instructor[i], "");
			String tmpFile = "Tetris" + index + "_" + i + ".pro";
			File tmpfileCfg = new File(tmpFile);
			if(tmpfileCfg.exists())
			{
				tmpfileCfg.delete();
			}
			OutputStream Out = null;
			try {
				Out = new FileOutputStream(tmpfileCfg);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] bytes = result.getBytes();
			try {
				Out.write(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String command = "cmd /c java -jar proguard.jar @" + tmpFile;
			try {
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();   // to make the command run timely.
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//	System.out.println("hehe");
			
			compareopt copt = new compareopt();
			if(DDir.exists() && SDir.exists())
			{
				GetFiles files = new GetFiles(SDir, DDir);
				try {
					record[i] = copt.test(files);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(DDir.exists())
			{
				DDir.delete();
			}
		}
		
		double Minnum = 100;
		int min_index = -1;
		for(int i = 4; i < 19; i++)
		{
			if(Minnum > record[i])
			{
				Minnum = record[i];
				min_index = i;
			}
		}
		
		a.setSingleConfig(0, min_index);
		File tmpfileCfg = new File("Tetris" + index + "_" + min_index + ".pro");
		try {
			result = readfile(tmpfileCfg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(Minnum + " " + min_index);
		
		/*********************writing*****************************/
		String fileCfg = "Tetris" + index + ".pro";
		OutputStream Out = null;
		try {
			Out = new FileOutputStream(fileCfg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bytes = result.getBytes();
		try {
			Out.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return fileCfg;
	}
	
	public boolean isDone()
	{
		for(int i = 0; i < 19; i++)
		{
			if(!mark[i])
				return false;
		}
		return true;
	}

	public boolean[] int22(int a)
	{
		boolean[] tmp = new boolean[19];
		tmp[0] = tmp[1] = tmp[2] = tmp[3] = true;
		for(int i = 0; i < 15; i++)
		{
			int b = a & 1;
			if(b == 1)
			{
				tmp[i + 4] = true; 		
			}
			else
			{
				tmp[i + 4] = false;
			}
			a = a >> 1;
		}	
		return tmp;
	}
	
	public String get_result(boolean[] a)
	{
		String result = instructor[4] + instructor[5] + instructor[6] + instructor[7] + "-ignorewarnins\r\n";
		for(int i = 0; i < 19; i++)
		{
			if(a[i])
			{
				if(i != 4 && i != 5 && i != 6 && i != 7)
				{
					result = result + instructor[i];
				}
				else
				{
					result = result.replaceAll(instructor[i], "");
				}
			}
		}
		return result;
	}
	
	public String write_file(String a)
	{
		String tmpFile = "Tetris.pro";
		File tmpfileCfg = new File(tmpFile);
		if(tmpfileCfg.exists())
		{
			tmpfileCfg.delete();
		}
		OutputStream Out = null;
		try {
			Out = new FileOutputStream(tmpfileCfg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bytes = a.getBytes();
		try {
			Out.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmpFile;
	}
	
	public double get_fitness(int a)
	{
		double tmp = 0.0;
		//step 1: change a into binary. fill mark array.
		mark = int22(a);

		String result = get_result(mark);
		String tmp_name = write_file(result);

		/*********************need to be changed****************************/
		String SourceFile = "in/compress.jar";
		String DestinationFile = "out/Tetris_out.jar";
		
		File SDir = new File(SourceFile);
		File DDir = new File(DestinationFile);
		
		if(DDir.exists())
		{
			DDir.delete();
		}
				
		String command = "cmd /c java -jar proguard.jar @" + tmp_name;
		try {
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();   // to make the command run timely.
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		compareopt copt = new compareopt();
		if(DDir.exists() && SDir.exists())
		{
			GetFiles files = new GetFiles(SDir, DDir);
			try {
				tmp = copt.test(files);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return tmp;
	}
}
