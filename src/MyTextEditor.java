
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Notepad extends JFrame implements ActionListener
	{		
	File fnameContainer;
	JTextArea jta  =new JTextArea();
	public Notepad()
		{

		Font fnt=new Font("Arial",Font.PLAIN,15);
		Container con=getContentPane();		
		JMenuBar jmb=new JMenuBar();
		JMenu jmfile = new JMenu("File");
		JMenu jmedit = new JMenu("Edit");
		JMenu jmhelp = new JMenu("Help");
		
		con.setLayout(new BorderLayout());
		JScrollPane sbrText = new JScrollPane(jta);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sbrText.setVisible(true);				
		jta.setFont(fnt);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);		
		con.add(sbrText);
		createMenuItem(jmfile,"New");
		createMenuItem(jmfile,"Open");
		createMenuItem(jmfile,"Save");
		jmfile.addSeparator();
		createMenuItem(jmfile,"Exit");		
		createMenuItem(jmedit,"Cut");
		createMenuItem(jmedit,"Copy");
		createMenuItem(jmedit,"Paste");		
		createMenuItem(jmhelp,"About Text Editor");		
		jmb.add(jmfile);
		jmb.add(jmedit);
		jmb.add(jmhelp);		
		setJMenuBar(jmb);
		}
	public void EditFile(String fname) throws IOException 
		{	
		BufferedReader d=new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
		String s;
		jta.setText("");	
		setCursor(new Cursor(Cursor.WAIT_CURSOR));		
		while((s=d.readLine())!= null) 
			{
			jta.setText(jta.getText()+s+"\r\n");
			}	
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		d.close();
		}

	public void createMenuItem(JMenu jm,String txt)
		{
		JMenuItem jmi=new JMenuItem(txt);
		jmi.addActionListener(this);
		jm.add(jmi);
		}

	public void SaveFileAs(String fname) throws IOException 
		{
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		DataOutputStream dos=new DataOutputStream(new FileOutputStream(fname));
		dos.writeBytes(jta.getText());
		dos.close();		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	
	public void actionPerformed(ActionEvent e)
		{	
		JFileChooser jfc=new JFileChooser();
		
		if(e.getActionCommand().equals("New"))
			{ 
			this.setTitle("Text Editor");
			jta.setText("");
			fnameContainer=null;		
			}
		else 
			if(e.getActionCommand().equals("Open"))
				{
				int ret=jfc.showDialog(null,"Open");			
				if(ret == JFileChooser.APPROVE_OPTION)
					{
					try
						{
						File fyl=jfc.getSelectedFile();
						OpenFile(fyl.getAbsolutePath());
						this.setTitle(fyl.getName()+ " - Text Editor");
						fnameContainer=fyl;
						}
					catch(IOException ers){}
					}
			
				}
			else 
				if(e.getActionCommand().equals("Save"))
					{
					if(fnameContainer != null)
						{
						jfc.setCurrentDirectory(fnameContainer);		
						jfc.setSelectedFile(fnameContainer);
						}
				else 
					{
					jfc.setSelectedFile(new File("Untitled.txt"));
					}
			
			int ret=jfc.showSaveDialog(null);				
			if(ret == JFileChooser.APPROVE_OPTION){
				try{
					
					File fyl=jfc.getSelectedFile();
					SaveFile(fyl.getAbsolutePath());
					this.setTitle(fyl.getName()+ " - Text Editor");
					fnameContainer=fyl;				
				}
				catch(Exception ers2){}
			}			
		}
		else 
			if(e.getActionCommand().equals("Exit"))
				{
				System.exit(0);
				}
		else 
			if(e.getActionCommand().equals("Copy"))
				{
				jta.copy();
				}
		else 
			if(e.getActionCommand().equals("Paste"))
				{
				jta.paste();
				}
		else 
			if(e.getActionCommand().equals("About Text Editor"))
				{ 
				JOptionPane.showMessageDialog(this,"","Text Editor",JOptionPane.INFORMATION_MESSAGE);
				}
		else 
			if(e.getActionCommand().equals("Cut"))
				{
				jta.cut();
				}
	}
	
	public void OpenFile(String fname) throws IOException
		 {	
		BufferedReader d=new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
		String s;
		jta.setText("");	
		setCursor(new Cursor(Cursor.WAIT_CURSOR));		
		while((s=d.readLine())!= null)
			{
			jta.setText(jta.getText()+s+"\r\n");
			}	
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		d.close();
		}
	
	public void SaveFile(String fname) throws IOException 
		{
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		DataOutputStream dos=new DataOutputStream(new FileOutputStream(fname));
		dos.writeBytes(jta.getText());
		dos.close();		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	public void setSize(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	public void addWindowListener(WindowAdapter windowAdapter) {
		// TODO Auto-generated method stub
		
	}
}


class MyTextEditor
	{
	public static void main (String[] args) 
		{
		Notepad n=new Notepad();	
		n.setSize(500,500);
		n.setTitle("Text Editor");				
		n.setVisible(true);
		n.addWindowListener(new WindowAdapter()
			{	
			public void windowClosing(WindowEvent e)
				{
				System.exit(0);
				}
			});
		}
	}