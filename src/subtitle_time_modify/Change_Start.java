package subtitle_time_modify;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * δ������⣺
 * �������֪FileͬĿ¼�´����ļ�
 * @author Kangkang
 *
 */
public class Change_Start {
	
	private static Pattern pattern = Pattern.compile("^(\\d\\d):(\\d\\d):(\\d\\d\\.\\d\\d\\d) --> (\\d\\d):(\\d\\d):(\\d\\d.\\d\\d\\d)");

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
		
		ClockTime ClockTime5=new ClockTime(00,47,20.437 );
		ClockTime ClockTime6=new ClockTime(0,0,-2.1);
		ClockTime5.delayTime(ClockTime6);
		ClockTime5.printTime();
		
		File subtitle=new File("C:\\Users\\padeoe\\Desktop\\subtitle.srt");
		modifySubtitleFile(subtitle);
		
	}

	public static void modifySubtitleFile(File file){
		File newSubtitle=new File("C:\\Users\\padeoe\\Desktop\\NewSubtitle.srt");
		try {
			newSubtitle.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			newSubtitle.delete();
			e1.printStackTrace();
		}
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
				System.out.println(stringBuilder.toString());
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

	
	public static String processLine(String line){
/*		Matcher matcher = pattern.matcher(line);
		if (!matcher.matches()) {
			return line;
		}*/
		
		String ss[]=line.split("[^0-9\\.]");
		String data[] = new String[6];

/*		if(ss.length!=7){
			
		}*/
	//	System.out.println("����"+ss.length);
		int n=0;
		for(int i=0;i<ss.length&&n<6;i++){
			if(ss[i].length()>=2){
				data[n]=ss[i];
				n++;
			}
		}
/*		boolean firstSkip=true;
		for(int i=0;i<ss.length&&t<6;i++){
			if(ss[i].length()>=2){
			//	System.out.println("������"+ss[i]);
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
		}*/
		if(n<6){
			return line;
		}
		else{
			ClockTime clockTimeA = new ClockTime(Integer.valueOf(data[0]),
					Integer.valueOf(data[1]),Double.valueOf(data[2]));
			ClockTime clockTimeB = new ClockTime(Integer.valueOf(data[3]),
					Integer.valueOf(data[4]),Double.valueOf(data[5]));
			
			
			ClockTime intervalTime=new ClockTime(0,0,-2.5);
			
			clockTimeA.printTime();
			clockTimeB.printTime();
			
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
					clockTimeA.hour+":"+clockTimeA.minute+":"+clockTimeA.second)
					.replaceFirst(data[3]+":"+data[4]+":"+data[5],
					clockTimeB.hour+":"+clockTimeB.minute+":"+clockTimeB.second);
				for(int i=0;i<6;i++)
					System.out.println(data[i]);
			return result2;
		}
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
		boolean c_second=false,c_minute=false;
		double sum_second=this.second+intervalTime.second-60;
		if(sum_second>0){
			this.second=sum_second;
			c_second=true;
		}
		else{
			this.second=this.second+intervalTime.second;
		}
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
		
		
		boolean c_second=false,c_minute=false;
	
		double sum_second=this.second-intervalTime.second+60;
		if(sum_second<60){
			this.second=sum_second;
			c_second=true;
		}
		else{
			this.second=this.second-intervalTime.second;
		}
		
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
		
		if(c_minute){
			this.hour=this.hour-intervalTime.hour-1;
		}
		else{
			this.hour=this.hour-intervalTime.hour;
		}
		DecimalFormat df= new DecimalFormat("#0.000");
		this.second=Double.valueOf(df.format(this.second));
		return this;
		
	}
	public void printTime(){
		System.out.println(this.hour+":"+this.minute+":"+this.second);
	}
	
}

