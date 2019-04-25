package StepByStep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import similarity.GetFiles;
import similarity.compareopt;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class StepLeng {
	int steplenth;
	
	public StepLeng()
	{
		steplenth = 3;
	}
	
	//random
	public void run1()
	{
		String sourceDir = "in/";
		String DestinationDir = "out/";
		
		if(!(new File(DestinationDir)).exists())
		{
			System.out.println("Destination Directory does not exist.");
			System.exit(-2);
		}
		sourceDir = sourceDir + "scimark2lib.jar";
		if(!(new File(sourceDir)).exists())
		{
			System.out.println("Source file does not exist.");
			System.exit(-2);
		}
		InstructorPool ip = new InstructorPool();	
		Random ra = new Random();
		double[] record = new double[50];
		RecordResult[] remember = new RecordResult[50];
		for(int i = 0; i < 50; i++)
		{
			record[i] = 0.0;
		}
		for(int i = 0; i < 50; i++)
		{
			System.out.println(i);
			String fileCfg = "Tetris" + i + ".pro";
			File configure = new File(fileCfg);
			DestinationDir = "out/Tetris_out" + i + ".jar";	
			int lenth = ra.nextInt(15) + 1;
			remember[i] = new RecordResult(lenth);

			if(configure.exists())
			{
				configure.delete();
			}
			fileCfg = ip.generateCfg1(i, lenth, remember[i]);
			String command = "cmd /c java -jar proguard.jar @" + fileCfg;
			try {
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();   // to make the command run timely.
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//	System.out.println("hehe");
			File DDir = new File(DestinationDir);
			File SDir = new File(sourceDir);
			
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
		}
		/***************************/
		Workbook book = new HSSFWorkbook();
		Sheet sheet1 = (Sheet)book.createSheet("Table1");
		Row row1 = sheet1.createRow(0);
		Row row2 = sheet1.createRow(1);
		for(int i = 0; i < 50; i++)
		{
			Cell cell1 = row1.createCell(i);
			cell1.setCellValue(remember[i].getResult());
			Cell cell2 = row2.createCell(i);
			cell2.setCellValue(record[i]);
		}
		try {
			book.write(new FileOutputStream("random.xls"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*************************************/
	}

	//greedy
	public void run2()   
	{
		//step1: preparation
		String sourceDir = "in/";
		String DestinationDir = "out/";
		
		if(!(new File(DestinationDir)).exists())
		{
			System.out.println("Destination Directory does not exist.");
			System.exit(-2);
		}
		sourceDir = sourceDir + "Tetris_out.jar";
		if(!(new File(sourceDir)).exists())
		{
			System.out.println("Source file does not exist.");
			System.exit(-2);
		}
		InstructorPool ip = new InstructorPool();
		double[] record = new double[100];
		RecordResult[] remember = new RecordResult[100];
		
		//step2: set the number of iteration is 10 temporarily
		double difference = 1.0;
		int i = 0;
		while(difference != 0.0)
		{
			String fileCfg = "Tetris" + i + ".pro";
			File configure = new File(fileCfg);
			DestinationDir = "out/Tetris_out" + i + ".jar";
			int lenth = 1;
			remember[i] = new RecordResult(lenth);
			
			if(configure.exists())
			{
				configure.delete();
			}
			fileCfg = ip.generateCfg2(i,remember[i]);
			String command = "cmd /c java -jar proguard.jar @" + fileCfg;
			
			try {
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();   // to make the command run timely.
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//	System.out.println("hehe");
			File DDir = new File(DestinationDir);
			File SDir = new File(sourceDir);
			
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
			if(i == 0)
				difference = record[0];
			else
				difference = record[i - 1] - record[i];
			i++;
		}
		/***************************/
		Workbook book = new HSSFWorkbook();
		Sheet sheet1 = (Sheet)book.createSheet("Table1");
		Row row1 = sheet1.createRow(0);
		Row row2 = sheet1.createRow(1);
		for(int n = 0; n < i; n++)
		{
			Cell cell1 = row1.createCell(n);
			cell1.setCellValue(remember[n].getResult());
			Cell cell2 = row2.createCell(n);
			cell2.setCellValue(record[n]);
		}
		try {
			book.write(new FileOutputStream("greedy.xls"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*************************************/
	}
	
	public void run3()
	{
		String sourceDir = "in/";
		String DestinationDir = "out/";
		
		if(!(new File(DestinationDir)).exists())
		{
			System.out.println("Destination Directory does not exist.");
			System.exit(-2);
		}
		/**********************need to be changed************************/
		sourceDir = sourceDir + "compress.jar";
		if(!(new File(sourceDir)).exists())
		{
			System.out.println("Source file does not exist.");
			System.exit(-2);
		}
		
		//we need to change fit function -- the reverse of similarity score.
		int iteration = 50;
		GA ga = new GA(iteration);
		ga.run();
	}
	
	public static void main(String[] args)
	{
		StepLeng sl = new StepLeng();
	//	sl.run1();
	//	sl.run2();
		sl.run3();
	}
}
