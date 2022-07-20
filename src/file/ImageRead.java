package file;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageRead extends JFrame{

	private JPanel panelCenter;


	public ImageRead(int i, String imgURL, int w, int h) {
		setTitle("이미지" + i);

		setLocation(w, h);
		setSize(400, 400);
		setResizable(false);
		setLayout(new BorderLayout());
		
		panelCenter = new JPanel();
		
		ImageIcon icon = new ImageIcon("C:/dream_coding/img/" + imgURL);
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
		ImageIcon changeImgIcon = new ImageIcon(changeImg);
		
		JLabel lblImg = new JLabel(changeImgIcon);
		panelCenter.add(lblImg);
		
		add(panelCenter);
		
		setVisible(true);
		
	}
	
}
