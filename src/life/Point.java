package life;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Point implements Comparable<Point>{
	final int x;
	final int y;
	
	private List<Point> neighbors;
	
	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public void computeNeighbors(){
		if(neighbors == null){
			neighbors = neighborsList(this);
		}
	}
	
	public Stream<Point> neighbors(){
		computeNeighbors();
		return neighbors.stream();
	}
	
	public static List<Point> neighborsList(final Point p){
		return Arrays.asList(new Point(p.x + 1, p.y - 1),
				            new Point(p.x + 1, p.y),
				            new Point(p.x + 1, p.y + 1),
				            new Point(p.x, p.y - 1),
				            new Point(p.x, p.y + 1),
				            new Point(p.x - 1, p.y - 1),
				            new Point(p.x - 1, p.y),
				            new Point(p.x - 1, p.y + 1));
	}
	
	@Override
	public int compareTo(final Point o) {
		if (o.x < this.x) { return -1; }
		if (o.x > this.x) { return 1; }
		
		if (o.y < this.y) { return -1; }
		if (o.y > this.y) { return 1; }
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		final Point other = (Point) obj;
		return other.x == this.x && other.y == this.y;
	}

	@Override
	public String toString() {
		return "{" + x + "," + y + "}";
	}
}
