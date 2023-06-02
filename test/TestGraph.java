import com.example.integrativetask_ii_ced.structure.graph.AdjacencyMatrixGraph;
import com.example.integrativetask_ii_ced.structure.graph.AdjencyListGraph;
import com.example.integrativetask_ii_ced.structure.graph.Edge;
import com.example.integrativetask_ii_ced.structure.graph.Vertex;
import com.example.integrativetask_ii_ced.structure.narytree.NaryTree;
import com.example.integrativetask_ii_ced.structure.narytree.Node;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


public class TestGraph {

    private static AdjencyListGraph<String> graph;
    private static AdjacencyMatrixGraph<String> graphAM;


    public void  setUpStage1(){
        graph = new AdjencyListGraph<>(false, false);
        graph.insertVertex("S");
    }

    public void setUpStage2NoDirected(){
        graph = new AdjencyListGraph<>(false, false);
        graph.insertVertex("A");
        graph.insertVertex("B");
    }
    public void setUpStage3Directed(){
        graph = new AdjencyListGraph<>(true, false);
        graph.insertVertex("A");
        graph.insertVertex("B");
    }
    public void setUpStage4NoDirected(){
        graph = new AdjencyListGraph<>(false, false);
        graph.insertVertex("V");
        graph.insertVertex("R");
        graph.insertVertex("S");
        graph.insertVertex("W");
        graph.insertVertex("T");
        graph.insertVertex("X");
        graph.insertVertex("U");
        graph.insertVertex("Y");
        //Insertions:
        graph.insertEdge("V","R");
        graph.insertEdge("S","R");
        graph.insertEdge("W","S");
        graph.insertEdge("T","W");
        graph.insertEdge("W","X");
        graph.insertEdge("X","T");
        graph.insertEdge("U","T");
        graph.insertEdge("X","U");
        graph.insertEdge("X","Y");
        graph.insertEdge("U","Y");
    }

    public void setUpStage4NoDirectedAM(){
        graphAM = new AdjacencyMatrixGraph<>(false, false);
        graphAM.insertVertex("V");
        graphAM.insertVertex("R");
        graphAM.insertVertex("S");
        graphAM.insertVertex("W");
        graphAM.insertVertex("T");
        graphAM.insertVertex("X");
        graphAM.insertVertex("U");
        graphAM.insertVertex("Y");
        //Insertions:
        graphAM.insertEdge("V","R");
        graphAM.insertEdge("S","R");
        graphAM.insertEdge("W","S");
        graphAM.insertEdge("T","W");
        graphAM.insertEdge("W","X");
        graphAM.insertEdge("X","T");
        graphAM.insertEdge("U","T");
        graphAM.insertEdge("X","U");
        graphAM.insertEdge("X","Y");
        graphAM.insertEdge("U","Y");
    }

    public void setUpStage5(){
        graph = new AdjencyListGraph<>(false, false);
        graph.insertVertex("S");
        graph.insertVertex("R");
        graph.insertVertex("W");
        graph.insertEdge("S","W");
        graph.insertEdge("S","R");

    }
    public void setUpStage5AM(){
        graphAM = new AdjacencyMatrixGraph<>(false, false);
        graphAM.insertVertex("S");
        graphAM.insertVertex("R");
        graphAM.insertVertex("W");
        graphAM.insertEdge("S","W");
        graphAM.insertEdge("S","R");

    }

    public void  setUpStage6(){
        graph = new AdjencyListGraph<>(false, false);
        graph.insertVertex("S");
        graph.deleteVertex("S");
    }

    public void setUpStage7Directed(){
        graph = new AdjencyListGraph<>(true, false);
        graph.insertVertex("V");
        graph.insertVertex("R");
        graph.insertVertex("S");
        graph.insertVertex("H");
        graph.insertVertex("L");
        //Insertions:
        graph.insertEdge("V","R");
        graph.insertEdge("S","R");
        graph.insertEdge("H","L");

    }

    // Weight Graph set up

    public void setUpStage8DirecetedAndWeight(){
        graph = new AdjencyListGraph<>(true, true);
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");

    }

    public void setUpStage9DirecetedAndWeight(){
        graph = new AdjencyListGraph<>(true, true);
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertWeightedEdge("A","B",10);
        graph.insertWeightedEdge("C","A",15);

    }

