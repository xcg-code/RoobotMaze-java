package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.MazeModel;
import models.MazePoint;
import view.CustomModel;

/**��������Թ�ģʽ*/
public class CustomModel {
	private JFrame randomPage;
	JPanel panel;
	//��ť
	JLabel create;
	JLabel saveBtn;
	JLabel solveAuto;
	JLabel againBtn;
	JLabel exitBtn;
	
	//�Թ���С����
	JLabel rowLeft;
	JLabel rowRight;
	JLabel colLeft;
	JLabel colRight;
	
	ImageIcon create1,create2;
	ImageIcon save1,save2;
	ImageIcon solveAll1,solveAll2;
	ImageIcon exitBtn1,exitBtn2;
	ImageIcon again1,again2;
	
	ImageIcon leftLight;
	ImageIcon leftDark;
	ImageIcon rightLight;
	ImageIcon rightDark;

	JLabel rowNum;
	JLabel colNum;
	
	
	ArrayList<ImageIcon> images_num;

	public static int mazeRow, mazeCol;// �Թ�������������

	ImageIcon iconRobot, iconDestination;// ��ť��ͼƬ

	ImageIcon maze_back;

	Image wallImage, robotImage, destinationImage;
	URL robotURL, destinationURL;
	final int RANDOM_DRAW = 0;// ��ʼ����Թ�
	final int STEP_DRAW = 1; // ִ��һ�κ��Թ���ͼ��
	final int ALL_DRAW = 2; // ִ�е����ʱ��ͨ·����

	final int MAZE_WIDTH = 500; // �����Թ��Ŀ��
	final int MAZE_HEIGHT = 500; // �����Թ��ĸ߶�
	final int MAZE_X = 80; // �Թ���x����
	final int MAZE_Y = 100; // �Թ���y����
	final int BTNS_OFFSET_X = 600;
	final int BTNS_OFFSET_Y = 205;

	// �����Թ���С���ĸ���ť�ĵ�����
	final int SIZE_OFFSET_X = 680;
	final int SIZE_OFFSET_Y = 130;
	final int ROW_GAP = 80;
	final int COL_GAP = 36;

	final int GAP_LENGTH = 60;// ����������ť֮��ľ���
	
	
	// 0����û·����1�����һ�γ��Եĵط���3�������õ�ǽ��4�����߹�2�ε�
		static Thread t;
		public JFrame customMaze;
		public MazeModel mazeModel;
		public ArrayList<MazePoint> maze;
		private int realX = 0, realY = 0;// �Թ�����ʵ��С
		private int sizeX = 0, sizeY = 0;// ��Ӧ�Թ��ķ����С
		ImageIcon icon5;
		ImageIcon iconapple;
		ImageIcon buton1, buton2, buton3, buton4, buton5;
		Image wa, app;
		URL apple;
		CanvasPanel mazePanel;
		boolean havecreat = false, ifend, enthread = true, haveaddmouse = false;;
		int screenWidth, screenHeight;
		int clickx, clicky;
		int runNumber = 0;
		MazePoint[] path;
		int choose = 0;
		int width = 0;
		boolean isModify = true;
		private MazePoint point_now;
		int step_number=0;//ͳ�Ʋ���
		
		public CustomModel(){
			mazeRow=5;
			mazeCol=5;
			
			
			mazePanel = new CanvasPanel();
			URL wall = this.getClass().getResource("/images/image_wall.jpg");
			icon5 = new ImageIcon(wall);
			wa = icon5.getImage();
			apple = this.getClass().getResource("/images/image_destination.png");
			iconapple = new ImageIcon(apple);
			app = iconapple.getImage();
			clickx = 0;
			clicky = 0;
			initializeThread();
			initialize();
			
		}
		
