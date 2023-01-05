import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
public class login extends JFrame implements ComponentListener,ActionListener,MouseListener{
JPanel login,l1,l2;
JLabel limg,lid,lpd;
JPasswordField pswd;
JButton log;
JTextArea id;
BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\priya\\eclipse-workspace\\resources\\login.jpg"));;
Image image;
ImageIcon icon;
login() throws IOException
{
login=new JPanel();
limg=new JLabel("");
limg.setSize(this.getSize());
limg.setOpaque(false);
image = bufferedImage.getScaledInstance(100,100, Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
limg.setIcon(icon);
JLabel lid=new JLabel("LIBRARIAN ID");
JLabel lpd=new JLabel("PASSWORD");
pswd=new JPasswordField();
id=new JTextArea();
log=new JButton("LOGIN");
log.addActionListener(this);
log.addMouseListener(this);
log.setBackground(new Color(222, 141, 11));
l1=new JPanel();
l1.setLayout(new GridLayout(2,2,50,50));
l1.add(lid);l1.add(id);l1.add(lpd);l1.add(pswd);
l2=new JPanel();
l2.add(log,BorderLayout.CENTER);
login.setLayout(new GridLayout(2,1,50,50));
l1.setBackground(new Color(170,185,192));
l2.setBackground(new Color(170,185,192));
login.add(l1);
login.add(l2);
login.setBackground(new Color(170,185,192));
//login page
this.setExtendedState(JFrame.MAXIMIZED_BOTH);
this.setResizable(false);
this.add(login);
this.add(limg);
this.addComponentListener(this);
this.setVisible(true);
this.setLayout(null);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String[]a) throws IOException
{
new login();
}
@Override
public void componentResized(ComponentEvent e) {
login.setBounds((int)(this.getWidth()*0.3), (int)(this.getHeight()*0.3), 500, 300);
limg.setSize(this.getSize());
image = bufferedImage.getScaledInstance(limg.getWidth(), limg.getHeight(), Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
limg.setIcon(icon);
}
@Override
public void actionPerformed(ActionEvent e) {
String s=String.valueOf(pswd.getPassword());
try {
Class.forName("oracle.jdbc.driver.OracleDriver");
Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
Statement st=con.createStatement();
String q="select password from librarian where id='"+id.getText()+"'";
ResultSet r=st.executeQuery(q);
if(r.next()==false)
{
JOptionPane.showMessageDialog(this, "Incorrect ID and password");
id.setText("");
pswd.setText("");
}
else if(r.getString(1).equals(s)==false)
{
JOptionPane.showMessageDialog(this, "Incorrect Password");
pswd.setText("");
}
else
{
this.dispose();
try {
new optionpage();
} catch (IOException e1) {e1.printStackTrace();}
}
con.close();st.close();r.close();
} catch (ClassNotFoundException e2) {e2.printStackTrace();
} catch (SQLException e1) {e1.printStackTrace();}
}
@Override
public void mouseEntered(MouseEvent e) {
log.setBackground(new Color(250,168,37));
}
@Override
public void mouseExited(MouseEvent e) {
log.setBackground(new Color(222, 141, 11));
}
@Override
public void mouseClicked(MouseEvent e) {}
@Override
public void mousePressed(MouseEvent e) {}
@Override
public void mouseReleased(MouseEvent e) {}
@Override
public void componentMoved(ComponentEvent e) {}
@Override
public void componentShown(ComponentEvent e) {}
@Override
public void componentHidden(ComponentEvent e) {}
}
