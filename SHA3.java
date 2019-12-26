import java.util.*;
public class SHA3 {
    List <Opcode> allCodes = new ArrayList<Opcode>();
    public SHA3() {
        populateCodeList();
    }

    private void populateCodeList() {
        allCodes.add(new Opcode("20", "SHA3", 2, "Compute Keccak-256 hash.", 0));
    }
}