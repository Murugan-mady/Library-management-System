
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.awt.image.*;
public class optionpage extends JFrame implements ActionListener,MouseListener,ComponentListener{
JPanel options;
JButton btn[]=new JButton[8];
BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\priya\\eclipse-workspace\\resources\\options.jpg"));
Image image;
ImageIcon icon;
int i;
JLabel oimg;
optionpage() throws IOException
{
//options
oimg=new JLabel("");
oimg.setSize(this.getSize());
oimg.setOpaque(false);
image = bufferedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
oimg.setIcon(icon);
btn[0]=new JButton("Available BOOKS");
btn[1]=new JButton("Borrowed BOOKS");
btn[2]=new JButton("Library BOOKS");
btn[3]=new JButton("New Entry");
btn[4]=new JButton("Close Entry");
btn[5]=new JButton("Add book");
btn[6]=new JButton("Delete Book");
btn[7]=new JButton("Log Out");
options=new JPanel();
options.setBackground(new Color(202, 222, 202));
options.setLayout(new GridLayout(8,1,20,20));
options.setBounds((int)(this.getWidth()*0.3), (int)(this.getHeight()*0.3), 250, 400);
for(i=0;i<8;i++)
{
btn[i].addActionListener(this);
btn[i].addMouseListener(this);
btn[i].setBackground(Color.gray);
options.add(btn[i]);
}
//options end
this.setExtendedState(JFrame.MAXIMIZED_BOTH);
this.setResizable(false);
this.add(options);
this.add(oimg);
this.addComponentListener(this);
this.setVisible(true);
this.setLayout(null);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
this.setExtendedState(JFrame.MAXIMIZED_BOTH);
setResizable(false);
}
@Override
public void componentResized(ComponentEvent e) {
options.setBounds((int)(this.getWidth()*0.4), (int)(this.getHeight()*0.3), 250, 400);
oimg.setSize(this.getSize());
image = bufferedImage.getScaledInstance(oimg.getWidth(), oimg.getHeight(), Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
oimg.setIcon(icon);
}
@Override
public void mouseEntered(MouseEvent e) {
for(i=0;i<8;i++)
if(e.getSource()==btn[i])
btn[i].setBackground(new Color(194,197,208));
}
@Override
public void mouseExited(MouseEvent e) {
for(i=0;i<8;i++)
if(e.getSource()==btn[i])
btn[i].setBackground(Color.gray);
}
@Override
public void actionPerformed(ActionEvent e) {
Object x=e.getSource();
if(x==btn[7])
{
	this.dispose();
try {
new login();
} catch (IOException e1) {e1.printStackTrace();}
}
Connection con;
Statement st;
ResultSet r;
if(x==btn[0])
{
String rows[][],col[]= {"BOOK ID","TITLE","AUTHOR","QUANTITY"},s="";
try
{
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
r=st.executeQuery("select count(book_id) from available");
int l=0,i=0;
if(r.next())
l=Integer.valueOf(r.getString(1));
r.close();
if(l==0)
JOptionPane.showMessageDialog(this,"No books are currently available.");
else
{
r=st.executeQuery("select available.book_id,title,author,available.quantity from books,available where available.book_id=books.book_id");
rows= new String[l][4];
while(r.next())
{
rows[i][0]=r.getString(1);
rows[i][1]=r.getString(2);
rows[i][2]=r.getString(3);
rows[i][3]=r.getString(4);
++i;
}
try
{
this.dispose();
new display(rows,col);
}
catch(Exception e1) {e1.printStackTrace();}
}
}
catch(Exception e1) {e1.printStackTrace();}
}
if(x==btn[1])
{
String rows[][],col[]= {"ENTRY ID","BORROWED BY","BOOK ID","BORROWED ON","FINE"};
try
{
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
r=st.executeQuery("select count(entry_id) from borrowed");
int l=0,i=0;
if(r.next())
l=Integer.valueOf(r.getString(1));
r.close();
if(l==0)
JOptionPane.showMessageDialog(this,"No books are borrowed yet.");
else
{
r=st.executeQuery("select *from borrowed");
rows= new String[l][5];
Statement stm=con.createStatement();
while(r.next())
{
rows[i][0]=r.getString(1);
rows[i][1]=r.getString(2);
rows[i][2]=r.getString(3);
rows[i][3]=r.getString(4);
ResultSet k=stm.executeQuery("select fine_amnt('"+rows[i][0]+"') from dual");
if(k.next())
rows[i][4]=k.getString(1);
++i;
}
try
{
this.dispose();
new display(rows,col);
}
catch(Exception e1)
{
e1.printStackTrace();
}
}
}
catch(Exception e1) {e1.printStackTrace();}
}
if(x==btn[2])
{
String rows[][],col[]= {"BOOK ID","TITLE","AUTHOR","QUANTITY"},s="";
try {
Class.forName("oracle.jdbc.driver.OracleDriver"); con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Murugan@18");
st=con.createStatement();
r=st.executeQuery("select count(book_id) from books");
int l=0,i=0;
if(r.next())
l=Integer.valueOf(r.getString(1));
r.close();
r=st.executeQuery("select * from books");
rows= new String[l][4];
while(r.next())
{
rows[i][0]=r.getString(1);
rows[i][1]=r.getString(2);
rows[i][2]=r.getString(3);
rows[i][3]=r.getString(4);
++i;
}
try
{
this.dispose();
new display(rows,col);
}
catch(Exception e1)
{e1.printStackTrace();}
}
catch(Exception e1) {System.out.println(e1); }
}
if(x==btn[3])
{
try
{
this.dispose();
new entry();
}
catch (Exception e1) {e1.printStackTrace();}
}
if(x==btn[4])
{
try
{
this.dispose();
new entry_close();
}
catch (Exception e1) {e1.printStackTrace();}
}
if(x==btn[5])
{
this.dispose();
try {
new newbook();
} catch (IOException e1) {e1.printStackTrace();}
}
if(x==btn[6])
{
this.dispose();
try {
new delbook();
} catch (IOException e1) {e1.printStackTrace();}
}
}
@Override
public void mousePressed(MouseEvent e) { }
@Override
public void componentMoved(ComponentEvent e) {}
@Override
public void componentShown(ComponentEvent e) {}
@Override
public void componentHidden(ComponentEvent e) {}
@Override
public void mouseClicked(MouseEvent e) {}
@Override
public void mouseReleased(MouseEvent e) {}
}