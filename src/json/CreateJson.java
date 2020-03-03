package src.json;

import src.utils.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * org.json(Version 20190722) library imported:
 * https://jar-download.com/artifacts/org.json
 * See https://github.com/stleary/JSON-java for source
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateJson {

    @SuppressWarnings("unchecked")
    public CreateJson(HashMap<Integer, ArrayList<String>> flow, List<Pair<Integer, Integer>> branchLinks) {
        JSONArray nodes = new JSONArray();
        JSONObject currentNode, tempNode;
        for (Map.Entry<Integer, ArrayList<String>> entry : flow.entrySet()) {
            currentNode = new JSONObject();
            tempNode = new JSONObject();
            currentNode.put("id", entry.getKey());
            tempNode.put("data", currentNode);
            nodes.add(tempNode);
        }
        JSONArray edges = addEdges(branchLinks);
        writeFile(nodes, edges);
    }

    @SuppressWarnings("unchecked")
    private JSONArray addEdges(List<Pair<Integer, Integer>> branchLinks) {
        int origin, dest;
        JSONArray edges = new JSONArray();
        JSONObject edgeType, actualEdge;
        String branchID;
        for(Pair<Integer, Integer> current : branchLinks) {
            edgeType = new JSONObject();
            actualEdge = new JSONObject();
            origin = current.getFirstPair();
            dest = current.getSecondPair();
            branchID = "edge" + String.valueOf(origin) + dest;
            actualEdge.put("data", edgeType);
            edgeType.put("target", dest);
            edgeType.put("source", origin);
            edgeType.put("id", branchID);
            edges.add(actualEdge);
        }
        return edges;
    }

    public void writeFile(JSONArray nodes, JSONArray edges) {
        try (FileWriter file = new FileWriter("webDisplay.json")) {
            file.write(nodes.toString());
            file.write(System.lineSeparator());
            file.write(edges.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
