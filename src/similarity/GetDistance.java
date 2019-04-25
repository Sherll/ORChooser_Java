package similarity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import similarity.MappingRecord.MappingMethods;
import similarity.ReadMapping.SingleMapping;

public class GetDistance {
	
	
	public double class_distance(ClassCount a, ClassCount b)
	{
		//dimension?
		double dimensions = 0.0;
		double[] distance = {0.0, 0.0, 0.0, 0.0,0.0};
		//normalized
		double[] point1 = new double[5];
		double[] point2 = new double[5];
		double donominator = Math.sqrt(Math.pow(a.getNumfunc(), 2) + Math.pow(a.getNuminnerclass(), 2)
										+ Math.pow(a.getNumvar(), 2) + Math.pow(a.getpubV(), 2) + Math.pow(a.getLOC(), 2)
										+ Math.pow(b.getNumfunc(), 2) + Math.pow(b.getNuminnerclass(), 2)+ Math.pow(b.getLOC(), 2)
										+ Math.pow(b.getNumvar(), 2) + Math.pow(b.getpubV(), 2));
		if(donominator != 0)
		{
			point1[0] = a.getNumfunc()/donominator;
			point1[1] = a.getNuminnerclass()/donominator;
			point1[2] = a.getNumvar()/donominator;
			point1[3] = a.getpubV()/donominator;
			point1[4] = a.getLOC()/donominator;
		}
		else 
		{
			point1[0] = a.getNumfunc();
			point1[1] = a.getNuminnerclass();
			point1[2] = a.getNumvar();
			point1[3] = a.getpubV();
			point1[4] = a.getLOC();
		}
		

		if(donominator != 0)
		{
			point2[0] = b.getNumfunc()/donominator;
			point2[1] = b.getNuminnerclass()/donominator;
			point2[2] = b.getNumvar()/donominator;
			point2[3] = b.getpubV()/donominator;
			point2[4] = b.getLOC()/donominator;
		}
		else 
		{
			point2[0] = b.getNumfunc();
			point2[1] = b.getNuminnerclass();
			point2[2] = b.getNumvar();
			point2[3] = b.getpubV();
			point2[4] = b.getLOC();
		}
		
		if(!(a.getNumfunc() == 0 && b.getNumfunc() == 0))
		{
			distance[0] = Math.abs(point1[0] - point2[0]);//(a.getNumfunc() + b.getNumfunc());
		}
		else
			distance[0] = 0.0;
		if(distance[0] != 0)
			dimensions++;
		
		if(!(a.getNuminnerclass() == 0 && b.getNuminnerclass() == 0))
		{
			distance[1] = Math.abs(point1[1] - point2[1]);//(a.getNuminnerclass() + b.getNuminnerclass());
		}
		else
			distance[1] = 0.0;
		if(distance[1] != 0)
			dimensions++;
		
		if(!(a.getNumvar() == 0 && b.getNumvar() == 0))
		{	
			distance[2] = Math.abs(point1[2] - point2[2]);//(a.getNumvar() + b.getNumvar());
		}
		else
			distance[2] = 0.0;
		if(distance[2] != 0)
			dimensions++;
		
		if(!(a.getpubV() == 0 && b.getpubV() == 0))
		{
			distance[3] = Math.abs(point1[3] - point2[3]);//(a.getpubV() + b.getpubV());
		}
		else
			distance[3] = 0.0;
		if(distance[3] != 0)
			dimensions++;
		
		if(!(a.getLOC() == 0 && b.getLOC() == 0))
		{
			distance[4] = Math.abs(point1[4] - point2[4]);//(a.getpubV() + b.getpubV());
		}
		else
			distance[4] = 0.0;
		if(distance[4] != 0)
			dimensions++;
		
		return dimensions * (distance[0] + distance[1] + distance[2] + distance[3] + distance[4]);
	}
	
