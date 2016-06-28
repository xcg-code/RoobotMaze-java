package models;

import java.util.ArrayList;
import java.util.Random;

import models.MazePoint;

/**
 * �����������Թ�ģ����Ϣ����װ����Թ�����
 * @author 14501_000
 *
 */
public class MazeModel {
	int width=0;
	int height=0;
	private Random random=new Random();
	private MazePoint point_now;//��ǰλ�õĵ�����
	private Stack pathStack;//�����Թ�����Ķ�ջ
	private MazePoint[] offset;//ƫ��������
	private ArrayList<MazePoint> maze;//�����Թ���ͼ�Ķ�̬����
	
	
	private int direction=0;//���巽��ѡ��
	boolean isDelete=false;//�Ƿ�ɾ��
	public static final int LAST_OPTION = 3;
	
	
	public MazeModel(int width,int height){
		this.width=width;
		this.height=height;
		maze=new ArrayList<MazePoint>();
		point_now=new MazePoint(0,0);//��ʼλ��
		pathStack=new Stack(width*height);
		offset=new MazePoint[4];
		offset[0]=new MazePoint(1,0);//����
		offset[1]=new MazePoint(0,1);//����
		offset[2]=new MazePoint(-1,0);//����
		offset[3]=new MazePoint(0,-1);//����
		
	}
	//��ʼ�Թ���
	public ArrayList<MazePoint> initializeMaze(){
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				MazePoint point = new MazePoint(w, h);
				maze.add(point);//�����Թ���
			}
		}
		maze.get(0).setState(1);
		return maze;
	}
	/**�����Թ���*/
	public ArrayList<MazePoint> resetMaze(){
		for(int i=0;i<maze.size();i++){
			maze.get(i).setState(0);
		}
		maze.get(0).setDown(1);
		return maze;
	}
	public ArrayList<MazePoint> getRandomMaze(){
		for(int h=0;h<height;h++){
			for(int w=0;w<width;w++){
				MazePoint mazepoint=new MazePoint(w,h);
				maze.add(mazepoint);
			}
		}
		return setRandomMaze();
	}
	
	/**���������������������Թ����㷨*/
	public ArrayList<MazePoint> setRandomMaze(){
		int top=0;
		int x=0;
		int y=0;
		ArrayList<MazePoint> pathStack=new ArrayList<MazePoint>();//��Ψһ·��
		pathStack.add(maze.get(x+y*width));
		while(top>=0){
			int nowPosDir[]=new int[]{-1,-1,-1,-1};
			int times=0;
			boolean nowMovable=false;//�ж���ǰ�����ܷ񵽴���������
			MazePoint topPoint=pathStack.get(top);//ȡ�����������е�top��Ԫ�أ�top��0��ʼ
			x=topPoint.getX();
			y=topPoint.getY();
			topPoint.visited=true;//��Ǹĵ��ѷ���
			loop: while(times<4){
				int dir=random.nextInt(4);
				if(nowPosDir[dir]==dir)continue;
				else nowPosDir[dir]=dir;
				switch(dir){
					case 0://������һ��
						if(x-1>=0&&maze.get(x-1+y*width).visited==false){
							maze.get(x+y*width).setLeft(1);
							maze.get(x-1+y*width).setRight(1);
							pathStack.add(maze.get(x-1+y*width));
							top++;
							nowMovable=true;
							break loop;
						}
						break;
					case 1://������һ��
						if(x+1<width&&maze.get(x+1+y*width).visited==false){
							maze.get(x+y*width).setRight(1);
							maze.get(x+1+y*width).setLeft(1);
							pathStack.add(maze.get(x+1+y*width));
							top++;
							nowMovable=true;
							break loop;
						}
						break;
					case 2://������һ��
						if(y-1>=0&&maze.get(x+(y-1)*width).visited==false){
							maze.get(x+y*width).setUp(1);
							maze.get(x+(y-1)*width).setDown(1);
							pathStack.add(maze.get(x+(y-1)*width));
							top++;
							nowMovable=true;
							break loop;
						}
						break;
					case 3://������һ��
						if ((y + 1) < height
								&& maze.get(x + (y + 1) * width).visited == false) {
							maze.get(x + y * width).setDown(1);
							maze.get(x + (y + 1) * width).setUp(1);
							pathStack.add(maze.get(x + (y + 1) * width));
							top++;
							nowMovable = true;
							break loop;
						}
						break;
				}
				times += 1;	
			}
			if (!nowMovable) {// ����ĸ����򶼲������ƶ������pointArray��ȡ���������λ��
				pathStack.remove(top);
				top--;
			}
		}
			
		point_now = maze.get(0);
		point_now.setState(1);

		return maze;
	}
	public MazePoint StepSolve() {// ����ִ��
		if (!point_now.equal(width - 1, height - 1)) {
			int r = 0;
			int c = 0;
			while (direction <= LAST_OPTION) {
				if (point_now.getX() == 0 && direction == 2
						|| point_now.getX() == width - 1 && direction == 0
						|| point_now.getY() == 0 && direction == 3
						|| point_now.getY() == height - 1 && direction == 1) {
					// �ж��Ƿ��ڱ߽�
					direction++;
				} else {
					r = point_now.getX() + offset[direction].getX();
					c = point_now.getY() + offset[direction].getY();
					if (r + c * width >= 0) {
						if (maze.get(r + c * width).getState() == 0
								&& point_now.getAhead(maze.get(r + c * width),
										direction)) {

							break;
						}
					}
					direction++;
				}
			}
			if (direction <= LAST_OPTION) {// �ƶ���maze(r+c*width)
				point_now.setState(1);
				pathStack.push(point_now);
				point_now = maze.get(r + c * width);
				direction = 0;
				isDelete = false;
			} else {
				MazePoint next = new MazePoint(0, 0);
				next = pathStack.deleted();
				isDelete = true;
				next.setState(4);
				if (next.getY() == point_now.getY())
					direction = 2 + next.getX() - point_now.getX();
				else
					direction = 3 + next.getY() - point_now.getY();
				point_now = next;
			}

		}

		return point_now;
	}
	public MazePoint Step_custom(){//�������Զ����Թ�
		if (!point_now.equal(width - 1, height - 1)) {
			int r = 0;
			int c = 0;
			while (direction <= LAST_OPTION) {

				if (point_now.getX() == 0
						&& direction == 2// ���ڱ߽�ʱ�߷����޶�
						|| point_now.getX() == width - 1 && direction == 0
						|| point_now.getY() == 0 && direction == 3
						|| point_now.getY() == height - 1 && direction == 1) {
					direction++;
				} else {
					r = point_now.getX() + offset[direction].getX();
					c = point_now.getY() + offset[direction].getY();
					if (r + c * width >= 0) {
						if (maze.get(r + c * width).getState() == 0) {
							break;
						}
					}
					direction++;
				}
			}
			if (direction <= LAST_OPTION) {// �ƶ���maze(r+c*width)
				isDelete = false;
				pathStack.push(point_now);
				point_now = maze.get(r + c * width);
				point_now.setState(1);
				direction = 0;
			} else {
				isDelete = true;
				MazePoint next = new MazePoint(0, 0);
				next = pathStack.deleted();
				if (next == null)
					return null;
				else {
					next.setState(4);
					if (next.getY() == point_now.getY())
						direction = 2 + next.getX() - point_now.getX();
					else
						direction = 3 + next.getY() - point_now.getY();
					point_now = next;

				}
			}

		}

		return point_now;
	}

	public void saveAuto(){
		while (!point_now.equal(width - 1, height - 1)) {
			StepSolve();
		}
	}
	public void setNowPoint(MazePoint x) {
		direction = 0;
		pathStack.setEmpty();
		point_now = x;
	}

	public MazePoint getNowPoint() {
		return point_now;
	}

	public int getDirection() {
		return direction;
	}
	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public Stack getStack() {
		return pathStack;
	}
	public boolean getIsDelete() {
		return isDelete;
	}
	

}
