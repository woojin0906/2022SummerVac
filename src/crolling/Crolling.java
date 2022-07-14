package crolling;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crolling {

	public static void main(String[] args) {
		String url = "https://movie.naver.com/movie/bi/mi/review.nhn?code=191633";
		Document doc = null;
		
		try {
			doc = Jsoup.connect(url).get();
			
			Elements titles = doc.select("ul.rvw_list_area strong");
			
			int i = 0;
			while(i<10) {
				Element title = titles.get(i);
				i++;
				System.out.println("[" + i + "th]" + title.text());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
