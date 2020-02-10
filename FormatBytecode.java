import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FormatBytecode {
    public List<String> formattedData;
    public FormatBytecode(Map<List<String>, List<String>> data) throws IndexOutOfBoundsException {
        try {
            formattedData = new ArrayList<String>();
            List<String> arg1 = new ArrayList<String>();
            List<String> arg2 = new ArrayList<String>();
            Object tempObj;
            String counter, code, name, added, removed;
            Iterator dataIterator = data.entrySet().iterator();
            while (dataIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) dataIterator.next();
                tempObj = pair.getKey();
                arg1 = castObject(tempObj);
                counter = arg1.get(0);
                code = arg1.get(1);
                name = arg1.get(2);
                tempObj = pair.getValue();
                arg2 = castObject(tempObj);
                //If statement is temp until all opcodes are fully functional
                if(arg2.size() >= 2) {
                    added = arg2.get(0);
                    removed = arg2.get(1);
                }else {
                    added = "NONE";
                    removed = "NONE";
                }
                formattedData.add(formatData(counter, code, name, added, removed));
                dataIterator.remove();
            }
        }catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private String formatData(String counter, String code, String name, String added, String removed) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%03d", Integer.parseInt(counter)));
        sb.append(" ").append(code);
        sb.append(" ").append(name);
        sb.append(" ").append(added);
        sb.append(" ").append(removed);
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private  <T extends List<?>> T castObject(Object obj) {

        return (T) obj;
    }

    public List<String> getFormattedData() {
        return formattedData;
    }
}