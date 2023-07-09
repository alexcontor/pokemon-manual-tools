package pojos;

public class PokemonGeneration {


    private int numberOfPokemonAdded;
    private String startingPokemon;
    private int generationNumber;
    private int startingPokemonId;
    private int endingPokemonId;

    public PokemonGeneration() {
    }

    public PokemonGeneration(int numberOfPokemonAdded, String startingPokemon, int generationNumber, int startingPokemonId, int endingPokemonId) {
        this.numberOfPokemonAdded = numberOfPokemonAdded;
        this.startingPokemon = startingPokemon;
        this.generationNumber = generationNumber;
        this.startingPokemonId = startingPokemonId;
        this.endingPokemonId = endingPokemonId;
    }

    public int getNumberOfPokemonAdded() {
        return numberOfPokemonAdded;
    }

    public void setNumberOfPokemonAdded(int numberOfPokemonAdded) {
        this.numberOfPokemonAdded = numberOfPokemonAdded;
    }

    public String getStartingPokemon() {
        return startingPokemon;
    }

    public void setStartingPokemon(String startingPokemon) {
        this.startingPokemon = startingPokemon;
    }

    public String getGenerationNumber() {
        return "GENERATION_" + generationNumber;
    }

    public int getGenerationNumberRaw() {
        return generationNumber;
    }

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }

    public int getStartingPokemonId() {
        return startingPokemonId;
    }

    public int getEndingPokemonId() {
        return endingPokemonId;
    }

    public void setEndingPokemonId(int endingPokemonId) {
        this.endingPokemonId = endingPokemonId;
    }

    public void setStartingPokemonId(int startingPokemonId) {
        this.startingPokemonId = startingPokemonId;
    }
}
