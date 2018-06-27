package boundary;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DAO.UtenteDAO;

public class LoginBoundary {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField usernameField;

	private void loginParse() {
		@SuppressWarnings("deprecation")
		String password = passwordField.getText();
    	String username = usernameField.getText();
    	if(password.equals("")|| username.equals("")) {
    		ToastMessage toastMessage = new ToastMessage("username o password vuoti non ammessi ",3000);
            toastMessage.setVisible(true);
    	}else {
    		System.out.print(username + " " + password);
    		if(!UtenteDAO.login(username, password)) {
    			ToastMessage toastMessage = new ToastMessage("username o password non corretti",3000);
                toastMessage.setVisible(true);
                passwordField.setText("");
                usernameField.setText("");
    		}else {
    			frame.setVisible(false);
    			frame.dispose();
    			if(UtenteDAO.getType(username)) {
    				System.out.println("admin");
    				//gui utente amministratire
    			}else {
    				System.out.println("user");
    				//gui utente registrato
    				UserBoundary.main(username);
    			}
    		}
        }
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginBoundary window = new LoginBoundary();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginBoundary() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Password");
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Welcome");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {

		    public void actionPerformed(ActionEvent e) {
		    	loginParse();
		    }
		});
		
		passwordField.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                   loginParse();

            }});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap(573, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel))
							.addGap(29)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(581)
							.addComponent(lblNewLabel_2)))
					.addContainerGap(578, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(636)
					.addComponent(btnNewButton)
					.addContainerGap(657, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2)
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(57)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(81)
					.addComponent(btnNewButton)
					.addContainerGap(453, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
