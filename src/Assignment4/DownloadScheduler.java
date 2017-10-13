package Assignment4;
/**
 * 
 * @author Wonguen Cho
 * Studnet ID : 109461283
 * 
 * 
 * This class provides the simulate method to execute this program. 
 * It is used in DownloadManager class. 
 * In the DownloadManager class, this role is executing one time step.
 * This class get the data like regular value and premium value from main method and save it
 * in DownloadQueue and DownloadJob classes. 
 * 
 * Data field : 
 *regularQ
 *  - This is the queue where you will store the regular download jobs
 *premiumQ 
 * - This is the queue where you will store the premium download jobs. Connected with the regularQ, this forms a two-level priority queue. 
 * 	 You will always dequeue from this queue until it is empty before dequeueing from the regularQ.
 *currentTime 
 * - This is where you store the time elapsed since the start of the simulation.
 *simulationEndTime
 * - this stores the time when the simulation should end.
 *random 
 *- this will generate jobs at each timestep. We will give this to you. You must use this class as provided, with NO MODIFICATIONS.
 *CurrentJobs 
 * - This is where you store the jobs that are currently downloading. 
 * Once a job is done downloading, you can remove it from the array (at the end of the timestep), update the statistics, and replace it with a new job. 
 *Precondition: the CurrentJobs array, download speed, probabilities of various jobs, and queues have been initialized.
 *downloadSpeed  - This stores the speed of the download in Megabytes Per Second.  If a job finishes partway through a second (ie: download speed =10Mbps, job size=214mb), 
 *			you should wait until the beginning of the next second to remove it and add replace it with a new job.
 * premiumEnd
 * 	-This value saves the number of finished premium jobs
 * regularEnd
 * 	-This value saves the number of finished regular jobs
 * premiumData
 * 	-This value saves total data size of finished premium jobs.
 * regularData
 * 	-This value saves total data size of finished regular jobs.
 * premiumWaitTime 
 * 	-This value saves the total time which has taken to finish premium jobs.
 * regularWaitTime
 * 	-This value saves the total time which has taken to finish regular jobs.
 * 
 * Constructor
 * 	DownloadScheduler(int sizeServer, int speed)
 * 		-This constructor initialized CurrentJobs array with sizeServer value. The sizeServer value will be the length of the array.
 * 		In addition, this constructor also initialized speed of downloading.
 * 	
 *  Method : 
 *  	setRegularJob
 *  		- This method set the regular job by receiving the random value from main method 
 *  	setPremiumJob
 *  		-This method set the premium job by receiving the random value from main method 
 *  	getRegularJob
 *  		-This method returns the instance of RegularJob
 *  	getPremiumJob
 *  		-This method returns the instance of PremiumJob
 *  	setCurrentJob
 *  		-This method initializes the array of CurrentJob
 *  	getCurrentJob
 *  		-This method returns an array of CurrentJob
 *  	allServerFull
 *  		-This method shows whether all of servers are full or not.
 *  	isServerFull
 *  		-This method shows whether a specific server is full or not.
 *  	showRegularQ
 *  		-This method returns an string which shows the state of Regular Q.
 *  	showPremiumQ
 *  		-This method returns an string which shows the state of Premium Q.
 *  	simulate
 *  		-This method execute a simulation. If the severs are full(which means that all servers are downloading data), this method will save new jobs on the Queue. 
 *  		If there is an empty server, this method will start downloading for new jobs.
 *		getPremiumEnd
 *			-This method returns the total number of premium jobs.
 *		getRegularEnd
 *			-This method returns the total number of regular jobs.
 *		getPremiumData
 *			- This method returns total premium data.
 *		getRegularData
 *			- This method returns total regular data.
 *		getPremiumWaitTime
 *			- This method returns total time which has taken to finish premium jobs
 *		getRegularWaitTime
 *			- This method returns total time which has taken to finish regular jobs.
 *		
**/
public class DownloadScheduler {
	private DownloadQueue regularQ = new DownloadQueue();
	private DownloadQueue premiumQ = new DownloadQueue();
	private int currentTime;
	private int simulationEndTime;
	private DownloadRandomizer random;
	private DownloadJob downloadData; 
	private DownloadJob[] CurrentJobs;
	private int downloadSpeed;
	private DownloadJob regularValue;
	private DownloadJob premiumValue;
	