    public void setUpWeightedGraphWithCycle(){
        graph = new AdjencyListGraph<>(false, true);
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("Z");
        graph.insertWeightedEdge("A","B",4);
        graph.insertWeightedEdge("A","C",2);
        graph.insertWeightedEdge("B","D",5);
        graph.insertWeightedEdge("B","C",1);
        graph.insertWeightedEdge("C","D",8 );
        graph.insertWeightedEdge("C","E",10);
        graph.insertWeightedEdge("E","D",2);
        graph.insertWeightedEdge("E","Z",3);
        graph.insertWeightedEdge("D","Z",6);

    }

    public void setUpStage10AMGNoDirected(){
        graphAM = new AdjacencyMatrixGraph<>(false, false);
        graphAM.insertVertex("V");
        graphAM.insertVertex("R");
        graphAM.insertVertex("S");
        graphAM.insertVertex("W");
        graphAM.insertVertex("T");
        graphAM.insertVertex("X");
        graphAM.insertVertex("U");
        graphAM.insertVertex("Y");
        //Insertions:
        graphAM.insertEdge("V","R");
        graphAM.insertEdge("S","R");
        graphAM.insertEdge("W","S");
        graphAM.insertEdge("T","W");
        graphAM.insertEdge("W","X");
        graphAM.insertEdge("X","T");
        graphAM.insertEdge("U","T");
        graphAM.insertEdge("X","U");
        graphAM.insertEdge("X","Y");
        graphAM.insertEdge("U","Y");
    }

    public void setUpStage9NoDirectedAndWeightAM() {
        graphAM = new AdjacencyMatrixGraph<>(false, true);
        graphAM.insertVertex("A");
        graphAM.insertVertex("B");
        graphAM.insertVertex("C");
        graphAM.insertEdge("A", "B", 10.0);
        graphAM.insertEdge("C", "A", 15.0);

    }

    public void setupKruskal1() {
        graphAM = new AdjacencyMatrixGraph<String>(false, true);

        graphAM.insertVertex("A");
        graphAM.insertVertex("B");
        graphAM.insertVertex("C");
        graphAM.insertVertex("D");
        graphAM.insertVertex("E");
        graphAM.insertVertex("Z");

        graphAM.insertEdge("A", "B", 4.0);
        graphAM.insertEdge("A", "C", 2.0);
        graphAM.insertEdge("B", "D", 5.0);
        graphAM.insertEdge("B", "C", 1.0);
        graphAM.insertEdge("C", "D", 8.0);
        graphAM.insertEdge("C", "E", 10.0);
        graphAM.insertEdge("D", "E", 2.0);
        graphAM.insertEdge("D", "Z", 6.0);
        graphAM.insertEdge("E", "Z", 3.0);

    }

    public void setupFloyd() {
        graphAM = new AdjacencyMatrixGraph<>(false, true);
        graphAM.insertVertex("A");
        graphAM.insertVertex("B");
        graphAM.insertVertex("C");
        graphAM.insertVertex("D");
        graphAM.insertVertex("E");
        graphAM.insertVertex("F");
        graphAM.insertVertex("G");


        graphAM.insertEdge("A", "B", 3.0);
        graphAM.insertEdge("A", "C", 5.0);
        graphAM.insertEdge("B", "D", 1.0);
        graphAM.insertEdge("B", "E", 8.0);
        graphAM.insertEdge("C", "F", 12.0);
        graphAM.insertEdge("C", "G", 5.0);
    }

    public void setUpStage9NoDirectedAndWeight() {
        graph = new AdjencyListGraph<>(false, true);
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertWeightedEdge("A", "B", 10);
        graph.insertWeightedEdge("C", "A", 15);

    }
    // Dijkstra testing

    @Test
    public void dijkstraForGraphWithCycle(){
        setUpWeightedGraphWithCycle();
        Map distance = graph.dijkstra("A")[0];

        Map prev = graph.dijkstra("A")[1];
        assertEquals(13,distance.get("Z"));
        assertEquals(0,distance.get("A"));
        assertEquals(3,distance.get("B"));
        assertEquals(2,distance.get("C"));
        assertEquals(8,distance.get("D"));
        assertEquals(10,distance.get("E"));

        String chain = "";
        String init = "Z";
        Stack stack = new Stack<>();
        stack.add(init);

        while(prev.get(init) != null){

            stack.add(prev.get(init));
            init = (String) prev.get(init);
        }
        while(!stack.isEmpty()){
            chain+= stack.pop() + " ";
        }
        assertEquals("A C B D E Z", chain.trim());
    }


