/**
 * 	迷宫中的MazePoint坐标类，用来封装迷宫中的坐标对象以及相关方法
 *	每个坐标包含上下左右四种联通状态：0为不通，1为连通。
 */
package models;

public class MazePoint {
	//方向表示
	public final int RIGHT=0;
	public final int DOWN=1;
	public final int LEFT=2;
	public final int UP=3;
	
	/**上下左右的连通状态*/
	private int up=0;
	private int down=0;
	private int left=0;
	private int right=0;
	
	private int row;//行数
	private int col;//列数
	public boolean visited;
	private int state = 0; // 状态0为初始化状态，未到达。状态1为已到达。状态2为返回路径。
	
	//构造方法
	public MazePoint(int x,int y){
		this.row=x;
		this.col=y;
	}
	//判定两个坐标是否相同
	public boolean equal(int x,int y){
		if (this.row == x && this.col == y)
			return true;
		return false;
	}
	//判定连通情况，确定方向，1为通，0为不通
	public int redirect(){
		if(right==1)
			return RIGHT;
		else if(up==1)
			return UP;
		else if(left==1)
			return LEFT;
		else 
			return DOWN;
	}
	//判定能否沿着direction方向到达mazepoint点
	public boolean getAhead(MazePoint mazepoint,int direction){
		switch(direction){
		case RIGHT:
			if(right==1&&mazepoint.getLeft()==1)
				return true;
			break;
		case DOWN:
			if (down==1&&mazepoint.getUp()==1)
				return true;
			break;
		case LEFT:
			if(left==1&&mazepoint.getRight()==1)
				return true;
			break;
		case UP:
			if(up==1&&mazepoint.getDown()==1)
				return true;
			break;
				
		}
		return false;
	}
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getX(){
		return row;
	}
	public void setX(int x){
		this.row=x;
	}
	public int getY(){
		return col;
	}
	public void setY(int y){
		this.col=y;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	

}
