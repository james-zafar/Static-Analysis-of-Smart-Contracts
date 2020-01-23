public class Opcode {
    private int gasCost;
    private String code, name, description;
    boolean extraData;

    public Opcode(String ref, String name, String desc, int gasCost, boolean extraData) {
        this.code = ref;
        this.name = name;
        this.description = desc;
        this.gasCost = gasCost;
        this.extraData = extraData;
    }

    public Opcode() { };

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

    public boolean extraDataRequired() {
        return this.extraData;
    }

    public Opcode getOpcode(String code) {
        return new Opcode(code, this.name, this.description, this.gasCost, this.extraData);
    }

    public String toString(Opcode code) {
        return ((this.code) + " " +  this.name + " " +
                this.description + " " + String.valueOf(this.gasCost));
    }
}