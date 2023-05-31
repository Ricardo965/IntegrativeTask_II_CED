package com.example.integrativetask_ii_ced.structure.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


import com.example.integrativetask_ii_ced.structure.interfaces.ColorType;
import com.example.integrativetask_ii_ced.structure.interfaces.Igraph;
import com.example.integrativetask_ii_ced.structure.narytree.NaryTree;
import com.example.integrativetask_ii_ced.structure.narytree.Node;


public class AdjacencyMatrixGraph <V extends Comparable<V>> implements Igraph<V> {

    ArrayList<Vertex<V>> vertexes;
    ArrayList<ArrayList<Double>> adjacencyMatrix;

    boolean isDirected;
    boolean isWeighted;

    public AdjacencyMatrixGraph(boolean isDirected, boolean isWeighted) {
        vertexes = new ArrayList<>();
        adjacencyMatrix = new ArrayList<>();
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
    }



    @Override
    public boolean insertVertex(V valueVertex) {
        Vertex<V> vertex = new Vertex<>(valueVertex);
        if(!vertexes.contains(vertex)){
            vertexes.add(vertex);
            int vertexPos = vertexes.indexOf(vertex);
            adjacencyMatrix.add(new ArrayList<Double>());
            if(vertexPos == 0){
                adjacencyMatrix.get(0).add(Double.MAX_VALUE);
                return true;
            }
            for(int i = 0; i < vertexPos; i++){
                adjacencyMatrix.get(i).add(Double.MAX_VALUE);
                adjacencyMatrix.get(vertexPos).add(Double.MAX_VALUE);
            }
            adjacencyMatrix.get(vertexPos).add(Double.MAX_VALUE);
            return true;
        }
        return false;
    }

    @Override
    public boolean insertEdge(V fromValue, V toValue) {
        Vertex<V> from = searchVertex(fromValue); 
        Vertex<V> to = searchVertex(toValue);
        if(!vertexes.contains(from) || !vertexes.contains(to)) return false;
        int v1Pos = vertexes.indexOf(from);
        int v2Pos = vertexes.indexOf(to);
        if (!isWeighted) {
            if(isDirected){
                if(adjacencyMatrix.get(v1Pos).get(v2Pos) < Double.MAX_VALUE) return false;
                adjacencyMatrix.get(v1Pos).set(v2Pos, 1.0);
            } else {
                if(adjacencyMatrix.get(v1Pos).get(v2Pos) < Double.MAX_VALUE || adjacencyMatrix.get(v2Pos).get(v1Pos) < Double.MAX_VALUE) return false;
                adjacencyMatrix.get(v1Pos).set(v2Pos, 1.0);
                adjacencyMatrix.get(v2Pos).set(v1Pos, 1.0);
            }
        }
        return true;
    }

    public boolean insertEdge(V fromValue, V toValue, Double weight) {
        Vertex<V> from = searchVertex(fromValue); 
        Vertex<V> to = searchVertex(toValue);
        if(!vertexes.contains(from) || !vertexes.contains(to)) return false;
        int v1Pos = vertexes.indexOf(from);
        int v2Pos = vertexes.indexOf(to);
        if (isWeighted) {
            if(isDirected){
                if(adjacencyMatrix.get(v1Pos).get(v2Pos) < Double.MAX_VALUE) return false;
                adjacencyMatrix.get(v1Pos).set(v2Pos, weight);
            } else {
                if(adjacencyMatrix.get(v1Pos).get(v2Pos) < Double.MAX_VALUE || adjacencyMatrix.get(v2Pos).get(v1Pos) < Double.MAX_VALUE) return false;
                adjacencyMatrix.get(v1Pos).set(v2Pos, weight);
                adjacencyMatrix.get(v2Pos).set(v1Pos, weight);
            }
        }
        return true;
    }


    @Override
    public boolean deleteVertex(V valueVertex) {
        Vertex<V> vertex = searchVertex(valueVertex);
        if(!vertexes.contains(vertex)) return false;
        int vertexPos = vertexes.indexOf(vertex);
        vertexes.remove(vertexPos);
        adjacencyMatrix.remove(vertexPos);
        for (ArrayList<Double> doubles : adjacencyMatrix) {
            doubles.remove(vertexPos);
        }
        return true;
    }

    @Override
    public boolean deleteEdge(V from, V to) {
        Vertex<V> v1 = searchVertex(from);
        Vertex<V> v2 = searchVertex(to);
        if(!vertexes.contains(v1) || !vertexes.contains(v2)) return false;
        int v1Pos = vertexes.indexOf(v1);
        int v2Pos = vertexes.indexOf(v2);
        if(isDirected){
            adjacencyMatrix.get(v1Pos).set(v2Pos, Double.MAX_VALUE);
            return true;
        } else {
            adjacencyMatrix.get(v1Pos).set(v2Pos, Double.MAX_VALUE);
            adjacencyMatrix.get(v2Pos).set(v1Pos, Double.MAX_VALUE);
            return true;
        }
    }

