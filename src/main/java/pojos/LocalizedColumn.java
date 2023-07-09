package pojos;

public class LocalizedColumn {

    private String column;
    private String englishValue;
    private String localizedValue;

    public LocalizedColumn(String column, String englishValue, String localizedValue) {
        this.column = column;
        this.englishValue = englishValue;
        this.localizedValue = localizedValue;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getEnglishValue() {
        return englishValue;
    }

    public void setEnglishValue(String englishValue) {
        this.englishValue = englishValue;
    }

    public String getLocalizedValue() {
        return localizedValue;
    }

    public void setLocalizedValue(String localizedValue) {
        this.localizedValue = localizedValue;
    }
}
