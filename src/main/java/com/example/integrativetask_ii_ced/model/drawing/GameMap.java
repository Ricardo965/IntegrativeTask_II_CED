package com.example.integrativetask_ii_ced.model.drawing;


import java.util.ArrayList;
import java.util.Random;

public class GameMap {

    ArrayList<ArrayList<MapNode>> mapGuide;
    Graph<Double[]> graph;
    
    public GameMap() {
        this.mapGuide = new ArrayList<>();
        this.graph=  new Graph<>(false,false);
    }
    
    public void initialFillingOfMapWithNodesAndCoordinates( double nodeSize, double width, double height){
        double yPosition = 0;

        for (int i = 0; i < height/nodeSize; i++) {

            yPosition+= nodeSize / 2;
            double xPosition= 0;
            mapGuide.add(new ArrayList<MapNode>());

            for (int j = 0; j < width/nodeSize ; j++) {
                xPosition+= nodeSize /2;
                boolean isNavigable = (i<6 || i>8) && (j<3 || j >5)? true: false;
                MapNode node =  new MapNode(xPosition, yPosition, isNavigable);
                getMapGuide().get(i).add(node);
            }
        }
    }

    public void creatingNotNavigableObstacles(int chunkSize, double nodeSize, double width, double height){

        Random random  =new Random();
        for (int i = 0; i < height/nodeSize; i+=chunkSize) {
            int yRange = i + chunkSize;
            for (int j = 0; j < width; j+=chunkSize) {
                
                int rowNodeSelection = random.nextInt(i, yRange);
                int columnNodeSelection = random.nextInt(j, j+chunkSize);
                getMapGuide().get(rowNodeSelection).get(columnNodeSelection).setNavigable(false);

                int blocks = 1;
                while(blocks <= chunkSize-2){

                    switch (random.nextInt(0,5)){
                        case 0: // up
                            if (rowNodeSelection -1 >=i) {
                                getMapGuide().get(rowNodeSelection -1).get(columnNodeSelection).setNavigable(false);
                                blocks++;
                            }
                            break;
                        case 1: // down
                            if (rowNodeSelection + 1 <= yRange) {
                                getMapGuide().get(rowNodeSelection + 1).get(columnNodeSelection).setNavigable(false);
                                blocks++;
                            }
                            break;
                        case 2: // left
                            if (columnNodeSelection -1 >= j) {
                                getMapGuide().get(rowNodeSelection ).get(columnNodeSelection-1).setNavigable(false);
                                blocks++;
                            }
                            break;
                        case 3: // Right
                            if (columnNodeSelection +1 <= j + chunkSize) {
                                getMapGuide().get(rowNodeSelection ).get(columnNodeSelection + 1).setNavigable(false);
                                blocks++;
                            }
                            break;

                    }

                }
            }
        }
    }
    
    public void establishGraphMapRepresentationForMinimumPaths(double nodeSize, double width, double height){

        for (int i = 0; i < height/nodeSize ; i++) {
            for (int j = 0; j < width/nodeSize; j++) {

                if (getMapGuide().get(i).get(j).isNavigable() && j == 0){
                    Double [] coordinates = new Double[2];
                    coordinates[0] =  getMapGuide().get(i).get(j).getPosition().getX();
                    coordinates[1] =  getMapGuide().get(i).get(j).getPosition().getY();
                    graph.insertVertex(coordinates);
                }

                if (getMapGuide().get(i).get(j).isNavigable() )
            }
        }
        
    }
    public ArrayList<ArrayList<MapNode>> getMapGuide() {
        return mapGuide;
    }

    public void setMapGuide(ArrayList<ArrayList<MapNode>> mapGuide) {
        this.mapGuide = mapGuide;
    }

    public Graph<Integer[]> getGraph() {
        return graph;
    }

    public void setGraph(Graph<Integer[]> graph) {
        this.graph = graph;
    }
}
