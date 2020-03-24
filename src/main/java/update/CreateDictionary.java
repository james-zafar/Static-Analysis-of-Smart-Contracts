package update;

import decode.decodeBytes.FullOpcodeList;
import opcodes.Opcode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CreateDictionary {

    public CreateDictionary() {
        List<Opcode> allCodes = new FullOpcodeList().getList();
        TreeMap<String, String> descriptions = new TreeMap<>();
        for(Opcode current : allCodes) {
            descriptions.put(current.getCode(), current.getDescription());
        }
        CreateJSON(descriptions);
    }

    /**
     *
     * @param descriptions A HashMap of descriptions, key is the opcode number, value is the description
     */

    @SuppressWarnings("unchecked")
    private void CreateJSON(TreeMap<String, String> descriptions) {
        JSONObject node;
        JSONArray allDescriptions = new JSONArray();
        for(Map.Entry<String, String> desc : descriptions.entrySet()) {
            node = new JSONObject();
            node.put("id", desc.getKey());
            node.put("value", desc.getValue());
            allDescriptions.add(node);
        }
        CreateFile(allDescriptions);
    }

    /**
     *
     * @param finished An array of JSONObjects containing definitions
     */
    private void CreateFile(JSONArray finished) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter("src/main/java/update/Dictionary.json"))) {
            file.write(finished.toJSONString());
            file.flush();
        } catch(IOException e) {
            System.out.println("Critical IO Error!");
            System.out.println("Could not create dictionary...");
            System.out.println(e.getMessage());
        }
    }
}
