package pojos;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class InsertPokedexRow {

    private HBox mainContainer;

    private TextField tvID;
    private TextField tvBiologyEn;
    private TextField tvBiologyEs;
    private TextField tvEtymologyEn;
    private TextField tvEtymologyEs;

    private TextField tvHeightMeters;
    private TextField tvHeightFeet;
    private TextField tvWeightKg;
    private TextField tvWeightPounds;

    private TextField tvCatchRate;
    private TextField tvBaseFriendship;
    private TextField tvBaseExp;
    private TextField tvGrowth;
    private TextField tvEggSteps;
    private TextField tvMaleRate;
    private TextField tvFemaleRate;

    private ComboBox<Specie> species;

    private ComboBox<EggGroup> eggGroup1;
    private ComboBox<EggGroup> eggGroup2;

    private ComboBox<Ability> ability1;
    private ComboBox<Ability> ability2;
    private ComboBox<Ability> hiddenAbility;
    private TextField tvEVS;

    public InsertPokedexRow(TextField tvID, HBox mainContainer, TextField tvBiologyEn, TextField tvBiologyEs, TextField tvEtymologyEn, TextField tvEtymologyEs, TextField tvHeightMeters, TextField tvHeightFeet, TextField tvWeightKg, TextField tvWeightPounds, TextField tvCatchRate, TextField tvBaseFriendship, TextField tvBaseExp, TextField tvGrowth, TextField tvEggSteps, TextField tvMaleRate, TextField tvFemaleRate, ComboBox<Specie> species, ComboBox<EggGroup> eggGroup1, ComboBox<EggGroup> eggGroup2, ComboBox<Ability> ability1, ComboBox<Ability> ability2, ComboBox<Ability> hiddenAbility, TextField tvEVS) {
        this.tvID = tvID;
        this.mainContainer = mainContainer;
        this.tvBiologyEn = tvBiologyEn;
        this.tvBiologyEs = tvBiologyEs;
        this.tvEtymologyEn = tvEtymologyEn;
        this.tvEtymologyEs = tvEtymologyEs;
        this.tvHeightMeters = tvHeightMeters;
        this.tvHeightFeet = tvHeightFeet;
        this.tvWeightKg = tvWeightKg;
        this.tvWeightPounds = tvWeightPounds;
        this.tvCatchRate = tvCatchRate;
        this.tvBaseFriendship = tvBaseFriendship;
        this.tvBaseExp = tvBaseExp;
        this.tvGrowth = tvGrowth;
        this.tvEggSteps = tvEggSteps;
        this.tvMaleRate = tvMaleRate;
        this.tvFemaleRate = tvFemaleRate;
        this.species = species;
        this.eggGroup1 = eggGroup1;
        this.eggGroup2 = eggGroup2;
        this.ability1 = ability1;
        this.ability2 = ability2;
        this.hiddenAbility = hiddenAbility;
        this.tvEVS = tvEVS;
    }

    public TextField getTvID() {
        return tvID;
    }

    public void setTvID(TextField tvID) {
        this.tvID = tvID;
    }

    public HBox getMainContainer() {
        return mainContainer;
    }

    public void setMainContainer(HBox mainContainer) {
        this.mainContainer = mainContainer;
    }

    public TextField getTvBiologyEn() {
        return tvBiologyEn;
    }

    public void setTvBiologyEn(TextField tvBiologyEn) {
        this.tvBiologyEn = tvBiologyEn;
    }

    public TextField getTvBiologyEs() {
        return tvBiologyEs;
    }

    public void setTvBiologyEs(TextField tvBiologyEs) {
        this.tvBiologyEs = tvBiologyEs;
    }

    public TextField getTvEtymologyEn() {
        return tvEtymologyEn;
    }

    public void setTvEtymologyEn(TextField tvEtymologyEn) {
        this.tvEtymologyEn = tvEtymologyEn;
    }

    public TextField getTvEtymologyEs() {
        return tvEtymologyEs;
    }

    public void setTvEtymologyEs(TextField tvEtymologyEs) {
        this.tvEtymologyEs = tvEtymologyEs;
    }

    public TextField getTvHeightMeters() {
        return tvHeightMeters;
    }

    public void setTvHeightMeters(TextField tvHeightMeters) {
        this.tvHeightMeters = tvHeightMeters;
    }

    public TextField getTvHeightFeet() {
        return tvHeightFeet;
    }

    public void setTvHeightFeet(TextField tvHeightFeet) {
        this.tvHeightFeet = tvHeightFeet;
    }

    public TextField getTvWeightKg() {
        return tvWeightKg;
    }

    public void setTvWeightKg(TextField tvWeightKg) {
        this.tvWeightKg = tvWeightKg;
    }

    public TextField getTvWeightPounds() {
        return tvWeightPounds;
    }

    public void setTvWeightPounds(TextField tvWeightPounds) {
        this.tvWeightPounds = tvWeightPounds;
    }

    public TextField getTvCatchRate() {
        return tvCatchRate;
    }

    public void setTvCatchRate(TextField tvCatchRate) {
        this.tvCatchRate = tvCatchRate;
    }

    public TextField getTvBaseFriendship() {
        return tvBaseFriendship;
    }

    public void setTvBaseFriendship(TextField tvBaseFriendship) {
        this.tvBaseFriendship = tvBaseFriendship;
    }

    public TextField getTvBaseExp() {
        return tvBaseExp;
    }

    public void setTvBaseExp(TextField tvBaseExp) {
        this.tvBaseExp = tvBaseExp;
    }

    public TextField getTvGrowth() {
        return tvGrowth;
    }

    public void setTvGrowth(TextField tvGrowth) {
        this.tvGrowth = tvGrowth;
    }

    public TextField getTvEggSteps() {
        return tvEggSteps;
    }

    public void setTvEggSteps(TextField tvEggSteps) {
        this.tvEggSteps = tvEggSteps;
    }

    public TextField getTvMaleRate() {
        return tvMaleRate;
    }

    public void setTvMaleRate(TextField tvMaleRate) {
        this.tvMaleRate = tvMaleRate;
    }

    public TextField getTvFemaleRate() {
        return tvFemaleRate;
    }

    public void setTvFemaleRate(TextField tvFemaleRate) {
        this.tvFemaleRate = tvFemaleRate;
    }

    public ComboBox<Specie> getSpecies() {
        return species;
    }

    public void setSpecies(ComboBox<Specie> species) {
        this.species = species;
    }

    public ComboBox<EggGroup> getEggGroup1() {
        return eggGroup1;
    }

    public void setEggGroup1(ComboBox<EggGroup> eggGroup1) {
        this.eggGroup1 = eggGroup1;
    }

    public ComboBox<EggGroup> getEggGroup2() {
        return eggGroup2;
    }

    public void setEggGroup2(ComboBox<EggGroup> eggGroup2) {
        this.eggGroup2 = eggGroup2;
    }

    public ComboBox<Ability> getAbility1() {
        return ability1;
    }

    public void setAbility1(ComboBox<Ability> ability1) {
        this.ability1 = ability1;
    }

    public ComboBox<Ability> getAbility2() {
        return ability2;
    }

    public void setAbility2(ComboBox<Ability> ability2) {
        this.ability2 = ability2;
    }

    public ComboBox<Ability> getHiddenAbility() {
        return hiddenAbility;
    }

    public void setHiddenAbility(ComboBox<Ability> hiddenAbility) {
        this.hiddenAbility = hiddenAbility;
    }

    public TextField getTvEVS() {
        return tvEVS;
    }

    public void setTvEVS(TextField tvEVS) {
        this.tvEVS = tvEVS;
    }
}
