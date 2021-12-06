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
        for (int v = 0; v < vertexCapacity; v++) {
            for (Vertex w : neighbours[v]) {
                o.append("arc: ").append(v + "-" + w.index + "\t cost: " + w.cost + ln);
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
        for (int i = 1; i < neighbours.length; i++) {
            vertices.add(new Vertex(Integer.MAX_VALUE, i));
        }

        while (true) {
            Vertex v = vertices.findSmallestUnknown();
            if (v == null) break;
            v.known = true;
            for (Vertex w : adj(v.index)) {
                /*1. Quel sera le nombre d’itération maximale et minimale pour la boucle suivante
                que vous avez implémenté dans le deuxième TODO:
                Le nombre d'itération maximale sera vertexCapacity-1 si le sommet v est adjacent à
                tous les autres sommets du graphe (il ne peux pas être ajdacent à lui-même). Le nombre
                d'itération minimale sera 0 si le sommet v n'a pas de noeuds adjacents.*/

                /*2. Dans le pire cas, quel sera le nombre de modification du coût pour un sommet?
                Le pire cas étant le cas qui cause le nombre de modifications au cout le plus élevé.
                Dans le pire cas, le sommet sera adjacent à tous les autres sommets du graphe et ces
                sommets seront parcourus dans l’ordre décroissant selon leur coût. Le nombre de modifications
                du coût pour un sommet dans ce pire cas sera, alors, vertexCapacity - 1.*/

                /* TODO Decrease the cost of the vertex in the Heap using decreaseKey if conditions are met */
                vertices.decreaseKey(w, v.cost + w.cost); //conditions are verified in decreaseKey method
            }
        }
        /*3. Quel sera le nombre d’itération pour la boucle que vous avez implémenté
        dans le troisième TODO selon le nombre de sommet suivant:
        a. 10 sommets : 10 itérations
        b. 100 sommets : 100 itérations
        c. 1000 sommets : 1000 itérations*/

        /*TODO Add up the total cost of the elements in the Heap */
        while (!vertices.isEmpty) {
            totalCost += vertices.poll().cost;
        }
        return totalCost;
    }
}
