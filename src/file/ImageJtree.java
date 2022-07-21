package file;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class ImageJtree extends JFrame implements ActionListener{

	private JPanel panelCenter;
	private JPanel panelNorth;
	private JLabel lblQues;
	private JTextField tfWord;
	private JButton btnOpen;
	private JPanel panelSearch;
	private JPanel panelSave;
	private JPanel panelBtn;
	private JButton btnSave;
	private JLabel lblSearch;
	private JLabel lblSave;
	private JTextField tfSave;
	private String wordSearch;

	public ImageJtree(String title) {
		setTitle(title);
		setLocation(250, 100);
		setSize(350, 220);
		setResizable(false);
		setLayout(new BorderLayout());
		
		setNorth();
		setCenter();
		
		setVisible(true);
	}
	
	
	private void setNorth() {
		panelNorth = new JPanel();
		panelNorth.setLayout(null);
		panelNorth.setPreferredSize(new Dimension(300, 50));
		
		lblQues = new JLabel("저장할 키워드 및 위치");
		lblQues.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 17));
		lblQues.setBounds(15, 14, 250, 30);
		panelNorth.add(lblQues);
		
		add(panelNorth, BorderLayout.NORTH);
	}


	private void setCenter() {
		panelCenter = new JPanel(new GridLayout(3, 1));

		panelSearch = new JPanel();
		panelSearch.setLayout(null);
		
		lblSearch = new JLabel("키워드");
		lblSearch.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 15));
		lblSearch.setBounds(15, 5, 100, 30);
		panelSearch.add(lblSearch);
		
		tfWord = new JTextField(10);
		tfWord.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 15));
		tfWord.setBounds(80, 7, 230, 25);
		tfWord.addActionListener(this);
		panelSearch.add(tfWord);

		panelSave = new JPanel();
		panelSave.setLayout(null);
		
		lblSave = new JLabel("저장 위치");
		lblSave.setBounds(15, 5, 100, 30);
		lblSave.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 15));
		panelSave.add(lblSave);
		
		tfSave = new JTextField(10);
		tfSave.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 15));
		tfSave.setBounds(80, 7, 160, 25);
		tfSave.setEnabled(false);
		tfSave.addActionListener(this);
		panelSave.add(tfSave);
		
		btnOpen = new JButton("열기");
		btnOpen.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		btnOpen.setBounds(250, 7, 60, 25);
		btnOpen.addActionListener(this);
		panelSave.add(btnOpen);
		
		panelBtn = new JPanel(new FlowLayout());
		
		btnSave = new JButton("저장");
		btnSave.setPreferredSize(new Dimension(270, 25));
		btnSave.addActionListener(this);
		panelBtn.add(btnSave);
		
		panelCenter.add(panelSearch);
		panelCenter.add(panelSave);
		panelCenter.add(panelBtn);
		
		add(panelCenter, BorderLayout.CENTER);
	}


	public static void main(String[] args) {
		ImageJtree imgJ = new ImageJtree("테스트");
		
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


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnSave) {
			if (tfWord.getText().equals(""))
				JOptionPane.showMessageDialog(this, "키워드를 입력하셔야 합니다");
			else if (tfSave.getText().equals(""))
				JOptionPane.showMessageDialog(this, "저장위치를 입력하셔야 합니다");
			else {
				wordSearch = tfWord.getText();
				
				this.dispose();
				
				
				String restApiKey = "9fe52013af1c347f70a2ce56386eabbf";  // 개인 rest-api 키 입력
				
				try {
					String text = URLEncoder.encode(wordSearch, "UTF-8"); // 검색어
					String postParams = "src_lang=kr&target_lang=en&query=" + text;  // 파라미터
					String apiURL = "https://dapi.kakao.com/v2/search/image?" + postParams;
					URL url = new URL(apiURL);
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					String basicAuth = "KakaoAK " + restApiKey;
					con.setRequestProperty("Authorization", basicAuth);
					
					// 이건 필요 유무 몰라서 빼놈
					con.setRequestMethod("GET");  // 웹 서버로부터 리소스를 가져옴
					con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					con.setRequestProperty("charset", "utf-8");
					con.setUseCaches(false);
					con.setDoInput(true); // 서버로부터 응답을 받겠다는 옵션
					con.setDoOutput(true);
					
					// 실제 서버로 request 요청하는 부분
					int responseCode = con.getResponseCode();
					System.out.println("responseCode >> " + responseCode);
					
					BufferedReader br;
					if(responseCode == 200) {
						br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					} else {
						br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
					}
					
					String inputLine;
					StringBuffer res = new StringBuffer();
					while ((inputLine = br.readLine()) != null) {
						res.append(inputLine);
						
					}
					br.close();

					// 가장 큰 JSONObject를 가져옵니다.
				    JSONObject jObject = new JSONObject(res.toString());
				    // 배열을 가져옵니다.
				    JSONArray jArray = jObject.getJSONArray("documents");

				    String savePath = tfSave.getText() + "\\"; // 이미지 저장 파일
				    String fileFormat = "jpg";
			
				    JTreeTest jt = new JTreeTest();

				    // 배열의 모든 아이템을 출력합니다.
				    for (int i = 1; i <= 5; i++) {
				        JSONObject Jobj = jArray.getJSONObject(i);
				        String imgURL = Jobj.getString("image_url");
				        String saveFileName = "test" + i + ".jpg";
				       
				        File saveFile = new File(savePath + saveFileName);
				        
				        saveImage(imgURL, saveFile, fileFormat);

				        jt.getDog().add(new DefaultMutableTreeNode(saveFileName));

				        System.out.println("image_url(" + i + "): " + imgURL);
				        
				        System.out.println();
				    }
				    
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("--확인용-- T_Test.java에서 오류 발생");
					System.out.println(e1);
				}
				
			}
			
		} else if(obj == btnOpen) {
//			JFileChooser fc = new JFileChooser();
//			fc.showOpenDialog(this);
//			File selfile = fc.getSelectedFile(); 
//			
//			tfSave.setText(fc.getSelectedFile().getAbsolutePath());
//			
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc.showDialog(this, null);
			File dir = jfc.getSelectedFile();

			tfSave.setText(dir == null ? "" : dir.getPath());
		}
	}

}
