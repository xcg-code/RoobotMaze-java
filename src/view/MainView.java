package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.*;;
/**
 * 迷宫界面的编写
 * @author 14501_000
 *
 */
public class MainView {
	static JFrame mainPage;//定义主窗口
	private JPanel panel;//定义面板
	private JLabel bt_randomModel;//随机模式按钮
	private JLabel bt_customModel;//自定义模式按钮
	private JLabel bt_aboutMe;
	private JLabel bt_exit;
	ImageIcon image_custom,image_custom1,image_random,image_random1,
	image_aboutMe,image_aboutMe1,image_exit,image_exit1;
	public static final int GAME_WIDTH=800;
	public static final int GAME_HEIGHT=600;
	public MainView(){
		super();
		/**设置窗口位置*/
		Toolkit tool=Toolkit.getDefaultToolkit();//获取工具包
		Dimension screenSize=tool.getScreenSize();//获取屏幕尺寸
		int screenWidth=screenSize.width / 2;
		int screenHeight=screenSize.height / 2;
		
		
		/**初始化窗口*/
		mainPage=new JFrame();
		mainPage.setResizable(false);
		mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口关闭方式为直接关闭应用程序
		mainPage.setUndecorated(true);//去除窗口边框
		mainPage.setBounds(screenWidth-GAME_WIDTH/2, screenHeight-GAME_HEIGHT/2, GAME_WIDTH,GAME_HEIGHT);
		
		/**设置背景*/
		URL back = this.getClass().getResource("/images/background.png");
		final ImageIcon backgroundIcon = new ImageIcon(back);
		panel=new JPanel(){
			//重绘背景起清屏作用
		protected void paintComponent(Graphics g) {
			g.drawImage(backgroundIcon.getImage(), 0, 0, this);//
			super.paintComponent(g);
			}
		};
		panel.setOpaque(false);//设置panel不透明
		panel.setLayout(null);//设置布局管理器为空
		//初始化图片
		URL random=this.getClass().getResource("/images/image_random.png");
		image_random=new ImageIcon(random);
		random=this.getClass().getResource("/images/image_random1.png");
		image_random1=new ImageIcon(random);
		
		URL custom=this.getClass().getResource("/images/image_custom.png");
		image_custom=new ImageIcon(custom);
		custom=this.getClass().getResource("/images/image_custom1.png");
		image_custom1=new ImageIcon(custom);
		
		URL about=this.getClass().getResource("/images/image_aboutMe.png");
		image_aboutMe=new ImageIcon(about);
		about=this.getClass().getResource("/images/image_aboutMe1.png");
		image_aboutMe1=new ImageIcon(about);
		
		URL exit=this.getClass().getResource("/images/exit.png");
		image_exit=new ImageIcon(exit);
		exit=this.getClass().getResource("/images/exit1.png");
		image_exit1=new ImageIcon(exit);
		
		int bt_distance=100;//定义按钮间距离
		
		/**随机模式按钮*/
		bt_randomModel=new JLabel();
		bt_randomModel.setOpaque(false);
		bt_randomModel.setIcon(image_random);
		bt_randomModel.setMaximumSize(new Dimension(image_random.getIconWidth(),
			image_random.getIconHeight()));
		bt_randomModel.setBounds(GAME_WIDTH - image_random.getIconWidth()-80,
				GAME_HEIGHT / 2 - 200, image_random.getIconWidth(),
				image_random.getIconHeight());
		
		/**自定义模式按钮*/
		bt_customModel=new JLabel();
		bt_customModel.setIcon(image_custom);
		bt_customModel.setMaximumSize(new Dimension(image_custom.getIconWidth(),
				image_custom.getIconHeight()));
		bt_customModel.setBounds(GAME_WIDTH - image_custom.getIconWidth()-80,
				GAME_HEIGHT / 2 - 200+bt_distance, image_custom.getIconWidth(),
				image_custom.getIconHeight());
		
		/**关于按钮*/
		bt_aboutMe=new JLabel();
		bt_aboutMe.setIcon(image_aboutMe);
		bt_aboutMe.setMaximumSize(new Dimension(image_aboutMe.getIconWidth(),
				image_aboutMe.getIconHeight()));
		bt_aboutMe.setBounds(GAME_WIDTH - image_aboutMe.getIconWidth()-80,
				GAME_HEIGHT / 2 - 200+bt_distance*2, image_aboutMe.getIconWidth(),
				image_aboutMe.getIconHeight());
		
		/**退出游戏*/
		bt_exit=new JLabel();
		bt_exit.setIcon(image_exit);
		bt_exit.setMaximumSize(new Dimension(image_exit.getIconWidth(),
				image_exit.getIconHeight()));
		bt_exit.setBounds(GAME_WIDTH - image_exit.getIconWidth()-80,
				GAME_HEIGHT / 2 - 200+bt_distance*3, image_exit.getIconWidth(),
				image_exit.getIconHeight());
		
		panel.add(bt_randomModel);
		panel.add(bt_customModel);
		panel.add(bt_aboutMe);
		panel.add(bt_exit);
		mainPage.getContentPane().add(panel);
		mainPage.setVisible(true);
		
		/**添加监听*/
		bt_randomModel.addMouseListener(new RandomMouseAdapter());
		bt_customModel.addMouseListener(new CustomMouseAdapter());
		bt_aboutMe.addMouseListener(new AboutMeMouseAdapter());
		bt_exit.addMouseListener(new ExitMouseAdapter());
		
	}
	/**按钮事件监听适配器*/
	class RandomMouseAdapter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			new RandomModel();
			mainPage.setVisible(false);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseEntered(arg0);
			bt_randomModel.setIcon(image_random1);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseExited(arg0);
			bt_randomModel.setIcon(image_random);
		}

	
		
	}
	
	class CustomMouseAdapter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			new CustomModel();
			mainPage.setVisible(false);
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseEntered(arg0);
			bt_customModel.setIcon(image_custom1);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseExited(arg0);
			bt_customModel.setIcon(image_custom);
		}

		
	}
	
	class AboutMeMouseAdapter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			new AboutMe();
			mainPage.setVisible(false);
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseEntered(arg0);
			bt_aboutMe.setIcon(image_aboutMe1);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseExited(arg0);
			bt_aboutMe.setIcon(image_aboutMe);
		}
		
	}
	
	
	class ExitMouseAdapter extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			System.exit(0);
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseEntered(arg0);
			bt_exit.setIcon(image_exit1);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseExited(arg0);
			bt_exit.setIcon(image_exit);
		}

		
	}
	
	public static void main(String[] args) {
		new MainView();
	}

}
