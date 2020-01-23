import java.util.*;
public class SHA3 {
    List <Opcode> allCodes = new ArrayList<Opcode>();
    public SHA3() {
        populateCodeList();
    }

    public List<Opcode> getAllCodes() {
        return allCodes;
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("20", "SHA3", "Compute Keccak-256 hash.", 0, false));
    }
}