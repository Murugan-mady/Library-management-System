import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;
public class delbook extends JFrame implements ComponentListener,ActionListener,MouseListener{
BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\priya\\eclipse-workspace\\resources\\dbook.jpg"));
Image image;
ImageIcon icon;
JLabel dmg,bi,qn;
JComboBox bid;
JTextArea qnty;
JButton del,back,log;
JPanel p;
int i=0,l=0;
String s[];
Color k=new Color(239,243,145),b=new Color(95, 163, 39),d=new Color(173, 250, 110);
delbook()throws IOException
{
dmg=new JLabel();
dmg.setSize(0,0);
dmg.setOpaque(false);
image = bufferedImage.getScaledInstance(5,5, Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
try
{
Connection con;
Statement st;
ResultSet r;
Class.forName("oracle.jdbc.driver.OracleDriver"); con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
r=st.executeQuery("select count(book_id) from books");
if(r.next())
l=Integer.parseInt(r.getString(1));
s=new String[l];
r=st.executeQuery("select book_id from books");
while(r.next())
s[i++]=r.getString(1);
}
catch(Exception e1){e1.printStackTrace();}
bi=new JLabel("Book ID");
qn=new JLabel("Quantity");
bid=new JComboBox(s);bid.setSelectedItem(null);
qnty=new JTextArea();
del=new JButton("DELETE");
back=new JButton("Back");
log=new JButton("Log Out");
del.addActionListener(this);
del.addMouseListener(this);
back.addActionListener(this);
back.addMouseListener(this);
log.addActionListener(this);
log.addMouseListener(this);
p=new JPanel();
p.setLayout(new GridLayout(4,2,10,10));
p.add(bi);p.add(bid);
p.add(qn);p.add(qnty);
p.add(new JLabel());p.add(del);
p.add(back);p.add(log);
p.setBackground(k);
del.setBackground(b);
back.setBackground(b);
log.setBackground(b);
this.add(p);
this.add(dmg);
this.addComponentListener(this);
this.setVisible(true);
this.setLayout(null);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
this.setExtendedState(JFrame.MAXIMIZED_BOTH);
this.setResizable(false);
}
@Override
public void mouseEntered(MouseEvent e) {
Object x=e.getSource();
if(x==del)
del.setBackground(d);
if(x==back)
back.setBackground(d);
if(x==log)
log.setBackground(d);
}
@Override
public void mouseExited(MouseEvent e) {
Object x=e.getSource();
if(x==del)
del.setBackground(b);
if(x==back)
back.setBackground(b);
if(x==log)
log.setBackground(b);
}
@Override
public void actionPerformed(ActionEvent e) {
Object x=e.getSource();
if(x==back)
{
this.dispose();
try {
new optionpage();
} catch (IOException e1) {e1.printStackTrace();}
}
if(x==log)
{
this.dispose();
try {
new login();
} catch (IOException e1) {
e1.printStackTrace();
}
}
if(x==del)
{
String q=qnty.getText(),b,aq="";
try
{
Connection con;
Statement st;
ResultSet r;
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
if(q.isBlank()||bid.getSelectedItem()==null)
JOptionPane.showMessageDialog(this, "Kindly give rquired details.");
else
{
b=bid.getSelectedItem().toString();
r=st.executeQuery("select quantity from available where book_id='"+b+"'");
if(r.next())
aq=r.getString(1);
if(Integer.parseInt(aq)<Integer.parseInt(q))
{
JOptionPane.showMessageDialog(this, "Quantity is beyond limit.Currently only "+aq+" books are available.");
bid.setSelectedItem(null);
qnty.setText("");
}
else
	{
r=st.executeQuery("select quantity from books where book_id='"+b+"'");
if(r.next()&&r.getString(1).equals(aq)&&aq.equals(q))
{
r=st.executeQuery("delete available where book_id='"+b+"'");
r=st.executeQuery("delete books where book_id='"+b+"'");
bid.removeItem(b);
}
else
{
r=st.executeQuery("update books set quantity=quantity-"+q+" where book_id='"+b+"'");
r=st.executeQuery("update available set quantity=quantity-"+q+" where book_id='"+b+"'");
}
r=st.executeQuery("commit");
JOptionPane.showMessageDialog(this,"Book deleted succuessfully.");
bid.setSelectedItem(null);
qnty.setText("");
}
}
}
catch (Exception e1)
{
e1.printStackTrace();
}
}
}
@Override
public void componentResized(ComponentEvent e) {
	}
@Override
public void componentMoved(ComponentEvent e) {}
@Override
public void componentShown(ComponentEvent e) {}
@Override
public void componentHidden(ComponentEvent e) {}
@Override
public void mouseClicked(MouseEvent e) {}
@Override
public void mousePressed(MouseEvent e) {}
@Override
public void mouseReleased(MouseEvent e) {}
}