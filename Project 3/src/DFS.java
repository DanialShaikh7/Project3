import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DFS implements PathingStrategy{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new ArrayList<>();

        if (start == end) {
            path.add(0, start);
            return path;
        }

        for (Point p : potentialNeighbors.apply(start).filter(canPassThrough).collect(Collectors.toList())) {
            return computePath(p, end, canPassThrough, withinReach, potentialNeighbors);
        }

        return path;
    }
}
