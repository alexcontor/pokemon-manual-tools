package pojos;

import enums.GameVersion;
import enums.Language;

import java.util.List;

public class Game extends GenericIndex {

    private static Game allGames = null;
    private int generation;
    private List<PokedexGame> subGames;
    private PokedexGame nationalDex;
    private GameVersion gameVersion;
    private String mainGameID;

    public Game(String indexNumber, int generation) {
        super(indexNumber);
        this.generation = generation;
        this.gameVersion = GameVersion.get(indexNumber);
    }

    public Game(String indexNumber, String localizedName, int generation) {
        super(indexNumber, localizedName);
        this.generation = generation;
        this.gameVersion = GameVersion.get(indexNumber);
    }

    public Game(String indexNumber, String localizedName, int generation, String mainGameID) {
        super(localizedName, localizedName);
        setIndexNumber(indexNumber);
        this.generation = generation;
        this.gameVersion = GameVersion.get(indexNumber);
        this.mainGameID = mainGameID;
    }

    public void setNationalDex(PokedexGame nationalDex) {
        this.nationalDex = nationalDex;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public List<PokedexGame> getSubGames() {
        return subGames;
    }

    public void setSubGames(List<PokedexGame> subGames) {
        this.subGames = subGames;
    }

    public GameVersion getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(GameVersion gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getMainGameID() {
        return mainGameID;
    }

    public void setMainGameID(String mainGameID) {
        this.mainGameID = mainGameID;
    }


    @Override
    public String toString() {
        return getLocalizedName(Language.EN);
    }
}
