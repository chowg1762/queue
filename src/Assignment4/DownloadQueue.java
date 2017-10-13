package Assignment4;
import java.util.Queue;
//import java.util.EmptyQueueException;

public class DownloadQueue{
	/**
	 * This method adds d to the end of the queue. 
	 * @param d
	 */
	private DownloadJobNode front;
	private DownloadJobNode rear;
	
	public DownloadQueue(){
		front=null;
		rear=null;
	}
	/**
	 * Adds d to the end of the queue. 
	 * @param d
	 */
	public void enqueue(DownloadJob d){
		DownloadJobNode newJobNode = new DownloadJobNode(d);
		if(front==null){
			front = newJobNode;
			rear = front;
		}
		else{
			rear.setLink(newJobNode);
			rear = newJobNode;
		}
	}
	
	/**
	 * takes the DownloadJob that is at the front of the queue, saves the
	 * value, removes the DownloadJob from the queue, and returns the Value.
	 * @return
	 */
	public DownloadJobNode dequeue(){
		DownloadJobNode answer; 
		if(front == null)
			throw new EmptyQueueException();
		
		answer = front;
		front = front.getLink();
		
		return answer;
	}
	/**
	 * Takes the DownloadJob that is at the front of the queue, and returns that
	 * value to the caller. It Doesn't remove that DownloadJob from the queue.
	 * @return
	 * 
	 */
	public DownloadJobNode peek(){
		if(front==null)
			throw new EmptyQueueException();
		
		return front;
	}
	
	/**
	 * Returns true if queue is empty, false otherwise.
	 * @return
	 */
	public boolean isEmpty(){
		return (front==null);
	}
}
