package kr.co.n3soft.n3com.net;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class OptDialog extends JDialog implements ActionListener
{
	FileChat m_fc;
	JPanel	m_content;
	JPanel	m_confirm;
	JPanel	m_con;	
	JLabel	m_labelIP;
	JTextField	m_textIP;
	JButton	m_buttonOK;
	JButton m_buttonCancel;
	
	String	m_strIP;

	public OptDialog(FileChat fc, String title, boolean modal)
	{
		super(fc, title, modal);
		this.m_fc = fc;
		
		Container cp = getContentPane();
		cp.setLayout( new FlowLayout() );
		
		m_content = new JPanel( new BorderLayout() );
		m_confirm = new JPanel( new GridLayout(2,1,10,10) );		
		m_con = new JPanel();		
		
		m_labelIP = new JLabel("IP 설정 : ");
		m_labelIP.setFont(m_fc.m_font);
		m_textIP = new JTextField(15);
		m_con.add( m_labelIP );
		m_con.add( m_textIP );
		m_content.add( m_con, BorderLayout.CENTER );
		 
		m_buttonOK = new JButton("확인");
		m_buttonCancel = new JButton("취소");
		m_buttonOK.setFont(m_fc.m_font);
		m_buttonCancel.setFont(m_fc.m_font);
		m_confirm.add( m_buttonOK );
		m_confirm.add( m_buttonCancel );
		
		m_buttonOK.addActionListener(this);
		m_buttonCancel.addActionListener(this);
		
		cp.add( m_content );
		cp.add( m_confirm );
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object obj = e.getSource();
		if( obj == m_buttonOK )
		{
			m_strIP = m_textIP.getText();
//System.out.println("(OK) : [" +  m_strIP + "]");
			m_fc.m_strIP = m_strIP;
			dispose();
			setVisible(false);

		}
		else if( obj == m_buttonCancel )
		{
			dispose();
			setVisible(false);			
		}
	}
}