    //Weight Graph testing

    @Test
    public void directedAndWeightWithNotEdges(){
        setUpStage9DirecetedAndWeight();
        assertEquals(10, graph.getWeightedMatrix().get("A").get("B"));
        assertEquals(15, graph.getWeightedMatrix().get("C").get("A"));
        assertEquals(Integer.MAX_VALUE, graph.getWeightedMatrix().get("A").get("C"));
        assertEquals(Integer.MAX_VALUE, graph.getWeightedMatrix().get("C").get("B"));
        assertEquals(0, graph.getWeightedMatrix().get("A").get("A"));
        assertEquals(0, graph.getWeightedMatrix().get("C").get("C"));
        assertEquals(0, graph.getWeightedMatrix().get("B").get("B"));
    }

    @Test
    public void directedAndWeightWithEdges(){
        setUpStage9DirecetedAndWeight();
        assertEquals(10, graph.getWeightedMatrix().get("A").get("B"));
        assertEquals(15, graph.getWeightedMatrix().get("C").get("A"));
        assertEquals(Integer.MAX_VALUE, graph.getWeightedMatrix().get("C").get("B"));
        assertEquals(Integer.MAX_VALUE, graph.getWeightedMatrix().get("B").get("A"));
        assertEquals(Integer.MAX_VALUE, graph.getWeightedMatrix().get("A").get("C"));
        assertEquals(0, graph.getWeightedMatrix().get("A").get("A"));
        assertEquals(0, graph.getWeightedMatrix().get("C").get("C"));
        assertEquals(0, graph.getWeightedMatrix().get("B").get("B"));
    }

    @Test
    public void insertionDirectToRoot(){
        setUpStage6();
        assertEquals(0,graph.getVertexes().size());
    }

    @Test
    public void  insertEdge(){
        setUpStage2NoDirected();
        graph.insertEdge("A","B");
        assertEquals(2,graph.getVertexes().size());
        assertEquals("B",graph.getVertexes().get(0).getAdjacency().get(0).getValue());
        assertEquals("A",graph.getVertexes().get(1).getAdjacency().get(0).getValue());
    }

    @Test
    public void insertEdgeDirected(){
        setUpStage3Directed();
        graph.insertEdge("A","B");
        assertEquals(2,graph.getVertexes().size());
        assertEquals("B",graph.getVertexes().get(0).getAdjacency().get(0).getValue());
        assertEquals(0,graph.getVertexes().get(1).getAdjacency().size());
    }

    @Test
    public void deleteDirectToRoot(){
        setUpStage1();
        assertEquals(1,graph.getVertexes().size());
        assertEquals("S", graph.getVertexes().get(0).getValue());
    }

    @Test
    public void  deleteEdge(){
        setUpStage2NoDirected();
        graph.insertEdge("A","B");
        assertEquals(2,graph.getVertexes().size());
        graph.deleteEdge("A","B");
        assertEquals(0, graph.getVertexes().get(0).getAdjacency().size());
        assertEquals(0,graph.getVertexes().get(1).getAdjacency().size());
    }

    @Test
    public void deleteEdgeDirected(){
        setUpStage3Directed();
        graph.insertEdge("A","B");
        assertEquals(2,graph.getVertexes().size());
        assertEquals("B",graph.getVertexes().get(0).getAdjacency().get(0).getValue());
        assertEquals(0,graph.getVertexes().get(1).getAdjacency().size());
        graph.deleteEdge("A","B");
        assertEquals(0, graph.getVertexes().get(0).getAdjacency().size());

    }

    @Test
    public void adjacencyTest(){
        setUpStage5();
        assertEquals("W",graph.getVertexes().get(0).getAdjacency().get(0).getValue());
        assertEquals("R",graph.getVertexes().get(0).getAdjacency().get(1).getValue());
        assertEquals("S",graph.getVertexes().get(1).getAdjacency().get(0).getValue());
    }


    //BFS

