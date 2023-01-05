import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;
public class entry_close extends JFrame implements ActionListener,ComponentListener,MouseListener,ItemListener{
JLabel dmg,id,fn;
JComboBox eid;
JTextArea fin;
JButton sub,back,log;
JPanel p1;
BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\priya\\eclipse-workspace\\resources\\centry.jpg"));
Image image;
ImageIcon icon;
String ed[];
int el,i=0;
Color k=new Color(199,204,207),b=new Color(100,105,53),d=new Color(192,201,159);
entry_close()throws IOException
{
dmg=new JLabel();
dmg.setOpaque(false);
image = bufferedImage.getScaledInstance(5, 5, Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
try
{
Connection con;
Statement st;
ResultSet r;
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
r=st.executeQuery("select count(entry_id) from borrowed");
if(r.next())
el=Integer.parseInt(r.getString(1));
ed=new String[el];
r=st.executeQuery("select entry_id from borrowed");
while(r.next())
{
ed[i++]=r.getString(1);
}
}
catch(Exception e1) {e1.printStackTrace();}
id=new JLabel("Entry ID");
eid=new JComboBox(ed);
fn=new JLabel("Fine Amount");
fin=new JTextArea();
sub=new JButton("CLOSE");
back=new JButton("Back");
log=new JButton("Log Out");
eid.setSelectedItem(null);
fin.setEditable(false);
eid.addItemListener(this);
back.addActionListener(this);
back.addMouseListener(this);
log.addActionListener(this);
log.addMouseListener(this);
sub.addActionListener(this);
sub.addMouseListener(this);
p1=new JPanel();
p1.setLayout(new GridLayout(4,2,10,10));
p1.add(id);p1.add(eid);
p1.add(fn);p1.add(fin);
p1.add(new JLabel());p1.add(sub);
p1.add(back);p1.add(log);
p1.setBackground(k);
back.setBackground(b);
sub.setBackground(b);
log.setBackground(b);
this.add(p1);
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
p1.setBounds((int)(this.getWidth()*0.35), (int)(this.getHeight()*0.35), 400, 200);
dmg.setSize(this.getWidth(),this.getHeight());
image = bufferedImage.getScaledInstance(dmg.getWidth(), dmg.getHeight(), Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
dmg.setIcon(icon);
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
if(eid.getSelectedItem()==null)
{
JOptionPane.showMessageDialog(this, "Kindly select the entry ID.");
}
else
{
String ei=eid.getSelectedItem().toString(),bid="";
r=st.executeQuery("select book_id from borrowed where entry_id='"+ei+"'");
if(r.next())
bid=r.getString(1);
r=st.executeQuery("delete borrowed where entry_id='"+ei+"'");
r=st.executeQuery("update available set quantity=quantity+1 where book_id='"+bid+"'");
r=st.executeQuery("commit");
JOptionPane.showMessageDialog(this, "Entry closed successfully.");
eid.removeItemAt(eid.getSelectedIndex());
eid.setSelectedItem(null);
r.close();
}
st.close();
}
catch(Exception e1){ e1.printStackTrace();}
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
public void itemStateChanged(ItemEvent e) {
if(eid.getSelectedItem()!=null)
{
String s=eid.getSelectedItem().toString();
try
{
Connection con;
Statement st;
ResultSet r;
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
r=st.executeQuery("select fine_amnt('"+s+"') from dual");
if(r.next())
s=r.getString(1);
fin.setText(s);
}
catch(Exception e1){e1.printStackTrace();}
}
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