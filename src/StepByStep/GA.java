package StepByStep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class GA {
	
	int population_size;
	int gene_len;
	double pc;
	double pm;
	int iteration;
	double keep_rate;
	double[] fitness1;
	double[] fitmean;
	double[] fitbest;
	List<int[]> result1;
	List<double[]> result2;
	List<double[]> result3;
	int[] population;   //to record the name of .pro files.
	
	
	public GA(int num)
	{
		population_size = 50;
		gene_len = 15;
		pc = 0.6;
		pm = 0.01;
		keep_rate = 0.2;
		iteration = num;
		fitness1 = new double[population_size];
		
		fitmean = new double[iteration];
		fitbest = new double[iteration];
		result1 = new ArrayList<>();   //to record population[] -> int[]
		result2 = new ArrayList<>();   //to record fitness[] -> double[]
		result3 = new ArrayList<>();   //to record score[] -> double[]
		population = new int[population_size];
		
		
		for(int i = 0; i < population_size; i++)
		{
			fitness1[i] = 0.0;
			population[i] = 0;
		}
		
		for(int i = 0; i < iteration; i++)
		{
			fitmean[i] = 0.0;
		}
		
	}
	
	
	public int[] get_origin()
	{
		int[] tmp = new int[population_size];
		for(int i = 0; i < population_size; i++)
		{
			tmp[i] = 0;
		}
		Random ra = new Random();
		for(int i = 0; i < population_size; i++)
		{
			for(int j = 0; j < gene_len; j++)
			{
				//from right to the left.
				tmp[i] = (int) (ra.nextInt(2) * (Math.pow(2,j)) + tmp[i]);				
			}
		}
		return tmp;
	}
	
	public double[] get_fitness(int[] a)
	{
		double[] tmp = new double[a.length];
		double[] tmp3 = new double[population_size];
		InstructorPool ip = new InstructorPool();
		
		double Maxnum = 0.0;
		for(int i = 0; i < a.length; i++)
		{
			tmp[i] = ip.get_fitness(a[i]);
			tmp3[i] = tmp[i];
		}		
		for(int i = 0; i < a.length; i++)
		{
			tmp[i] = 1.0 / tmp[i];
		}
		result3.add(tmp3);
		return tmp;
	}
	
	public double get_mean(double[] a)
	{
		double sum = 0.0;
		for(int i = 0; i < a.length; i++)
		{
			sum = sum + a[i];
		}
		sum = sum/a.length;
		return sum;
	}
	
	public double get_best(double[] a)
	{
		double Maxnum = 0.0;
		for(int i = 0; i < a.length; i++)
		{
			if(Maxnum < a[i])
			{
				Maxnum = a[i];
			}
		}
		return Maxnum;
	}
	
	public void regularization(double[] b)
	{
		for(int i = b.length - 2; i >= 0; i--)
		{
			double total = 0.0;
			int j = 0;
			
			while(j <= i)
			{
				total = total + b[j];
				j++;
			}
			
			b[i] = total;
		}
		b[b.length - 1] = 1;
	}
	
	public int[] get_order(double[] b)
	{
		int[] array_index = new int[b.length];
		boolean[] mark = new boolean[b.length];
		for(int i = 0; i < b.length; i++)
		{
			mark[i] = false;
		}
		double maxnum = 0;
		int maxindex = -1;
		double tmp = 0;
		for(int i = 0; i < b.length; i++)
		{
			for(int j = 0; j < b.length; j++)
			{
				if(maxnum < b[j] && mark[j] != true)
				{
					maxnum = b[j];
					maxindex = j;
				}
			}
			mark[maxindex] = true;
			array_index[i] = maxindex;
			maxnum = 0;
		}
		return array_index;
	}
	
	public void selection(double[] b)
	{
		double[] new_fitness = new double[b.length];
		int[] a = population;

		double total_fitness = 0.0;
		for(int i = 0; i < b.length; i++)
		{
			total_fitness = total_fitness + b[i];
		}
		for(int i = 0; i < b.length; i++)
		{
			new_fitness[i] = b[i]/total_fitness;
		}
		regularization(new_fitness);
		
		Random ra = new Random();
		double[] ms = new double[a.length];
		int pop_len = a.length;
		for(int i = 0; i < pop_len; i++)
		{
			ms[i] = ra.nextDouble();
		}
		Arrays.sort(ms, 0, pop_len);
		
		//roulette temporarily. Improving: keep the top 20%;

		int fitin = 0;
		int newin = 0;//(int) (population_size*keep_rate);
		int[] new_pop = new int[pop_len];

		while(newin < pop_len)
		{
			if(ms[newin] < new_fitness[fitin])
			{
				new_pop[newin] = a[fitin];
				newin++;
			}
			else
			{
				fitin++;
			}
		}
		double max = 0.0;
		int max_index = -1;
		for(int i = 0; i < b.length; i++) {
			if(max < b[i]) {
				max = b[i];
				max_index = i;
			}
		}
		new_pop[0] = population[max_index];
		population = new_pop;
	}
	
	public int[] get_newpopulation(int cp, int a1, int a2)
	{
		int tmp11 = 0;
		int tmp12 = 0;
		int tmp21 = 0;
		int tmp22 = 0;
		int[] tmp = new int[2];
		tmp[0] = 0;
		tmp[1] = 1;
		int IsoneOrnot1 = 0;
		int IsoneOrnot2 = 0;
		int b1 = a1;
		int b2 = a2;
		
		for(int i = 0; i <= cp; i++)
		{
			IsoneOrnot1 = b1 & 1;
			if(IsoneOrnot1 == 1)
			{
				tmp11 = (int) (tmp11 + Math.pow(2, i));
			}
			b1 = b1 >> 1;

			IsoneOrnot2 = b2 & 1;
			if(IsoneOrnot2 == 1)
			{
				tmp21 = (int) (tmp21 + Math.pow(2, i));
			}
			b2 = b2 >> 1;
		}
		for(int i = cp + 1; i < gene_len; i++)
		{
			IsoneOrnot1 = b1 & 1;
			if(IsoneOrnot1 == 1)
			{
				tmp12 = (int)(tmp12 + Math.pow(2, i));
			}
			b1 = b1 >> 1;
			
		    IsoneOrnot2 = b2 & 1;
		    if(IsoneOrnot2 == 1)
		    {
		    	tmp22 = (int)(tmp22 + Math.pow(2, i));
		    }
		    b2 = b2 >> 1;	
		}
		tmp[0] = tmp11 + tmp22;
		tmp[1] = tmp12 + tmp21;

		return tmp;
	}
	
	public void crossover(int[] a)
	{
		int pop_len = a.length;
		Random ra1 = new Random();
		Random ra2 = new Random();
		for(int i = 0; i < pop_len - 1; i++)
		{
			if(ra1.nextDouble() < pc)
			{
				
				int cpoint = ra2.nextInt(15);
				int[] temporary = get_newpopulation(cpoint, a[i], a[i+1]);
				a[i] = temporary[0];
				a[i+1] = temporary[1];
			}
		}
	}
	
	public void mutation(int[] a)
	{
		Random ra1 = new Random();
		Random ra2 = new Random();
		for(int i = 0; i < population_size; i++)
		{
			if(ra1.nextDouble() < pm)
			{				
				int mpoint = ra2.nextInt(15);
		
				int b = a[i];
				if(((b >> mpoint) & 1) == 1)
				{
					b = b - (int)Math.pow(2, mpoint);
				}
				else
				{
					b = b + (int)Math.pow(2, mpoint);
				}
				a[i] = b;
			}
		}
	}
	
	public void misorder(int[] a)
	{
		Random ra = new Random();
		int tmp = -1;
		for(int i = 0; i < 10; i++)
		{
			int index1 = ra.nextInt(a.length);
			int index2 = ra.nextInt(a.length);
			tmp = a[index1];
			a[index1] = a[index2];
			a[index2] = tmp;
		}
	}
	
	public void run()
	{
		
		//preparation
		/**************get initial group****************/
		population = get_origin();
		
		for(int i = 0; i < iteration; i++)
		{
			System.out.println("i:" + i);
			fitness1 = get_fitness(population);
			int[] tmp1 = new int[population_size];
			double[] tmp2 = new double[population_size];
			
			for(int j = 0; j < population_size; j++)
			{
				tmp1[j] = population[j];
				tmp2[j] = fitness1[j];
			}
			
			result2.add(tmp2);  
			result1.add(tmp1);
			
			fitmean[i] = get_mean(tmp2);
			fitbest[i] = get_best(tmp2);

			selection(fitness1);
			crossover(population);
			mutation(population);
			misorder(population);
		}
		
			
		/*****************write to excel**********************/		
		/*****************XHSSFWorkbook might be help*********************/
//		Sheet sheet2 = (Sheet)book.createSheet("Fitness");
//		Sheet sheet3 = (Sheet)book.createSheet("Fitmean");
//		Sheet sheet4 = (Sheet)book.createSheet("Fitbest");
//		Sheet sheet5 = (Sheet)book.createSheet("OriginalScore");
//		Row row3 = sheet3.createRow(0);
//		Row row4 = sheet4.createRow(0);
		//each time write 200
//		Workbook book = new HSSFWorkbook();
//		Sheet sheet1 = (Sheet)book.createSheet("Population");
		int cal = 0;
		while(cal < iteration)
		{
			int n = cal;		
			FileOutputStream population = null;
			FileOutputStream fitness = null;
			FileOutputStream originscore = null;
			try {
				population = new FileOutputStream("population.xls", true);
				fitness = new FileOutputStream("fitness.xls", true);
				originscore = new FileOutputStream("originscore.xls", true);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String tmp1 = "";
			String tmp2 = "";
			String tmp3 = "";
			for(; n < iteration && (n - cal) < 100; n++)
			{
				for(int m = 0; m < population_size; m++)
				{
					tmp1 = tmp1 + result1.get(n)[m] + "\t";
					tmp2 = tmp2 + result2.get(n)[m] + "\t";
					tmp3 = tmp3 + result3.get(n)[m] + "\t";
				}
				tmp1 = tmp1 + "\n";
				tmp2 = tmp2 + "\n";
				tmp3 = tmp3 + "\n";
			}
				byte[] bytes1 = tmp1.getBytes();
				byte[] bytes2 = tmp2.getBytes();
				byte[] bytes3 = tmp3.getBytes();
				try {
					population.write(bytes1);
					fitness.write(bytes2);
					originscore.write(bytes3);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cal = 100 + cal;
				try {
					population.close();
					fitness.close();
					originscore.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			Row row2 = sheet2.createRow(n);
//			Row row5 = sheet5.createRow(n);
//			for(int m = 0; m < population_size; m++)
//			{
//				Cell cell1 = row1.createCell(m);
//				cell1.setCellValue(result1.get(n)[m]);
//				Cell cell2 = row2.createCell(m);
//				cell2.setCellValue(result2.get(n)[m]);
//				Cell cell5 = row5.createCell(m);
//				cell5.setCellValue(result3.get(n)[m]);
//			}
//			
//			Cell cell3 = row3.createCell(n);
//			cell3.setCellValue(fitmean[n]);
//			Cell cell4 = row4.createCell(n);
//			cell4.setCellValue(fitbest[n]);
//			
			//}
//			try {
//				book.write(outStream);
//				outStream.flush();
//				outStream.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		
//		try {
//			book.write(new FileOutputStream("genetic_algorithm.xls"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}