    @Test
    public void bfsSearchForOneNode(){
        setUpStage4NoDirected();
        List result = graph.bfsForOneNode("S", "U");
        String chain  = "";
        for (int i = result.size()-1 ; i > -1; i--) {
            chain += result.get(i) + " ";
        }
        assertEquals("S W T U", chain.trim());
    }
    @Test
    public void bfsSearchForOneNode2(){
        setUpStage4NoDirected();
        List result = graph.bfsForOneNode("R", "V");
        String chain  = "";
        for (int i = result.size()-1 ; i > -1; i--) {
            chain += result.get(i) + " ";
        }
        assertEquals("R V", chain.trim());
    }

    @Test
    public void bfsSearchForOneNode3(){
        setUpStage4NoDirected();
        List result = graph.bfsForOneNode("Y", "V");
        String chain  = "";
        for (int i = result.size()-1 ; i > -1; i--) {
            chain += result.get(i) + " ";
        }
        assertEquals("Y X W S R V", chain.trim());
    }

    @Test
    public void bfsSearchForOneNode4(){
        setUpStage4NoDirected();
        List result = graph.bfsForOneNode("W", "U");
        String chain  = "";
        for (int i = result.size()-1 ; i > -1; i--) {
            chain += result.get(i) + " ";
        }
        assertEquals("W T U", chain.trim());
    }

    @Test
    public void bfsTreeConstruction(){
        setUpStage4NoDirected();
        String test = "";
        for (Node n:graph.bfs("S").preOrder()) {
            test += n.getElement() + " ";
        }
        assertEquals("S R V W T U X Y " , test);

    }

    @Test
    public void bfsTreeConstructionLevels(){
        setUpStage4NoDirected();
        String test = "";
        for (Node n:graph.bfs("S").postOrder()) {
            test += n.getElement() + " ";
        }
        assertEquals("V R U T Y X W S " , test);

    }


    @Test
    public void dfsForOneNodeSearch(){
        setUpStage5();
        List<String> list = graph.dfsForOneNode("S","R");
        String chain = "";
        for (String a: list
             ) {
            chain += a +" ";
        }
        assertEquals("R S", chain.trim());

    }
    @Test
    public void dfsForOneNodeSearch1(){
        setUpStage4NoDirected();
        List<String> list = graph.dfsForOneNode("V","U");
        String chain = "";
        for (String a: list
        ) {
            chain += a +" ";
        }
        assertEquals("U X T W S R V", chain.trim());

    }

    @Test
    public void dfsForOneNodeSearch2(){
        setUpStage4NoDirected();
        List<String> list = graph.dfsForOneNode("Y","X");
        String chain = "";
        for (String a: list
        ) {
            chain += a +" ";
        }
        assertEquals("X U Y", chain.trim());

    }

    @Test
    public void dfsOneNodeSearch4(){
        setUpStage4NoDirected();
        List<String> list = graph.dfsForOneNode("T","Y");
        String chain = "";
        for (String a: list
        ) {
            chain += a +" ";
        }
        assertEquals("Y U X T", chain.trim());
    }


    @Test
    public void dfsTreeConstruction(){
        setUpStage5();
        String test = "";
        for (Node n:graph.dfs().get(0).postOrder()) {
            test += n.getElement() + " ";
        }
        assertEquals("W R S " , test);

    }

    @Test
    public void dfsTreeConstructionLevels(){
        setUpStage4NoDirected();
        String test = "";
        for (Node n:graph.dfs().get(0).postOrder()) {
            test += n.getElement() + " ";
        }
        assertEquals("Y U X T W S R V " , test);
    }

    @Test
    public void dfsBasicTreeConstructionPreOrder(){
        setUpStage5();
        String test = "";
        for (Node n:graph.dfs().get(0).preOrder()) {
            test += n.getElement() + " ";
        }
        assertEquals("S W R " , test);
    }

    @Test
    public void dfsBigTreeConstructionPreOrder(){
        setUpStage4NoDirected();
        String test = "";
        for (Node n:graph.dfs().get(0).preOrder()) {
            test += n.getElement() + " ";
        }
        assertEquals("V R S W T X U Y " , test);
    }

