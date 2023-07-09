package pojos;

public class Evolution {

    private Integer id;
    private String name;
    private Integer evoID;
    private String evoName;
    private Integer originPokemonID;
    private String originPokemon;
    private String method;

    public Evolution() {
    }

    public Evolution(Integer id, String name, Integer evoID, String evoName, Integer originPokemonID, String originPokemon, String method) {
        this.id = id;
        this.name = name;
        this.evoID = evoID;
        this.evoName = evoName;
        this.originPokemonID = originPokemonID;
        this.originPokemon = originPokemon;
        this.method = method;
    }

    public Evolution(String name, String method, String evoName, String originPokemon) {
        this.name = name;
        this.method = method;
        this.evoName = evoName;
        this.originPokemon = originPokemon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEvoID() {
        return evoID;
    }

    public void setEvoID(Integer evoID) {
        this.evoID = evoID;
    }

    public String getEvoName() {
        return evoName;
    }

    public void setEvoName(String evoName) {
        this.evoName = evoName;
    }

    public Integer getOriginPokemonID() {
        return originPokemonID;
    }

    public void setOriginPokemonID(Integer originPokemonID) {
        this.originPokemonID = originPokemonID;
    }

    public String getOriginPokemon() {
        return originPokemon;
    }

    public void setOriginPokemon(String originPokemon) {
        this.originPokemon = originPokemon;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
