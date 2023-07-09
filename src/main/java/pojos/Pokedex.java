package pojos;

public class Pokedex {

    PokedexData pokedexData;
    Training training;
    Breeding breeding;

    public Pokedex() {
    }

    public Pokedex(PokedexData pokedexData, Training training, Breeding breeding) {
        this.pokedexData = pokedexData;
        this.training = training;
        this.breeding = breeding;
    }

    public PokedexData getPokedexData() {
        return pokedexData;
    }

    public void setPokedexData(PokedexData pokedexData) {
        this.pokedexData = pokedexData;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Breeding getBreeding() {
        return breeding;
    }

    public void setBreeding(Breeding breeding) {
        this.breeding = breeding;
    }
}
