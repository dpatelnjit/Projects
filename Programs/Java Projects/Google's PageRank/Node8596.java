
/* DHRUV PATEL    CS435    8596    mp */

import java.util.ArrayList;
import java.util.List;

public class Node8596 {
	private String nodeDesc;
	private List<String> inLinks;
	private List<String> outLinks;
	private float oldPageRank;
	private float newPageRank;
	
	public Node8596(String nodeDesc) {
		super();
		this.nodeDesc = nodeDesc;
		inLinks = new ArrayList<String>();
		outLinks = new ArrayList<String>();
	}
	
	public String getNodeDesc8596() {
		return nodeDesc;
	}
	
	public List<String> getInLinks8596() {
		return inLinks;
	}
	
	public List<String> getOutLinks8596() {
		return outLinks;
	}
	
	public void setOutLink8596(String destDesc)
	{
		this.outLinks.add(destDesc);
	}
	
	public void setInLink8596(String srcDesc)
	{
		this.inLinks.add(srcDesc);
	}
	
	public float getOldPageRank8596() {
		return oldPageRank;
	}
	
	public void setOldPageRank8596(float oldPageRank) {
		this.oldPageRank = oldPageRank;
	}
	
	public float getNewPageRank8596() {
		return newPageRank;
	}

	public void setNewPageRank8596(float newPageRank) {
		this.newPageRank = newPageRank;
	}
}
