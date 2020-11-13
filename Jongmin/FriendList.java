import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

public class FriendList extends JFrame{
	JTree tree;
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("친구목록");
	public FriendList() {
		DefaultMutableTreeNode online = new DefaultMutableTreeNode("접속중인 친구");
		DefaultMutableTreeNode offline = new DefaultMutableTreeNode("비접속중인 친구");
		
		DefaultMutableTreeNode f1 = new DefaultMutableTreeNode("A");
		DefaultMutableTreeNode f2 = new DefaultMutableTreeNode("B");
		DefaultMutableTreeNode f3 = new DefaultMutableTreeNode("C");
		DefaultMutableTreeNode f4 = new DefaultMutableTreeNode("D");
		DefaultMutableTreeNode f5 = new DefaultMutableTreeNode("E");
		
		root.add(online);
		root.add(offline);
		
		online.add(f1); //이 코드를 이용해 친구의 온,오프라인 확인
		online.add(f2);
		online.add(f3);
		
		offline.add(f4);
		offline.add(f5);
		
		tree = new JTree(root);
		
		tree.expandRow(1);
		tree.expandRow(5);
		tree.setRowHeight(50);
		
		DefaultTreeCellRenderer dt = new DefaultTreeCellRenderer();
		dt.setOpenIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Open.gif"));
		dt.setClosedIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Close.gif"));
		dt.setLeafIcon(new ImageIcon("C:\\Users\\jeonj\\Desktop\\TermProject\\Leaf.gif"));
		
		tree.setCellRenderer(dt);
		
		JScrollPane js = new JScrollPane(tree);
		add("Center",js);
		setSize(350,500);
		setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception ex) {}
		new FriendList();
	}

}
