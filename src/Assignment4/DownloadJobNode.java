package Assignment4;

public class DownloadJobNode {
	private DownloadJob data;
	private DownloadJobNode link;
	
	/**
	 * This constructor initializes the indicator value by parameters
	 * And This sets the link as null. 
	 * @param d
	 * the object of DownloadJob
	 */
	DownloadJobNode(DownloadJob d){
		this.data = d;
		link = null;
	}
	
	public void setData(DownloadJob d){
		this.data = d;
	}
	public DownloadJob getData(){
		return data;
	}
	
	public void setLink(DownloadJobNode link){
		this.link = link;
	}
	
	public DownloadJobNode getLink(){
		return link;
	}
	
	
	
}
