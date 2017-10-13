package Assignment4;

public class EmptyQueueException extends NullPointerException{
	public EmptyQueueException(){
		super();
	}
	public EmptyQueueException(String a){
		super(a);
	}
}
