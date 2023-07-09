package pojos;

import java.util.Objects;

public class PokedexGame extends GenericIndex {

    private String gameIndex;
    private boolean isNational;

    public PokedexGame(String indexNumber) {
        super(indexNumber);
    }

    public PokedexGame(String indexNumber, String gameIndex, boolean isNational) {
        super(indexNumber);
        this.gameIndex = gameIndex;
        this.isNational = isNational;
    }

    public String getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(String gameIndex) {
        this.gameIndex = gameIndex;
    }

    public boolean isNational() {
        return isNational;
    }

    public void setNational(boolean national) {
        isNational = national;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PokedexGame that = (PokedexGame) o;
        return isNational == that.isNational && Objects.equals(gameIndex, that.gameIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gameIndex, isNational);
    }

    @Override
    public String toString() {
        return getIndexNumber();
    }
}
