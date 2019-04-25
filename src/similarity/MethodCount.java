package similarity;

public class MethodCount {

	int LOC;
	String Functionname;
	String Returntype;
	int Totalparameter;
	String[] Paratype;
	int[] Instruction;
	
	MethodCount()
	{
		LOC = 0;
		Functionname = null;
		Returntype = null;
		Totalparameter = 0;
		Paratype = null;
		Instruction = new int[202];
		for(int i = 0; i < 202; i++)
		{
			Instruction[i] = 0;
		}
	}
	
	public void setLOC(int a)
	{
		LOC = a;
	}
	
	public void setFunctionname(String a)
	{
		Functionname = a;
	}
	
	public void setReturntype(String a)
	{
		Returntype = a;
	}
	
	public void setTotalparameter(int a)
	{
		Totalparameter = a;
	}
	
	public void setParatype(String[] a)
	{
		Paratype = new String[Totalparameter];
		for(int i = 0; i < Totalparameter; i++)
		{			
			Paratype[i] = new String(a[i]);  //need or not?
		}
	}
	
	public void setInstruction(String a)
	{
		if(getIndex(a) == -1) return;
		else 
			Instruction[getIndex(a)]++;
	}
	
	public int getIndex(String a) // a with no blanks
	{
		switch(a)
		{
			case"nop": return 0;
			case"aconst_null": return 1;
			case"iconst_m1": return 2;
			case"iconst_0": return 3;
			case"iconst_1": return 4;
			case"iconst_2": return 5;
			case"iconst_3": return 6;
			case"iconst_4": return 7;
			case"iconst_5": return 8;
			case"lconst_0": return 9;
			case"lconst_1": return 10;
			case"fconst_0": return 11;
			case"fconst_1": return 12;
			case"fconst_2": return 13;
			case"dconst_0": return 14;
			case"dconst_1": return 15;
			case"bipush": return 16;
			case"sipush": return 17;
			case"ldc": return 18;
			case"ldc_w": return 19;
			case"ldc2_w": return 20;
			case"iload": return 21;
			case"lload": return 22;
			case"fload": return 23;
			case"dload": return 24;
			case"aload": return 25;
			case"iload_0": return 26;
			case"iload_1": return 27;
			case"iload_2": return 28;
			case"iload_3": return 29;
			case"lload_0": return 30;
			case"lload_1": return 31;
			case"lload_2": return 32;
			case"lload_3": return 33;
			case"fload_0": return 34;
			case"fload_1": return 35;
			case"fload_2": return 36;
			case"fload_3": return 37;
			case"dload_0": return 38;
			case"dload_1": return 39;
			case"dload_2": return 40;
			case"dload_3": return 41;
			case"aload_0": return 42;
			case"aload_1": return 43;
			case"aload_2": return 44;
			case"aload_3": return 45;
			case"iaload": return 46;
			case"laload": return 47;
			case"faload": return 48;
			case"daload": return 49;
			case"aaload": return 50;
			case"baload": return 51;
			case"caload": return 52;
			case"saload": return 53;
			case"istore": return 54;
			case"lstore": return 55;
			case"fstore": return 56;
			case"dstore": return 57;
			case"astore": return 58;
			case"istore_0": return 59;
			case"istore_1": return 60;
			case"istore_2": return 61;
			case"istore_3": return 62;
			case"lstore_0": return 63;
			case"lstore_1": return 64;
			case"lstore_2": return 65;
			case"lstore_3": return 66;
			case"fstore_0": return 67;
			case"fstore_1": return 68;
			case"fstore_2": return 69;
			case"fstore_3": return 70;
			case"dstore_0": return 71;
			case"dstore_1": return 72;
			case"dstore_2": return 73;
			case"dstore_3": return 74;
			case"astore_0": return 75;
			case"astore_1": return 76;
			case"astore_2": return 77;
			case"astore_3": return 78;
			case"iastore": return 79;
			case"lastore": return 80;
			case"fastore": return 81;
			case"dastore": return 82;
			case"aastore": return 83;
			case"bastore": return 84;
			case"castore": return 85;
			case"sastore": return 86;
			case"pop": return 87;
			case"pop2": return 88;
			case"dup": return 89;
			case"dup_x1": return 90;
			case"dup_x2": return 91;
			case"dup2": return 92;
			case"dup2_x1": return 93;
			case"dup2_x2": return 94;
			case"iadd": return 96;
			case"ladd": return 97;
			case"fadd": return 98;
			case"dadd": return 99;
			case"isub": return 100;
			case"lsub": return 101;
			case"fsub": return 102;
			case"dsub": return 103;
			case"imul": return 104;
			case"lmul": return 105;
			case"fmul": return 106;
			case"dmul": return 107;
			case"idiv": return 108;
			case"ldiv": return 109;
			case"fdiv": return 110;
			case"ddiv": return 111;
			case"irem": return 112;
			case"lrem": return 113;
			case"frem": return 114;
			case"drem": return 115;
			case"ineg": return 116;
			case"lneg": return 117;
			case"fneg": return 118;
			case"dneg": return 119;
			case"ishl": return 120;
			case"lshl": return 121;
			case"ishr": return 122;
			case"lshr": return 123;
			case"iushr": return 124;
			case"lushr": return 125;
			case"iand": return 126;
			case"land": return 127;
			case"ior": return 128;
			case"lor": return 129;
			case"ixor": return 130;
			case"lxor": return 131;
			case"iinc": return 132;
			case"i2l": return 133;
			case"i2f": return 134;
			case"i2d": return 135;
			case"l2i": return 136;
			case"l2f": return 137;
			case"l2d": return 138;
			case"f2i": return 139;
			case"f2l": return 140;
			case"f2d": return 141;
			case"d2i": return 142;
			case"d2l": return 143;
			case"d2f": return 144;
			case"i2b": return 145;
			case"i2c": return 146;
			case"i2s": return 147;
			case"lcmp": return 148;
			case"fcmpl": return 149;
			case"fcmpg": return 150;
			case"dcmpl": return 151;
			case"dcmpg": return 152;
			case"ifeq": return 153;
			case"ifne": return 154;
			case"iflt": return 155;
			case"ifge": return 156;
			case"ifgt": return 157;
			case"ifle": return 158;
			case"if_icmpeq": return 159;
			case"if_icmpne": return 160;
			case"if_icmplt": return 161;
			case"if_icmpge": return 162;
			case"if_icmpgt": return 163;
			case"if_icmple": return 164;
			case"if_acmpeq": return 165;
			case"if_acmpne": return 166;
			case"goto": return 167;
			case"jsr": return 168;
			case"ret": return 169;
			case"tableswitch": return 170;
			case"lookupswitch": return 171;
			case"ireturn": return 172;
			case"lreturn": return 173;
			case"freturn": return 174;
			case"dreturn": return 175;
			case"areturn": return 176;
			case"return": return 177;
			case"getstatic": return 178;
			case"putstatic": return 179;
			case"getfield": return 180;
			case"putfield": return 181;
			case"invokevirtual": return 182;
			case"invokespecial": return 183;
			case"invokestatic": return 184;
			case"invokeinterface": return 185;
			case"--": return 186;
			case"new": return 187;
			case"newarray": return 188;
			case"anewarray": return 189;
			case"arraylength": return 190;
			case"athrow": return 191;
			case"checkcast": return 192;
			case"instanceof": return 193;
			case"monitorenter": return 194;
			case"monitorexit": return 195;
			case"wide": return 196;
			case"multianewarray": return 197;
			case"ifnull": return 198;
			case"ifnonnull": return 199;
			case"goto_w": return 200;
			case"jsr_w": return 201;
			default: return -1;
		}		
	}
	
	public void increaseLOC()
	{
		LOC++;
	}
	
	public String getFunctionname()
	{
		return Functionname;
	}
	
	public String getReturntype()
	{
		return Returntype;
	}
	
	public int getTotalparameter()
	{
		return Totalparameter;
	}
	
	public String[] getParatype()
	{
		return Paratype;
	}
	
	public int[] getInstruction()
	{
		return Instruction;
	}
	
	public int getLOC()
	{
		return LOC;
	}
	
	public void print()
	{
		String a = "";
		for(int i = 0; i < Totalparameter; i++)
		{
			a = a + Paratype[i] + " ";
		}
		System.out.println(Functionname + ": " + Returntype + " " + Totalparameter + "->" + a);
//		a = "";
//		for(int i = 0; i < 202; i++)
//		{
//			a = a + i + ":" + Instruction[i] + " ";
//		}
//		System.out.println(a);
	}
}
