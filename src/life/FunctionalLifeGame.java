package life;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalLifeGame {
	
	private static Set<Point> points = new HashSet<>(Arrays.asList(new Point(1,1), new Point(1,2), new Point(1,3)));
	
	private static final Random rand = new Random();
	
	public static void main(final String[] args) throws Exception {
		
		points = Stream.generate(() -> new Point(rand.nextInt(1000000), rand.nextInt(1000000)))
		               .limit(10000)
		               .flatMap(t -> Stream.of(t, new Point(t.x, t.x + 1), new Point(t.x, t.x + 2)))
		               .collect(Collectors.toSet());
		
		System.out.println("Start points:" + points.size());
		
		int i = 0;
		final long start = System.currentTimeMillis();  
		for(; !points.isEmpty() && i < 10000; i++){
			points = nextGeneration(points).collect(Collectors.toSet());
			//System.out.println(points);
		}
		System.out.println("Total time:" + (System.currentTimeMillis() - start) + ", iterations count:" + i);
	}

	public static Stream<Point> nextGeneration(final Set<Point> alive){
		return alive.parallelStream()
				.flatMap(t -> Stream.concat(Stream.of(t), t.neighbors()))
				.distinct()
				.filter(t2 -> isEnoughNeighbors(t2, isAlive(alive, t2), t2.neighbors().filter(t1 -> isAlive(alive, t1)).count()));
	}
	
	private static boolean isAlive(final Set<Point> points, final Point p){
		return points.contains(p);
	}

	private static boolean isEnoughNeighbors(final Point p, final boolean isAlive, final long neigborsCount){
		return (isAlive && neigborsCount >= 2 && neigborsCount <= 3) || (!isAlive && neigborsCount == 3);
	}
}