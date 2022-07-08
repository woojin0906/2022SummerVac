package file;

	import java.io.InputStreamReader;
	import java.net.HttpURLConnection;
	import java.net.URL;
	import java.net.URLEncoder;

import javax.swing.DefaultListModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
	import java.io.IOException;
public class test2 {
//	private DefaultListModel model;
	    public static void main(String[] args) throws IOException {
	    	// 1. URL을 만들기 위한 StringBuilder.
	        StringBuilder urlBuilder = new StringBuilder("https://dapi.kakao.com/v2/search/image"); /*URL*/
	        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
	        urlBuilder.append("client_id=" + "9fe52013af1c347f70a2ce56386eabbf");
	        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/

	        // 3. URL 객체 생성.
	        URL url = new URL(urlBuilder.toString());
	        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        // 5. 통신을 위한 메소드 SET.
	        conn.setRequestMethod("GET");
	        // 6. 통신을 위한 Content-type SET. 
	        conn.setRequestProperty("Content-type", "application/json");
	        // 7. 통신 응답 코드 확인.
	        System.out.println("Response code: " + conn.getResponseCode());
	        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        // 10. 객체 해제.
	        rd.close();
	        conn.disconnect();
	        // 11. 전달받은 데이터 확인.
	        System.out.println(sb.toString());
	        
//	        // 1. 문자열 형태의 JSON을 파싱하기 위한 JSONParser 객체 생성.
//	        JSONParser parser = new JSONParser();
//	        // 2. 문자열을 JSON 형태로 JSONObject 객체에 저장. 	
//	        JSONObject obj = (JSONObject)parser.parse(sb.toString());
//	        // 3. 필요한 리스트 데이터 부분만 가져와 JSONArray로 저장.
//	        JSONArray dataArr = (JSONArray) obj.get("data");
//	        // 4. model에 담아준다.
//	        
//	        model.addAttribute("data",dataArr);

	    }
}
