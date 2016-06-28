package view;

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

public class RandomModel {
	private JFrame randomPage;
	private MazeModel mazeModel;
	private MazePoint mazePoint;
	private ArrayList<MazePoint> maze;
	static Thread myThread;
	private MazePoint point_now;
	private MazePoint step_point;
	private MazePoint test_point;
	JPanel panel;
	CanvasPanel mazePanel;

	// 四个按钮
	private JLabel bt_create;
	private JLabel bt_solveAuto;
	private JLabel bt_exit1;
	private JLabel bt_again;
	private JLabel bt_prompt;

	// 调节尺寸的按钮
	JLabel rowLeft;
	JLabel rowRight;
	JLabel colLeft;
	JLabel colRight;
	JLabel bt_up;
	JLabel bt_down;
	JLabel bt_left;
	JLabel bt_right;

	ImageIcon image_create, image_create1;
	ImageIcon image_solveAuto, image_solveAuto1;
	ImageIcon image_exit, image_exit1;
	ImageIcon image_again, image_again1;
	ImageIcon image_prompt, image_prompt1;
	ImageIcon leftLight;
	ImageIcon leftDark;
	ImageIcon rightLight;
	ImageIcon rightDark;
	ImageIcon up, up1;
	ImageIcon down, down1;
	ImageIcon left, left1;
	ImageIcon right, right1;
	final int RANDOM_DRAW = 0;
	final int STEP_DRAW = 1;// 初始随机迷宫
	final int ALL_DRAW = 2; // 执行到最后时将通路画出
	int step_number = 0;// 完成迷宫的步数

	// 显示行列数字
	JLabel rowNum;
	JLabel colNum;
	ArrayList<ImageIcon> imagesList;

	private static int mazeRow, mazeCol;// 迷宫的行列
	private int realx = 0, realy = 0;// 迷宫的真实大小
	private int sizex = 0, sizey = 0;// 对应迷宫的方格大小
	private int stepx = 0, stepy = 0;// 手动走位置

	int choose = -1;
	final ImageIcon image_wall;// 墙的图片
	ImageIcon image_robot, image_deImageIcon;
	Image wallImage, robotImage, destinationImage;
	URL robot, destination;
	boolean haveCreat = true;// 标识迷宫是否已创建
	boolean ifEnd = true;// 是否结束寻找
	boolean enThread = true;// 控制线程循环
	int runNumber = 0, firstTime = 0;
	MazePoint[] path;//路径储存数组

	/** 迷宫所在位置 */
	final int MAZE_WIDTH = 500;
	final int MAZE_HEIGHT = 500;
	final int MAZE_X = 80;
	final int MAZE_Y = 100;

	/** 按钮所在位置 */
	final int BTNS_OFFSET_X = 600;
	final int BTNS_OFFSET_Y = 205;

	// 调节迷宫大小的四个按钮的的坐标
	final int SIZE_OFFSET_X = 680;
	final int SIZE_OFFSET_Y = 130;
	final int ROW_GAP = 80;
	final int COL_GAP = 36;

	final int bt_distance = 50;// 按钮间距离

	public RandomModel() {
		mazeRow = 5;
		mazeCol = 5;
		mazePanel = new CanvasPanel();
		step_point = new MazePoint(0, 0);
		// System.out.println("x:"+step_point.getX()+"  y:"+step_point.getY());
		// 加载图片
		URL wall = this.getClass().getResource("/images/image_wall.jpg");
		image_wall = new ImageIcon(wall);
		wallImage = image_wall.getImage();
		robot = this.getClass().getResource("/images/image_robot.png");
		image_robot = new ImageIcon(robot);
		destination = this.getClass().getResource(
				"/images/image_destination.png");// 终点处
		image_deImageIcon = new ImageIcon(destination);
		destinationImage = image_deImageIcon.getImage();
		initialzeThread();
		initialize();// 初始化迷宫界面

	}

