import src.decode.decodeBytes.Decoder;

public class Run {
    /*
        Class that starts the system
     */
    public static void main(String [] args) {
        if(args.length == 0) {
            new Decoder("default");
        }else {
            new Decoder(getOutputType(String.join(",", args)));
        }
    }

    private static String getOutputType(String args) {
        switch(args.toLowerCase().replaceAll("[^a-zA-Z\\\\s]", "")) {
            case "opcodes":
                return "list";
            case "branch":
                return "fullBranch";
            case "simplebranch":
                return "simpleBranch";
            default:
                throw new RuntimeException("Output type not recognised");
        }
    }
}