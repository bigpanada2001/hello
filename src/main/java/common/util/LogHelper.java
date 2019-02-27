package common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogHelper {
	public static void log(String fileName, String content) {
		File file = null;
//		FileWriter fileWritter = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			file =new File(fileName);
			if(!file.exists()){
				file.createNewFile();
			}
//			true = append file
//			fileWritter = new FileWriter(file.getName(),true);
//			fileWritter.write(content);
		   fw = new FileWriter(file, true);
		   bw = new BufferedWriter(fw);
		   bw.append("\r\n");
		   bw.append(content);

		}catch(IOException e){
			e.printStackTrace();
		} finally {
			try {
//				if(fileWritter != null) {
//					fileWritter.flush();
//					fileWritter.close();
//				}
				if(bw != null) {
					bw.flush();
					bw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public static void main(String[] args) {
		LogHelper.log("c://20190122.txt", "111111111111111111");
		LogHelper.log("c://20190122.txt", "222222222222");
	}

}
