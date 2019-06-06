import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
* Provides a gui and functions as button action listeners to manage clients.
* @author A.Benaceur
* @version 2.0
* @since March 21st, 2018
*/
public class ClientView extends JFrame {

	/*
	 *A frame for the gui.
	 */
	static Frame frame;
	/*
	 *Screen to post information.
	 */
	static JTextArea screen;

	/*
	 * constructs a client view GUI. calls methods from ClientController in action listener for events.
	 * 
	 */
	public ClientView() {

		JFrame frame;
		// ui components

		JScrollPane screenPanel;
		JPanel buttonPanel;
		Dimension dim;

		// frame stuff
		frame = new JFrame("CLIENT MANAGER");
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		// frame.setVisible(true);
		frame.setSize(1000, 700);
		frame.setLocation((dim.width / 2 - frame.getWidth() / 2), (dim.height - frame.getHeight()) / 2);
		// frame.add(rp = new RenderPanel() );
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		Container pane = frame.getContentPane();

		// buttons
		Button add = new Button("ADD NEW CLIENT");
		Button modify = new Button("MODIFY EXISTING CLIENT");
		Button delete = new Button("DELETE EXISTING CLIENT");
		Button search = new Button("SEARCH FOR A CLIENT ");
		Button browse = new Button("BROWSE CLIENTS");

		// button panel stuff
		buttonPanel = new JPanel();
		buttonPanel.add(browse);
		buttonPanel.add(add);
		buttonPanel.add(modify);
		buttonPanel.add(delete);
		buttonPanel.add(search);

		// screen stuff
		screen = new JTextArea();
		screen.setFont(screen.getFont().deriveFont(16f));
		screen.setEditable(false);
		screenPanel = new JScrollPane(screen);

		// ACTION LISTENER STUFF GOES HERE

		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				screen.setText("");
				ClientController.printClients();
			}

		});
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField firstnameField = new JTextField();
				JTextField lastnameField = new JTextField();
				JTextField adressField = new JTextField();
				JTextField phoneField = new JTextField(9);
				JTextField postalField = new JTextField();
				JTextField idField = new JTextField();

				// set maximum lengths
				firstnameField.setColumns(20);
				lastnameField.setColumns(20);
				adressField.setColumns(50);
				phoneField.setColumns(12);
				postalField.setColumns(7);

				String[] type = { "Residential", " Commercial" };
				JComboBox types = new JComboBox(type);

				Object[] insertOptions = { "Insert", "Cancel" };

				final JComponent[] fill_ins = new JComponent[] { new JLabel(""), new JLabel("Enter First Name "),
						firstnameField, new JLabel("Enter Last Name "), lastnameField,
						new JLabel("Enter Client's Adress"), adressField, new JLabel("Enter Clients Phone Number "),
						phoneField, new JLabel("Enter Clients Postal Code"), postalField,
						new JLabel("Enter Clients ID"), idField, new JLabel("Enter Type of Client"), types,

				};

				int result = JOptionPane.showOptionDialog(null, fill_ins, "Insert a New Client",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, insertOptions, null);

				if (result == JOptionPane.OK_OPTION) {

					Client temp = new Client(firstnameField.getText().trim(), lastnameField.getText().trim(),
							adressField.getText().trim(), phoneField.getText().trim(), postalField.getText().trim(),
							idField.getText().trim(), types.getSelectedIndex());
					ClientController.addClientToDB(temp);

					String confirm = "Client: " + temp.firstName + " " + temp.lastName
							+ " Has successfully been added to Database";

					JOptionPane.showMessageDialog(frame.getContentPane(), confirm);

				} else {
					screen.setText("");
				}
			}

		});

		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField inputField = new JTextField();

				// set maximum lengths
				inputField.setColumns(20);

				String[] type = { "BY NAME", " BY PHONE NUMBER", "BY ID " };
				JComboBox types = new JComboBox(type);

				Object[] insertOptions = { "Search", "Cancel" };

				final JComponent[] fill_ins = new JComponent[] { new JLabel("Search by..."), types,
						new JLabel("Enter Information "), inputField, };

				int result = JOptionPane.showOptionDialog(null, fill_ins, "Search for a Client",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, insertOptions, null);

				if (result == JOptionPane.OK_OPTION) {
					Client found = null;
					String sql = "";

					if (types.getSelectedIndex() == 0)
						found = ClientController.searchDB("name", inputField.getText().trim());
					if (types.getSelectedIndex() == 1)
						found = ClientController.searchDB("phone", inputField.getText().trim());
					if (types.getSelectedIndex() == 2)
						found = ClientController.searchDB("phone", inputField.getText().trim());

					if (found != null) {
						String confirm = "FOUND: " + found.toString();
						JOptionPane.showMessageDialog(frame.getContentPane(), confirm);
					} else
						JOptionPane.showMessageDialog(frame.getContentPane(),
								"NO CLIENT WITH THAT INFO IN THE DATABASE.");

				} else {
					screen.setText("Something went wrong. try again. ");
				}
			}

		});
		modify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField inputField = new JTextField();

				// set maximum lengths
				inputField.setColumns(20);

				String[] type = { "BY NAME", " BY PHONE NUMBER", "BY ID " };
				JComboBox types = new JComboBox(type);

				Object[] insertOptions = { "Search", "Cancel" };

				final JComponent[] fill_ins = new JComponent[] { new JLabel("Search by..."), types,
						new JLabel("Enter Information "), inputField, };

				int result = JOptionPane.showOptionDialog(null, fill_ins, "Search for a Client",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, insertOptions, null);

				if (result == JOptionPane.OK_OPTION) {
					Client found = null;
					String sql = "";

					if (types.getSelectedIndex() == 0)
						found = ClientController.searchDB("name", inputField.getText());
					if (types.getSelectedIndex() == 1)
						found = ClientController.searchDB("phone", inputField.getText());
					if (types.getSelectedIndex() == 2)
						found = ClientController.searchDB("id", inputField.getText());

					if (found != null) {
						// HERE---------------------------------------

						String confirm = "MODIFY: " + found.toString();
						JOptionPane.showMessageDialog(frame.getContentPane(), confirm);
						JTextField firstnameField = new JTextField();
						JTextField lastnameField = new JTextField();
						JTextField adressField = new JTextField();
						JTextField phoneField = new JTextField(9);
						JTextField postalField = new JTextField();
						JTextField idField = new JTextField();

						// set maximum lengths
						firstnameField.setColumns(20);
						lastnameField.setColumns(20);
						adressField.setColumns(50);
						phoneField.setColumns(12);
						postalField.setColumns(7);

						String[] type1 = { "Residential", " Commercial" };
						JComboBox types1 = new JComboBox(type);

						Object[] insertOptions1 = { "Save", "Cancel" };

						final JComponent[] fill_ins1 = new JComponent[] { new JLabel(""),
								new JLabel("Enter First Name "), firstnameField, new JLabel("Enter Last Name "),
								lastnameField, new JLabel("Enter Client's Adress"), adressField,
								new JLabel("Enter Clients Phone Number "), phoneField,
								new JLabel("Enter Clients Postal Code"), postalField, new JLabel("Enter Clients ID"),
								idField, new JLabel("Enter Type of Client"), types,

						};

						firstnameField.setText(found.firstName);
						lastnameField.setText(found.lastName);
						adressField.setText(found.adress);
						phoneField.setText(found.phoneNumber);
						idField.setText(found.id);
						postalField.setText(found.postalCode);

						int result1 = JOptionPane.showOptionDialog(null, fill_ins1, "MODIFY a Client",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, insertOptions1, null);

						if (result == JOptionPane.OK_OPTION)
							if (result == JOptionPane.OK_OPTION) {
								Client updated = new Client(firstnameField.getText(), lastnameField.getText(),
										adressField.getText(), phoneField.getText(), postalField.getText(),
										idField.getText(), types1.getSelectedIndex());
								ClientController.deleteClientFromDB(found);
								ClientController.addClientToDB(updated);
							} else
								JOptionPane.showMessageDialog(frame.getContentPane(),
										"NO CLIENT WITH THAT NAME IN THE DATABASE.");

					} else {
						screen.setText("");
					}
				}

			}}
		);

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField inputField = new JTextField();

				// set maximum lengths
				inputField.setColumns(20);

				String[] type = { "BY NAME", " BY PHONE NUMBER", "BY ID " };
				JComboBox types = new JComboBox(type);

				Object[] insertOptions = { "Search", "Cancel" };

				final JComponent[] fill_ins = new JComponent[] {
						new JLabel("Search for a Client to delete based on..."), types,
						new JLabel("Enter Information "), inputField, };

				int result = JOptionPane.showOptionDialog(null, fill_ins, "Search for a Client",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, insertOptions, null);
				Client found = null;
				if (result == JOptionPane.OK_OPTION) {

					String sql = "";

					if (types.getSelectedIndex() == 0)
						found = ClientController.searchDB("name", inputField.getText());
					if (types.getSelectedIndex() == 1)
						found = ClientController.searchDB("phone", inputField.getText());
					if (types.getSelectedIndex() == 2)
						found = ClientController.searchDB("id", inputField.getText());

				}

				if (found != null) {
					ClientController.deleteClientFromDB(found);
					String confirm = "DELETED: " + found.toString();
					JOptionPane.showMessageDialog(frame.getContentPane(), confirm);

				} else
					JOptionPane.showMessageDialog(frame.getContentPane(), "NO CLIENT WITH THAT NAME IN THE DATABASE.");

			}
		});

		frame.setLayout(new BorderLayout(1, 2));

		pane.add(buttonPanel, BorderLayout.PAGE_END);
		pane.add(screenPanel, BorderLayout.CENTER);

		frame.setVisible(true);

	}
}
