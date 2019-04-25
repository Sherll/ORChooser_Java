package StepByStep;

public class RecordResult {
	int[] config;
	int lenth;
	
	RecordResult(int length)
	{
		lenth = length;
		config = new int[length];
	}
	
	public void setConfig(int[] a)
	{
		for(int i = 0; i < lenth; i++)
		{
			config[i] = a[i];
		}
	}
	
	public void setSingleConfig(int index, int value)
	{
		config[index] = value;
	}
	
	public void print()
	{
		String tmp = "";
		for(int i = 0; i < lenth; i++)
		{
			tmp = tmp + config[i] + " ";
		}
		tmp = tmp + "\n";
		System.out.println(tmp);
	}
	
	public String getResult()
	{
		String tmp = "";
		for(int i = 0; i < lenth; i++)
		{
			tmp = tmp + config[i] + " ";
		}
		return tmp;
	}
}
