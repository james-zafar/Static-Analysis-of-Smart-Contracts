public class Opcode {
    private int gasCost;
    private String code, name, description;

    public Opcode(String ref, String name, String desc, int gasCost) {
        this.code = ref;
        this.name = name;
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
        return ((this.code) + " " +  this.name + " " +
                this.description + " " + String.valueOf(this.gasCost));
    }
}