	public void getMap(RecordClass a, RecordClass b, ReadMapping r)       //higher distance means more differences.
	{
		int common_len = a.getlength() < b.getlength() ? a.getlength() :  b.getlength();
		MappingRecord[] mapping = new MappingRecord[common_len];
		double[][] distance = new double[a.getlength()][b.getlength()];
		double min_distance =  16.0; //longest distance might be 4;
		int[][] min_index = new int[a.getlength()][2];
		for(int i = 0; i < a.getlength(); i++)
		{
			for(int j = 0; j < b.getlength(); j++)
			{
				distance[i][j] = class_distance(a.getclass(i), b.getclass(j));	
			//	System.out.println("i:" + i + " j:" + j + " -> " + distance[i][j]);
			}			
			
		}
		
		for(int m = 0; m < common_len; m++)
		{
			for(int i = 0; i < a.getlength(); i++)       //dijkstra distance?? Firstly we try common way -> choose the common_len
				//closest datas.
			{
				for(int j = 0; j < b.getlength(); j++)
				{
					if(distance[i][j] != -1 && min_distance > distance[i][j])
					{
						min_distance = distance[i][j];
						min_index[m][0] = i;
						min_index[m][1] = j;
					}
				}
			}
			//System.out.println(min_distance);
			for(int i = 0; i < a.getlength(); i++)
			{
				distance[i][min_index[m][1]] = -1;
			}
			for(int j = 0; j < b.getlength(); j++)
			{
				distance[min_index[m][0]][j] = -1;
			}
			min_distance = 16.0;
		
		}
		for(int i = 0; i < common_len; i++)
		{
			int common_num = Math.min(a.getclass(min_index[i][0]).getNumfunc(), b.getclass(min_index[i][1]).getNumfunc());
			//System.out.println(a.getclass(min_index[i][0]).getClassname() + " -> "  + b.getclass(min_index[i][1]).getClassname());
			mapping[i] = new MappingRecord(common_num);
			mapping[i].setmappingClass(a.getclass(min_index[i][0]).getClassname(), b.getclass(min_index[i][1]).getClassname());
		}
		getMethodsMap(mapping, a, b, min_index, r);
		
		//estimate(a, b, r, min_index, common_len);
	}
	
