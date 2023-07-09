package pojos;

public class Item extends GenericIndex {

    private Integer price;
    private Integer generation;
    private String description;

    public Item(String indexNumber) {
        super(indexNumber);
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;
        return (getIndexNumber().equals(item.getIndexNumber()));
    }

    @Override
    public int hashCode() {
        int result = getIndexNumber().hashCode();
        result = 31 * result;
        return result;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
