import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;
public class entry extends JFrame implements ComponentListener,MouseListener,ActionListener{
JLabel dmg,id,bi,si;
JTextArea eid,sid;
JComboBox bid;
JButton sub,back,log;
JPanel p;
BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\priya\\eclipse-workspace\\resources\\entry.jpg"));
Image image;
ImageIcon icon;
String bkd[];
int bl=0,i=0;
Color k=new Color(230,210,239),b=new Color(199,152,192),d=new Color(230,152,192);
entry()throws IOException
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
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
st.executeQuery("select count(book_id) from available where quantity>0");
if(r.next())
bl=Integer.parseInt(r.getString(1));
bkd=new String[bl];
r=st.executeQuery("select book_id from available where quantity>0");
while(r.next())
{
bkd[i++]=r.getString(1);
}
}
catch(Exception e1) {e1.printStackTrace();}
id=new JLabel("Entry ID");
bi=new JLabel("Book ID");
si=new JLabel("Student ID");
eid=new JTextArea();
bid=new JComboBox(bkd);
sid=new JTextArea();
sub=new JButton("ENTER");
back=new JButton("Back");
log=new JButton("Log Out");
bid.setSelectedItem(null);
back.addActionListener(this);
back.addMouseListener(this);
log.addActionListener(this);
log.addMouseListener(this);
sub.addActionListener(this);
sub.addMouseListener(this);
p=new JPanel();
p.setLayout(new GridLayout(5,2,20,20));
p.add(id);p.add(eid);
p.add(si);p.add(sid);
p.add(bi);p.add(bid);
p.add(new JLabel());p.add(sub);
p.add(back);p.add(log);
p.setBackground(k);
sub.setBackground(b);
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
public void actionPerformed(ActionEvent e) {
Object src=e.getSource();
if(src==sub)
{
try
{
Connection con;
Statement st;
ResultSet r;
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
String en,s,b="";
en=eid.getText();
if(bid.getSelectedItem()!=null)
b=bid.getSelectedItem().toString();
s=sid.getText();
if(en.isBlank()==false&&b.isBlank()==false&&s.isBlank()==false)
{
r=st.executeQuery("update available set quantity=quantity-1 where book_id='"+b+"'");
r=st.executeQuery("insert into borrowed values('"+en+"','"+s+"','"+b+"',sysdate,0)");
r=st.executeQuery("commit");
r=st.executeQuery("select quantity from available where book_id='"+b+"'");
s="";
if(r.next())
s=r.getString(1);
if(s.equals("0"))
bid.removeItemAt(bid.getSelectedIndex());
JOptionPane.showMessageDialog(this,"Entry made successfully.");
eid.setText("");
bid.setSelectedItem(null);
sid.setText("");
}
else
JOptionPane.showMessageDialog(this, "Kindly fill all details to make entry.");
}
catch (Exception e1){e1.printStackTrace();}
}
if(src==back)
{
this.dispose();
try {
new optionpage();
} catch (IOException e1) {e1.printStackTrace();}
}
if(src==log)
{
this.dispose();
try {
new login();
} catch (IOException e1) {e1.printStackTrace();}
}
}
@Override
public void mouseEntered(MouseEvent e) {
Object x=e.getSource();
if(x==sub)
sub.setBackground(d);
if(x==back)
back.setBackground(d);
if(x==log)
log.setBackground(d);
}
@Override
public void mouseExited(MouseEvent e) {
Object x=e.getSource();
if(x==sub)
sub.setBackground(b);
if(x==back)
back.setBackground(b);
if(x==log)
log.setBackground(b);
}
@Override
public void componentResized(ComponentEvent e) {
p.setBounds((int)(this.getWidth()*0.3), (int)(this.getHeight()*0.3), 500, 300);
dmg.setSize(this.getWidth(),this.getHeight());
image = bufferedImage.getScaledInstance(dmg.getWidth(), dmg.getHeight(), Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
dmg.setIcon(icon);
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