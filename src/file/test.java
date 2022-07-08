package file;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.lang.reflect.InvocationTargetException;
	import java.net.URL;
	import java.net.URLConnection;
	import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
	import org.json.JSONObject;

	public class test {

	 public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, JSONException {
	   String query = "각시탈";

	        URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+URLEncoder.encode(query, "UTF-8") + "&userip=127.0.0.1&rsz=8");

	        URLConnection connection = url.openConnection();
	        connection.addRequestProperty("Referer", "http://google.com");

	        String line;

	        StringBuilder builder = new StringBuilder();

	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

	        while((line = reader.readLine()) != null) {

	         builder.append(line);

	        }

	        System.out.println(builder);
	        JSONObject json = new JSONObject(builder.toString());

		}

}