    @Test
    public void dfsForestConstructionPreOrder1(){
        setUpStage7Directed();
        String test1 = "";
        for (Node n:graph.dfs().get(0).preOrder()) {
            test1 += n.getElement() + " ";
        }

        assertEquals(3, graph.dfs().size());
        assertEquals(2, graph.dfs().get(0).weight());
        assertEquals("V R " , test1);
    }
    @Test
    public void dfsForestConstructionPreOrder2(){
        setUpStage7Directed();
        String test2 = "";
        for (Node n:graph.dfs().get(1).preOrder()) {
            test2 += n.getElement() + " ";
        }
        assertEquals(3, graph.dfs().size());
        assertEquals(1, graph.dfs().get(1).weight());
        assertEquals("S " , test2);
    }
    @Test
    public void dfsForestConstructionPreOrder3(){
        setUpStage7Directed();
        String test3 = "";
        for (Node n:graph.dfs().get(2).preOrder()) {
            test3 += n.getElement() + " ";
        }
        assertEquals(3, graph.dfs().size());
        assertEquals(2, graph.dfs().get(2).weight());
        assertEquals("H L " , test3);
    }

    @Test
    public void bfsForestConstructionPreOrder1(){
        setUpStage7Directed();
        String test1 = "";
        for (Node n: graph.bfs("V").preOrder()) {
            test1 += n.getElement() + " ";
        }
        assertEquals(2, graph.bfs("V").weight());
        assertEquals("V R " , test1);
    }
    @Test
    public void bfsForestConstructionPreOrder2(){
        setUpStage7Directed();
        String test2 = "";
        for (Node n:graph.bfs("R").preOrder()) {
            test2 += n.getElement() + " ";
        }
        assertEquals(1, graph.bfs("R").weight());
        assertEquals("R " , test2);
    }

    @Test
    public void bfsForestConstructionPreOrder3(){
        setUpStage7Directed();
        String test2 = "";
        for (Node n:graph.bfs("H").preOrder()) {
            test2 += n.getElement() + " ";
        }
        assertEquals(2, graph.bfs("H").weight());
        assertEquals("H L " , test2);
    }

    @Test
    public void bfsSearchForOneNodeAM(){
        setUpStage10AMGNoDirected();
        List<String> result = graphAM.bfsSingleNode("S", "U");
        String chain  = "";
        for (int i = result.size()-1 ; i > -1; i--) {
            chain += result.get(i) + " ";
        }
        assertEquals("S W T U", chain.trim());
    }
    @Test
    public void bfsSearchForOneNode2AM(){
        setUpStage10AMGNoDirected();
        List<String> result = graphAM.bfsSingleNode("R", "V");
        String chain  = "";
        for (int i = result.size()-1 ; i > -1; i--) {
            chain += result.get(i) + " ";
        }
        assertEquals("R V", chain.trim());
    }

    @Test
    public void bfsSearchForOneNode3AM(){
        setUpStage10AMGNoDirected();
        List<String> result = graphAM.bfsSingleNode("Y", "V");
        String chain  = "";
        for (int i = result.size()-1 ; i > -1; i--) {
            chain += result.get(i) + " ";
        }
        assertEquals("Y X W S R V", chain.trim());
    }

    @Test
    public void bfsSearchForOneNode4AM(){
        setUpStage10AMGNoDirected();
        List<String> result = graphAM.bfsSingleNode("W", "U");
        String chain  = "";
        for (int i = result.size()-1 ; i > -1; i--) {
            chain += result.get(i) + " ";
        }
        assertEquals("W T U", chain.trim());
    }

    @Test
    public void dfsForOneNodeSearchAM(){
        setUpStage5AM();
        List<String> list = graphAM.dfsForOneNode("S","R");
        String chain = "";
        for (String a: list
        ) {
            chain += a +" ";
        }
        assertEquals("R S", chain.trim());

    }

    @Test
    public void dfsForOneNodeSearch1AM(){
        setUpStage4NoDirectedAM();
        List<String> list = graphAM.dfsForOneNode("V","U");
        String chain = "";
        for (String a: list
        ) {
            chain += a +" ";
        }


        ArrayList<Node> ch2 = graphAM.dfs().get(0).preOrder();
        String chain2 = "";
        for (Node a: ch2
        ) {
            chain2 += ((String)a.getElement()) +" ";
        }

        ArrayList<Node> ch3 = graphAM.dfs().get(0).postOrder();
        String chain3 = "";
        for (Node a: ch3
        ) {
            chain3 += ((String)a.getElement()) +" ";
        }
        assertEquals("U X T W S R V", chain.trim());
        assertEquals("V R S W T X U Y", chain2.trim());
        assertEquals("Y U X T W S R V", chain3.trim());

    }

