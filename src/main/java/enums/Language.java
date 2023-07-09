package enums;

public enum Language {

    ES("_es", "es", "Spanish"),
    EN("", "en", "English"),
    IT("_it", "it", "Italian"),
    FR("_fr", "fr", "French"),
    DE("_de", "de", "German"),
    PT("_pt", "pt-PT", "Portuguese"),
    RU("_ru", "ru", "Russian");

    Language(String isocode, String realIsocode, String name) {
        this.name = name;
        this.isocode = isocode;
        this.realIsocode = realIsocode;
    }

    private final String isocode;
    private final String realIsocode;
    private final String name;

    public String getIsocode() {
        return isocode;
    }

    public String getRealIsocode() {
        return realIsocode;
    }

    public String getName() {
        return name;
    }
}
