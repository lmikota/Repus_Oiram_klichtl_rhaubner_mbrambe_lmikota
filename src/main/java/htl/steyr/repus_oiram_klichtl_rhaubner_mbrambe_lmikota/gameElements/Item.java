package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

public abstract class Item {
    private boolean isVisible = true;
    private String itemName;
    private int tileNumber;
    private String imageFilePath;

    public Item(String itemName, int tileNumber, String imageFilePath) {
        setItemName(itemName);
        setTileNumber(tileNumber);
        setImageFilePath(imageFilePath);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
}
