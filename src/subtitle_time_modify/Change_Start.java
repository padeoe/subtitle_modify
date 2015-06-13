package subtitle_time_modify;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
/**
 * 未解决问题：
 * 如何在已知File同目录下创建文件
 * @author Kangkang
 *
 */
public class Change_Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClockTime ClockTime1=new ClockTime(00,47,22.839 );
		ClockTime ClockTime2=new ClockTime(0,0,38.50);
		ClockTime1.delayTime(ClockTime2);
		ClockTime1.printTime();
		
		ClockTime ClockTime3=new ClockTime(00,47,20.437 );
		ClockTime ClockTime4=new ClockTime(0,0,38.50);
		ClockTime3.delayTime(ClockTime4);
		ClockTime3.printTime();
		
		//开始修改文件
		File subtitle=new File("C:/Users/Kangkang/Desktop/字幕 - 副本.srt");
		modifySubtitleFile(subtitle);
		
	}

	public static void modifySubtitleFile(File file){
		File newSubtitle=new File("C:/Users/Kangkang/Desktop/新字幕.srt");
		try {
			newSubtitle.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			newSubtitle.delete();
			e1.printStackTrace();
		}
/*		if(!newSubtitle.exists()){
			try {
				newSubtitle.createNewFile();
				newSubtitle.delete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		StringBuilder stringBuilder = new StringBuilder();
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String line=scanner.nextLine();
				stringBuilder.append(processLine(line)+'\n');
			}
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(newSubtitle);
				fileWriter.write(stringBuilder.toString());
		//		System.out.println(stringBuilder.toString());
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 在行里找到时间进行修改
	 * @param line
	 * @return
	 */
	public static String processLine(String line){
		String ss[]=line.split("[^0-9\\.]");
		String data[] = new String[6];
/*		if(ss.length!=7){
			
		}*/
	//	System.out.println("长度"+ss.length);
		int t=0;
/*		for(int i=0;i<ss.length&&t<6;i++){
			if(ss[i].length()>=2){
				data[t]=ss[i];
				t++;
			}
		}*/
		boolean firstSkip=true;
		for(int i=0;i<ss.length&&t<6;i++){
			if(ss[i].length()>=2){
			//	System.out.println("数字是"+ss[i]);
				if(t==3){
					if(firstSkip){
						firstSkip=false;
					}
					else{
						data[t]=ss[i];
						t++;
					}

				}
				else{
					data[t]=ss[i];
					t++;
				}
			}
		}
		if(t<6){
			System.out.println("没有找到数字");
			return line;
		}
		else{
			System.out.println("找到数字了");
		
			ClockTime clockTimeA = new ClockTime(Integer.valueOf(data[0]),
					Integer.valueOf(data[1]),Double.valueOf(data[2]));
			ClockTime clockTimeB = new ClockTime(Integer.valueOf(data[3]),
					Integer.valueOf(data[4]),Double.valueOf(data[5]));
			
			
			//在这里修改时间!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
			//数字越小，字幕出现的越早
			ClockTime intervalTime=new ClockTime(0,0,5);
			
			clockTimeA.printTime();
			clockTimeB.printTime();
			
			boolean a=false;
			if(clockTimeA.hour==00&&clockTimeA.minute==47&&clockTimeA.second==20.437){
				a=true;
			}
			clockTimeA.delayTime(intervalTime);
			clockTimeB.delayTime(intervalTime);
/*			clockTimeA.delayTime(intervalTime);
			clockTimeB.delayTime(intervalTime);*/
/*			String result2=line.replaceFirst(data[0],String.valueOf(clockTimeA.hour))
			.replaceFirst(data[1],String.valueOf(clockTimeA.minute));
			String result=result2.replaceFirst(data[2],String.valueOf(clockTimeA.second)).
			replaceFirst(data[3],String.valueOf(clockTimeB.hour)).
			replaceFirst(data[4],String.valueOf(clockTimeB.minute)).
			replaceFirst(data[5],String.valueOf(clockTimeB.second));*/
			String result2=line.replaceFirst(data[0]+":"+data[1]+":"+data[2],
					clockTimeA.hour+":"+clockTimeA.minute+":"+(int)clockTimeA.second)
					.replaceFirst(data[3]+":"+data[4]+":"+data[5],
					clockTimeB.hour+":"+clockTimeB.minute+":"+(int)clockTimeB.second);
			
			if(a){
				
		//		System.out.println(line);
				for(int i=0;i<6;i++)
					System.out.println(data[i]);
		//		System.out.println(result2);
			}
			return result2;
		}
/*		ClockTime clockTimeA = new ClockTime();
		ClockTime clockTimeB = new ClockTime();*/

		
	}
}
class ClockTime{
	int hour;
	int minute;
	double second;
	ClockTime(int hour,int minute,double second){
		this.hour=hour;
		this.minute=minute;
		this.second=second;
	}
	public ClockTime delayTime(ClockTime intervalTime){
		/**
		 * 输入检验
		 * 为了考虑效率，暂时省略
		 */
/*		if(intervalTime.second>=60||intervalTime.minute>=60){
			System.out.println("输入的时间表示不正确："+intervalTime.hour+":"+intervalTime.minute+":"+intervalTime.second);
		}*/
		
		
		boolean c_second=false,c_minute=false;
		//计算秒
		double sum_second=this.second+intervalTime.second-60;
		if(sum_second>0){
			this.second=sum_second;
			c_second=true;
		}
		else{
			this.second=this.second+intervalTime.second;
		}
		//计算分
		if(c_second){
			int sum_minute=this.minute+intervalTime.minute+1-60;
			if(sum_minute>0){
				this.minute=sum_minute;
				c_minute=true;
			}
			else{
				this.minute=this.minute+intervalTime.minute+1;
			}
		}
		else{
			int sum_minute=this.minute+intervalTime.minute-60;
			if(sum_minute>0){
				this.minute=sum_minute;
				c_minute=true;
			}
			else{
				this.minute=this.minute+intervalTime.minute;
			}
		}
		//计算小时
		if(c_minute){
			this.hour=this.hour+intervalTime.hour+1;
		}
		else{
			this.hour=this.hour+intervalTime.hour;
		}
		DecimalFormat df= new DecimalFormat("#0.000");
		this.second=Double.valueOf(df.format(this.second));
//		this.second=Double.valueOf(this.second);
		return this;
		
	}
	public ClockTime advanceTime(ClockTime intervalTime){
		/**
		 * 输入检验
		 * 为了考虑效率，暂时省略
		 */
/*		if(intervalTime.second>=60||intervalTime.minute>=60){
			System.out.println("输入的时间表示不正确："+intervalTime.hour+":"+intervalTime.minute+":"+intervalTime.second);
		}*/
		
		
		boolean c_second=false,c_minute=false;
		//计算秒
		double sum_second=this.second-intervalTime.second+60;
		if(sum_second<60){
			this.second=sum_second;
			c_second=true;
		}
		else{
			this.second=this.second-intervalTime.second;
		}
		//计算分
		if(c_second){
			int sum_minute=this.minute-intervalTime.minute-1+60;
			if(sum_minute<60){
				this.minute=sum_minute;
				c_minute=true;
			}
			else{
				this.minute=this.minute-intervalTime.minute-1;
			}
		}
		else{
			int sum_minute=this.minute-intervalTime.minute+60;
			if(sum_minute<60){
				this.minute=sum_minute;
				c_minute=true;
			}
			else{
				this.minute=this.minute-intervalTime.minute;
			}
		}
		//计算小时
		if(c_minute){
			this.hour=this.hour-intervalTime.hour-1;
		}
		else{
			this.hour=this.hour-intervalTime.hour;
		}
//		DecimalFormat df= new DecimalFormat("#0.000");
//		this.second=Double.valueOf(df.format(this.second));
		return this;
		
	}
	public void printTime(){
		System.out.println(this.hour+":"+this.minute+":"+(int)this.second);
	}
	
}