	/** 初始化一步执行的线程 */
	public void initialzeThread() {
		myThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (enThread) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
					choose = 1;
					mazePanel.repaint();
					if (point_now.equal(realx - 1, realy - 1)) {
						enThread = false;
						JOptionPane.showMessageDialog(null, "到达目的地,共走了  "
								+ step_number + " 步");
						runNumber = 2;
						choose = 2;
						mazePanel.repaint();
						ifEnd = true;
						myThread.suspend();

					}
				}
			}
		});
	}

	// 生成x乘y的迷宫，返回给maze
	private void GetMaze(int x, int y) {
		mazeModel = new MazeModel(x, y);
		maze = mazeModel.getRandomMaze();
		point_now = new MazePoint(0, 0);
		enThread = true;
		ifEnd = false;
	}

	public void initialize() {
		randomPage = new JFrame();
		randomPage.setResizable(false);
		randomPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit tool = Toolkit.getDefaultToolkit();// 定义工具包
		Dimension screenSize = tool.getScreenSize();
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高

		URL creat = this.getClass().getResource("/images/image_create.png");
		image_create = new ImageIcon(creat);
		creat = this.getClass().getResource("/images/image_create1.png");
		image_create1 = new ImageIcon(creat);

		URL sovelAuto = this.getClass().getResource(
				"/images/image_sovelAuto.png");
		image_solveAuto = new ImageIcon(sovelAuto);
		sovelAuto = this.getClass().getResource("/images/image_sovelAuto1.png");
		image_solveAuto1 = new ImageIcon(sovelAuto);

		URL again = this.getClass().getResource("/images/image_again.png");
		image_again = new ImageIcon(again);
		again = this.getClass().getResource("/images/image_again1.png");
		image_again1 = new ImageIcon(again);

		URL exit = this.getClass().getResource("/images/random_exit.png");
		image_exit = new ImageIcon(exit);
		exit = this.getClass().getResource("/images/random_exit1.png");
		image_exit1 = new ImageIcon(exit);
		URL button;

		button = this.getClass().getResource("/images/image_tishi.png");
		image_prompt = new ImageIcon(button);
		button = this.getClass().getResource("/images/image_tishi1.png");
		image_prompt1 = new ImageIcon(button);

		button = this.getClass().getResource("/images/left_dark.png");
		leftDark = new ImageIcon(button);
		button = this.getClass().getResource("/images/left_light.png");
		leftLight = new ImageIcon(button);

		button = this.getClass().getResource("/images/right_dark.png");
		rightDark = new ImageIcon(button);
		button = this.getClass().getResource("/images/right_light.png");
		rightLight = new ImageIcon(button);

		// 上下左右按钮
		button = this.getClass().getResource("/images/up.png");
		up = new ImageIcon(button);
		button = this.getClass().getResource("/images/up1.png");
		up1 = new ImageIcon(button);

		button = this.getClass().getResource("/images/down.png");
		down = new ImageIcon(button);
		button = this.getClass().getResource("/images/down1.png");
		down1 = new ImageIcon(button);

		button = this.getClass().getResource("/images/left.png");
		left = new ImageIcon(button);
		button = this.getClass().getResource("/images/left1.png");
		left1 = new ImageIcon(button);

		button = this.getClass().getResource("/images/right.png");
		right = new ImageIcon(button);
		button = this.getClass().getResource("/images/right1.png");
		right1 = new ImageIcon(button);

		imagesList = new ArrayList<ImageIcon>();
		for (int i = 1; i <= 25; i++) {
			String name = "/images/" + i + ".png";
			button = this.getClass().getResource(name);
			imagesList.add(new ImageIcon(button));
		}

		rowNum = new JLabel();
		rowNum.setIcon(imagesList.get(mazeRow - 1));
		rowNum.setMaximumSize(new Dimension(imagesList.get(0).getIconWidth(),
				imagesList.get(0).getIconHeight()));
		rowNum.setBounds(SIZE_OFFSET_X + ROW_GAP / 2, SIZE_OFFSET_Y, imagesList
				.get(0).getIconWidth(), imagesList.get(0).getIconHeight());

		colNum = new JLabel();
		colNum.setIcon(imagesList.get(mazeRow - 1));
		colNum.setMaximumSize(new Dimension(imagesList.get(0).getIconWidth(),
				imagesList.get(0).getIconHeight()));
		colNum.setBounds(SIZE_OFFSET_X + ROW_GAP / 2, SIZE_OFFSET_Y + COL_GAP,
				imagesList.get(0).getIconWidth(), imagesList.get(0)
						.getIconHeight());

		/** ------------------上下左右按钮---------------------- */
		bt_up = new JLabel();
		bt_up.setIcon(up);
		bt_up.setMaximumSize(new Dimension(down.getIconWidth(), down
				.getIconHeight()));
		bt_up.setBounds(SIZE_OFFSET_X, 425, down.getIconWidth() + 50,
				down.getIconHeight() + 50);

		bt_down = new JLabel();
		bt_down.setIcon(down);
		bt_down.setMaximumSize(new Dimension(down.getIconWidth(), down
				.getIconHeight()));
		bt_down.setBounds(SIZE_OFFSET_X, 525, down.getIconWidth(),
				down.getIconHeight());

		bt_left = new JLabel();
		bt_left.setIcon(left);
		bt_left.setMaximumSize(new Dimension(left.getIconWidth(), left
				.getIconHeight()));
		bt_left.setBounds(632, 490, left.getIconWidth(), left.getIconHeight());

		bt_right = new JLabel();
		bt_right.setIcon(right);
		bt_right.setMaximumSize(new Dimension(right.getIconWidth(), right
				.getIconHeight()));
		bt_right.setBounds(730, 490, right.getIconWidth(),
				right.getIconHeight());

		/************* 设置四个按钮 **********************/
		rowLeft = new JLabel();
		rowLeft.setIcon(leftDark);
		rowLeft.setMaximumSize(new Dimension(leftDark.getIconWidth(), leftDark
				.getIconHeight()));
		rowLeft.setBounds(SIZE_OFFSET_X, SIZE_OFFSET_Y,
				leftDark.getIconWidth(), leftDark.getIconHeight());

		rowRight = new JLabel();
		rowRight.setIcon(rightDark);
		rowRight.setMaximumSize(new Dimension(rightDark.getIconWidth(),
				rightDark.getIconHeight()));
		rowRight.setBounds(SIZE_OFFSET_X + ROW_GAP, SIZE_OFFSET_Y,
				rightDark.getIconWidth(), rightDark.getIconHeight());

		colLeft = new JLabel();
		colLeft.setIcon(leftDark);
		colLeft.setMaximumSize(new Dimension(leftDark.getIconWidth(), leftDark
				.getIconHeight()));
		colLeft.setBounds(SIZE_OFFSET_X, SIZE_OFFSET_Y + COL_GAP,
				leftDark.getIconWidth(), leftDark.getIconHeight());

		colRight = new JLabel();
		colRight.setIcon(rightDark);
		colRight.setMaximumSize(new Dimension(rightDark.getIconWidth(),
				rightDark.getIconHeight()));
		colRight.setBounds(SIZE_OFFSET_X + ROW_GAP, SIZE_OFFSET_Y + COL_GAP,
				rightDark.getIconWidth(), rightDark.getIconHeight());

		/************* 设置五个按钮 **********************/
		bt_create = new JLabel();
		bt_create.setIcon(image_create);
		bt_create.setMaximumSize(new Dimension(image_create.getIconWidth(),
				image_create.getIconHeight()));
		bt_create.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y,
				image_create.getIconWidth(), image_create.getIconHeight());

		bt_solveAuto = new JLabel();
		bt_solveAuto.setIcon(image_solveAuto);
		bt_solveAuto.setMaximumSize(new Dimension(image_solveAuto
				.getIconWidth(), image_solveAuto.getIconHeight()));
		bt_solveAuto
				.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y + bt_distance,
						image_solveAuto.getIconWidth(),
						image_solveAuto.getIconHeight());

		bt_again = new JLabel();
		bt_again.setIcon(image_again);
		bt_again.setMaximumSize(new Dimension(image_again.getIconWidth(),
				image_again.getIconHeight()));
		bt_again.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y + bt_distance * 2,
				image_again.getIconWidth(), image_again.getIconHeight());
		bt_prompt = new JLabel();
		bt_prompt.setIcon(image_prompt);
		bt_prompt.setMaximumSize(new Dimension(image_prompt.getIconWidth(),
				image_prompt.getIconHeight()));
		bt_prompt.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y + bt_distance * 3,
				image_prompt.getIconWidth(), image_prompt.getIconHeight());

		bt_exit1 = new JLabel();
		bt_exit1.setIcon(image_exit);
		bt_exit1.setMaximumSize(new Dimension(image_exit.getIconWidth(),
				image_exit.getIconHeight()));
		bt_exit1.setBounds(BTNS_OFFSET_X, BTNS_OFFSET_Y + bt_distance * 4,
				image_exit.getIconWidth(), image_exit.getIconHeight());

		URL back = this.getClass().getResource("/images/random_maze.png");
		ImageIcon backgrouondIcon = new ImageIcon(back);
		panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.drawImage(backgrouondIcon.getImage(), 0, 0, this);
				super.paintComponent(g);
			}
		};
		panel.setLayout(null);
		panel.add(bt_create);
		panel.add(bt_solveAuto);
		panel.add(bt_again);
		panel.add(bt_prompt);
		panel.add(bt_exit1);
		panel.add(rowLeft);
		panel.add(rowRight);
		panel.add(colLeft);
		panel.add(colRight);
		panel.add(rowNum);
		panel.add(colNum);
		panel.add(bt_up);
		panel.add(bt_down);
		panel.add(bt_left);
		panel.add(bt_right);

		panel.setBounds(0, 0, 800, 600);
		panel.setOpaque(false);
		randomPage.setUndecorated(true);// 去除窗口的边框
		randomPage.setBounds(screenWidth - MainView.GAME_WIDTH / 2,
				screenHeight - MainView.GAME_HEIGHT / 2, MainView.GAME_WIDTH,
				MainView.GAME_HEIGHT);
		randomPage.setLayout(null);
		randomPage.getContentPane().add(panel);
		randomPage.setVisible(true);

		rowLeft.addMouseListener(new RowLeftMouseAdapter());
		rowRight.addMouseListener(new RowRightMouseAdapter());
		colLeft.addMouseListener(new ColLeftMouseAdapter());
		colRight.addMouseListener(new ColRightMouseAdapter());

		bt_up.addMouseListener(new UpMouseAdapter());
		bt_down.addMouseListener(new DownMouseAdapter());
		bt_left.addMouseListener(new LeftMouseAdapter());
		bt_right.addMouseListener(new RightMouseAdapter());

		bt_create.addMouseListener(new CreateMouseAdapter());
		bt_solveAuto.addMouseListener(new SolveMouseAdapter());
		bt_again.addMouseListener(new AgainMouseAdapter());
		bt_prompt.addMouseListener(new PromptMouseAdapter());
		bt_exit1.addMouseListener(new ExitMouseAdapter());
	}

	class CanvasPanel extends JPanel {
		int WALL_HEIGHT = 5;
		private static final long serialVersionUID = 6386167515595185903L;

		/** 重写paint()方法 */
		@Override
		public void paint(Graphics arg0) {
			// TODO Auto-generated method stub
			super.paint(arg0);
			/** 设置背景图片 */
			URL back = this.getClass().getResource(
					"/images/random_maze_back.png");
			final ImageIcon backgroundIcon = new ImageIcon(back);
			arg0.drawImage(backgroundIcon.getImage(), 0, 0, null);
			if (choose == RANDOM_DRAW) {
				paintStart(arg0);// 初始随机迷宫
			} else if (choose == STEP_DRAW) {
				paintOneStep(arg0);// 执行一次后迷宫的图像
			} else if (choose == ALL_DRAW) {// 执行到最后时将通路画出
				paintRoad(arg0);
				paintOneStep(arg0);
			} else if (choose == 4) {
				paintUp(arg0);
			} else if (choose == 5) {
				paintDown(arg0);
			} else if (choose == 6) {
				paintLeft(arg0);
			} else if (choose == 7) {
				paintRight(arg0);
			}
		}

		public void paintUp(Graphics g) {
			drawMaze(g);
			// if(maze.get(now_x+mazeCol*(now_y-1)).getState()==1){
			g.drawImage(robotImage, step_point.getX() * sizex + sizex / 6,
					step_point.getY() * sizey + sizey / 6, sizex - sizex / 6,
					sizey - sizey / 6, null);
			if (step_point.getX() != (realx - 1)
					|| step_point.getY() != (realy - 1))
				g.drawImage(destinationImage, maze.get(maze.size() - 1).getX()
						* sizex + sizex / 6, maze.get(maze.size() - 1).getY()
						* sizey + sizey / 6, sizex - sizex / 6, sizey - sizey
						/ 6, null);
			step_number++;

			// }
		}

		public void paintDown(Graphics g) {
			drawMaze(g);
			g.drawImage(robotImage, step_point.getX() * sizex + sizex / 6,
					step_point.getY() * sizey + sizey / 6, sizex - sizex / 6,
					sizey - sizey / 6, null);
			if (step_point.getX() != (realx - 1)
					|| step_point.getY() != (realy - 1))
				g.drawImage(destinationImage, maze.get(maze.size() - 1).getX()
						* sizex + sizex / 6, maze.get(maze.size() - 1).getY()
						* sizey + sizey / 6, sizex - sizex / 6, sizey - sizey
						/ 6, null);
			step_number++;
			}

		public void paintLeft(Graphics g) {
			drawMaze(g);
			// if(mazePoint.getAhead(point_now,0)){
			g.drawImage(robotImage, step_point.getX() * sizex + sizex / 6,
					step_point.getY() * sizey + sizey / 6, sizex - sizex / 6,
					sizey - sizey / 6, null);
			if (step_point.getX() != (realx - 1)
					|| step_point.getY() != (realy - 1))
				g.drawImage(destinationImage, maze.get(maze.size() - 1).getX()
						* sizex + sizex / 6, maze.get(maze.size() - 1).getY()
						* sizey + sizey / 6, sizex - sizex / 6, sizey - sizey
						/ 6, null);

			step_number++;

			// }
		}

		public void paintRight(Graphics g) {
			drawMaze(g);
			// if(mazePoint.getAhead(point_now,2)){
			g.drawImage(robotImage, step_point.getX() * sizex + sizex / 6,
					step_point.getY() * sizey + sizey / 6, sizex - sizex / 6,
					sizey - sizey / 6, null);
			if (step_point.getX() != (realx - 1)
					|| step_point.getY() != (realy - 1))
				g.drawImage(destinationImage, maze.get(maze.size() - 1).getX()
						* sizex + sizex / 6, maze.get(maze.size() - 1).getY()
						* sizey + sizey / 6, sizex - sizex / 6, sizey - sizey
						/ 6, null);

			step_number++;

			// }

		}

		/** 绘制迷宫图像方法 */
		public void drawMaze(Graphics g) {
			MazePoint temp;
			int x, y, up, down, left, right;
			for (int i = 0; i < maze.size(); i++) {
				temp = maze.get(i);
				x = temp.getX() * sizex + sizex;
				y = temp.getY() * sizey + sizey;
				up = temp.getUp();
				down = temp.getDown();
				left = temp.getLeft();
				right = temp.getRight();

				int sizeMin = Math
						.min(sizex / WALL_HEIGHT, sizey / WALL_HEIGHT);
				// 绘制指定图像中已缩放到适合指定矩形内部的图像
				if (up == 0)
					g.drawImage(wallImage, x - sizex, y - sizey, sizex
							+ sizeMin, sizeMin, null);
				if (down == 0)
					g.drawImage(wallImage, x - sizex, y, sizex + sizeMin,
							sizeMin, null);
				if (left == 0)
					g.drawImage(wallImage, x - sizex, y - sizey, sizeMin, sizey
							+ sizeMin, null);
				if (right == 0)
					g.drawImage(wallImage, x, y - sizey, sizeMin, sizey
							+ sizeMin, null);

			}

		}

		/** 绘制机器人及终点图片的方法 */
		public void paintStart(Graphics g) {
			drawMaze(g);
			robotImage = image_robot.getImage();
			g.drawImage(robotImage, sizex / 6, sizey / 6, sizex - sizex / 6,
					sizey - sizey / 6, null);
			g.drawImage(destinationImage, maze.get(maze.size() - 1).getX()
					* sizex + sizex / 6, maze.get(maze.size() - 1).getY()
					* sizey + sizey / 6, sizex - sizex / 6, sizey - sizey / 6,
					null);

		}

		public void paintOneStep(Graphics g) {// 前进一步
			drawMaze(g);
			point_now = mazeModel.StepSolve();
			g.drawImage(robotImage, point_now.getX() * sizex + sizex / 6,
					point_now.getY() * sizey + sizey / 6, sizex - sizex / 6,
					sizey - sizey / 6, null);
			if (point_now.getX() != (realx - 1)
					|| point_now.getY() != (realy - 1))
				g.drawImage(destinationImage, maze.get(maze.size() - 1).getX()
						* sizex + sizex / 6, maze.get(maze.size() - 1).getY()
						* sizey + sizey / 6, sizex - sizex / 6, sizey - sizey
						/ 6, null);
			step_number++;
		}

		public void paintRoad(Graphics g) {
			path = mazeModel.getStack().getStack();
			int count = mazeModel.getStack().getTop();
			for (int i = 0; i < count; i++) {
				g.drawLine(path[i].getX() * sizex + sizex / 2, path[i].getY()
						* sizey + sizey / 2, path[i + 1].getX() * sizex + sizex
						/ 2, path[i + 1].getY() * sizey + sizey / 2);
			}
			g.drawLine(path[count].getX() * sizex + sizex / 2,
					path[count].getY() * sizey + sizey / 2,
					maze.get(maze.size() - 1 - mazeModel.getWidth()).getX()
							* sizex + sizex / 2,
					maze.get(maze.size() - 1 - mazeModel.getWidth()).getY()
							* sizey + 3 * sizey / 2);

		}

	}

	/** 创建按钮监听事件 */
	class CreateMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			step_number = 0;
			haveCreat = true;
			realx = RandomModel.mazeCol;
			realy = RandomModel.mazeRow;
			sizex = MAZE_WIDTH / (realx + 1);
			sizey = MAZE_HEIGHT / (realy + 1);
			GetMaze(realx, realy);
			mazePanel.setLayout(null);
			mazePanel.setBounds(MAZE_X, MAZE_Y, MAZE_WIDTH, MAZE_HEIGHT);

			randomPage.getContentPane().add(mazePanel, 0);
			choose = 0;
			mazePanel.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			bt_create.setIcon(image_create1);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			bt_create.setIcon(image_create);
		}

	}

	class SolveMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			if(step_point.getX()==0&&step_point.getY()==0){
			step_number = 0;
			}
			point_now=step_point;
			if (!haveCreat) {
				JOptionPane.showMessageDialog(null, "请先生成一个迷宫");
			} else if (!ifEnd) {
				runNumber++;
				if (runNumber == 1) {
					myThread.start();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (runNumber % 2 == 1) {
					myThread.resume();
				} else {
					myThread.suspend();
				}
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			bt_solveAuto.setIcon(image_solveAuto1);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			bt_solveAuto.setIcon(image_solveAuto);
		}

	}

	class AgainMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			step_number=0;
			point_now = maze.get(0);
			mazeModel.setNowPoint(point_now);
			choose = 0;
			mazePanel.repaint();
			enThread = true;
			ifEnd = false;
			if (runNumber != 0)
				runNumber = 2;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			bt_again.setIcon(image_again1);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			bt_again.setIcon(image_again);
		}

	}

	class PromptMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			if (!haveCreat) {
				JOptionPane.showMessageDialog(null, "请先生成一个迷宫");
			} else {
				if (point_now.getX() != (realx - 1)
						|| point_now.getY() != (realy - 1)) {
					choose = 1;
					mazePanel.repaint();
				} else {
					choose = 2;
					JOptionPane.showMessageDialog(null, "到达目的地！");
					mazePanel.repaint();
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			bt_prompt.setIcon(image_prompt1);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			bt_prompt.setIcon(image_prompt);
		}

	}

	class ExitMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			randomPage.setVisible(false);
			MainView.mainPage.setVisible(true);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			bt_exit1.setIcon(image_exit1);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			bt_exit1.setIcon(image_exit);
		}

	}

	// 调节迷宫大小的四个监听器
	class RowLeftMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			mazeRow--;
			rowNum.setIcon(imagesList.get(mazeRow - 1));
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
			rowNum.setIcon(imagesList.get(mazeRow - 1));

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
			colNum.setIcon(imagesList.get(mazeCol - 1));

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
			colNum.setIcon(imagesList.get(mazeCol - 1));

		}

		public void mouseEntered(MouseEvent e) {
			colRight.setIcon(rightLight);

		}

		public void mouseExited(MouseEvent e) {
			colRight.setIcon(rightDark);
		}
	}

	// 上下左右键
	class UpMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			test_point= maze.get(step_point.getX()+mazeRow*step_point.getY());
			if(test_point.getUp()==1){
			step_point.setY(step_point.getY() - 1);
			choose = 4;
			panel.repaint();
			System.out.println("x:" + step_point.getX() + "  y:"
					+ step_point.getY() + "  step:" + step_number);
			if (step_point.getX() == (mazeCol - 1)
					&& step_point.getY() == (mazeRow - 1)) {
				JOptionPane.showMessageDialog(null, "到达终点,共走了 "+step_number+" 步");
			}
			}
		}

		public void mouseEntered(MouseEvent e) {
			bt_up.setIcon(up1);
		}

		public void mouseExited(MouseEvent e) {
			bt_up.setIcon(up);
		}
	}

	class DownMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			test_point= maze.get(step_point.getX()+mazeRow*step_point.getY());
			if(test_point.getDown()==1){
			step_point.setY(step_point.getY() + 1);
			choose = 5;
			panel.repaint();
			System.out.println("x:" + step_point.getX() + "  y:"
					+ step_point.getY() + "  step:" + step_number);
			if (step_point.getX() == ((mazeCol - 1))
					&& step_point.getY() == (mazeRow - 1)) {
				JOptionPane.showMessageDialog(null, "到达终点,共走了 "+step_number+" 步");
			}
			}

		}

		public void mouseEntered(MouseEvent e) {
			bt_down.setIcon(down1);

		}

		public void mouseExited(MouseEvent e) {
			bt_down.setIcon(down);
		}
	}

	class LeftMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			test_point= maze.get(step_point.getX()+mazeRow*step_point.getY());
			if(test_point.getLeft()==1){
			step_point.setX(step_point.getX() - 1);
			choose = 6;
			panel.repaint();
			System.out.println("x:" + step_point.getX() + "  y:"
					+ step_point.getY() + "  step:" + step_number);
			if (step_point.getX() == (mazeCol - 1)
					&& step_point.getY() == (mazeRow - 1)) {
				JOptionPane.showMessageDialog(null, "到达终点,共走了 "+step_number+" 步");
			}
			}
		}

		public void mouseEntered(MouseEvent e) {
			bt_left.setIcon(left1);

		}

		public void mouseExited(MouseEvent e) {
			bt_left.setIcon(left);
		}
	}

	class RightMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			test_point= maze.get(step_point.getX()+mazeRow*step_point.getY());
			if(test_point.getRight()==1){
			step_point.setX(step_point.getX() + 1);
			choose = 7;
			panel.repaint();
			System.out.println("x:" + step_point.getX() + "  y:"
					+ step_point.getY() + "  step:" + step_number);
			if (step_point.getX() == (mazeCol - 1)
					&& step_point.getY() == (mazeRow - 1)) {
				JOptionPane.showMessageDialog(null, "到达终点,共走了 "+step_number+" 步");
			}
			}
		}

		public void mouseEntered(MouseEvent e) {
			bt_right.setIcon(right1);

		}

		public void mouseExited(MouseEvent e) {
			bt_right.setIcon(right);
		}
	}

}
