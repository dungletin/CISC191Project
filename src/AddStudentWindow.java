import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddStudentWindow extends JFrame implements ActionListener
{
	private JPanel centerPanel;
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JLabel IDLabel;
	private JTextField IDTextField;
	private JButton addButton;
	private JButton exitButton;
	private JPanel southPanel;
	// private GUIView view;
	private StudentInformation studentInfo;
	private JComboBox<String> comboBox;
	private JPanel northPanel;
	private JLabel titleLabel;
	private JLabel statusLabel;

	public AddStudentWindow(StudentInformation studentInfo)
	{
		this.studentInfo = studentInfo;
		createPanel();
		setLayout(new BorderLayout());
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		add(northPanel, BorderLayout.NORTH);
		// set the size
		setSize(300, 300);

		// set the program to end when the window is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// display the window
		setVisible(true);
	}

	/**
	 * Create Panel
	 */
	public void createPanel()
	{
		centerPanel = new JPanel();
		nameLabel = new JLabel("Name:");
		nameTextField = new JTextField(10);
		IDLabel = new JLabel("ID:");
		IDTextField = new JTextField(10);
		String[] comboOptions = { "Regular", "Exchange", "Honored",
				"Tutoring" };
		comboBox = new JComboBox<String>(comboOptions);
		statusLabel = new JLabel();

		// name panel
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameTextField);

		// ID panel
		JPanel IDPanel = new JPanel();
		IDPanel.add(IDLabel);
		IDPanel.add(IDTextField);

		// combo box Panel
		JPanel comboBoxPanel = new JPanel();
		comboBoxPanel.add(comboBox);

		// status panel
		JPanel statusPanel = new JPanel();
		statusPanel.add(statusLabel);

		// Add components into center panel
		centerPanel.setLayout(new GridLayout(4, 1));
		centerPanel.add(namePanel);
		centerPanel.add(IDPanel);
		centerPanel.add(comboBoxPanel);
		centerPanel.add(statusPanel);
		// create south panel
		southPanel = new JPanel();
		exitButton = new JButton("Exit");
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		southPanel.add(exitButton);
		southPanel.add(addButton);

		// create north panel
		northPanel = new JPanel();
		titleLabel = new JLabel("Please enter student infomation:");
		northPanel.add(titleLabel);

	}

	/*
	 * 
	 */
	public boolean isValidName(String name)
	{

		char[] chars = name.toCharArray();
		for (char character : chars)
		{
			if (!Character.isLetter(character))
			{
				return false;
			}
		}
		return true;
	}

	public boolean isValidID(String ID)
	{
		char[] chars = ID.toCharArray();
		for (char character : chars)
		{
			if (!Character.isDigit(character))
			{
				return false;

			}
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == addButton)
		{

			String name = nameTextField.getText();
			String ID = IDTextField.getText();
			String type = (String) comboBox.getSelectedItem();

			//check if the name and ID are valid
			if (!isValidName(name) || !isValidID(ID))
			{
				statusLabel.setText("Fail");
			}
			else
			{
				
				int newID = Integer.valueOf(ID);
				//check if the ID already exist in the class
				if (studentInfo.containID(newID))
				{
					statusLabel.setText("ID already exists");
				}
				//if id does not exist create new student object based on the info
				else
				{
					Student student;
					if (type.equals("Exchange"))
					{
						student = new ExchangeStudent(name, newID);
					}
					else if (type.equals("Honored"))
					{
						student = new HonoredStudent(name, newID);
					}
					else if (type.equals("Tutoring"))
					{
						student = new TutoringStudent(name, newID);
					}
					else
					{
						student = new Student(name, newID);
					}

					try
					{
						studentInfo.addStudent(student);

						new Controller(new GUIView());
						dispose();
					}
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}

	}

}
