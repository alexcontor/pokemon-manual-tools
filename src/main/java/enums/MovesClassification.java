package enums;

public enum MovesClassification {

    BY_LEVEL("poke_learnset_moves"),
    BY_TM_HM_TR("poke_tm_hm_tr_moves"),
    BY_EGGMOVE("poke_egg_moves"),
    BY_TUTOR("poke_tutor_moves"),
    BY_TRANSFER("poke_transfer_only_moves"),
    BY_EVENT("poke_event_moves");

    private String name;

    MovesClassification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
