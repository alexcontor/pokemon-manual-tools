package pojos;

import org.apache.commons.lang3.tuple.Pair;

public class Breeding {

    private Pair<String, String> eggGroups;
    private Pair<String, String> genders;
    private String eggCycles;

    public Breeding() {
    }

    public Breeding(Pair<String, String> eggGroups, Pair<String, String> genders, String eggCycles) {
        this.eggGroups = eggGroups;
        this.genders = genders;
        this.eggCycles = eggCycles;
    }

    public Pair<String, String> getEggGroups() {
        return eggGroups;
    }

    public void setEggGroups(Pair<String, String> eggGroups) {
        this.eggGroups = eggGroups;
    }

    public Pair<String, String> getGenders() {
        return genders;
    }

    public void setGenders(Pair<String, String> genders) {
        this.genders = genders;
    }

    public String getEggCycles() {
        return eggCycles;
    }

    public void setEggCycles(String eggCycles) {
        this.eggCycles = eggCycles;
    }
}
