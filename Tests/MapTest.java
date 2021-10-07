import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void shouldVisit(){
        Map graph = new Map();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Green");
        boolean result = graph.isVisited("Green", list);
        assertTrue(result);
    }

    @Test
    void shouldNotVisit(){
        Map graph = new Map();
        ArrayList<String> list = new ArrayList<String>();
        list.add("Green");
        boolean result = graph.isVisited("Ana", list);
        assertFalse(result);
    }

    @Test
    void shouldStoreVertexAndEdge(){
        Map graph = new Map();
        graph.addPath("Green Street", "Ana Street", 5);
        graph.addPath("Green Street", "James Street", 4);

        assertEquals(graph.searchVertices("Green Street").getEdges().get(0).getDestination(), "Ana Street");
    }




}