    @Test
    public void dfsForOneNodeSearch2AM(){
        //Here is missing further testing
        setUpStage4NoDirectedAM();
        List<String> list = graphAM.dfsForOneNode("Y","X");
        String chain = "";
        for (String a: list
        ) {
            chain += a +" ";
        }
        assertEquals("X U Y", chain.trim());

    }

    @Test
    public void dfsForOneNodeSearch3AM(){
        setUpStage4NoDirectedAM();
        List<String> list = graphAM.dfsForOneNode("V","W");
        String chain = "";
        for (String a: list
        ) {
            chain += a +" ";
        }
        assertEquals("W S R V", chain.trim());
    }

    @Test
    public void dfsOneNodeSearch4AM(){
        //Here is missing further testing
        setUpStage4NoDirectedAM();
        List<String> list = graphAM.dfsForOneNode("T","Y");
        String chain = "";
        for (String a: list
        ) {
            chain += a +" ";
        }
        assertEquals("Y U X T", chain.trim());
    }


    @Test
    public void testKruskal1() {
        setupKruskal1();
        ArrayList<Edge<String>> paths = graphAM.kruskal();
        String chain = "";
        String result = "B C A C D E E Z B D";
        for (int i = 0; i < paths.size(); i++) {
            chain += paths.get(i).getFrom().getValue() + " " + paths.get(i).getTo().getValue() + " ";
        }
        assertEquals(result, chain.trim());
    }

    @Test
    public void testKruskal2() {
        setUpWeightedGraphWithCycle();

        ArrayList<Edge<String>> paths = graph.kruskal();
        String chain = "";
        for (int i = 0; i < paths.size(); i++) {
            chain += paths.get(i).getFrom().getValue() + " " + paths.get(i).getTo().getValue() + " ";
        }
        assertEquals("B C A C E D E Z B D", chain.trim());
    }

    @Test
    public void testKruskal3() {
        setUpStage9NoDirectedAndWeight();
        ArrayList<Edge<String>> paths = graph.kruskal();
        String chain = "";
        for (int i = 0; i < paths.size(); i++) {
            chain += paths.get(i).getFrom().getValue() + " " + paths.get(i).getTo().getValue() + " ";
        }
        assertEquals("A B C A", chain.trim());
    }

    @Test
    public void testPrim1() {
        setupKruskal1();
        String[] expectedResult = {"A", "C", "B", "D", "E", "Z"};
        //NaryTree<Vertex<String>> result = graphAM.prim("A");
        NaryTree<Vertex<String>> result = graphAM.prim();
        ArrayList<Node> resultPreOrder = result.preOrder();
        for (int i = 0; i < resultPreOrder.size(); i++) {
            assertEquals(expectedResult[i], ((Vertex) resultPreOrder.get(i).getElement()).getValue());
        }

    }

    @Test
    public void testPrim1_1() {
        setupKruskal1();
        String[] expectedResult = {"A", "C", "B", "D", "E", "Z"};
        //NaryTree<Vertex<String>> result = graphAM.prim("A");
        NaryTree<Vertex<String>> result = graphAM.prim("A");
        ArrayList<Node> resultPreOrder = result.preOrder();
        for (int i = 0; i < resultPreOrder.size(); i++) {
            assertEquals(expectedResult[i], ((Vertex) resultPreOrder.get(i).getElement()).getValue());
        }

    }

    @Test
    public void testPrim2() {
        setUpWeightedGraphWithCycle();
        String[] expectedResult = {"A", "C", "B", "D", "E", "Z"};
        NaryTree<Vertex<String>> result = graph.prim("A");
        ArrayList<Node> resultPreOrder = result.preOrder();
        for (int i = 0; i < resultPreOrder.size(); i++) {
            assertEquals(expectedResult[i], ((Vertex) resultPreOrder.get(i).getElement()).getValue());
        }

    }

    @Test
    public void testPrim3() {
        setUpWeightedGraphWithCycle();
        String[] expectedResult = {"D", "B", "C", "A", "E", "Z"};
        NaryTree<Vertex<String>> result = graph.prim("D");
        ArrayList<Node> resultPreOrder = result.preOrder();
        for (int i = 0; i < resultPreOrder.size(); i++) {
            assertEquals(expectedResult[i], ((Vertex) resultPreOrder.get(i).getElement()).getValue());
        }

    }

    // Dijkstra testing

