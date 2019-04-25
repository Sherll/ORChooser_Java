package SimEst;

//import java.awt.Container;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import similarity.*;

public class HelloWorldSwing extends JFrame{
	File dir1 = null;
	File dir2 = null;
	JTextField textField1 = new JTextField();
	JTextField textField2 = new JTextField();
//	public static void main(String[] args) {
//		HelloWorldSwing frame = new HelloWorldSwing();
//		frame.setVisible(true);
//		}
//	
   public HelloWorldSwing(){
		setTitle("SimEst");
		setBounds(200, 200, 500, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		final JLabel label = new JLabel("Similarity Calculator");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(106, 31, 262, 66);
		label.setBorder(new TitledBorder(null, "" , TitledBorder.DEFAULT_JUSTIFICATION,
		TitledBorder.DEFAULT_JUSTIFICATION, null, null));
		label.setForeground(new Color(0, 255, 0));
		label.setFont(new Font("",Font.BOLD,18));
		getContentPane().add(label);

		final JLabel label2 = new JLabel("Original File");
		label2.setBounds(58, 122, 60, 15);
		getContentPane().add(label2);
		
		textField1.setBounds(133, 111, 213, 36);
		getContentPane().add(textField1);
//		textField1.setPromptText("0");

		final JLabel label3 = new JLabel("Target File");
		label3.setBounds(58, 192, 60, 15);
		getContentPane().add(label3);
		
		textField2.setBounds(133, 181, 213, 36);
		getContentPane().add(textField2);
		
		final JButton button1 = new JButton("...");
		button1.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.CUSTOM_DIALOG);
				int ret = chooser.showOpenDialog(null);
				dir1 = null;
				if (ret == JFileChooser.APPROVE_OPTION) {
				dir1 = chooser.getSelectedFile();
				textField1.setText(dir1.getAbsolutePath());
				}
			}
		});
		button1.setBounds(350, 113, 30, 30);
		getContentPane().add(button1);
			
		
		
		final JButton button2 = new JButton("...");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.CUSTOM_DIALOG);
				int ret = chooser.showOpenDialog(null);
				dir2 = null;
				if (ret == JFileChooser.APPROVE_OPTION) {
				dir2 = chooser.getSelectedFile();
				textField2.setText(dir2.getAbsolutePath());
				}
			}
		});
		button2.setBounds(350, 183, 30, 30);
		getContentPane().add(button2);
		
		final JButton button = new JButton("Go");		
		button.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {	
				//System.out.println(textField1.getText());
				if(dir1 == null)
				{
				//	System.out.println(textField1.getText());
					dir1 = new File(textField1.getText());
				}
				if(dir2 == null)
				{
				//	System.out.println(textField2.getText());
					dir2 = new File(textField2.getText());					
				}
				compareopt score = new compareopt();
				GetFiles files = new GetFiles(dir1, dir2);
				try {
					score.test(files);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(200, 294, 83, 36);
		getContentPane().add(button);
   }
}
