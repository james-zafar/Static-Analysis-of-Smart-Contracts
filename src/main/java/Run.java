import decode.decodeBytes.Decoder;
import update.Update;

public class Run {
    /*
        Class that starts the system
     */
   public static void main(String [] args) {
        if(args.length == 0) {
            new Decoder("default");
        }
        else if(getOutputType(String.join(",", args)).matches("update")) {
            new Update();
        } else {
            new Decoder(getOutputType(String.join(",", args)));
        }
    }

    private static String getOutputType(String args) {
        switch(args.toLowerCase().replaceAll("[^a-zA-Z\\\\s]", "")) {
            case "opcodes":
                return "list";
            case "branch":
                return "fullbranch";
            case "simplebranch":
                return "simplebranch";
            case "ui":
            case "gui":
            case "webdisplay":
                return "web";
            case "update":
                return "update";
            default:
                throw new RuntimeException("Output type not recognised");
        }
    }
}