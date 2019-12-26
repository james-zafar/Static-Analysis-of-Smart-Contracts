import java.util.*;
public class Decoder {
    public static void main(String [] args) {
        ExchangeOperations allCodes = new ExchangeOperations();
        List<Opcode> list = allCodes.getList();
        for(int i = 0; i < list.size(); i++) {
            Opcode temp = list.get(i);
            System.out.println(temp.toString(temp));
        }
        System.out.println(list.size());

    }
}