		//��ʼ���߳�
		public void initializeThread(){
			t = new Thread(new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					while (enthread) {
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
						choose = 2;
						mazePanel.repaint();
						if (point_now.equal(realX - 1, realY - 1)) {
							enthread = false;
							JOptionPane.showMessageDialog(null, "����Ŀ�ĵأ�����"+step_number+"��");
							runNumber = 2;
							ifend = true;
							t.suspend();

						}
					}

				}
			});
		}
		
		private void GetMaze(int x, int y) {// �����Թ�
			mazeModel = new MazeModel(x, y);
			maze = mazeModel.initializeMaze();
			width = x;
			point_now = new MazePoint(0, 0);
			enthread = true;
			ifend = false;
		}
		
		
		public void initialize(){
			customMaze = new JFrame();

			customMaze.setResizable(false);
			customMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			Toolkit kit = Toolkit.getDefaultToolkit(); // ���幤�߰�
			Dimension screenSize = kit.getScreenSize(); // ��ȡ��Ļ�ĳߴ�
			screenWidth = screenSize.width / 2; // ��ȡ��Ļ�Ŀ�
			screenHeight = screenSize.height / 2; // ��ȡ��Ļ�ĸ�

			customMaze.setBounds(screenWidth - MainView.GAME_WIDTH / 2, screenHeight
					- MainView.GAME_HEIGHT / 2, MainView.GAME_WIDTH, MainView.GAME_HEIGHT);
			customMaze.setLayout(null);
			customMaze.setUndecorated(true);
			
			// ��ʼ��ͼƬ
			URL button = this.getClass().getResource("/images/image_create.png");
			create1 = new ImageIcon(button);

			button = this.getClass().getResource("/images/image_create1.png");
			create2 = new ImageIcon(button);

			button = this.getClass().getResource("/images/image_save.png");
			save1 = new ImageIcon(button);

			button = this.getClass().getResource("/images/image_save1.png");
			save2 = new ImageIcon(button);

			button = this.getClass().getResource("/images/image_sovelAuto.png");
			solveAll1 = new ImageIcon(button);

			button = this.getClass().getResource("/images/image_sovelAuto1.png");
			solveAll2 = new ImageIcon(button);

			button = this.getClass().getResource("/images/random_exit.png");
			exitBtn1 = new ImageIcon(button);

			button = this.getClass().getResource("/images/random_exit1.png");
			exitBtn2 = new ImageIcon(button);

			button = this.getClass().getResource("/images/image_again.png");
			again1 = new ImageIcon(button);

			button = this.getClass().getResource("/images/image_again1.png");
			again2 = new ImageIcon(button);

			button = this.getClass().getResource("/images/left_dark.png");
			leftDark = new ImageIcon(button);

			button = this.getClass().getResource("/images/left_light.png");
			leftLight = new ImageIcon(button);

			button = this.getClass().getResource("/images/right_dark.png");
			rightDark = new ImageIcon(button);

			button = this.getClass().getResource("/images/right_light.png");
			rightLight = new ImageIcon(button);

			images_num = new ArrayList<ImageIcon>();
			for (int i = 1; i <= 25; i++) {
				String name = "/images/" + i + ".png";
				button = this.getClass().getResource(name);
				images_num.add(new ImageIcon(button));
			}

			rowNum = new JLabel();
			rowNum.setIcon(images_num.get(mazeRow - 1));
			rowNum.setMaximumSize(new Dimension(images_num.get(0).getIconWidth(),
					images_num.get(0).getIconHeight()));
			rowNum.setBounds(SIZE_OFFSET_X + ROW_GAP / 2, SIZE_OFFSET_Y, images_num
					.get(0).getIconWidth(), images_num.get(0).getIconHeight());

			colNum = new JLabel();
			colNum.setIcon(images_num.get(mazeRow - 1));
			colNum.setMaximumSize(new Dimension(images_num.get(0).getIconWidth(),
					images_num.get(0).getIconHeight()));
			colNum.setBounds(SIZE_OFFSET_X + ROW_GAP / 2, SIZE_OFFSET_Y + COL_GAP,
					images_num.get(0).getIconWidth(), images_num.get(0).getIconHeight());

			/************* �����ĸ���ť **********************/
			rowLeft = new JLabel();
			rowLeft.setIcon(leftDark);
			rowLeft.setMaximumSize(new Dimension(leftDark.getIconWidth(), leftDark
					.getIconHeight()));
			rowLeft.setBounds(SIZE_OFFSET_X, SIZE_OFFSET_Y,
					leftDark.getIconWidth(), leftDark.getIconHeight());
			rowLeft.addMouseListener(new RowLeftMouseAdapter());

			rowRight = new JLabel();
			rowRight.setIcon(rightDark);
			rowRight.setMaximumSize(new Dimension(rightDark.getIconWidth(),
					rightDark.getIconHeight()));
			rowRight.setBounds(SIZE_OFFSET_X + ROW_GAP, SIZE_OFFSET_Y,
					rightDark.getIconWidth(), rightDark.getIconHeight());
			rowRight.addMouseListener(new RowRightMouseAdapter());

			colLeft = new JLabel();
			colLeft.setIcon(leftDark);
			colLeft.setMaximumSize(new Dimension(leftDark.getIconWidth(), leftDark
					.getIconHeight()));
			colLeft.setBounds(SIZE_OFFSET_X, SIZE_OFFSET_Y + COL_GAP,
					leftDark.getIconWidth(), leftDark.getIconHeight());
			colLeft.addMouseListener(new ColLeftMouseAdapter());

			colRight = new JLabel();
			colRight.setIcon(rightDark);
			colRight.setMaximumSize(new Dimension(rightDark.getIconWidth(),
					rightDark.getIconHeight()));
			colRight.setBounds(SIZE_OFFSET_X + ROW_GAP, SIZE_OFFSET_Y + COL_GAP,
					rightDark.getIconWidth(), rightDark.getIconHeight());
			colRight.addMouseListener(new ColRightMouseAdapter());
			
			/************* ���������ť **********************/
			create = new JLabel();
			create.setIcon(create1);
			create.setMaximumSize(new Dimension(create1.getIconWidth(), create1
					.getIconHeight()));
			create.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y, create1.getIconWidth(),
					create1.getIconHeight());
			create.addMouseListener(new CreateMouseAdapter());

			saveBtn = new JLabel();
			saveBtn.setIcon(save1);
			saveBtn.setMaximumSize(new Dimension(save1.getIconWidth(), save1
					.getIconHeight()));
			saveBtn.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y + GAP_LENGTH,
					save1.getIconWidth(), save1.getIconHeight());
			saveBtn.addMouseListener(new SaveMouseAdapter());

			solveAuto = new JLabel();
			solveAuto.setIcon(solveAll1);
			solveAuto.setMaximumSize(new Dimension(solveAll1.getIconWidth(),
					solveAll1.getIconHeight()));
			solveAuto.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y + GAP_LENGTH * 2,
					solveAll1.getIconWidth(), create1.getIconHeight());
			solveAuto.addMouseListener(new SolveAllMouseAdapter());

			againBtn = new JLabel();
			againBtn.setIcon(again1);
			againBtn.setMaximumSize(new Dimension(again1.getIconWidth(), again1
					.getIconHeight()));
			againBtn.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y + GAP_LENGTH * 3,
					again1.getIconWidth(), create1.getIconHeight());
			againBtn.addMouseListener(new AgainMouseAdapter());

			exitBtn = new JLabel();
			exitBtn.setIcon(exitBtn1);
			exitBtn.setMaximumSize(new Dimension(exitBtn1.getIconWidth(), exitBtn1
					.getIconHeight()));
			exitBtn.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y + GAP_LENGTH * 4,
					exitBtn1.getIconWidth(), create1.getIconHeight());
			exitBtn.addMouseListener(new ExitMouseAdapter());

			URL back = this.getClass().getResource("/images/random_maze.png");
			final ImageIcon icon = new ImageIcon(back);
			panel = new JPanel() {
				private static final long serialVersionUID = -8064956336571267618L;

				protected void paintComponent(Graphics g) {
					g.drawImage(icon.getImage(), 0, 0, null);
					super.paintComponent(g);
				}
			};
			
			panel.setLayout(null);
			panel.add(create);
			panel.add(solveAuto);
			panel.add(againBtn);
			panel.add(saveBtn);
			panel.add(exitBtn);
			panel.add(rowLeft);
			panel.add(rowRight);
			panel.add(colLeft);
			panel.add(colRight);
			panel.add(rowNum);
			panel.add(colNum);
			panel.setBounds(0, 0, 1000, 600);
			panel.setOpaque(false);
			customMaze.getContentPane().add(panel);
			customMaze.setVisible(true);

		}
		class CanvasPanel extends JPanel{
			
			//��дpaint()f 
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				super.paint(g);
				/** ���ñ���ͼƬ */
				URL back = this.getClass().getResource(
						"/images/random_maze_back.png");
				final ImageIcon backgroundIcon = new ImageIcon(back);

				g.drawImage(backgroundIcon.getImage(), 0, 0, null);

				if (choose == 1) {// �Զ�����Թ�
					paintChange(g);
				} else if (choose == 2) {// ��ʼ��
					paintOneStep(g);
				}
				
			}
			public void paintChange(Graphics g){//�Զ��廭ͼ����
				MazePoint temp;
				int x,y;
				for(int i=0;i<maze.size();i++){
					temp=maze.get(i);
					x=temp.getX()*sizeX+sizeX;
					y=temp.getY()*sizeY+sizeY;
					g.setColor(Color.BLACK);
					g.drawRect(x-sizeX, y-sizeY, sizeX, sizeY);
					if(temp.getState()==1){
						g.setColor(Color.YELLOW);
						g.fillRect(x - sizeX + sizeX / 10, y - sizeY + sizeY / 10,
								sizeX - sizeX / 5, sizeY - sizeY / 5);
					}
					if(temp.getState()==3){
						g.setColor(Color.BLACK);
						g.fillRect(x - sizeX + sizeX / 10, y - sizeY + sizeY / 10,
							sizeX - sizeX / 5, sizeY - sizeY / 5);
					}
					if(temp.getState()==4){
						g.setColor(Color.BLUE);
						g.fillRect(x - sizeX + sizeX / 10, y - sizeY + sizeY / 10,
							sizeX - sizeX / 5, sizeY - sizeY / 5);
					}
					
				}
				//�����յ�
				if(point_now.getX()!=(realX-1)||point_now.getY()!=(realY-1)){
					g.drawImage(app, maze.get(maze.size() - 1).getX() * sizeX
							+ sizeX / 6, maze.get(maze.size() - 1).getY() * sizeY
							+ sizeY / 6, sizeX - sizeX / 6, sizeY - sizeY / 6, null);
				}
				
			}
			/**ÿ��һ�����õķ���*/
			public void paintOneStep(Graphics g){
				MazePoint temp=mazeModel.getNowPoint();
				point_now=mazeModel.Step_custom();
				if(point_now==null){
					//t.suspend();
					t.stop();
					point_now=temp;
					paintChange(g);
					choose=-1;
					JOptionPane.showMessageDialog(null, "��������Թ���ͨ· ��");
					paintChange(g);
				}else{
					if(point_now.getState()==4){
						maze.get(point_now.getX() + point_now.getY() * width).setState(4);
					}
					if(point_now.getState()==4&&temp.getState()==1){
						temp.setState(4);
					}
					paintChange(g);
					choose=-1;
				}
				step_number++;
			}
			
		}
