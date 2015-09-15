package subtitle_time_modify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class SubtitleEncoding {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fileInputStream = new FileInputStream(new File("/media/padeoe/学习/OneDrive/Java2015/subtitle_time_modify/src/here.srt"));
		byte[] buffer = new byte[fileInputStream.available()];
		fileInputStream.read(buffer);
		String str = new String(buffer);
		FileWriter fileWriter = new FileWriter(new File("/media/padeoe/学习/OneDrive/Java2015/subtitle_time_modify/src/here.srt"));
		System.out.println(str);
		fileWriter.write(str);
		fileWriter.close();
	//	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader();
		
	}
	

}
