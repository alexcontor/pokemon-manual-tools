package pojos;

public class Training {

    private String catchRate;
    private String baseFriendship;
    private String baseExp;
    private String growthRate;

    public Training() {
    }

    public Training(String catchRate, String baseFriendship, String baseExp, String growthRate) {

        this.catchRate = catchRate;
        this.baseFriendship = baseFriendship;
        this.baseExp = baseExp;
        this.growthRate = growthRate;
    }

    public String getCatchRate() {
        return catchRate;
    }

    public void setCatchRate(String catchRate) {
        this.catchRate = catchRate;
    }

    public String getBaseFriendship() {
        return baseFriendship;
    }

    public void setBaseFriendship(String baseFriendship) {
        this.baseFriendship = baseFriendship;
    }

    public String getBaseExp() {
        return baseExp;
    }

    public void setBaseExp(String baseExp) {
        this.baseExp = baseExp;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }
}
