package file;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeTest extends JFrame {

	private DefaultMutableTreeNode dog;
	private DefaultMutableTreeNode cat;
	private JTree jt;

	public JTreeTest() {
		
		setLocation(250, 300);
		setSize(200, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");
		dog = new DefaultMutableTreeNode("C:");
		cat = new DefaultMutableTreeNode("D:");
		root.add(dog);
		root.add(cat);
		
		jt = new JTree(root);
		add(jt);
		
		setVisible(true);
		
	}

	public DefaultMutableTreeNode getDog() {
		return dog;
	}


}


