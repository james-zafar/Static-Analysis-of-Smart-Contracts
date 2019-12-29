public class Opcode {
    private int gasCost;
    private String code, name, description;

    public Opcode(String ref, String name, String desc, int gasCost) {
        this.code = ref;
        this.name = name;
        this.description = desc;
        this.gasCost = gasCost;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getGasCost() {
        return this.gasCost;
    }

    public String toString(Opcode code) {
        return ((this.code) + " " +  this.name + " " +
                this.description + " " + String.valueOf(this.gasCost));
    }
}