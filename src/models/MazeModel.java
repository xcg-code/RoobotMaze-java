package models;

import java.util.ArrayList;
import java.util.Random;

import models.MazePoint;

/**
 * 储存完整的迷宫模型信息，封装相关迷宫功能
 * @author 14501_000
 *
 */
public class MazeModel {
	int width=0;
	int height=0;
	private Random random=new Random();
	private MazePoint point_now;//当前位置的点坐标
	private Stack pathStack;//储存迷宫坐标的堆栈
	private MazePoint[] offset;//偏移量数组
	private ArrayList<MazePoint> maze;//储存迷宫地图的动态数组
	
	
	private int direction=0;//定义方向选择
	boolean isDelete=false;//是否删除
	public static final int LAST_OPTION = 3;
	
	
	public MazeModel(int width,int height){
		this.width=width;
		this.height=height;
		maze=new ArrayList<MazePoint>();
		point_now=new MazePoint(0,0);//初始位置
		pathStack=new Stack(width*height);
		offset=new MazePoint[4];
		offset[0]=new MazePoint(1,0);//向右
		offset[1]=new MazePoint(0,1);//向下
		offset[2]=new MazePoint(-1,0);//向左
		offset[3]=new MazePoint(0,-1);//向上
		
	}
	//初始迷宫点
	public ArrayList<MazePoint> initializeMaze(){
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				MazePoint point = new MazePoint(w, h);
				maze.add(point);//储存迷宫点
			}
		}
		maze.get(0).setState(1);
		return maze;
	}
	/**返回迷宫点*/
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
	
	/**基于深度搜索，随机生成迷宫的算法*/
	public ArrayList<MazePoint> setRandomMaze(){
		int top=0;
		int x=0;
		int y=0;
		ArrayList<MazePoint> pathStack=new ArrayList<MazePoint>();//存唯一路径
		pathStack.add(maze.get(x+y*width));
		while(top>=0){
			int nowPosDir[]=new int[]{-1,-1,-1,-1};
			int times=0;
			boolean nowMovable=false;//判定当前坐标能否到达相邻坐标
			MazePoint topPoint=pathStack.get(top);//取出坐标数组中第top各元素，top从0开始
			x=topPoint.getX();
			y=topPoint.getY();
			topPoint.visited=true;//标记改点已访问
			loop: while(times<4){
				int dir=random.nextInt(4);
				if(nowPosDir[dir]==dir)continue;
				else nowPosDir[dir]=dir;
				switch(dir){
					case 0://向左移一格
						if(x-1>=0&&maze.get(x-1+y*width).visited==false){
							maze.get(x+y*width).setLeft(1);
							maze.get(x-1+y*width).setRight(1);
							pathStack.add(maze.get(x-1+y*width));
							top++;
							nowMovable=true;
							break loop;
						}
						break;
					case 1://向右移一格
						if(x+1<width&&maze.get(x+1+y*width).visited==false){
							maze.get(x+y*width).setRight(1);
							maze.get(x+1+y*width).setLeft(1);
							pathStack.add(maze.get(x+1+y*width));
							top++;
							nowMovable=true;
							break loop;
						}
						break;
					case 2://向上移一格
						if(y-1>=0&&maze.get(x+(y-1)*width).visited==false){
							maze.get(x+y*width).setUp(1);
							maze.get(x+(y-1)*width).setDown(1);
							pathStack.add(maze.get(x+(y-1)*width));
							top++;
							nowMovable=true;
							break loop;
						}
						break;
					case 3://向下移一格
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
			if (!nowMovable) {// 如果四个方向都不可以移动，则从pointArray中取出这个坐标位置
				pathStack.remove(top);
				top--;
			}
		}
			
		point_now = maze.get(0);
		point_now.setState(1);

		return maze;
	}
	public MazePoint StepSolve() {// 单步执行
		if (!point_now.equal(width - 1, height - 1)) {
			int r = 0;
			int c = 0;
			while (direction <= LAST_OPTION) {
				if (point_now.getX() == 0 && direction == 2
						|| point_now.getX() == width - 1 && direction == 0
						|| point_now.getY() == 0 && direction == 3
						|| point_now.getY() == height - 1 && direction == 1) {
					// 判断是否处于边界
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
			if (direction <= LAST_OPTION) {// 移动到maze(r+c*width)
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
	public MazePoint Step_custom(){//单步走自定义迷宫
		if (!point_now.equal(width - 1, height - 1)) {
			int r = 0;
			int c = 0;
			while (direction <= LAST_OPTION) {

				if (point_now.getX() == 0
						&& direction == 2// 处于边界时走法的限定
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
			if (direction <= LAST_OPTION) {// 移动到maze(r+c*width)
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
