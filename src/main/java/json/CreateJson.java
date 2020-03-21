package json;

import utils.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateJson {

    @SuppressWarnings("unchecked")
    public CreateJson(HashMap<Integer, ArrayList<String>> flow,
                      List<Pair<Integer, Integer>> branchLinks)
    {
        JSONArray nodes = new JSONArray();
        JSONObject currentNode, tempNode, dataNode;
        List<JSONObject> allData = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<String>> entry : flow.entrySet()) {
            currentNode = new JSONObject();
            tempNode = new JSONObject();
            dataNode = new JSONObject();
            currentNode.put("id", entry.getKey());
            tempNode.put("data", currentNode);
            nodes.add(tempNode);
            //String data = entry.getValue().stream().reduce("\\s", String::concat);
            dataNode.put(("Branch" + entry.getKey()), entry.getValue().toString());
            allData.add(dataNode);
        }
        JSONArray edges = addEdges(branchLinks);
        writeFile(nodes, edges, allData);
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
            origin = current.getFirst();
            dest = current.getSecond();
            branchID = "edge" + String.valueOf(origin) + dest;
            actualEdge.put("data", edgeType);
            edgeType.put("target", dest);
            edgeType.put("source", origin);
            edgeType.put("id", branchID);
            edges.add(actualEdge);
        }
        return edges;
    }

    private void writeFile(JSONArray nodes, JSONArray edges, List<JSONObject> info) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter("src/main/java/upload/webDisplay.json"))) {
            file.write(nodes.toString());
            file.write(System.lineSeparator());
            file.write(edges.toString());
            for(JSONObject current : info) {
                file.write(System.lineSeparator());
                file.write(current.toJSONString());
            }
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
