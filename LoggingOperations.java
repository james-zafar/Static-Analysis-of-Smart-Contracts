import java.util.*;
public class LoggingOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public LoggingOperations() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("a0", "LOG0", "Append log record with no topics", 0));
        allCodes.add(new Opcode("a1", "LOG1", "Append log record with one topics", 0));
        allCodes.add(new Opcode("a2", "LOG2", "Append log record with two topics", 0));
        allCodes.add(new Opcode("a3", "LOG3", "Append log record with three topics", 0));
        allCodes.add(new Opcode("a4", "LOG4", "Append log record with four topics", 0));
    }

}