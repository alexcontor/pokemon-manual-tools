package pojos;

import enums.Language;
import enums.MachineTypesEnum;

public class Machine extends GenericIndex {

    private MachineTypesEnum machineType;
    private Integer number;
    private Move move;

    public Machine(String indexNumber) {
        super(indexNumber);
    }

    public MachineTypesEnum getMachineType() {
        return machineType;
    }

    public void setMachineType(MachineTypesEnum machineType) {
        this.machineType = machineType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    @Override
    public String getLocalizedName(Language language) {
        String moveName = move.getLocalizedName(language);
        return String.format("%s%d (%s)", machineType.getIndex(), number, moveName);
    }
}
