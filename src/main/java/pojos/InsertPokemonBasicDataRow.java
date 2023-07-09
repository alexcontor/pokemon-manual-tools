package pojos;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class InsertPokemonBasicDataRow {

    private HBox mainContainer;
    private ImageView ivMiniImagePreview;
    private ImageView ivImagePreview;
    private ImageView ivShinyImagePreview;
    private TextField tfImageURL;
    private TextField tfShinyImageURL;
    private TextField tfMiniImageURL;
    private TextField tvID;
    private TextField tvRealID;
    private TextField tvBaseStats;
    private ComboBox<Type> type1;
    private ComboBox<Type> type2;
    private TextField tfNameEN;
    private TextField tfNameES;
    private TextField tfColor;
    private TextField tfColorDark;

    public InsertPokemonBasicDataRow(HBox mainContainer, ImageView ivMiniImagePreview, TextField tfMiniImageURL, ImageView ivImagePreview, TextField tfImageURL, ImageView ivShinyImagePreview, TextField tfShinyImageURL, TextField tvID, TextField tvRealID, TextField tvBaseStats, ComboBox<Type> type1, ComboBox<Type> type2, TextField tfNameEN, TextField tfNameES, TextField tfColor, TextField tfColorDark) {
        this.mainContainer = mainContainer;
        this.ivMiniImagePreview = ivMiniImagePreview;
        this.ivImagePreview = ivImagePreview;
        this.ivShinyImagePreview = ivShinyImagePreview;
        this.tfImageURL = tfImageURL;
        this.tfShinyImageURL = tfShinyImageURL;
        this.tfMiniImageURL = tfMiniImageURL;
        this.tvID = tvID;
        this.tvRealID = tvRealID;
        this.tvBaseStats = tvBaseStats;
        this.type1 = type1;
        this.type2 = type2;
        this.tfNameEN = tfNameEN;
        this.tfNameES = tfNameES;
        this.tfColor = tfColor;
        this.tfColorDark = tfColorDark;
    }

    public TextField getTfImageURL() {
        return tfImageURL;
    }

    public ImageView getIvShinyImagePreview() {
        return ivShinyImagePreview;
    }

    public void setIvShinyImagePreview(ImageView ivShinyImagePreview) {
        this.ivShinyImagePreview = ivShinyImagePreview;
    }

    public TextField getTfShinyImageURL() {
        return tfShinyImageURL;
    }

    public void setTfShinyImageURL(TextField tfShinyImageURL) {
        this.tfShinyImageURL = tfShinyImageURL;
    }

    public void setTfImageURL(TextField tfImageURL) {
        this.tfImageURL = tfImageURL;
    }

    public TextField getTfMiniImageURL() {
        return tfMiniImageURL;
    }

    public void setTfMiniImageURL(TextField tfMiniImageURL) {
        this.tfMiniImageURL = tfMiniImageURL;
    }

    public TextField getTfNameES() {
        return tfNameES;
    }

    public void setTfNameES(TextField tfNameES) {
        this.tfNameES = tfNameES;
    }

    public TextField getTfNameEN() {
        return tfNameEN;
    }

    public void setTfNameEN(TextField tfNameEN) {
        this.tfNameEN = tfNameEN;
    }

    public ImageView getIvMiniImagePreview() {
        return ivMiniImagePreview;
    }

    public void setIvMiniImagePreview(ImageView ivMiniImagePreview) {
        this.ivMiniImagePreview = ivMiniImagePreview;
    }

    public ImageView getIvImagePreview() {
        return ivImagePreview;
    }

    public void setIvImagePreview(ImageView ivImagePreview) {
        this.ivImagePreview = ivImagePreview;
    }

    public TextField getTfColor() {
        return tfColor;
    }

    public void setTfColor(TextField tfColor) {
        this.tfColor = tfColor;
    }

    public TextField getTfColorDark() {
        return tfColorDark;
    }

    public void setTfColorDark(TextField tfColorDark) {
        this.tfColorDark = tfColorDark;
    }

    public TextField getTvID() {
        return tvID;
    }

    public void setTvID(TextField tvID) {
        this.tvID = tvID;
    }

    public TextField getTvRealID() {
        return tvRealID;
    }

    public void setTvRealID(TextField tvRealID) {
        this.tvRealID = tvRealID;
    }

    public TextField getTvBaseStats() {
        return tvBaseStats;
    }

    public void setTvBaseStats(TextField tvBaseStats) {
        this.tvBaseStats = tvBaseStats;
    }

    public ComboBox<Type> getType1() {
        return type1;
    }

    public void setType1(ComboBox<Type> type1) {
        this.type1 = type1;
    }

    public ComboBox<Type> getType2() {
        return type2;
    }

    public void setType2(ComboBox<Type> type2) {
        this.type2 = type2;
    }

    public HBox getMainContainer() {
        return mainContainer;
    }

    public void setMainContainer(HBox mainContainer) {
        this.mainContainer = mainContainer;
    }

}
