package code;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This represents the data structure storing all the roads, nodes, and
 * segments, as well as some information on which nodes and segments should be
 * highlighted.
 * 
 * @author tony
 */
public class Graph {
	// map node IDs to Nodes.
	Map<Integer, Node> nodes = new HashMap<>();
	// map road IDs to Roads.
	Map<Integer, Road> roads;
	// just some collection of Segments.
	Collection<Segment> segments;

	Node highlightedStart, highlightedGoal;
	Collection<Road> highlightedRoads = new HashSet<>();

	public Graph(File nodes, File roads, File segments, File polygons) {
		this.nodes = Parser.parseNodes(nodes, this);
		this.roads = Parser.parseRoads(roads, this);
		this.segments = Parser.parseSegments(segments, this);
	}

	public void draw(Graphics g, Dimension screen, Location origin, double scale) {
		// a compatibility wart on swing is that it has to give out Graphics
		// objects, but Graphics2D objects are nicer to work with. Luckily
		// they're a subclass, and swing always gives them out anyway, so we can
		// just do this.
		Graphics2D g2 = (Graphics2D) g;

		// draw all the segments.
		g2.setColor(Mapper.SEGMENT_COLOUR);
		for (Segment s : segments)
			s.draw(g2, origin, scale);

		// draw the segments of all highlighted roads.
		g2.setColor(Mapper.HIGHLIGHT_COLOUR);
		g2.setStroke(new BasicStroke(3));
		for (Road road : highlightedRoads) {
			for (Segment seg : road.components) {
				seg.draw(g2, origin, scale);
			}
		}

		// draw all the nodes.
		g2.setColor(Mapper.NODE_COLOUR);
		for (Node n : nodes.values())
			n.draw(g2, screen, origin, scale);

		// draw the highlighted node, if it exists.
		if (highlightedStart != null) {
			g2.setColor(Mapper.HIGHLIGHT_COLOUR);
			highlightedStart.draw(g2, screen, origin, scale);
		}
		if (highlightedGoal != null) {
			g2.setColor(Mapper.HIGHLIGHT_COLOUR);
			highlightedGoal.draw(g2, screen, origin, scale);
		}
	}

	public void setHighlightStart(Node node) {
		for( Segment seg: segments) {
			seg.highlighted = false;
		}
		this.highlightedStart = node;
	}
	
	public void setHighlightGoal(Node node) {
		for (Segment seg : segments) {
			seg.highlighted = false;
		}
		this.highlightedGoal = node;
		List<AStarSearch> nodes = Mapper.search(highlightedStart, highlightedGoal);
		Set<Road> roads = new HashSet<>();
		for (int i = 0; i < nodes.size() - 1; i++){
			Node child = nodes.get(i).getNode();
			Node parent = nodes.get(i + 1).getNode();
			for (Segment s : child.segments) {
				boolean hasPrev = (s.start == parent || s.end == parent);
				if (hasPrev){
					s.highlighted = true;
					boolean hasRoad = false;
					for (Road road : roads){
						if (road.name.equals(s.road.name)){
							hasRoad = true;
						}
					}
					if (!hasRoad) {
						roads.add(s.road);
					}
					break;
				}
			}
		}
	}
	public void setHighlight(Collection<Road> roads) {
		this.highlightedRoads = roads;
	}
}

// code for COMP261 assignments