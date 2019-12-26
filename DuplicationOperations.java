import java.util.*;
public class DuplicationOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public DuplicationOperations() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("50", " ", 0, " ", 0));
    }

}