package App;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ShortestPath {
    public static LinkedList<Integer> findPath(int start, int end) {
        int n = Points.PointsList.keySet().stream().max(Integer::compare).get() +1;

        int[] dist = new int[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, Integer.MIN_VALUE);

        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        pq.add(new int[]{start, dist[start]});

        int[] curr;
        while (!pq.isEmpty()) {
            curr = pq.poll();
            int node = curr[0];
            int cost = curr[1];

            if (visited[node]) continue;

            visited[node] = true;

            if (node == end) break;

            Points.PointsList.get(node).getAvailableDirections().forEach((next, weight) -> {
                if (cost + weight < dist[next]) {
                    dist[next] = cost + weight;
                    parent[next] = node;
                    pq.add(new int[]{next, dist[next]});
                }
            });
        }

        LinkedList<Integer> path = new LinkedList<>();

        if (dist[end] == Integer.MAX_VALUE) return path;

        int i = end;
        while (i != Integer.MIN_VALUE) {
            path.addFirst(i);
            i = parent[i];
        }

        return path;
    }
}
