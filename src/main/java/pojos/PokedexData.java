package pojos;

import java.util.HashMap;

public class PokedexData {

    private String id;
    private int realId;
    private String species;
    private Double heightMeters;
    private Double heightFeet;
    private Double weightKilograms;
    private Double weightPounds;
    private HashMap<PokedexGame, Integer> pokedexNumbersByRegion;

    public PokedexData() {
    }

    public PokedexData(String id, int realId, String species, Double heightMeters, Double heightFeet, Double weightKilograms, Double weightPounds) {
        this.id = id;
        this.realId = realId;
        this.species = species;
        this.heightMeters = heightMeters;
        this.weightKilograms = weightKilograms;
        this.heightFeet = heightFeet;
        this.weightPounds = weightPounds;
    }

    public PokedexData(String id, int realId, String species, Double heightMeters, Double heightFeet, Double weightKilograms, Double weightPounds, HashMap<PokedexGame, Integer> pokedexNumbersByRegion) {
        this.id = id;
        this.realId = realId;
        this.species = species;
        this.heightMeters = heightMeters;
        this.weightKilograms = weightKilograms;
        this.heightFeet = heightFeet;
        this.weightPounds = weightPounds;
        this.pokedexNumbersByRegion = pokedexNumbersByRegion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRealId() {
        return realId;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Double getHeightMeters() {
        return heightMeters;
    }

    public void setHeightMeters(Double heightMeters) {
        this.heightMeters = heightMeters;
    }

    public Double getWeightKilograms() {
        return weightKilograms;
    }

    public void setWeightKilograms(Double weightKilograms) {
        this.weightKilograms = weightKilograms;
    }

    public Double getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(Double heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Double getWeightPounds() {
        return weightPounds;
    }

    public void setWeightPounds(Double weightPounds) {
        this.weightPounds = weightPounds;
    }
}
