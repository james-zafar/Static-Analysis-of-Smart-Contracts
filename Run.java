import src.decode.Decoder;

public class Run {
    /*
        Class that starts the system
     */
    public static void main(String [] args) {
        if(args.length == 0) {
            new Decoder("default");
        }else {
            new Decoder(getOutputTypes(args));
        }
    }

    private static String getOutputTypes(String[] args) {
        switch(args[0].toLowerCase().replaceAll("\\s", "")) {
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