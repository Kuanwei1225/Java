package test;

import java.io.*;

public class IntToExcel {
	public static void main(String[] args) throws Exception {
		//BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
		PrintStream pout = new PrintStream(new BufferedOutputStream(new
			        FileOutputStream("output.csv", false)));
		int[] a = new int[20];
		int[] b = new int[20];
		//int c = 10;
		
		for(int i=0;i<20;i++){
			a[i] = i;
			b[i] = 3*i;
		}
		PrintWriter out = new PrintWriter(pout, true);
		for(int i=0;i<20;i++){
			out.println(a[i] +", "+b[i]);
		}
		out.close();
	    //out.println(); // ��X�e�i output.txt ���
		//��X��Ƭy�J�w�ľ���A��X��test.txt�ɮפ�
		//String s="�r����X��ƬyBufferedWriter!!!!!";
		//bw.write(s);
		//��write��k�Ns��X
		//bw.close();
		//��close��k��X��������
	}
}