package opcodes;

import java.util.ArrayList;
import java.util.List;
public class LoggingOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public LoggingOperations() {
        populateCodeList();
    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }


    private void populateCodeList() {
        allCodes.add(new Opcode("a0", "LOG0", "Append log record with no topics", 8, true));
        allCodes.add(new Opcode("a1", "LOG1", "Append log record with one topics", 375, true));
        allCodes.add(new Opcode("a2", "LOG2", "Append log record with two topics", 375, true));
        allCodes.add(new Opcode("a3", "LOG3", "Append log record with three topics", 375, true));
        allCodes.add(new Opcode("a4", "LOG4", "Append log record with four topics", 375, true));
    }

}