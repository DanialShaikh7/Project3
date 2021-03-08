import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {



        Comparator<Point> compareF = (p1, p2) -> (int)(p1.f - p2.f);
        Comparator<Point> compareG = (p1, p2) -> (int)(p1.g - p2.g);

        HashMap<Integer, Point> closedList = new HashMap<>();
        Queue<Point> openList = new PriorityQueue<>(10, compareF.thenComparing(compareG));
        HashMap<Integer, Point> openListHash = new HashMap<>();
        List<Point> path = new LinkedList<>();

        Point currentNode = start;
        Point potentialNextNode = null;

        Predicate<Point> notInClosedList = p -> !(closedList.containsKey(p.hashCode()));

        openListHash.put(currentNode.hashCode(), null);

        while(!withinReach.test(currentNode, end)) {
            for (Point p : potentialNeighbors.apply(currentNode).filter(notInClosedList).filter(canPassThrough).collect(Collectors.toList())) {
                if (openListHash.get(p.hashCode()) == null) {
//                    p.setG(currentNode.g + calculateGStep(currentNode, p));
                    p.setG(currentNode.g + 1);
                    p.calculateF(end);
                    openListHash.put(p.hashCode(), currentNode);
                    openList.add(p);
                }

            }
            if (openList.isEmpty()) {
                break;
            }
            closedList.put(currentNode.hashCode(), openListHash.get(currentNode.hashCode()));
            openListHash.remove(currentNode.hashCode());
            currentNode = openList.remove();
        }

        if (withinReach.test(currentNode, end) == true) {
            closedList.put(currentNode.hashCode(), openListHash.get(currentNode.hashCode()));
            while (closedList.get(currentNode.hashCode()) != null) {
                path.add(0, currentNode);
                currentNode = closedList.get(currentNode.hashCode());
            }
        }

        return path;
    }


    private double calculateGStep(Point prev, Point current) {
        return Math.sqrt(Math.pow(prev.x - current.x, 2) + Math.pow(prev.y - current.y, 2));
    }



}

