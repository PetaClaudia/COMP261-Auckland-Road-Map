package code;

import java.util.ArrayDeque;

public class ArticulationPoints{
	
	private AStarSearch nodes;
	private int reach;
	private ArticulationPoints prev;
	private int pntDepth;
	private ArrayDeque<Node> children;
		
	public ArticulationPoints(AStarSearch firstNode, int reachBack, ArticulationPoints parent){
		this.nodes = firstNode;
		this.reach = reachBack;
		this.pntDepth = reachBack;
		this.prev = parent;
	}
	
	public int getPointdepth(){
		return pntDepth;
	}
	
	public void setPointdepth(int pntDepth){
		this.pntDepth = pntDepth;
	}
	
	public AStarSearch getNode(){
		return nodes;
	}

	public void setNodes(AStarSearch nodes){
		this.nodes = nodes;
	}
	
	public int getReachBack(){
		return reach;
	}
	
	public void setReachBack(int reachBack){
		this.reach = reachBack;
	}
	
	public ArticulationPoints getPrev(){
		return prev;
	}
	
	public void setPrev(ArticulationPoints prev){
		this.prev = prev;
	}
	
	public ArrayDeque<Node> children(){
		return this.children;
	}	
	
	public void setChildren(ArrayDeque<Node> children){
		this.children = children;
	}
}
