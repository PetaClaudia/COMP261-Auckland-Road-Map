package code;

import java.util.List;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class AStarSearch {

	Node node;
	AStarSearch prevNode;
	private int NodeDepth;
	double gCost;
	double fCost;
	Set<AStarSearch> neighbours = new HashSet<AStarSearch>();
	
	public AStarSearch(Node node, AStarSearch prevNode, double gCost, double fCost) {
		this.node = node;
		this.prevNode = prevNode;
		this.gCost = gCost;
		this.fCost = fCost;
	}
	
	public Node getNode() {
		return node;
	}
	
	public double getGCost() {
		return gCost;
	}
	
	public double getFCost() {
		return fCost;
	}
	
	//find heuristic dist
	public double hCost(Node goalNode) {
		double xs = goalNode.location.x - this.node.location.x;
		double ys = goalNode.location.y - this.node.location.y;
		double xSqrd = xs*xs;
		double ySqrd = ys*ys;
		double dist = Math.sqrt(xSqrd + ySqrd);
		return dist;
	}
	
	public AStarSearch getPrev() {
		return prevNode;
	}
	
	public Set<AStarSearch> getNeighbours() {
		for( Segment seg : this.node.segments) {
			AStarSearch neighbStart = new AStarSearch(seg.start, this, gCost+seg.length, fCost);	//gCost and fCost are wrong
			AStarSearch neighbEnd = new AStarSearch(seg.end, this, gCost+seg.length, fCost);
			if (seg.start == this.getNode()) {
				neighbours.add(neighbEnd);
			}else {
				neighbours.add(neighbStart);
			}
		}
		return neighbours;
	}
	
	class SearchComparator implements Comparator<AStarSearch>{

		@Override
		public int compare(AStarSearch n1, AStarSearch n2) {
			if (n1.fCost<n2.fCost) {
				return -1;
			}
			else if (n1.fCost>n2.fCost) {
				return 1;
			}
			return 0;
		}
		
	}

	public int getNodeDepth() {
		return NodeDepth;
		
	}


	
	
	
	
	
}