    @Test
    public void dijkstraForGraphWithCycleA() {
        setUpWeightedGraphWithCycle();
        Map distance = graph.dijkstra("A")[0];

        Map prev = graph.dijkstra("A")[1];
        assertEquals(13, distance.get("Z"));
        assertEquals(0, distance.get("A"));
        assertEquals(3, distance.get("B"));
        assertEquals(2, distance.get("C"));
        assertEquals(8, distance.get("D"));
        assertEquals(10, distance.get("E"));

        String chain = "";
        String init = "Z";
        Stack stack = new Stack<>();
        stack.add(init);

        while (prev.get(init) != null) {

            stack.add(prev.get(init));
            init = (String) prev.get(init);
        }
        while (!stack.isEmpty()) {
            chain += stack.pop() + " ";
        }
        assertEquals("A C B D E Z", chain.trim());
    }

    @Test
    public void dijkstraForGraphWithCycleZ() {
        setUpWeightedGraphWithCycle();
        Map distance = graph.dijkstra("Z")[0];

        Map prev = graph.dijkstra("A")[1];
        assertEquals(0, distance.get("Z"));
        assertEquals(13, distance.get("A"));
        assertEquals(10, distance.get("B"));
        assertEquals(11, distance.get("C"));
        assertEquals(5, distance.get("D"));
        assertEquals(3, distance.get("E"));

        String chain = "";
        String init = "Z";
        Stack stack = new Stack<>();
        stack.add(init);

        while (prev.get(init) != null) {

            stack.add(prev.get(init));
            init = (String) prev.get(init);
        }
        while (!stack.isEmpty()) {
            chain += stack.pop() + " ";
        }
        assertEquals("A C B D E Z", chain.trim());
    }

    @Test
    public void dijkstraForGraphWithCycleD() {
        setUpWeightedGraphWithCycle();
        Map distance = graph.dijkstra("D")[0];

        Map prev = graph.dijkstra("A")[1];
        assertEquals(5, distance.get("Z"));
        assertEquals(8, distance.get("A"));
        assertEquals(5, distance.get("B"));
        assertEquals(6, distance.get("C"));
        assertEquals(0, distance.get("D"));
        assertEquals(2, distance.get("E"));

        String chain = "";
        String init = "Z";
        Stack stack = new Stack<>();
        stack.add(init);

        while (prev.get(init) != null) {

            stack.add(prev.get(init));
            init = (String) prev.get(init);
        }
        while (!stack.isEmpty()) {
            chain += stack.pop() + " ";
        }
        assertEquals("A C B D E Z", chain.trim());
    }

    //Floyd Testing

    @Test
    public void testFloydWarshall1() {
        setupFloyd();
        double[][] expectedResult = {
                {0, 3, 5, 4, 11, 17, 10, Double.MAX_VALUE},
                {3, 0, 8, 1, 8, 20, 13, Double.MAX_VALUE},
                {5, 8, 0, 9, 16, 12, 5, Double.MAX_VALUE},
                {4, 1, 9, 0, 9, 21, 14, Double.MAX_VALUE},
                {11, 8, 16, 9, 0, 28, 21, Double.MAX_VALUE},
                {17, 20, 12, 21, 28, 0, 17, Double.MAX_VALUE},
                {10, 13, 5, 14, 21, 17, 0, Double.MAX_VALUE},
                {Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, 0}
        };
        double[][] result = graphAM.floydWarshall();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                assertEquals(expectedResult[i][j], result[i][j], 0);
            }
        }
    }

    @Test
    public void testFloydWarshall2(){
        setupKruskal1();
        double[][] expectedResult = {
                {0, 3, 2, 8, 10, 13},
                {3, 0, 1, 5, 7, 10},
                {2, 1, 0, 6, 8, 11},
                {8, 5, 6, 0, 2, 5},
                {10, 7, 8, 2, 0, 3},
                {13, 10, 11, 5, 3, 0},

        };
        double[][] result = graphAM.floydWarshall();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                assertEquals(expectedResult[i][j], result[i][j], 0);
            }
        }
    }

    @Test
    public void testFloydWarshall3(){
        setUpStage9NoDirectedAndWeightAM();
        double[][] expectedResult = {
                {0, 10, 15},
                {10, 0, 25},
                {15, 25, 0},
        };
        double[][] result = graphAM.floydWarshall();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                assertEquals(expectedResult[i][j], result[i][j], 0);
            }
        }
    }
}
