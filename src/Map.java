import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Map {

    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();

    public void addPath(String source, String destination, int distance){

        //if the vertex doesn't exist (source AND destination), create it, add to vertices list

        if(!vertexExist(source)){
            //Create vertex object

            Vertex vertex = new Vertex(source);

            //Add it to vertices

            vertices.add(vertex);
        }

        //Create the edge and add it to it's list of edges

        Edge edge = new Edge(source, destination, distance);

        searchVertices(source).getEdges().add(edge);

        //Check if the destination Exists and if not create a vertex and add it to vertices

        if(!vertexExist(destination)){
            //Create vertex object

            Vertex d_vertex = new Vertex(destination);

            //Add it to vertices

            vertices.add(d_vertex);
        }

    }

    public void BFS(String source) {

        // I created a list of streets visited to check if visited

        ArrayList<String> visited = new ArrayList<String>(); //Number of Vertex

        // Create a queue for BFS

        LinkedList<String> queue = new LinkedList<String>();

        // Mark the current node as visited and enqueue it

        visited.add(source); //Put in visited List
        queue.add(source);

        int index = 0;
        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            int i = 0;
            int edge_num = 0;

            source = queue.poll();
            try{
                while(searchVertices(source).getEdges().size() > edge_num){
                    System.out.print((index+1)+ ") "+searchVertices(source).getLocation() + " is connected to " + searchVertices(source).getEdges().get(edge_num).getDestination() + " by " + searchVertices(source).getEdges().get(edge_num).getWeight() + "km\n");
                    edge_num++;index++;
                }
            }catch(Exception e){
                //This was last minute when reading over the assignment :(
            }


            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it

            if(searchVertices(source).getEdges().size() != 0){ //Ana Street is never a source
                while(i < searchVertices(source).getEdges().size()){
                    String current = searchVertices(source).getEdges().get(i).getDestination();

                    if (!isVisited(current, visited)) {
                        visited.add(current);
                        queue.add(current);
                    }
                    i++;
                }
            }
        }
        System.out.println("\n");
    }

    public void displayLocations(){
        int i = 0;
        while(vertices.size() > i){
            System.out.println((i+1)+ ") " +vertices.get(i).getLocation());
            i++;
        }
    }

    public String getSource(int index){
        return vertices.get(index-1).getLocation();
    }

    public boolean isVisited(String location, ArrayList<String> visited) {
        int i = 0;
        while (visited.size() > i) {
            if (visited.get(i).equals(location)) {
                return true;
            }i++;
        }
        return false;
    }

    public void Dijkstra(String source, String destination){ //Dijkstra Algorithm

        Vertex currentVertex;

        //Set source distance as 0

        searchVertices(source).setDistance(0);

        LinkedList<Vertex> vertexQueue = new LinkedList<Vertex>();
        int k = 0;
        while(vertices.size() > k){
            vertexQueue.add(vertices.get(k));
            k++;
        }

        //Loop until queue is empty

        while(vertexQueue.size() != 0){

            currentVertex = vertexQueue.poll();

            currentVertex = searchVertices(currentVertex.getLocation()); //Working from this list, Queue is to organize the order of operations

            int i = 0;

            //Places like Ana Street seem to have no parent

            while(currentVertex.getEdges().size() > i){

                if(currentVertex.getDistance() + currentVertex.getEdges().get(i).getWeight() < searchVertices(currentVertex.getEdges().get(i).getDestination()).getDistance()){
                    searchVertices(currentVertex.getEdges().get(i).getDestination()).setDistance(currentVertex.getDistance() + currentVertex.getEdges().get(i).getWeight());
                    searchVertices(currentVertex.getEdges().get(i).getDestination()).setParent(currentVertex.getLocation());
                    searchVertices(currentVertex.getEdges().get(i).getDestination());
                }

                i++;
            }
            //if(currentVertex.getEdges().get)
        }

        //Check the shortest distances and their parent

        Stack<String> pathList = new Stack<String>();
        Stack<Integer> distanceList = new Stack<Integer>();

        //Use stack and go backwards start from destination

        String nextPath = searchVertices(destination).getLocation();

        while(!searchVertices(nextPath).getParent().equals("")){
            pathList.push(nextPath);
            distanceList.push(searchVertices(nextPath).getDistance());

            nextPath = searchVertices(nextPath).getParent();

            //nextDistance = searchVertices(searchVertices(destination).getParent()).getDistance();
        }

        pathList.push(searchVertices(nextPath).getLocation());
        distanceList.push(searchVertices(nextPath).getDistance());

        int a = 0, b = 0, c = 0;

        System.out.print("Starting at " + pathList.pop() + " go to"); distanceList.pop();

        while(!pathList.isEmpty()){
            if(!distanceList.isEmpty()){
                b = distanceList.peek();
                c = b - a;
                a = distanceList.pop();
                //System.out.print()
            }
            System.out.print(", " + pathList.pop() + " in " + c + "km ");
        }
    }

    //Floyd–Warshall

    public void FloydWarshall(String source, String destination){
        int k, i, j = 0;

        int num_vertex = vertices.size(); //Set number of vertices
        int[][] distance = new int[num_vertex][num_vertex];

        int v = 0;
        int u = 0;

        while(v < num_vertex){
            distance[v][v] = 0;
            v++;
        }

        //Set weight for each Edge

        v = 0;

        while(v < num_vertex){
            while(u < num_vertex){
                distance[v][u] = getWeight(v, u); //Gets to edges based on index number then returns the weight
            }
        }

        //while

    }

    public int getIndexOfVertex(String vertex){
        int i = 0;
        while(i < vertices.size()){
            if(vertex.equals(vertices.get(i).getLocation())){
                return i;
            }
            i++;
        }
        return -1;
    }

    public int getWeight(int i, int j){

        //find out what index j is

        String destination = vertices.get(j).getLocation();

        //Search Vertex by index and check if they have a weight between i <- Source and j <- Destination

        //Loop vertex to see if it holds whatever index j is

        int y = 0;

        while(y < vertices.get(i).getEdges().size()) {
            if(destination.equals(vertices.get(i).getEdges().get(y).getDestination())){
                return vertices.get(i).getEdges().get(y).getWeight();
            }
            y++;
        }
        return Integer.MAX_VALUE;
    }

    public boolean isVertexVisited(Vertex vertex, ArrayList<Vertex> visited){
        int i = 0;
        while (visited.size() > i) {
            if (visited.get(i).getLocation().equals(vertex.getLocation())) {
                return true;
            }i++;
        }
        return false;
    }

    public Vertex searchVertices(String vertex){
        //Search List
        int i = 0;
        while(vertices.size() > i){

            if(vertices.get(i).getLocation().equals(vertex)){
                return vertices.get(i);
            }
            i++;
        }
        return null;
    }

    public boolean vertexExist(String vertex){
        //Search List
        int i = 0;
        while(vertices.size() > i){

            if(vertex.equals(vertices.get(i).getLocation())){
                return true;
            }
            i++;
        }
        return false;
    }
}

class Vertex{

    private String location;
    private int distance;
    private String parent;

    //List of Neighbors

    private ArrayList<Edge> edges;

    Vertex(String location){
        this.location = location;
        this.distance = Integer.MAX_VALUE;
        edges = new ArrayList<Edge>();
        this.parent = "";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}

class Edge{

    private String source, destination;
    private int weight;

    Edge(String source, String destination, int weight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}