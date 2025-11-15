package App;

import java.util.*;

public class ShortestPath {
    public static LinkedList<Integer> findPath(int start, int end) {
        int n = Points.PointsList.keySet().stream().max(Integer::compare).get() + 1;

        double[] dist = new double[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, Integer.MIN_VALUE);

        dist[start] = 0;

        PriorityQueue<Node> pQueue = new PriorityQueue<>(Comparator.comparingDouble(a -> a.cost));

        pQueue.add(new Node(start, dist[start]));

        Node current;
        while (!pQueue.isEmpty()) {
            current = pQueue.poll();

            if (visited[current.node]) continue;

            visited[current.node] = true;

            if (current.node == end) break;

            Node finalCurrent = current;
            getAvailableDirections(Points.PointsList.get(current.node)).forEach((next, weight) -> {
                if (finalCurrent.cost + weight < dist[next]) {
                    dist[next] = finalCurrent.cost + weight;
                    parent[next] = finalCurrent.node;
                    pQueue.add(new Node(next, dist[next]));
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

    private static Map<Integer, Double> getAvailableDirections(Points p) {
        Map<Integer, Double> dir = new HashMap<>();

        addIfValid(dir, p.getTop(),    p, false);
        addIfValid(dir, p.getBottom(), p, false);
        addIfValid(dir, p.getLeft(),   p, true);
        addIfValid(dir, p.getRight(),  p, true);

        return dir;
    }

    private static void addIfValid(Map<Integer, Double> dir, int next, Points p, boolean useX) {
        if (next >= 0) {
            Points np = Points.PointsList.get(next);
            dir.put(next, Math.abs(useX ? p.getX() - np.getX() : p.getY() - np.getY()));
        }
    }

    private record Node(int node, double cost) {
    }
}
