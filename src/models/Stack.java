package models;
/**
 * �����洢������Ϣ�Ķ�ջ
 * @author 14501_000
 *
 */
public class Stack {
	private int top;//���ջ��Ԫ������ֵ��ջΪ��ʱֵΪ-1
	private int maxtop;//ջ��Ԫ������
	private MazePoint[] stack;//�����洢ջ��Ԫ�ص�MazePoint����
	
	
	/**���췽��,StackSizeΪջ��Ԫ�ظ���*/
	public Stack(int StackSize){
		maxtop=StackSize-1;
		stack=new MazePoint[StackSize];
		top=-1;
	}
	
	/**�ж�ջ�Ƿ�Ϊ��*/
	public boolean isEmpty(){
		return top==-1;
	}
	
	/**�ж���ջ�Ƿ�����*/
	public boolean isFull(){
		return top==maxtop;
	}
	 /**����ջ��Ԫ��*/
	public MazePoint pop(){
		return stack[top];
	}
	/**���ջ*/
	public void setEmpty(){
		while(top>=0){
			stack[top].setState(0);
			top--;
		}
	}
	/**����ջ��Ԫ�ظ���*/
	public int length(){
		return top+1;
		
	}
	/**���Ԫ��*/
	public void push(MazePoint p){
		stack[++top]=p;
	}
	/**ɾ��ջ��Ԫ��,������ջ��Ԫ��*/
	public MazePoint deleted(){
		if (!isEmpty()) {
			return stack[top--];
		} else {
			return null;
		}	
	}
	/**��������ջ*/
	public MazePoint[] getStack(){
		return stack;
	}
	/**����ջ������ֵ*/
	public int getTop(){
		return top;
	}

}