    @Override
    public NaryTree<V> bfs(V from) {
        NaryTree<V> naryTree = new NaryTree<>();
        naryTree.setRoot(new Node<V>(from));
        Queue<Vertex<V>> queue = new LinkedList<>();

        for (Vertex<V> u :vertexes) {
            u.setColor(ColorType.WHITE);
            u.setDistance(Integer.MAX_VALUE);
            u.setFather(null);
        }
        Vertex<V> s = searchVertex(from);

        s.setColor(ColorType.GRAY);
        s.setDistance(0);
        s.setFather(null);
        queue.add(s);
        while(!queue.isEmpty()){
            Vertex<V> u = queue.poll();
            int uPos = vertexes.indexOf(u);
            for(int i = 0; i < adjacencyMatrix.get(uPos).size(); i++){
                if(adjacencyMatrix.get(uPos).get(i) < Double.MAX_VALUE){
                    if(vertexes.get(i).getColor() == ColorType.WHITE){
                        vertexes.get(i).setColor(ColorType.GRAY);
                        vertexes.get(i).setDistance(u.getDistance()+1);
                        vertexes.get(i).setFather(u);
                        naryTree.insertNode(vertexes.get(i).getValue(), vertexes.get(i).getFather().getValue());
                        queue.add(vertexes.get(i));
                    }
                }
            }
            u.setColor(ColorType.BLACK);
        }
        return naryTree;
    }

    @Override
    public ArrayList<NaryTree<V>> dfs() {
        ArrayList<NaryTree<V>> naryTrees = new ArrayList<>();
        for (Vertex<V> u: vertexes) {
            u.setColor(ColorType.WHITE);
            u.setFather(null);
        }

        for (Vertex<V> u: vertexes) {
            if (u.getColor() == ColorType.WHITE){
                NaryTree<V> naryTree = new NaryTree<>();
                naryTree.setRoot(new Node<V>(u.getValue()));
                dfsVisit(u, naryTree);
                naryTrees.add(naryTree);
            }
        }

        return naryTrees;
    }

    @Override
    public void dfsVisit(Vertex<V> from, NaryTree<V> tree) {
        from.setColor(ColorType.GRAY);

        int uPos = vertexes.indexOf(from);
        for(int i = 0; i < adjacencyMatrix.get(uPos).size(); i++) {
            if(adjacencyMatrix.get(uPos).get(i) < Double.MAX_VALUE) {
                if(vertexes.get(i).getColor() == ColorType.WHITE){
                    vertexes.get(i).setFather(from);
                    tree.insertNode(vertexes.get(i).getValue(), from.getValue());
                    dfsVisit(vertexes.get(i),tree);
                }
            }
        }
        from.setColor(ColorType.BLACK);
    }

    @Override
    public Vertex<V> searchVertex(V values) {
        if (getVertexes().isEmpty()) return null;
        else {
            for (Vertex<V> vertex: getVertexes()) {
                if (vertex.getValue().equals(values)) return vertex;
            }
        }
        return null;
    }


    public ArrayList<V> bfsSingleNode(V from , V to) {
        if (getVertexes().isEmpty()) return null;
        Vertex<V> fromVertex  = searchVertex(from);
        Vertex<V> toVertex  = searchVertex(to);

        for (Vertex<V> u :vertexes) {
            u.setColor(ColorType.WHITE);
            u.setDistance(Integer.MAX_VALUE);
            u.setFather(null);
        }

        fromVertex.setColor(ColorType.GRAY);
        fromVertex.setDistance(0);

        Queue<Vertex<V>> queue = new LinkedList<>();
        queue.add(fromVertex);

        while(!queue.isEmpty()){
            Vertex<V> u = queue.poll();
            int uPos = vertexes.indexOf(u);
            for(int i = 0; i < adjacencyMatrix.get(uPos).size(); i++){
                if(adjacencyMatrix.get(uPos).get(i) < Double.MAX_VALUE){
                    if(vertexes.get(i).getColor() == ColorType.WHITE){
                        vertexes.get(i).setColor(ColorType.GRAY);
                        vertexes.get(i).setDistance(u.getDistance()+1);
                        vertexes.get(i).setFather(u);
                        queue.add(vertexes.get(i));
                    }
                }
            }
            u.setColor(ColorType.BLACK);
        }

        ArrayList<V> path = new ArrayList<>();
        Vertex<V> current = toVertex;
        while(current != null){
            path.add(current.getValue());
            current = current.getFather();
        }
        return path;

    }


    public ArrayList<Vertex<V>> getVertexes() {
        return vertexes;
    }



    public void setVertexes(ArrayList<Vertex<V>> vertexes) {
        this.vertexes = vertexes;
    }



    public ArrayList<ArrayList<Double>> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }



    public void setAdjacencyMatrix(ArrayList<ArrayList<Double>> adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }



    public boolean isDirected() {
        return isDirected;
    }



    public void setDirected(boolean isDirected) {
        this.isDirected = isDirected;
    }



    public boolean isWeighted() {
        return isWeighted;
    }



    public void setWeighted(boolean isWeighted) {
        this.isWeighted = isWeighted;
    }
    
}
