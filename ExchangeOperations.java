import java.util.*;
public class ExchangeOperations {
    List<Opcode> allCodes = new ArrayList<Opcode>();

    public ExchangeOperations() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("50", " ", " ", 0));
    }

}