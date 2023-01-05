import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;
public class newbook extends JFrame implements ComponentListener,ActionListener,MouseListener{
JLabel dmg,bi,bt,ba,bq;
JTextArea btl,bar,bqt,bid;
JPanel p;
JButton sub,back,log;
BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\priya\\eclipse-workspace\\resources\\newbook.jpg"));
Image image;
ImageIcon icon;
int ec=0;
Color k=new Color(174,188,170),b=new Color(154,140,121),d=new Color(174,140,121);
newbook()throws IOException
{
dmg=new JLabel();
dmg.setSize(0,0);
dmg.setOpaque(false);
image = bufferedImage.getScaledInstance(5,5, Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
bi=new JLabel("Book ID");
bt=new JLabel("Title");
ba=new JLabel("Author");
bq=new JLabel("Quantity");
bid=new JTextArea("Just enter book id and uantity if it already exist");
btl=new JTextArea();
bar=new JTextArea();
bqt=new JTextArea();
sub=new JButton("ADD");
back=new JButton("Back");
log=new JButton("Log Out");
p=new JPanel();
p.setLayout(new GridLayout(6,2,10,10));
bid.addMouseListener(this);
sub.addMouseListener(this);
sub.addActionListener(this);
back.addActionListener(this);
back.addMouseListener(this);
log.addMouseListener(this);
log.addActionListener(this);
p.add(bi);p.add(bid);
p.add(bt);p.add(btl);
p.add(ba);p.add(bar);
p.add(bq);p.add(bqt);
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
public void mouseEntered(MouseEvent e) {
Object x=e.getSource();
if(x==bid&&ec==0)
{bid.setText("");++ec;}
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
public void actionPerformed(ActionEvent e) {
Object x=e.getSource();
if(x==back)
{
this.dispose();
try {
new optionpage();
} catch (IOException e1) {
e1.printStackTrace();
}
}
if(x==log)
{
this.dispose();
try {
new login();
} catch (IOException e1) {e1.printStackTrace();}
}
if(x==sub)
{
try
{
int ck=-1;
String i=bid.getText(),t=btl.getText(),a=bar.getText(),q=bqt.getText();
Connection con;
Statement st;
ResultSet r;
Class.forName("oracle.jdbc.driver.OracleDriver"); con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
if(i.isBlank()==false&&q.isBlank()==false)
{
r=st.executeQuery("select book_id from books");
while(r.next())
{
if(r.getString(1).equals(i))
ck=1;
}
if(ck==1)
{
int k=Integer.parseInt(q);
r=st.executeQuery("update books set quantity=quantity+"+k+" where book_id='"+i+"'");
r=st.executeQuery("update available set quantity=quantity+"+k+" where book_id='"+i+"'");
r=st.executeQuery("commit");
JOptionPane.showMessageDialog(this, "Books added successfully."); bid.setText("");btl.setText("");bar.setText("");bqt.setText("");
}
else if(t.isBlank()==false&&a.isBlank()==false)
{
r=st.executeQuery("insert into books values('"+i+"','"+t+"','"+a+"',"+q+")");
r=st.executeQuery("insert into available values('"+i+"',"+q+")");
r=st.executeQuery("commit");
JOptionPane.showMessageDialog(this, "Books added successfully.");
bid.setText("");btl.setText("");bar.setText("");bqt.setText("");
}
else
JOptionPane.showMessageDialog(this, "Kindly enter all details.");
}
else
JOptionPane.showMessageDialog(this, "Kindly enter all details.");
}
catch(Exception e1){e1.printStackTrace();}
}
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
public void mousePressed(MouseEvent e) { }
@Override
public void mouseReleased(MouseEvent e) {}
}