//		public void checkMzae(){
//			MazePoint temp=mazeModel.getNowPoint();
//			point_now=mazeModel.Step_custom();
//			if(point_now==null){
//				point_now=temp;
//				JOptionPane.showMessageDialog(null, "��������Թ���ͨ· ��");
//			}else{
//				if(point_now.getState()==4){
//					maze.get(point_now.getX() + point_now.getY() * width).setState(4);
//				}
//				if(point_now.getState()==4&&temp.getState()==1){
//					temp.setState(4);
//				}
//			}
//		}
		/**�����ϰ���*/
		public void setPoint(int x,int y){
			 int index=x+y*width;
			 if(index+1==maze.size()||index==0){
				 JOptionPane.showMessageDialog(null, "�����������յ������ϰ���");
			 }else if(maze.get(index).getState()==0){
				 maze.get(index).setState(3);
			 }else{
				 maze.get(index).setState(0);
			 }
			 choose=1;
			 mazePanel.repaint();
			
		}
		class MakeMazeAdapter extends MouseAdapter {
			// ����¼��Ĵ���
			public void mousePressed(MouseEvent e) {
				if (isModify) {
					clickx = e.getX();
					clicky = e.getY();
					int mazex, mazey;
					mazex = clickx / sizeX;
					mazey = clicky / sizeY;
					setPoint(mazex, mazey);
				}

			}
		}
		class CreateMouseAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				step_number=0;
				isModify=true;
				havecreat=true;
				realX=mazeRow;
				realY=mazeCol;
				sizeX = MAZE_WIDTH / (realX + 1);
				sizeY=MAZE_HEIGHT/(realY+1);
				GetMaze(realX,realY);
				choose=1;
				mazePanel.setLayout(null);
				mazePanel.setBounds(MAZE_X, MAZE_Y, MAZE_WIDTH, MAZE_HEIGHT);
				if (!haveaddmouse) {
					mazePanel.addMouseListener(new MakeMazeAdapter());
					haveaddmouse = true;
				}
				customMaze.getContentPane().add(mazePanel);
				mazePanel.repaint();
				
			}

			public void mouseEntered(MouseEvent e) {
				create.setIcon(create2);
			}

			public void mouseExited(MouseEvent e) {
				create.setIcon(create1);
			}
		}
		class SaveMouseAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				isModify=false;
			}

			public void mouseEntered(MouseEvent e) {
				saveBtn.setIcon(save2);
			}

			public void mouseExited(MouseEvent e) {
				saveBtn.setIcon(save1);
			}
		}
		class SolveAllMouseAdapter extends MouseAdapter {
			@SuppressWarnings("deprecation")
			public void mouseClicked(MouseEvent e) {
				if (!havecreat) {
					JOptionPane.showMessageDialog(null, "��������һ���Թ�");
				} else {
					if (isModify) {
						JOptionPane.showMessageDialog(null, "���ȱ���һ���Թ�");
					} else if (!ifend) {
						runNumber++;
						if (runNumber == 1) {
							t.start();
						} else if (runNumber % 2 == 1) {
							t.resume();

						} else {
							t.suspend();
						}
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
				solveAuto.setIcon(solveAll2);
			}

			public void mouseExited(MouseEvent e) {
				solveAuto.setIcon(solveAll1);
			}
		}
		class AgainMouseAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				step_number=0;
				point_now = maze.get(0);
				// save.setEnabled(true);
				mazeModel.setNowPoint(point_now);
				choose = 1;
				maze = mazeModel.resetMaze();
				isModify = true;
				mazePanel.repaint();
				enthread = true;
				ifend = false;
				if (runNumber != 0)
					runNumber = 2;
			}

			public void mouseEntered(MouseEvent e) {
				againBtn.setIcon(again2);
			}

			public void mouseExited(MouseEvent e) {
				againBtn.setIcon(again1);
			}
		}
		class ExitMouseAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				customMaze.setVisible(false);
				MainView.mainPage.setVisible(true);
			}

			public void mouseEntered(MouseEvent e) {
				exitBtn.setIcon(exitBtn2);
			}

			public void mouseExited(MouseEvent e) {
				exitBtn.setIcon(exitBtn1);
			}
		}
		// �����Թ���С���ĸ�������
		class RowLeftMouseAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				mazeRow--;
				rowNum.setIcon(images_num.get(mazeRow - 1));
			}

			public void mouseEntered(MouseEvent e) {
				rowLeft.setIcon(leftLight);
			}

			public void mouseExited(MouseEvent e) {
				rowLeft.setIcon(leftDark);
			}
		}

		class RowRightMouseAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				mazeRow++;
				rowNum.setIcon(images_num.get(mazeRow - 1));

			}

			public void mouseEntered(MouseEvent e) {
				rowRight.setIcon(rightLight);

			}

			public void mouseExited(MouseEvent e) {
				rowRight.setIcon(rightDark);
			}
		}

		class ColLeftMouseAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				mazeCol--;
				colNum.setIcon(images_num.get(mazeCol - 1));

			}

			public void mouseEntered(MouseEvent e) {
				colLeft.setIcon(leftLight);

			}

			public void mouseExited(MouseEvent e) {
				colLeft.setIcon(leftDark);
			}
		}

		class ColRightMouseAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				mazeCol++;
				colNum.setIcon(images_num.get(mazeCol - 1));

			}

			public void mouseEntered(MouseEvent e) {
				colRight.setIcon(rightLight);

			}

			public void mouseExited(MouseEvent e) {
				colRight.setIcon(rightDark);
			}
		}

	
}
