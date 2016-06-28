package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutMe {
	JFrame aboutMe;
	JPanel mainPanel;// 主面板
	JLabel exit;
	ImageIcon exit1, exit2;

	public AboutMe() {// 初始化图像
		/** 设置窗口在屏幕正中间 */
		Toolkit toolkit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = toolkit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高

		/** 初始化窗口 */
		aboutMe = new JFrame();
		aboutMe.setResizable(false);
		aboutMe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aboutMe.setUndecorated(true);// 去除窗口的边框
		aboutMe.setBounds(screenWidth - MainView.GAME_WIDTH / 2, screenHeight
				- MainView.GAME_HEIGHT / 2, MainView.GAME_WIDTH, MainView.GAME_HEIGHT);

		/** 设置背景图片 */
		URL back = this.getClass().getResource("/images/aboutme_background.png");
		final ImageIcon backgroundIcon = new ImageIcon(back);
		mainPanel = new JPanel() {
			private static final long serialVersionUID = -3083505087816720878L;

			protected void paintComponent(Graphics g) {
				g.drawImage(backgroundIcon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		mainPanel.setOpaque(false);
		mainPanel.setLayout(null);

		URL bu1 = this.getClass().getResource("/images/about_back1.png");
		exit1 = new ImageIcon(bu1);
		URL bu2 = this.getClass().getResource("/images/about_back2.png");
		exit2 = new ImageIcon(bu2);

		/** 退出按钮 */
		exit = new JLabel();
		exit.setIcon(exit1);
		exit.setMaximumSize(new Dimension(exit1.getIconWidth(), exit1
				.getIconHeight()));
		exit.setBounds(450, 120, exit1.getIconWidth(), exit1.getIconHeight());
		exit.addMouseListener(new ExitMouseAdapter());

		mainPanel.add(exit);

		aboutMe.getContentPane().add(mainPanel);
		aboutMe.setVisible(true);
	}

	// 退出按钮的监听适配器
	class ExitMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			aboutMe.setVisible(false);
			MainView.mainPage.setVisible(true);
		}

		public void mouseEntered(MouseEvent e) {
			exit.setIcon(exit2);
		}

		public void mouseExited(MouseEvent e) {
			exit.setIcon(exit1);
		}
	}
}
