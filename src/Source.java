import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Source {

	private JFrame frame;
	private JTextArea textArea;
	private String inputStr = "";
	private String loadPath = "input.txt";
	private String savePath = "output.txt";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Source window = new Source();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Source() {
		initialize();
	}

	public String handleStr(String str, String substr)
	{
		StringBuilder stringBuilder = new StringBuilder(str);
		int start = 0;
		int end = -1;
		boolean isA = false;
		for(int i = 0; i < stringBuilder.length() - 1; i++)
		{	
			if(stringBuilder.charAt(i + 1) != ' ' && start < 0)
				start = i + 1;
			
			if(start >= 0)
			{
				if(stringBuilder.charAt(i) == 'a' || stringBuilder.charAt(i) == 'A')
					isA = true;
				
				if(i + 2 >= stringBuilder.length())
				{
					end = i + 1;
					i += 1;
				}
				else if(stringBuilder.charAt(i + 1) == ' ')
					end = i;
			}
			
			if(start >= 0 && end >= 0)
			{
				if(isA == true)
				{
					stringBuilder.insert(start, "<");
					if(i + 1 >= stringBuilder.length() - 1)
						stringBuilder.insert(end + 1, ">");
					else
						stringBuilder.insert(end + 2, ">");
					isA = false;
					i++;
				}
				start = -1;
				end = -1;
			}
		}
		
		return stringBuilder.toString();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 617, 394);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Load");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser(loadPath);
					int returnValue = fileChooser.showOpenDialog(null);
					
					if(returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						loadPath = selectedFile.getAbsolutePath();
					}	
					
					Scanner sc = new Scanner(new File(loadPath));
					
					inputStr = "";
					while(sc.hasNextLine())
					{
						inputStr = inputStr + sc.nextLine() + "\n";
					}
					StringBuilder stringBuilder = new StringBuilder(inputStr);
					textArea.setText(stringBuilder.reverse().toString());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(502, 23, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Process");
		btnNewButton_1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String handledStr = handleStr(inputStr, "a");
				textArea.setText("\n" + handledStr);			
			}});
		btnNewButton_1.setBounds(502, 146, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		textArea = new JTextArea();
		textArea.setBounds(0, 0, 485, 357);
		frame.getContentPane().add(textArea);
		
		JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(loadPath);
				int returnValue = fileChooser.showSaveDialog(null);
				
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					savePath = selectedFile.getAbsolutePath();
				}
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(savePath));
					writer.write(inputStr);
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonSave.setBounds(502, 56, 89, 23);
		frame.getContentPane().add(buttonSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {				
					Scanner sc = new Scanner(new File(loadPath));
					
					inputStr = "";
					while(sc.hasNextLine())
					{
						inputStr = inputStr + sc.nextLine() + "\n";
					}
					StringBuilder stringBuilder = new StringBuilder(inputStr);
					textArea.setText(stringBuilder.reverse().toString());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(502, 115, 85, 21);
		frame.getContentPane().add(btnUpdate);
	}
}
