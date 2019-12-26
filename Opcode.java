public class Opcode {
    private int gasCost, args;
    private String code, name, description;

    public Opcode(String ref, String name, int args, String desc, int gasCost) {
        this.code = ref;
        this.name = name;
        this.args = args;
        this.description = desc;
        this.gasCost = gasCost;
    }

    public String getCode(String name) {
        return code;
    }

    public String getName(int code) {
        return name;
    }

    public String toString(Opcode code) {
        return ((this.code) + " " +  this.name + " " + String.valueOf(this.args) + " " +
                this.description + " " + String.valueOf(this.gasCost));
    }
}