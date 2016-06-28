package models;
/**
 * 用来存储坐标信息的堆栈
 * @author 14501_000
 *
 */
public class Stack {
	private int top;//标记栈顶元素索引值，栈为空时值为-1
	private int maxtop;//栈内元素数量
	private MazePoint[] stack;//用来存储栈中元素的MazePoint数组
	
	
	/**构造方法,StackSize为栈的元素个数*/
	public Stack(int StackSize){
		maxtop=StackSize-1;
		stack=new MazePoint[StackSize];
		top=-1;
	}
	
	/**判定栈是否为空*/
	public boolean isEmpty(){
		return top==-1;
	}
	
	/**判定堆栈是否已满*/
	public boolean isFull(){
		return top==maxtop;
	}
	 /**返回栈顶元素*/
	public MazePoint pop(){
		return stack[top];
	}
	/**清空栈*/
	public void setEmpty(){
		while(top>=0){
			stack[top].setState(0);
			top--;
		}
	}
	/**返回栈内元素个数*/
	public int length(){
		return top+1;
		
	}
	/**添加元素*/
	public void push(MazePoint p){
		stack[++top]=p;
	}
	/**删除栈顶元素,返回新栈顶元素*/
	public MazePoint deleted(){
		if (!isEmpty()) {
			return stack[top--];
		} else {
			return null;
		}	
	}
	/**返回整个栈*/
	public MazePoint[] getStack(){
		return stack;
	}
	/**返回栈顶索引值*/
	public int getTop(){
		return top;
	}

}
