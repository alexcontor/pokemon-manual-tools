package pojos;

public class Ability extends GenericIndex {

    private boolean isHidden;

    public Ability(String indexNumber) {
        super(indexNumber);
    }

    public Ability(String indexNumber, boolean isHidden) {
        super(indexNumber);
        this.isHidden = isHidden;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ability ability = (Ability) o;
        return (getIndexNumber().equals(ability.getIndexNumber()));
    }

    @Override
    public int hashCode() {
        int result = getIndexNumber().hashCode();
        result = 31 * result;
        return result;
    }
}
