package file;
// 메모장
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class OutputStreamEx {
	public static void main(String[] args) {
		try {
			OutputStream output = new FileOutputStream("C:\\JAVA_JAP\\Output.txt");
			String str = "hello";
			byte[] by = str.getBytes();
			output.write(by);
		System.out.println("성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
