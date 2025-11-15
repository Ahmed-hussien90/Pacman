package App;

import java.util.*;

public class ShortestPath {
    public static LinkedList<Integer> findPath(int start, int end, int size, Callback callback) {
        int[] parent = new int[size];
        Arrays.fill(parent, Integer.MIN_VALUE);

        double[] dist = new double[size];
        Arrays.fill(dist, Integer.MAX_VALUE);

        boolean[] visited = new boolean[size];

        PriorityQueue<Node> pQueue = new PriorityQueue<>(Comparator.comparingDouble(a -> a.cost));

        dist[start] = 0;

        pQueue.add(new Node(start, dist[start]));

        while (!pQueue.isEmpty()) {
            Node current = pQueue.poll();

            if (visited[current.node]) continue;

            visited[current.node] = true;

            if (current.node == end) break;

            callback.calculateMinWeight(current).forEach(neighbour -> {
                if (current.cost + neighbour.cost < dist[neighbour.node]) {
                    dist[neighbour.node] = current.cost + neighbour.cost;
                    parent[neighbour.node] = current.node;
                    pQueue.add(new ShortestPath.Node(neighbour.node, dist[neighbour.node]));
                }
            });
        }

        LinkedList<Integer> path = new LinkedList<>();

        if (dist[end] == Integer.MAX_VALUE) return path;

        for (int i = end; i != Integer.MIN_VALUE; i = parent[i]) {
            path.addFirst(i);
        }

        return path;
    }

    public record Node(int node, double cost) {
    }

    public interface Callback {
        List<Node> calculateMinWeight(Node current);
    }
}
