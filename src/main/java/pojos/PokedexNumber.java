package pojos;

public class PokedexNumber {

    Pokemon poke;
    String number;
    String game;
    String pokedexGame;

    public PokedexNumber() {
    }

    public PokedexNumber(Pokemon poke, String number, String game, String pokedexGame) {
        this.poke = poke;
        this.number = number;
        this.game = game;
        this.pokedexGame = pokedexGame;
    }

    public Pokemon getPoke() {
        return poke;
    }

    public void setPoke(Pokemon poke) {
        this.poke = poke;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPokedexGame() {
        return pokedexGame;
    }

    public void setPokedexGame(String pokedexGame) {
        this.pokedexGame = pokedexGame;
    }

    @Override
    public String toString() {
        return poke.toString() + " ||| " + game + "-" + pokedexGame + ": Nº" + number;
    }
}
