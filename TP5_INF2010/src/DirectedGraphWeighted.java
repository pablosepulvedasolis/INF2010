import java.util.HashSet;

public class DirectedGraphWeighted {
    public HashSet<Vertex>[] neighbours;
    public int vertexCapacity;
    public int edgeQuantity;

    /* TODO Initialize de DirectedGraph */
    public void initialize(int numNodes) {
        neighbours = new HashSet[numNodes];
        for(int i = 0; i < numNodes; i++) neighbours[i] = new HashSet();
        this.vertexCapacity = numNodes;
        edgeQuantity = 0;
    }

    /*TODO Create an edge between the vertices - Veuillez vous referez aux notes de cours */
    public void connect(int v1, Vertex vertex) {
        if (v1 < 0 || v1 >= vertexCapacity) return;
        if (vertex.index < 0 || vertex.index >= vertexCapacity) return;
        if (neighbours[v1].contains(vertex)) return;
        neighbours[v1].add(vertex);
        edgeQuantity++;
    }

    /* TODO Print all the edges connecting vertices*/
    public String toString(){
        StringBuilder o = new StringBuilder();
        String ln = System.getProperty("line.separator");
        o.append(vertexCapacity).append(ln).append(edgeQuantity).append(ln);
        for(int i = 0; i < neighbours.length; i++){
            for(Vertex vertex: neighbours[i]){
                o.append("arc: ").append(vertex.index).append("\tcoût: ").append(vertex.cost).append(ln);
            }
        }
        return o.toString();
    }

    /* TODO Return a HashMap of adjacent edges / vertices */
    public HashSet<Vertex> adj(int v) {
        return neighbours[v];
    }

    public DirectedGraphWeighted(int numNodes){
        initialize(numNodes);
    }

    public int findLowestCost() {
        /* NE PAS MODIFIER CE CODE */
        int totalCost = 0;

        Heap vertices = new Heap(vertexCapacity + 1);
        /* NE PAS MODIFIER CE CODE */

        /* TODO Add all of the vertices to the Heap start at Index 1. The default cost should be the largest possible value for an integer */
        for (int i = 1; i < vertexCapacity; i++) {
            vertices.add(new Vertex(Integer.MAX_VALUE, i));
        }

        while (true) {
            Vertex v = vertices.findSmallestUnknown();
            if (v == null) break;
            v.known = true;
            for (Vertex w : adj(v.index)) {
                /* TODO Decrease the cost of the vertex in the Heap using decreaseKey if conditions are met */
                if (!w.known) {
                    vertices.decreaseKey(w, v.cost + w.cost);
                }
            }
        }

        /*TODO Add up the total cost of the elements in the Heap */
        while (!vertices.isEmpty) {
            totalCost += vertices.poll().cost;
        }
        return totalCost;
    }
}
