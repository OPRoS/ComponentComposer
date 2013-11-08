package kr.co.n3soft.n3com.net;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class Programmer extends JDialog implements ActionListener
{
	FileChat m_fc;
	JPanel	m_content;
	JPanel	m_confirm;
	JPanel	m_con;	
	JLabel	m_labelMyInfo1;
	JLabel	m_labelMyInfo2;
	JButton	m_buttonOK;		

	public Programmer(FileChat fc, String title, boolean modal)
	{
		super(fc, title, modal);
		this.m_fc = fc;
		
		Container cp = getContentPane();
		cp.setLayout( new FlowLayout() );
		
		m_content = new JPanel( new GridLayout(2,1,100,3) );
		m_confirm = new JPanel( new GridLayout(1,1,10,10) );
		m_con = new JPanel();
		
		m_labelMyInfo1 = new JLabel("FileChat version 1.0");		
		m_labelMyInfo2 = new JLabel("만든이 : 최승용 (onstone@kebi.com)");
		m_labelMyInfo1.setFont(m_fc.m_font);
		m_labelMyInfo2.setFont(m_fc.m_font);
		m_labelMyInfo2.setOpaque(true);
		m_labelMyInfo2.setForeground(Color.blue);
		
		m_content.add( m_labelMyInfo1 );
		m_content.add( m_labelMyInfo2 );
		 
		m_buttonOK = new JButton("확인");
		m_buttonOK.setFont(m_fc.m_font);
		m_confirm.add( m_buttonOK );
		
		m_buttonOK.addActionListener(this);		
		
		cp.add( m_content );
		cp.add( m_confirm );
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object obj = e.getSource();
		if( obj == m_buttonOK )
		{			
			dispose();
			setVisible(false);
		}
	}
}