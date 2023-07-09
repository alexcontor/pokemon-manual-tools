package pojos;

import java.util.Objects;

public class Stats {

    private Integer hp, atk, def, special, spatk, spdef, speed;
    private Integer fromGeneration, toGeneration;

    public Stats(Integer hp, Integer atk, Integer def, Integer special, Integer spatk, Integer spdef, Integer speed, Integer fromGeneration, Integer toGeneration) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.special = special;
        this.spatk = spatk;
        this.spdef = spdef;
        this.speed = speed;
        this.fromGeneration = fromGeneration;
        this.toGeneration = toGeneration;
    }

    public Integer getFromGeneration() {
        return fromGeneration;
    }

    public Integer getToGeneration() {
        return toGeneration;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getAtk() {
        return atk;
    }

    public void setAtk(Integer atk) {
        this.atk = atk;
    }

    public Integer getDef() {
        return def;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public Integer getSpatk() {
        return spatk;
    }

    public void setSpatk(Integer spatk) {
        this.spatk = spatk;
    }

    public Integer getSpdef() {
        return spdef;
    }

    public void setSpdef(Integer spdef) {
        this.spdef = spdef;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return Objects.equals(hp, stats.hp) &&
                Objects.equals(atk, stats.atk) &&
                Objects.equals(def, stats.def) &&
                Objects.equals(special, stats.special) &&
                Objects.equals(spatk, stats.spatk) &&
                Objects.equals(spdef, stats.spdef) &&
                Objects.equals(speed, stats.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hp, atk, def, special, spatk, spdef, speed);
    }
}
