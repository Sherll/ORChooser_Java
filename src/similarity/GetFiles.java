package similarity;

import gr.gousiosg.javacg.stat.JCallGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class GetFiles {

	String origin = "origin.txt";
	String result = "Tetris_out.txt";
	String target = "target.txt";
	String filename = "mapping.map";
	String _origin = "_origin.txt";
	String _target = "_0.txt";
	File file1 = null;
	File file2 = null;
	public GetFiles(File file1, File file2)
	{
		this.file1 = file1;
		this.file2 = file2;
		System.out.println(file1);
		System.out.println(file2);
	}
	
	public String get_origin()
	{
		if(new File(_origin).exists())
		{
			return _origin;
		}
		if(file1.getName().contains(".class"))
		{
			//System.out.println("Start_origin");
			String command = "cmd /c javap -verbose -c " + file1.getAbsolutePath();
			BufferedReader br = null;
			try {
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();
				br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
	            String line = null; 
	            StringBuilder sb = new StringBuilder(); 
	            while ((line = br.readLine()) != null) { 
	                sb.append(line + "\r\n"); 
	            } 
	            PrintStream standard = System.out;
	            PrintStream ps = null;
	            ps = new PrintStream(new FileOutputStream(_origin));
	            System.setOut(ps);
	            System.out.println(sb);
	            ps.close();
	            System.setOut(standard);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(file1.getName().contains(".jar"))
		{
			//unzip 
			String descDir = "in/" + file1.getName().substring(0, file1.getName().length() - 4);
			unZipFiles(file1, descDir);
			//find all the names of the classfile
			List<File> files = searchFiles(new File(descDir));
			int i = 0;
			Process p = null;
			while(i < files.size())
			{
				String command = "cmd /c javap -verbose -c";
				int j = i;
				for(; j < files.size() && j < i + 5; j++)
				{
					command = command + " " + files.get(j).getAbsolutePath();
				}
				i = j;
//				System.out.println(i);

				BufferedReader br = null;
				try {
					p = Runtime.getRuntime().exec(command);
					//p.waitFor();
					br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
					String line = null; 
					StringBuilder sb = new StringBuilder(); 
					while ((line = br.readLine()) != null) { 
						sb.append(line + "\r\n"); 
					} 
					PrintStream standard = System.out;
					PrintStream ps = null;
					ps = new PrintStream(new FileOutputStream(_origin, true));
					System.setOut(ps);
					System.out.println(sb);
					ps.close();
					System.setOut(standard);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			deleteDir(descDir);
		}
		else
		{
			System.out.println("Illegal input!");
			System.exit(-1);
		}
		return _origin;
	}
	
	public String get_target()
	{
		if(file2.getName().contains(".class"))
		{
			System.out.println("Start_origin");
			String command = "cmd /c javap -verbose -c " + file2.getAbsolutePath();
			BufferedReader br = null;
			try {
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();
				br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
	            String line = null; 
	            StringBuilder sb = new StringBuilder(); 
	            while ((line = br.readLine()) != null) { 
	                sb.append(line + "\r\n"); 
	            } 
	            PrintStream standard = System.out;
	            PrintStream ps = null;
	            ps = new PrintStream(new FileOutputStream(_target));
	            System.setOut(ps);
	            System.out.println(sb);
	            ps.close();
	            System.setOut(standard);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(file2.getName().contains(".jar"))
		{
			//unzip 
			String descDir = "out/" + file2.getName().substring(0, file2.getName().length() - 4);
			unZipFiles(file2, descDir);
			//find all the names of the classfile
			List<File> files = searchFiles(new File(descDir));
			int i = 0;
			Process p = null;
			while(i < files.size())
			{
				String command = "cmd /c javap -verbose -c";
				int j = i;
				for(; j < files.size() && j < i + 5; j++)
				{
					command = command + " " + files.get(j).getAbsolutePath();
				}
				i = j;
				
				
				BufferedReader br = null;
				try {
					p = Runtime.getRuntime().exec(command);
					br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
					String line = null; 
					StringBuilder sb = new StringBuilder(); 
					while ((line = br.readLine()) != null) { 
						sb.append(line + "\r\n"); 
					} 
					PrintStream standard = System.out;
					PrintStream ps = null;
					ps = new PrintStream(new FileOutputStream(_target, true));
					System.setOut(ps);
					System.out.println(sb);
					ps.close();
					System.setOut(standard);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			deleteDir(descDir);
		}
		else
		{
			System.out.println("Illegal input!");
			System.exit(-1);
		}
		return _target;
	}
	
	@SuppressWarnings("unchecked")
	public String getorigin() throws ClassNotFoundException
	{
		String name = null;
		if(new File(origin).exists())
		{
			return origin;
		}
		if(file1.getName().contains(".class"))
		{
			//zip the classfile.
			System.out.println("Start!");
			String source = file1.getAbsolutePath();
			String target1 = file1.getName().substring(0, file1.getName().length()-6) + ".jar";
			name = target1;
			zip(file1, new File(target1));		
		}
		else if(file1.getName().contains(".jar"))
		{
			name = file1.getAbsolutePath();
		}
		else
		{
			System.out.println("Illegal input!");
			System.exit(-1);
		}
		PrintStream out = System.out;
		PrintStream ps = null;
		try {
			ps = new PrintStream(new FileOutputStream(origin));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setOut(ps);
		JCallGraph.main(new String[]{name});
		ps.close();
		System.setOut(out);
		return origin;
	}
	
	public String gettarget()
	{
		return target;
	}   // doesn't need to open new file.
	
	public String getresult()
	{
		String name = null;
		if(file2.getName().contains(".class"))
		{
			//zip the classfile.
			String source = file2.getAbsolutePath();
			String target1 = file2.getName().substring(0, file2.getName().length()-6) + ".jar";
			name = target1;
			zip(file2, new File(target1));		
		}
		else if(file2.getName().contains(".jar"))
		{
			name = file2.getAbsolutePath();
		}
		else
		{
			System.out.println("Illegal input!");
			System.exit(-1);
		}
		PrintStream out = System.out;
		PrintStream ps = null;
		try {
			ps = new PrintStream(new FileOutputStream(result));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setOut(ps);
		JCallGraph.main(new String[]{name});
		ps.close();
		System.setOut(out);
		return result;
	}
	
	public String getfilename()
	{
		return filename;
	}   // doesn't need to open new file

	public void zip(File filein, File fileout)
	{
		ZipOutputStream zos = null;
		try {
			zos = new JarOutputStream(new FileOutputStream(fileout));
			zos.setComment("From Log");
			zipFile(zos, filein, null);
			System.err.println("Compress accomplished!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Failed to compress");
		} finally{
			if(zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public void zipFile(ZipOutputStream zos, File filein, String path)
	{
		zipSingleFile(zos, filein, filein.getName());
	}
	
	public void zipSingleFile(ZipOutputStream zos, File filein, String path)
	{
		try {
			InputStream in = new FileInputStream(filein);
			try {
				zos.putNextEntry(new ZipEntry(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			write(in, zos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public void unZipFiles(File zipFile, String descDir)
	{
		try {
			
			ZipFile zip = new ZipFile(zipFile,Charset.forName("GBK"));
			File pathFile = new File(descDir);  
		    if (!pathFile.exists()) {  
		         pathFile.mkdirs();  
		     } 
		    for(Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) 
		    {
		    	ZipEntry entry = (ZipEntry) entries.nextElement();
		    	String zipEntryName = entry.getName();
	
		    	InputStream in = zip.getInputStream(entry);
		    	String outPath = (descDir + "/" + zipEntryName).replaceAll("\\*", "/"); //the form of the name of files
		    	//judge the filepath.
		    	File file = new File(outPath.substring(0,outPath.lastIndexOf('/')));
		    	if(!file.exists())
		    	{
		    		file.mkdirs();
		    	}
		    	if((new File(outPath)).isDirectory())
		    	{
		    		continue;
		    	}
		    	FileOutputStream out = new FileOutputStream(outPath); // test for different form of letter name.
		    	byte[] buf1 = new byte[1024];
		    	int len;
		    	while((len = in.read(buf1)) > 0)
		    	{
		    		
		    		out.write(buf1, 0, len);
		    	}
		    	in.close();	
		    	out.close();	    	
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void write(InputStream in, ZipOutputStream zos) 
	{
		// TODO Auto-generated method stub
		int len = -1;
		byte[] buff = new byte[1024];
		try {
			while((len = in.read(buff)) != -1)
			{
				zos.write(buff, 0, len);
			}
			zos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<File> searchFiles(File folder)
	{
		if(!folder.exists())
		{
			System.out.println("Directory not found" + folder.getAbsolutePath());
			System.exit(-2);
		}
		List<File> result = new ArrayList<File>();
		if(folder.isFile())
		{
			result.add(folder);
		}
		
		File[] subFolders = folder.listFiles(new FileFilter(){
			public boolean accept(File file)
			{
				if(file.isDirectory())
				{
					return true;
				}
				if(file.getName().contains(".class"))
				{
					return true;
				}
				return false;
			}
		});
		
		if(subFolders != null)
		{
			for(File file : subFolders)
			{
				if(file.isFile())
				{
					result.add(file);
				}
				else
				{
					result.addAll(searchFiles(file));
				}
			}
		}
		return result;
	}
	
	public void deleteDir(String dir)
	{
		File Dir = new File(dir);
		if(Dir.isFile())
		{
			Dir.delete();
		}
		else
		{
			String[] subDir = Dir.list();
			for(String filepath:subDir)
			{
				String filename = dir + "/" + filepath;
				deleteDir(filename);
			}
			Dir.delete();
		}
	}
}
