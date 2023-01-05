import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
public class display extends JFrame implements ActionListener,ComponentListener,MouseListener{
JTable op;
JLabel dmg;
JScrollPane sp;
JPanel dis,d1,d2;
BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\priya\\eclipse-workspace\\resources\\table.jpg"));
Image image;
ImageIcon icon;
JButton logout,back;
int i,j;
Color k=new Color(228,219,216),d=new Color(90,189,173),b=new Color(114,220,216);
display(String rows[][],String col[])throws IOException
{
//table display
i=rows[0].length;
j=col.length;
dmg=new JLabel();
dmg.setOpaque(false);
image = bufferedImage.getScaledInstance(5,5, Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
back=new JButton("Back");
logout=new JButton("Log Out");
back.setSize(100,30);
logout.setSize(100,30);
back.addActionListener(this);
back.addMouseListener(this);
logout.addMouseListener(this);
logout.addActionListener(this);
op=new JTable(rows,col);
d1=new JPanel();
d2=new JPanel();
sp=new JScrollPane(op);
d1.add(sp);
d2.setLayout(new FlowLayout(FlowLayout.CENTER,70,10));
d2.add(back);
d2.add(logout);
dis=new JPanel();
dis.setLayout(new GridLayout(2,1,10,10));
dis.setBounds((int)(this.getWidth()*0.3), (int)(this.getHeight()*0.3),sp.getWidth(),sp.getHeight());
dis.add(d1);
dis.add(d2);
back.setBackground(d);
setBackground(d);
dis.setBackground(k);
d1.setBackground(k);
d2.setBackground(k);
sp.setBackground(k);
//table display end
this.add(dis);
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
if(e.getSource()==back)
back.setBackground(b);
if(e.getSource()==logout)
logout.setBackground(b);
}
@Override
public void mouseExited(MouseEvent e) {
if(e.getSource()==back)
back.setBackground(d);
if(e.getSource()==logout)
logout.setBackground(d);
}
@Override
public void componentResized(ComponentEvent e) {
back.setSize(100,30);
logout.setSize(100,30);
dis.setBounds((int)(this.getWidth()*0.3), (int)(this.getHeight()*0.3), sp.getWidth(),sp.getHeight());
dmg.setSize(this.getWidth(),this.getHeight());
image = bufferedImage.getScaledInstance(dmg.getWidth(), dmg.getHeight(), Image.SCALE_DEFAULT);
icon = new ImageIcon(image);
dmg.setIcon(icon);
}
@Override
public void actionPerformed(ActionEvent e) {
Object x=e.getSource();
this.dispose();
if(x==back)
{
try {
new optionpage();
} catch (IOException e1) {
e1.printStackTrace();
}
}
if(x==logout)
{
try {
new login();
} catch (IOException e1) {e1.printStackTrace();}
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