import java.util.*;
public class FormatBytecode {
    public List<String> formattedData;
    public FormatBytecode(Map<List<String>, List<String>> data) throws IndexOutOfBoundsException {
        try {
            formattedData = new ArrayList<String>();
            List<String> temp = new ArrayList<String>();
            List<String> arg1 = new ArrayList<String>();
            List<String> arg2 = new ArrayList<String>();
            Object tempObj;
            int counter = 0;
            String code, name, added, removed;
            Iterator dataIterator = data.entrySet().iterator();
            while (dataIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) dataIterator.next();
                tempObj = pair.getKey();
                arg1 = castObject(tempObj);
                code = arg1.get(0);
                name = arg1.get(1);
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
                counter++;
            }
        }catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private String formatData(int counter, String code, String name, String added, String removed) {
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%03d", counter));
        sb.append(" " + code);
        sb.append(" " + name);
        sb.append(" " + added);
        sb.append(" " + removed);
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