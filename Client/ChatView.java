package Client;

import java.awt.EventQueue;
import java.net.InetAddress;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ChatView extends JFrame {

	private JPanel contentPane;
	private static int clientPort = 0;
	private static int serverPort = 0;
	private static InetAddress serverIP = null;
	private static String username = null;
	private static String OnlUser;
	private JTextField txtChatView;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChatView frame = new ChatView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public ChatView(InetAddress serverIP, int serverPort, int clientPort, String username, String msg) throws Exception {
		this.clientPort = clientPort;
		this.username = username;
		this.OnlUser = msg;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatView chat = new ChatView();
					chat.setVisible(true);
					System.out.println(msg);
					Client client = new Client(serverIP, serverPort);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		txtChatView = new JTextField();
		txtChatView.setHorizontalAlignment(SwingConstants.CENTER);
		txtChatView.setText("CHAT VIEW");
		txtChatView.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(155).addComponent(txtChatView,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(155, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane
						.createSequentialGroup().addContainerGap(124, Short.MAX_VALUE).addComponent(txtChatView,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(118)));
		contentPane.setLayout(gl_contentPane);
	}
}
