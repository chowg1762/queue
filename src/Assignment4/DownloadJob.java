package Assignment4;

public class DownloadJob {
	private int downloadSize;	
	private int downloadSizeRemaining;	
	private int timeRequested;	
	private boolean isPremium;	
	private int id=1;
	private int takingTime = 0;
	public DownloadJob(int newDownLoadSize){
		this.downloadSize = newDownLoadSize;
		this.downloadSizeRemaining = newDownLoadSize;
		this.timeRequested = 0;
		this.isPremium = false;
	}
	
	public void setDownloadSize(int newDownLoadSize){
		this.downloadSize = newDownLoadSize;
		
	}
	
	public int getDownloadSize(){
		return downloadSize;
	}
	
	public void setDownloadSizeRemaining(int remaining){
		this.downloadSizeRemaining = remaining;
	}
	
	public int getDownloadSizeRemaining(){
		return downloadSizeRemaining;
	}
	
	public void setTimeRequeqsted(int setTime){
		this.timeRequested = setTime;
	}
	
	public int getTimeRequested(){
		return timeRequested;
	}
	
	public void setIsPremium(boolean iP){
		this.isPremium = iP;
	}
	
	public boolean getIsPremium(){
		return isPremium;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	
	public void setTakingTime(int takingTime){
		this.takingTime = takingTime;
	}
	public int getTakingTime(){
		return takingTime;
	}

}
