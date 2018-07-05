
/* DHRUV PATEL    CS435    8596    mp */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Graph8596 {
	private Map<String, Node8596> nodes;
	private static int length ;

	private Graph8596() {
		nodes = new HashMap<String, Node8596>();
	}

	public static Graph8596 createGraph8596(File file,int initialvalue) {
		Graph8596 graph = new Graph8596();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String firstLine = reader.readLine();
			float length=Float.parseFloat(firstLine.split(" ")[0]);
			setLength8596((int)(length));
			Float initialPageRank = null;
			DecimalFormat df = new DecimalFormat("#.000000");
			
			switch (initialvalue) {
			case 0:
				initialPageRank=Float.parseFloat(df.format(0));
				break;
			case 1:
				initialPageRank=Float.parseFloat(df.format(1));
				break;
			case -1:
				initialPageRank=Float.parseFloat(df.format(1/length));
				break;
			case -2:
				initialPageRank=Float.parseFloat(df.format(1/Math.sqrt(length)));
				break;
			default:
				break;
			}
			String edge;
			int i = 0;
			while ((edge = reader.readLine()) != null) {
					String[] nodePair = edge.split(" ");
					String srcDesc = nodePair[0];
					String destDesc = nodePair[1];
					Node8596 srcNode = graph.getNode8596(srcDesc);
					if (srcNode == null) {
						srcNode = new Node8596(srcDesc);
						srcNode.setOutLink8596(destDesc);
						srcNode.setOldPageRank8596(initialPageRank);
						graph.nodes.put(srcDesc, srcNode);
					} else {
						srcNode.setOutLink8596(destDesc);
					}
					Node8596 destNode = graph.getNode8596(destDesc);
					if (destNode == null) {
						destNode = new Node8596(destDesc);
						destNode.setInLink8596(srcDesc);
						destNode.setOldPageRank8596(initialPageRank);
						graph.nodes.put(destDesc, destNode);
					} else {
						destNode.setInLink8596(srcDesc);
					}
				}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return graph;
	}

	public Map <String, Node8596> getNodes8596() {
		return nodes;
	}

	public Node8596 getNode8596(String nodeDesc) {
		return nodes.get(nodeDesc);
	}
	public void display8596(int iterations)
	{
		TreeMap<String, Node8596> treeMap = new TreeMap<String, Node8596>(nodes);
		Iterator<String> i = treeMap.keySet().iterator();
		int j =1;
		float sum = 0f;
		DecimalFormat df1 = new DecimalFormat("#.000000");
		int p;
		if(iterations== 0){
			p=999;
		}else{
			p=iterations;
		}
		float temp = nodes.get(i.next()).getOldPageRank8596();
		System.out.print("Base\t : 0"+" :");
		for(Map.Entry<String,Node8596> entry : treeMap.entrySet()) {
			String key = entry.getKey();
			Node8596 node = nodes.get(key);
			System.out.print("P["+node.getNodeDesc8596()+"] = "+df1.format(temp) +" ");
		}
		System.out.println();
		while(j<=p){
			if(j<=9){
				System.out.print("Iter\t : 0"+j+" :");
			}else{
				System.out.print("Iter\t : "+j+" :");
			}
			for(Map.Entry<String,Node8596> entry : treeMap.entrySet()) {
				String key = entry.getKey();
				sum =0f;
				Node8596 node = nodes.get(key);
				List<String> inLinkNodes = node.getInLinks8596();
				for (String inLinkNode : inLinkNodes) {
					Node8596 tempNode = nodes.get(inLinkNode);
					sum=sum+(tempNode.getOldPageRank8596()/tempNode.getOutLinks8596().size());
				}
				float newPageRank = 0.0375f+0.85f*(sum);
				node.setNewPageRank8596(Float.parseFloat(df1.format(newPageRank)));
				System.out.print("P["+node.getNodeDesc8596()+"] = "+df1.format(newPageRank) +" ");
			}
			System.out.println();
			j++;
			int m=0;
			for(Map.Entry<String,Node8596> entry : treeMap.entrySet()) {
				String key = entry.getKey();
				sum =0f;
				Node8596 node = nodes.get(key);
				if(node.getOldPageRank8596()-node.getNewPageRank8596()<0.0001){
					m++;
				}
				node.setOldPageRank8596(node.getNewPageRank8596());
			}
			if(m==getLength8596() && iterations == 0){
				break;
			}
		}
	}

	public static int getLength8596() {
		return length;
	}


	public static void setLength8596(int length) {
		Graph8596.length = length;
	}
}