package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

public abstract class Item {
    private String itemName;
    private boolean isActive;

    public Item(String itemName) {
        setItemName(itemName);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
