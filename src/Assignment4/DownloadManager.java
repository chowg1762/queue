package Assignment4;
import java.util.Scanner;
import java.util.InputMismatchException;
public class DownloadManager {
	public static void main(String[] args){
		int numberOfServers=0;
		int downloadSpeed=0;
		int simulationLength=0;
		double probabilityOfPremium=0;
		double probabilityOfRegular=0;
		
	
		int numberOfPremiumJobs = 0;
		int numberOfRegularJobs = 0;
		int totalDownloadedSize = 0;
		int totalPremiumDownloadSize = 0;
		int totalRegularDownloadSize = 0;
		int totalPremiumDownloadTime = 0;
		int totalRegularDownloadTime = 0;
		
		DownloadRandomizer random;
		DownloadScheduler schedule;
		Scanner input = new Scanner(System.in);
		
		try{
		System.out.print("Please enter a number of servers : ");
		numberOfServers = input.nextInt();
		System.out.print("Please enter a download speed : ");
		downloadSpeed = input.nextInt();
		System.out.print("Please enter a length of time : ");
		simulationLength = input.nextInt();
		System.out.print("Please enter a probability of new premium job per timestep : ");
		probabilityOfPremium = input.nextDouble();
		System.out.print("Please enter a probability of new regular job per timestep : ");
		probabilityOfRegular = input.nextDouble();
		}
		catch(InputMismatchException ex){
			System.out.println("Your input type is wrong, please start the program again.");
			return ;
		}
		
		if(downloadSpeed<0||simulationLength<0||probabilityOfPremium<0||probabilityOfRegular<0){
			System.out.println("Inputs have to be greater or equal than 0");
			System.out.println("Please, start again this program.");
			return;
		}
		System.out.println("---------------------Simulation Starting-------------------");
		
		int count = 0;
		try{
		schedule = new DownloadScheduler(numberOfServers, downloadSpeed);
		}
		catch(IllegalArgumentException ex){
			System.out.println("The length of time must be greater than 0");
			System.out.println("Please, start again this program.");
			return;
		}
		int id = 1;
		
		while(count < simulationLength){
			System.out.println("Timestep : "+count);
			DownloadJob regularJob;
			DownloadJob premiumJob;
			random = new DownloadRandomizer(probabilityOfPremium, probabilityOfRegular);
			int regular = random.getRegular();
			int premium = random.getPremium();
			regularJob = new DownloadJob(regular);
			premiumJob = new DownloadJob(premium);
			
			if(regular!=-1){
				
				regularJob.setDownloadSize(regular);
				regularJob.setId(id);
				regularJob.setTimeRequeqsted(count);
				System.out.println("	New Regular Job : Job#"+regularJob.getId()+
						": Size : "+regular);
				id++;	
			}
			else
				System.out.println("	New Regular Job : n/a");
			
			
			schedule.setRegularJob(regularJob);
			
			
			if(premium!=-1){	
				premiumJob.setDownloadSize(premium);
				premiumJob.setId(id);
				premiumJob.setTimeRequeqsted(count);
				System.out.println("	New Premium Job : Job#"+premiumJob.getId()+
						": Size : "+premium);
				premiumJob.setIsPremium(true);
				id++;
				
			}
			else
				System.out.println("	New PremiumJob : n/a");
			
			schedule.setPremiumJob(premiumJob);
			
			
			System.out.print(schedule.simulate());
			numberOfPremiumJobs += schedule.getPremiumEnd();
			totalPremiumDownloadSize += schedule.getPremiumData();
			totalPremiumDownloadTime += schedule.getPremiumWaitTime();
			
			numberOfRegularJobs += schedule.getRegularEnd();
			totalRegularDownloadSize += schedule.getRegularData();
			totalRegularDownloadTime += schedule.getPremiumWaitTime();
			count++;
		}
		
		System.out.println("Simulation Ended : ");
		System.out.println("    Total Jobs served :" + numberOfPremiumJobs+numberOfRegularJobs);
		System.out.println("    Total Premium Jobs Served : "+numberOfPremiumJobs);
		System.out.println("    Total Regular Jobs Seved : "+numberOfRegularJobs);
		System.out.println("    Total Data Served : "+(totalPremiumDownloadSize+totalRegularDownloadSize));
		System.out.println("    Total Premium Data Served : "+totalPremiumDownloadSize);
		System.out.println("    Total Regular Data Served : "+totalRegularDownloadSize);
		System.out.println("    Average Premium Wait Time : "+(double)totalPremiumDownloadTime/numberOfPremiumJobs);
		System.out.println("    Average Regular Wait Time : "+(double)totalRegularDownloadTime/numberOfRegularJobs);
		System.out.println("-----------------Thank You-------------------");
	}
}
