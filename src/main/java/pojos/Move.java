package pojos;

import enums.Language;

public class Move extends GenericIndex {

    private String name;
    private String nameExplanation;
    private Integer type;
    private Integer categoryType;
    private Integer contestType;
    private String pp;
    private String ppExplanation;
    private String power;
    private String powerExplanation;
    private String accuracy;
    private String accuracyExplanation;
    private Integer generation;
    private String nameEs;
    private String effect;
    private String effectEs;

    private Boolean makeContact;
    private Boolean affectedByProtect;
    private Boolean affectedByMagicCoat;
    private Boolean affectedBySnatch;
    private Boolean affectedByMirrorMove;
    private Boolean affectedByKingsRock;
    private Boolean soundBasedMove;


    public Move(String indexNumber) {
        super(indexNumber);
    }


    public Move(int indexNumber, String name, String nameExplanation, Integer type, Integer categoryType, String ppExplanation, String power, String powerExplanation, String accuracy, String accuracyExplanation, Integer generation) {
        super(String.valueOf(indexNumber));
        this.name = name;
        this.nameExplanation = nameExplanation;
        this.type = type;
        this.categoryType = categoryType;
        this.ppExplanation = ppExplanation;
        this.power = power;
        this.powerExplanation = powerExplanation;
        this.accuracy = accuracy;
        this.accuracyExplanation = accuracyExplanation;
        this.generation = generation;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameExplanation() {
        return nameExplanation;
    }

    public void setNameExplanation(String nameExplanation) {
        this.nameExplanation = nameExplanation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getContestType() {
        return contestType;
    }

    public void setContestType(Integer contestType) {
        this.contestType = contestType;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getPpExplanation() {
        return ppExplanation;
    }

    public void setPpExplanation(String ppExplanation) {
        this.ppExplanation = ppExplanation;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPowerExplanation() {
        return powerExplanation;
    }

    public void setPowerExplanation(String powerExplanation) {
        this.powerExplanation = powerExplanation;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getAccuracyExplanation() {
        return accuracyExplanation;
    }

    public void setAccuracyExplanation(String accuracyExplanation) {
        this.accuracyExplanation = accuracyExplanation;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Boolean getMakeContact() {
        return makeContact;
    }

    public void setMakeContact(Boolean makeContact) {
        this.makeContact = makeContact;
    }

    public Boolean getAffectedByProtect() {
        return affectedByProtect;
    }

    public void setAffectedByProtect(Boolean affectedByProtect) {
        this.affectedByProtect = affectedByProtect;
    }

    public Boolean getAffectedByMagicCoat() {
        return affectedByMagicCoat;
    }

    public void setAffectedByMagicCoat(Boolean affectedByMagicCoat) {
        this.affectedByMagicCoat = affectedByMagicCoat;
    }

    public Boolean getAffectedBySnatch() {
        return affectedBySnatch;
    }

    public void setAffectedBySnatch(Boolean affectedBySnatch) {
        this.affectedBySnatch = affectedBySnatch;
    }

    public Boolean getAffectedByMirrorMove() {
        return affectedByMirrorMove;
    }

    public void setAffectedByMirrorMove(Boolean affectedByMirrorMove) {
        this.affectedByMirrorMove = affectedByMirrorMove;
    }

    public Boolean getAffectedByKingsRock() {
        return affectedByKingsRock;
    }

    public void setAffectedByKingsRock(Boolean affectedByKingsRock) {
        this.affectedByKingsRock = affectedByKingsRock;
    }


    public Boolean getSoundBasedMove() {
        return soundBasedMove;
    }

    public void setSoundBasedMove(Boolean soundBasedMove) {
        this.soundBasedMove = soundBasedMove;
    }

    @Override
    public String toString() {
        return getLocalizedName(Language.EN);
    }
}
