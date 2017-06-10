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
	    //out.println(); // 輸出送進 output.txt 文件
		//輸出資料流入緩衝器後再輸出到test.txt檔案中
		//String s="字元輸出資料流BufferedWriter!!!!!";
		//bw.write(s);
		//用write方法將s輸出
		//bw.close();
		//用close方法輸出完後關閉
	}
}