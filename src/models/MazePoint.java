/**
 * 	�Թ��е�MazePoint�����࣬������װ�Թ��е���������Լ���ط���
 *	ÿ�����������������������ͨ״̬��0Ϊ��ͨ��1Ϊ��ͨ��
 */
package models;

public class MazePoint {
	//�����ʾ
	public final int RIGHT=0;
	public final int DOWN=1;
	public final int LEFT=2;
	public final int UP=3;
	
	/**�������ҵ���ͨ״̬*/
	private int up=0;
	private int down=0;
	private int left=0;
	private int right=0;
	
	private int row;//����
	private int col;//����
	public boolean visited;
	private int state = 0; // ״̬0Ϊ��ʼ��״̬��δ���״̬1Ϊ�ѵ��״̬2Ϊ����·����
	
	//���췽��
	public MazePoint(int x,int y){
		this.row=x;
		this.col=y;
	}
	//�ж����������Ƿ���ͬ
	public boolean equal(int x,int y){
		if (this.row == x && this.col == y)
			return true;
		return false;
	}
	//�ж���ͨ�����ȷ������1Ϊͨ��0Ϊ��ͨ
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
	//�ж��ܷ�����direction���򵽴�mazepoint��
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
