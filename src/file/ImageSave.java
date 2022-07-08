package file;
// 이미지 저장
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageSave {
	public static void main(String[] args) {
		String imageUrl = "https://search1.kakaocdn.net/argon/600x0_65_wr/ImZk3b2X1w8";
		String savePath = "C:\\JAVA_JAP\\";
		String saveFileName = "test.jpg";
		String fileFormat = "jpg";
		
		System.out.println(" IMAGE URL ::: " + imageUrl);
		System.out.println(" SAVE PATH ::: " + savePath);
		System.out.println(" SAVE FILE NAME ::: " + saveFileName);
		System.out.println(" FILE FORMAT ::: " + fileFormat);
		
		File saveFile = new File(savePath + saveFileName);

		saveImage(imageUrl, saveFile, fileFormat);		
	}
	
	public static void saveImage(String imageUrl, File saveFile, String fileFormat) {
		    
		URL url = null;
		BufferedImage bi = null;
		
		try {
			url = new URL(imageUrl); // 다운로드 할 이미지 URL
			bi = ImageIO.read(url);
			ImageIO.write(bi, fileFormat, saveFile); // 저장할 파일 형식, 저장할 파일명
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
