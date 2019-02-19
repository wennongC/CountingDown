package wennong.cai.countingdown.Provider;

import java.util.Date;

public class ItemValues {
    private int id;
    private String itemTitle;
    private String itemDescription;
    private Date itemDue;

    public ItemValues(){}

    public ItemValues(int id, String itemTitle, String itemDescription, Date itemDue){
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemDue = itemDue;
    }

    public int getId() {
        return id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Date getItemDue() {
        return itemDue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemDue(Date itemDue) {
        this.itemDue = itemDue;
    }


}
