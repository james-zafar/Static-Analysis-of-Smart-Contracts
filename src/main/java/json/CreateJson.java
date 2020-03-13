package json;

import utils.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        createServer(nodes, edges);
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

    private void writeFile(JSONArray nodes, JSONArray edges) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter("src/ui/js/webDisplay.json"))) {
            file.write(nodes.toString());
            file.write(System.lineSeparator());
            file.write(edges.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createServer(JSONArray nodes, JSONArray edges) {
        try {
            ServerSocket mySocket = new ServerSocket(8080);
            Socket conn = mySocket.accept();
            ObjectOutputStream writeToServer = new ObjectOutputStream(conn.getOutputStream());
            writeToServer.writeObject(nodes.toString());
            writeToServer.writeObject(System.lineSeparator());
            writeToServer.writeObject(edges.toString());
            writeToServer.flush();
        } catch(IOException e) {
            System.out.println("Can not create JSON at this time.");
            System.out.println(e.getMessage());
        }
    }

}
