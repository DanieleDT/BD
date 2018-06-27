package boundary;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Frame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Font;

public class UserBoundary {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String username) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserBoundary window = new UserBoundary(username);
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
	public UserBoundary(String username) {
		initialize(username);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String username) {
		frame = new JFrame();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel usernameWelcome = new JLabel("Benvenuto " + username);
		usernameWelcome.setFont(new Font("Dialog", Font.BOLD, 16));
		usernameWelcome.setVerticalAlignment(SwingConstants.TOP);
		usernameWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(usernameWelcome);
		
		JButton btnNewButton = new JButton("Informazioni Satellite");
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Ricerca Filamento per contrasto ed Ellitticità");
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Informazioni Satellite");
		frame.getContentPane().add(btnNewButton_2);
	}

}
