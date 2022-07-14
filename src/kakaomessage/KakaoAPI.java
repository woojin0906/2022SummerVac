package kakaomessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
// rest api, 
@Service
public class KakaoAPI {

	static String url;
	static RestTemplate restTemplate = new RestTemplate(); // Spring에서 지원하는 객체로 간편하게 Rest 방식 API를 호출할 수 있는 Spring 내장 클래스
	static HttpHeaders header = new HttpHeaders();

	// 토큰 발급
	public void getAccessToken(String authorize_code) throws JSONException {
		System.out.println("getAccessToken 메서드");
		String reqURL = "https://kauth.kakao.com/oauth/token";
		String access_Token = "";
		String refresh_Token = "";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // http 통신을 가능하게 해주는 클래스

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로 변경
			conn.setRequestMethod("POST"); // 폼에 입력된 내용을 서버로 전송
			conn.setDoOutput(true); // post 데이터를 넘겨주겠다는 옵션

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			// outputStreamWriter : 문자 스트림에서 바이트 스트림으로 연결되는 다리
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder(); // 문자열끼리 연결해주는 클래스
			sb.append("grant_type=authorization_code");  // append를 사용해 더해야하는 문자열 입력
			sb.append("&client_id=9fe52013af1c347f70a2ce56386eabbf"); // rest_api 앱 키 입력
			sb.append("&redirect_uri=https://localhost.com");
			sb.append("&code=" + authorize_code); // 발급받은 code 입력
			
			bw.write(sb.toString());  // StringBuilder는 출력 시 toString()으로 문자열로 변환해줘야함
			bw.flush();  // 현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비움

			// 결과 코드가 200이라면 성공(실제 서버로 request 요청하는 부분)
			int responseCode = conn.getResponseCode();  // 연결 성공 코드 200
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result); // 생성된 토큰 출력
			

			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			access_Token = element.getAsJsonObject().get("access_token").getAsString(); // getAsJsonObject을 이용해 원하는 타입의 값을 받아옴
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			// 토큰 저장
			save_tokens(result);
			// 토큰 읽기
			load_tokens("kakao_token");
			// 토큰 갱신
			update_tokens(refresh_Token);
			
			br.close();
			bw.close();

			// 나에게 카카오톡 메시지 보내는 메서드
			sendMessageToMe(access_Token);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 토큰 저장하는 메서드
	public void save_tokens(String tokens) throws IOException {
		System.out.println("save_tokens 메서드");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();    //gson을 사용하기 위해 이처럼 클래스 선언
		// Java 객체를 JSON 표현으로 변환하는 데 사용할 수 있는 Java 라이브러리
		// java 객체 -> json 변환 
		// 그리고 json -> java 객체로 변환해주는 라이브러리
		// 자동 개행 및 줄 바꿈
		// Json key, value 추가
		JsonObject kakao_token = new JsonObject();
		kakao_token.addProperty("filename", "kakao_token"); // 데이터를 넣어주는 것
		// filename이라는 이름(별명)으로 kakao_token의 값을 넣는 것
		kakao_token.addProperty("tokens", tokens);

		// JsonObject를 파일에 쓰기
		FileWriter fw = new FileWriter("c:\\temp\\kakaoAPI_Tokens.json");
		gson.toJson(kakao_token, fw); // JsonObject -> JSON 문자열로 변경( 객체를 json 형식의 string으로 변환)
		fw.flush();
		fw.close();

	}

	// 토큰 읽어오는 메서드
	public JsonObject load_tokens(String filename) throws IOException {
		System.out.println("load_tokens 메서드");
		// FileReader 생성
		Reader reader = new FileReader("c:\\temp\\kakaoAPI_Tokens.json");

		// Json 파일 읽어서, Lecture 객체로 변환
		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(reader, JsonObject.class);  // reader는 키로 키에 해당하는 것만 매핑되어 값이 삽입됨

		return obj;
	}

	// refresh토큰으로 access 토큰 갱신하는 메서드 (사용 X)
	public void update_tokens(String refresh_Token) throws IOException, JSONException {
		System.out.println("update_tokens 메서드");
		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로 변경
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=refresh_token");
			sb.append("&client_id=9fe52013af1c347f70a2ce56386eabbf"); // rest_api 앱 키 입력
			sb.append("&refresh_token=" + refresh_Token); // 발급받은 code 입력
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			System.out.println("결과 코드가 200이면 토큰 갱신 성공");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 나에게 카카오톡 메시지 보내는 메서드
	public static String sendMessageToMe(String access_Token) throws JSONException, IOException { 
		url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
		header.add("Authorization", "Bearer " + access_Token);

		System.out.println("----- Input Tokens Finish ----");
		// data 입력
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		// MultiValueMap : 키 값 중복 허용
		// LinkedMultiValueMap : 키 순서 그대로 출력
		requestBody.add("template_object", buildTemplateObject());

		System.out.println("---- Input Data Finish ----");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, header);

		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		System.out.println("finish");
		System.out.println(response);

		return response.getStatusCode().name();
	}

	// 메시지 내용 입력하는 메서드
	private static String buildTemplateObject() throws JSONException {
		System.out.println("buildTemplateObject 메시지 내용 보내는 메서드");
		JSONObject templateObject = new JSONObject();  
		JSONObject urlObject = new JSONObject();    // 주소
		JSONArray arrayObject = new JSONArray();    // 배열
		JSONObject firstObject = new JSONObject();  // 첫번째 리스트
		JSONObject twoObject = new JSONObject();    // 두번째 리스트
		
		templateObject.put("object_type", "list");
		templateObject.put("header_title", "초밥 사진");
		
		firstObject.put("title", "1. 광어초밥");
		firstObject.put("description", "광어는 맛있다");
		firstObject.put("image_url", "https://search1.kakaocdn.net/argon/0x200_85_hr/8x5qcdbcQwi");
		firstObject.put("image_width", 50);
		firstObject.put("image_height", 50);
		firstObject.put("link", urlObject);
		arrayObject.put(firstObject);
		
		twoObject.put("title", "2. 참치초밥");
		twoObject.put("description", "참치는 맛있다");
		twoObject.put("image_url", "https://search2.kakaocdn.net/argon/0x200_85_hr/IjIToH1S7J1");
		twoObject.put("image_width", 50);
		twoObject.put("image_height", 50);
		twoObject.put("link", urlObject);
		arrayObject.put(twoObject);
		
		templateObject.put("contents", arrayObject);
		urlObject.put("web_url", "www.naver.com");
		urlObject.put("mobile_web_url", "www.naver.com");
		
		templateObject.put("header_link", urlObject);
		templateObject.put("button_title", "웹으로 이동");
		System.out.println(templateObject.toString());
		
		return templateObject.toString();
	}

	public static void main(String[] args) throws JSONException {
		// 카카오톡 인증 코드 메서드 매개변수에 입력
		KakaoAPI kakaoAPI = new KakaoAPI();
		kakaoAPI.getAccessToken("MYicL6ZJ4rWhShwKWsLYnRlTe0bqxnP2RxppS52RvZKGosOgp5ruwDE1J0HeUe3DncwGswo9cxcAAAGB-28e6Q");
	}
}