	public void getMethodsMap(MappingRecord[] mapping, RecordClass a, RecordClass b, int[][] min_index, ReadMapping r)
	{
		int common_len = Math.min(a.getlength(), b.getlength());
		for(int m = 0; m < common_len; m++)
		{
			ClassCount class1 = a.getclass(min_index[m][0]);
			ClassCount class2 = b.getclass(min_index[m][1]);
//			System.out.println(mapping[m].returnClassOrigin() + " -> " + mapping[m].returnClassTarget() + ":");
//			System.out.println(class1.getNumfunc() + " " + class2.getNumfunc());
			int common_num = Math.min(class1.getNumfunc(), class2.getNumfunc());
			double[][] distance_instruction = new double[class1.getNumfunc()][class2.getNumfunc()];
			double[][] distance_function = new double[class1.getNumfunc()][class2.getNumfunc()];
			double min_distance = 5.0;
			int[][] mixmin_index = new int[common_num][2];
			
			for(int i = 0; i < class1.getNumfunc(); i++)
			{
				for(int j = 0; j < class2.getNumfunc(); j++)
				{
					distance_instruction[i][j] = instruction_distance(class1.getMethod(i).getInstruction(), 
																	  class2.getMethod(j).getInstruction());
					distance_function[i][j] = function_distance(class1.getMethod(i), class2.getMethod(j));
				}
			}
			
			double max_distance = 0.0;
			for(int i = 0; i < class1.getNumfunc(); i++)
			{
				for(int j = 0; j < class2.getNumfunc(); j++)
				{
					if(max_distance < distance_instruction[i][j] + distance_function[i][j]) // this equation can be changed.
					{
						max_distance = distance_instruction[i][j] + distance_function[i][j];
					}
				}
			}
			
			min_distance = max_distance + 1.0;
			for(int n = 0; n < common_num; n++)
			{
				for(int i = 0; i < class1.getNumfunc(); i++)
				{
					for(int j = 0; j < class2.getNumfunc(); j++)
					{
						if(distance_instruction[i][j] != -1 && distance_function[i][j] != -1 && 
								min_distance > distance_instruction[i][j] + distance_function[i][j]) // this equation can be changed.
						{
							min_distance = distance_instruction[i][j] + distance_function[i][j];
							mixmin_index[n][0] = i;
							mixmin_index[n][1] = j;
						}
					}
				}
				for(int i = 0; i < class1.getNumfunc(); i++)
				{
					distance_instruction[i][mixmin_index[n][1]] = -1;
				}
				for(int j = 0; j < class2.getNumfunc(); j++)
				{
					distance_function[mixmin_index[n][0]][j] = -1;
				}
				min_distance = max_distance + 1.0;
			}
			
			for(int i = 0; i < common_num; i++)
			{
				//manage the Strings. class 2 is ok.
				//Firstly manage the initial method.
				String tmp1 = "";
				String tmp2 = "";
				if(class1.getMethod(mixmin_index[i][0]).getFunctionname().equals(class1.getClassname()))
				{
					tmp1 = "<init>";
					tmp2 = "<init>";
				}
				else
				{
					tmp1 = class1.getMethod(mixmin_index[i][0]).getFunctionname();
					tmp2 = class2.getMethod(mixmin_index[i][1]).getFunctionname();
				}
				//Secondly add parameters for class1.
				tmp1 = tmp1 + "(";
				for(int l = 0; l < class1.getMethod(mixmin_index[i][0]).getTotalparameter(); l++)
				{
					tmp1 = tmp1 + class1.getMethod(mixmin_index[i][0]).getParatype()[l];
					if(l != class1.getMethod(mixmin_index[i][0]).getTotalparameter() - 1)
					{
						tmp1 = tmp1 + ",";
					}
				}
				tmp1 = tmp1 + ")";
				mapping[m].getmappingMethods().setmethod(i, tmp1, tmp2);
				//When write it to a file, 4 blanks should be added. But in a data structure, there is no blank space.
			}
			
//			for(int i = 0; i < r.returnClasslen(); i++)
//			{
//				if(r.return_array()[i].returnclassname_origin().equals(class1.getClassname()))
//				{	
//				//	System.out.println(r.return_array()[i].returnclassname_origin()+ " " + class1.getClassname());
//					estimate_method(class1, class2, mixmin_index, common_num, r.return_array()[i]);
//			
//				}
//			}
		}
		try {
			write_map(mapping);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mappingVSTetris0(mapping,r);
	}
	
	public void mappingVSTetris0(MappingRecord[] mapping, ReadMapping r)
	{
		int count = 0;
		int sum = 0;
		int count1 = 0;
		for(int i = 0; i < mapping.length; i++)
		{
			sum = sum + mapping[i].getmappingMethods().getlength();
			
			for(int m = 0; m < r.returnClasslen(); m++)
			{
				if(mapping[i].returnClassOrigin().equals(r.return_array()[m].returnclassname_origin()) &&
						mapping[i].returnClassTarget().equals(r.return_array()[m].returnclassname_target()))
				{
					count1 ++;
					MappingMethods compare1 = mapping[i].getmappingMethods();
					SingleMapping compare2 = r.return_array()[m];
					for(int j = 0; j < compare1.getlength(); j++)
					{
						for(int n = 0; n < compare2.getnummethod(); n++)
						{
							if(compare1.getmethod(j).getorigin().equals(compare2.getmethodname(n).return_origin()) &&
									compare1.getmethod(j).gettarget().equals(compare2.getmethodname(n).return_target()))
							{
//								System.out.println(compare1.getmethod(j).getorigin() + " -> " + compare1.getmethod(j).gettarget());
//								System.out.println(compare2.getmethodname(n).return_origin() + " -> " + compare2.getmethodname(n).return_target());
								count++;
							}
						}
					}
				}
			}
		}
		System.out.println((double)count/sum + " " + (double)count1/Math.min(mapping.length, r.returnClasslen()));
		
	}
	
	public void estimate_method(ClassCount a, ClassCount b, int[][] distance, int num, SingleMapping s)
	{
		double count = 0.0;
		for(int i = 0; i < num; i++)
		{
			for(int j = 0; j < s.getnummethod(); j++)
			{
				
				String compare1 = s.getmethodname(j).return_origin();
				compare1 = compare1.substring(0,compare1.indexOf("("));
				if(a.getMethod(distance[i][0]).getFunctionname().equals(compare1) &&
						b.getMethod(distance[i][1]).getFunctionname().equals(s.getmethodname(j).return_target()))
				{
					count++;
				}
			}
		}
		System.out.println(count/num);
	}
	
	public double instruction_distance(int[] a, int[] b)
	{
		double sum = 0.0;
		for(int i = 0; i < a.length; i++)
		{
			sum = sum + Math.pow(a[i], 2) + Math.pow(b[i], 2);
		}
		sum = Math.sqrt(sum);
		double result = 0.0;
		for(int i = 0; i < a.length; i++)
		{
			result = result + (Math.abs(a[i] - b[i])/sum);
		}
		return result;
	}
	
	public double function_distance(MethodCount a, MethodCount b)
	{
		double donominator = 0.0;
		donominator = Math.pow(a.getLOC(), 2) + 2 * Math.pow(a.getTotalparameter(), 2)
						+ Math.pow(b.getLOC(), 2) + 2 * Math.pow(b.getTotalparameter(), 2) + 1; // try to avoid 0 for the dominator.
		donominator = Math.sqrt(donominator);
		double[] distance = {0.0, 0.0, 0.0, 0.0};
		int dimensions = 0;
		distance[0] = Math.abs(a.getLOC() - b.getLOC())/donominator;
		if(distance[0] != 0)
		{
			dimensions++;
		}
//		System.out.println("Infunction:" + b.getReturntype() + "hehe");
		if(a.getReturntype().equals(b.getReturntype()))
		{
			distance[1] = 0.0/donominator;
		}
		else
		{
			dimensions++;
			distance[1] = 1.0/donominator;
		}
		distance[2] = Math.abs(a.getTotalparameter() - b.getTotalparameter())/donominator;
		if(distance[2] != 0)
			dimensions++;
		int count = 0;
		boolean[] mark = new boolean[b.getTotalparameter()];//true for has been visited; false for not.
		for(int j = 0; j < b.getTotalparameter(); j++)
		{
			mark[j] = false;
		}
		//System.out.println("function:");
		for(int i = 0; i < a.getTotalparameter(); i++)
		{
			for(int j = 0; j < b.getTotalparameter(); j++)
			{
				if(mark[j] != true && a.getParatype()[i].equals(b.getParatype()[j]))
				{
					count++;
					mark[j] = true;
				}
			}
		}
		if(a.getTotalparameter() != b.getTotalparameter() || count != (a.getTotalparameter() + b.getTotalparameter())/2)
		{
			dimensions++;
		}
		distance[3] = (a.getTotalparameter() + b.getTotalparameter() - 2 * count)/donominator;//as a dimension.
		return dimensions * (distance[0] + distance[1] + distance[2] + distance[3]);
	}
			
	public void estimate(RecordClass a, RecordClass b, ReadMapping r, int[][] min_index, int common_len)
	{
		int count = 0;
		for(int m = 0; m < common_len; m++)
		{
			for(int i = 0; i < common_len; i++)
			{
				if(r.return_array()[m].returnclassname_origin().equals(a.getclass(min_index[i][0]).getClassname()) && 
					r.return_array()[m].returnclassname_target().equals(b.getclass(min_index[i][1]).getClassname()))
					{
						count++;
						System.out.println(r.return_array()[m].returnclassname_origin() + " -> " + r.return_array()[m].returnclassname_target());
						System.out.println(a.getclass(min_index[i][0]).getClassname() + " -> " + b.getclass(min_index[i][1]).getClassname());
						System.out.println("");
					}
			}
		}
		System.out.println(count/30.0);
	}

	public void write_map(MappingRecord[] map) throws IOException
	{
		String cout = "";
		for(int i = 0; i < map.length; i++)
		{
			cout = cout + map[i].getprint();
		}
		File filestream = new File("mapping.Map");
		OutputStream Out = new FileOutputStream(filestream);
		byte[] bytes = cout.getBytes();
		Out.write(bytes);
		Out.close();
	}
	
//	public static void main(String[] arg)
//	{
//		String _origin = "_origin.txt";
//		String target = "_0.txt";
//		String mapfile = "Tetris0.map";
//		RecordClass before = new RecordClass();
//		RecordClass after = new RecordClass();
//		ReadMapping mapping = new ReadMapping();
//		mapping.read_Mapping(mapfile);
//		after.record_class(target);
//		before.record_class(_origin);
//		//mapping.print();
//		GetDistance g = new GetDistance();
//		g.getMap(before, after, mapping);
//	}
}