	private int premiumEnd = 0;
	private int regularEnd = 0;
	private int premiumData = 0;
	private int regularData = 0;
	private int premiumWaitTime = 0;
	private int regularWaitTime = 0;
	
/**
* -This constructor initialized CurrentJobs array with sizeServer value. The sizeServer value will be the length of the array.
 * 		In addition, this constructor also initialized speed of downloading.
* @param sizeServer
* 	this value is used for initializing the length of array.
 * @param speed  
* 	 this value is used for initializing the speed of downloading. 		
*   <dt><b>Precondition:</b><dd>
*    The size of Server has to be greater than 0
* @throws IllegalArgumentExcpetion
*	 if sizeServer is less than or equal zero, exception will be thrown.
*/
	public DownloadScheduler(int sizeServer, int speed){
		if(sizeServer<=0)
			throw new IllegalArgumentException();
		CurrentJobs = new DownloadJob[sizeServer];
		downloadSpeed = speed; 
		this.setCurrentJob();
	}
	
	/**
	 * setRegularJob
 *  		- This method set the regular job by receiving the random value from main method 
	 * @param rJob
	 * 	rJob is an instance to be saved on RegularJob 
	 */
	public void setRegularJob(DownloadJob rJob){
		regularValue = rJob;
	}
	/**
	 * 	setPremiumJob
	 *  	-This method set the premium job by receiving the random value from main method 
	 * @param pJob
	 * 	pJob is an instance to be saved on premiumJob
	 */
	public void setPremiumJob(DownloadJob pJob){
		premiumValue = pJob;
	}
	/**
	 * 
	 * @return
	 */
	public DownloadJob getRegularJob(){
		return regularValue;
	}
	/**
	 * 
	 * @return
	 */
	public DownloadJob getPremiumJob(){
		return premiumValue;
	}
	/**
	 * 
	 */
	public void setCurrentJob(){
		for(int i=0; i<CurrentJobs.length; i++)
			this.CurrentJobs[i] = new DownloadJob(-1);
	}
	/**
	 * 
	 * @return
	 */
	public DownloadJob[] getCurrentJob(){
		return CurrentJobs;
	}
	/**
	 * 
	 * @param a
	 * @return
	 */
	public boolean allServerFull(DownloadJob[] a){
		boolean result = true;
		for(int i=0; i<a.length; i++){
			if(a[i].getDownloadSize()==-1)
				result = false;
		}
		return result;
	}
	/**
	 * 
	 * @param a
	 * @return
	 */
	public boolean isServerFull(DownloadJob a){
		boolean result = true;
		if(a.getDownloadSize()==-1)
			result =false;
		return result;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String showRegularQ(){
		String answer = "";
		try{
		DownloadJobNode cursor = regularQ.peek();
			while(cursor!=null){
				answer = answer+"[#"+cursor.getData().getId()+": "+cursor.getData().getDownloadSize()+"Mb] ";
				cursor = cursor.getLink();
			}
		}
		catch(EmptyQueueException ex){
			answer = "empty";
		}
		return answer;
	}
	
	/**
	 * 
	 * @return
	 */
	public String showPremiumQ(){
		String answer = "";
		/*if(cursor==null){
			answer="empty";
		}*/
		try{
			DownloadJobNode cursor = premiumQ.peek();
		//else{
			while(cursor!=null){
				answer = answer+"[#"+cursor.getData().getId()+": "+cursor.getData().getDownloadSize()+"Mb] ";
				cursor = cursor.getLink();
			//}
			}
		}
		catch(EmptyQueueException ex){
			answer = "empty";
		}
		return answer;
	}
	
	/**
	 * this mehtod creates a simulation based on the user input.
	 * @return
	 */
	public String simulate(){
		boolean isJobDone = false;
		DownloadJob[] finishedJob = new DownloadJob[CurrentJobs.length];
		String result ="";
		
		if(allServerFull(CurrentJobs)){
			if(getPremiumJob().getDownloadSize()!=-1)
				premiumQ.enqueue(this.getPremiumJob());
			if(getRegularJob().getDownloadSize()!=-1)
				regularQ.enqueue(this.getRegularJob());
		}
		else{
			int i = 0;
			while(!allServerFull(CurrentJobs)&&i<CurrentJobs.length){
				if(!isServerFull(CurrentJobs[i])){
					if(getPremiumJob().getDownloadSize()!=-1){
						CurrentJobs[i] = getPremiumJob();
						setPremiumJob(new DownloadJob(-1));
					}
					else if(getRegularJob().getDownloadSize()!=-1){
						CurrentJobs[i] = getRegularJob();
						setRegularJob(new DownloadJob(-1));
					}
					else
						CurrentJobs[i].setDownloadSize(-1);
				}
				i++;
			}
			if(getPremiumJob().getDownloadSize()!=-1)
				premiumQ.enqueue(this.getPremiumJob());
			if(getRegularJob().getDownloadSize()!=-1)
				regularQ.enqueue(this.getRegularJob());
		}
		
		
		for(int j=0; j<CurrentJobs.length; j++){
			finishedJob[j] = new DownloadJob(-1);
			if(CurrentJobs[j].getDownloadSizeRemaining()<=0&&CurrentJobs[j].getDownloadSize()>0){
				finishedJob[j] = CurrentJobs[j];
				if(!premiumQ.isEmpty())
					CurrentJobs[j] = premiumQ.dequeue().getData();
				else if(!regularQ.isEmpty())
					CurrentJobs[j] = regularQ.dequeue().getData();
				else
					CurrentJobs[j].setDownloadSize(-1);
			}
		}
		System.out.println("	RegularQueue  : "+showRegularQ());
		System.out.println("	RegularQueue  : "+showPremiumQ());
		
		for(int j=0; j<CurrentJobs.length; j++){
			if(isServerFull(CurrentJobs[j])){
				result = result+"	Server "+(j+1) + ": [#"+CurrentJobs[j].getId()+": "+CurrentJobs[j].getDownloadSize()+"Mb total, "
						+CurrentJobs[j].getDownloadSizeRemaining()+"Mb remaining, Request Time : "+CurrentJobs[j].getTimeRequested()+", "+
						(CurrentJobs[j].getIsPremium()?"Premium]":"Regular]")+"\n";
				CurrentJobs[j].setDownloadSizeRemaining(CurrentJobs[j].getDownloadSizeRemaining()-downloadSpeed);
				CurrentJobs[j].setTakingTime(CurrentJobs[j].getTakingTime()+1);
			
			}
			else{
				
				result = result+"	Server "+(j+1)+": idle\n";
			}
		}
		
		
		for(int k=0; k<finishedJob.length; k++){
			if(finishedJob[k].getDownloadSize()!=-1){
				simulationEndTime = finishedJob[k].getTakingTime();
				
				if(finishedJob[k].getIsPremium()){
					result = result+"Job "+finishedJob[k].getId()+" finished,  Premium Job. "+finishedJob[k].getDownloadSize()+
								" Mb served, Total Wait : "+simulationEndTime+"\n";
					premiumEnd++;
					premiumWaitTime += simulationEndTime;
					premiumData += finishedJob[k].getDownloadSize();
				}
				else{
					result = result + "Job "+finishedJob[k].getId()+" finished,  Regular Job. "+finishedJob[k].getDownloadSize()+
							" Mb served, Total Wait : "+simulationEndTime+"\n";
					regularEnd++;
					regularWaitTime += simulationEndTime;
					regularData += finishedJob[k].getDownloadSize();
				}
			}
		}
		result = result+"----------------------------------------------------\n";
		return result;
	}
	/**
	 * 
	 * @return
	 */
	public int getPremiumEnd(){
		return premiumEnd;
	}
	/**
	 * 
	 * @return
	 */
	public int getRegularEnd(){
		return regularEnd;
	}
	/**
	 * 
	 * @return
	 */
	public int getPremiumData(){
		return premiumData;
	}
	/**
	 * 
	 * @return
	 */
	public int getRegularData(){
		return regularData;
	}
	/**
	 * 
	 * @return
	 */
	public int getPremiumWaitTime(){
		return premiumWaitTime;
	}
	/**
	 * 
	 * @return
	 */
	public int getRegularWaitTime(){
		return regularWaitTime;
	}
	
	
}

