package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.*;;
/**
 * �Թ�����ı�д
 * @author 14501_000
 *
 */
public class MainView {
	static JFrame mainPage;//����������
	private JPanel panel;//�������
	private JLabel bt_randomModel;//���ģʽ��ť
	private JLabel bt_customModel;//�Զ���ģʽ��ť
	private JLabel bt_aboutMe;
	private JLabel bt_exit;
	ImageIcon image_custom,image_custom1,image_random,image_random1,
	image_aboutMe,image_aboutMe1,image_exit,image_exit1;
	public static final int GAME_WIDTH=800;
	public static final int GAME_HEIGHT=600;
	public MainView(){
		super();
		/**���ô���λ��*/
		Toolkit tool=Toolkit.getDefaultToolkit();//��ȡ���߰�
		Dimension screenSize=tool.getScreenSize();//��ȡ��Ļ�ߴ�
		int screenWidth=screenSize.width / 2;
		int screenHeight=screenSize.height / 2;
		
		
		/**��ʼ������*/
		mainPage=new JFrame();
		mainPage.setResizable(false);
		mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ô��ڹرշ�ʽΪֱ�ӹر�Ӧ�ó���
		mainPage.setUndecorated(true);//ȥ�����ڱ߿�
		mainPage.setBounds(screenWidth-GAME_WIDTH/2, screenHeight-GAME_HEIGHT/2, GAME_WIDTH,GAME_HEIGHT);
		
		/**���ñ���*/
		URL back = this.getClass().getResource("/images/background.png");
		final ImageIcon backgroundIcon = new ImageIcon(back);
		panel=new JPanel(){
			//�ػ汳������������
		protected void paintComponent(Graphics g) {
			g.drawImage(backgroundIcon.getImage(), 0, 0, this);//
			super.paintComponent(g);
			}
		};
		panel.setOpaque(false);//����panel��͸��
		panel.setLayout(null);//���ò��ֹ�����Ϊ��
		//��ʼ��ͼƬ
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
		
		int bt_distance=100;//���尴ť�����
		
		/**���ģʽ��ť*/
		bt_randomModel=new JLabel();
		bt_randomModel.setOpaque(false);
		bt_randomModel.setIcon(image_random);
		bt_randomModel.setMaximumSize(new Dimension(image_random.getIconWidth(),
			image_random.getIconHeight()));
		bt_randomModel.setBounds(GAME_WIDTH - image_random.getIconWidth()-80,
				GAME_HEIGHT / 2 - 200, image_random.getIconWidth(),
				image_random.getIconHeight());
		
		/**�Զ���ģʽ��ť*/
		bt_customModel=new JLabel();
		bt_customModel.setIcon(image_custom);
		bt_customModel.setMaximumSize(new Dimension(image_custom.getIconWidth(),
				image_custom.getIconHeight()));
		bt_customModel.setBounds(GAME_WIDTH - image_custom.getIconWidth()-80,
				GAME_HEIGHT / 2 - 200+bt_distance, image_custom.getIconWidth(),
				image_custom.getIconHeight());
		
		/**���ڰ�ť*/
		bt_aboutMe=new JLabel();
		bt_aboutMe.setIcon(image_aboutMe);
		bt_aboutMe.setMaximumSize(new Dimension(image_aboutMe.getIconWidth(),
				image_aboutMe.getIconHeight()));
		bt_aboutMe.setBounds(GAME_WIDTH - image_aboutMe.getIconWidth()-80,
				GAME_HEIGHT / 2 - 200+bt_distance*2, image_aboutMe.getIconWidth(),
				image_aboutMe.getIconHeight());
		
		/**�˳���Ϸ*/
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
		
		/**��Ӽ���*/
		bt_randomModel.addMouseListener(new RandomMouseAdapter());
		bt_customModel.addMouseListener(new CustomMouseAdapter());
		bt_aboutMe.addMouseListener(new AboutMeMouseAdapter());
		bt_exit.addMouseListener(new ExitMouseAdapter());
		
	}
	/**��ť�¼�����������*/
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
