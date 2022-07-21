package file;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import imgjtree.Util;

public class JTreeTest extends JFrame {

	private DefaultMutableTreeNode dog;
	private DefaultMutableTreeNode cat;
	private JScrollPane sp;
	private JSplitPane jsp;
	private JScrollPane scp;
	private Container con = this.getContentPane();
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("내 컴퓨터");
	private JTree jt = new JTree(root);
	
	
	public JTreeTest() {
		
		setLocation(250, 300);
		setSize(500, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		con.setLayout(new BorderLayout());
		
		dog = new DefaultMutableTreeNode("C:");
		cat = new DefaultMutableTreeNode("D:");
		root.add(dog);
		root.add(cat);
		
		ImageIcon imageicon = new ImageIcon("C:/images/gosling.jpg");
		JLabel img = new JLabel(imageicon);
		
		sp = new JScrollPane(jt);
		jsp = new JSplitPane();
		jsp.setLeftComponent(sp);
		con.add("Center", jsp);
		
		scp = new JScrollPane(img);
		jsp.setRightComponent(scp);
		
		setVisible(true);
		
	}

	public DefaultMutableTreeNode getDog() {
		return dog;
	}
}


