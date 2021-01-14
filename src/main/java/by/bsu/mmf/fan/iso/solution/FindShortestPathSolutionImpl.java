package by.bsu.mmf.fan.iso.solution;

import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.model.EdgeIntegerLengthData;
import by.bsu.mmf.fan.iso.tasks.FindShortestPathSolution;

import java.util.*;



public class FindShortestPathSolutionImpl implements FindShortestPathSolution {

    static class Pair implements Comparable<Pair> {
        private int vertId;
        private int dist;

        public Pair() {
        }

        public int getId() {
            return this.vertId;
        }

        public void setId(int id) {
            this.vertId = id;
        }

        public int getDist() {
            return this.dist;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }


        @Override
        public int compareTo(Pair node) {
            if(this.dist < node.dist) {
                return -1;
            }
            if(this.dist > node.dist) {
                return 1;
            }
            return 0;
        }
    }

    @Override
    public List<Integer> getShortestPath(
            ContiguousIntegerBasicGraph<EdgeIntegerLengthData> graph,
            int fromVertex,
            int toVertex
    ) {
        HashSet<Integer> visitedVertices = new HashSet<>();
        var size = graph.getNumberOfVertices();
        int[] dist = new int[size];
        int[] prev = new int[size];
        PriorityQueue<Pair> pairPriorityQueue = new PriorityQueue<>();
        var vertices = graph.getVertices();
        dist[fromVertex] = 0;
        for(var vert : vertices) {
            if(vert != fromVertex) {
                dist[vert] = Integer.MAX_VALUE / 2;
            }
            prev[vert] = -1;
            Pair pair = new Pair();
            pair.setDist(dist[vert]);
            pair.setId(vert);
            pairPriorityQueue.add(pair);
        }

        while (!pairPriorityQueue.isEmpty()) {

            Pair u = pairPriorityQueue.poll();

            while(visitedVertices.contains(u.getId()) && !pairPriorityQueue.isEmpty()) {
                u = pairPriorityQueue.poll();
            }

            if(!pairPriorityQueue.isEmpty()) {

                visitedVertices.add(u.getId());

                for (var vTemp : graph.getEdgesFromVertex(u.getId())) {
                    var v = vTemp.getTargetVertex();

                    var alt = dist[u.getId()] + graph.lookupEdge(u.getId(), v).getData().getLength();
                    if (alt < dist[v]) {
                        dist[v] = alt;
                        prev[v] = u.getId();
                        Pair pair = new Pair();
                        pair.setDist(alt);
                        pair.setId(v);
                        pairPriorityQueue.add(pair);
                    }

                }
            }else {
                break;
            }
        }

        List<Integer> toReturn = new LinkedList<>();
        var u = toVertex;
        if(prev[u] != -1 || u == fromVertex) {
            while (u != -1) {
                toReturn.add(0, u);
                u = prev[u];
            }
        }
        return toReturn;
    }
}
