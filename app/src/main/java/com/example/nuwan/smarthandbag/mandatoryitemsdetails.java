package com.example.nuwan.smarthandbag;

public class mandatoryitemsdetails {
    private String ItemName,ItemStatus,description,priority,tagInuse;
            private long tagId;

    public mandatoryitemsdetails() {
    }
    public mandatoryitemsdetails(String itemName, String description) {
        ItemName = itemName;
        this.description = description;
    }
    public mandatoryitemsdetails(String itemName, String itemStatus, String description, String priority, String tagInuse, long tagId) {
        ItemName = itemName;
        ItemStatus = itemStatus;
        this.description = description;
        this.priority = priority;
        this.tagInuse = tagInuse;
        this.tagId = tagId;
    }



    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemStatus() {
        return ItemStatus;
    }

    public void setItemStatus(String itemStatus) {
        ItemStatus = itemStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTagInuse() {
        return tagInuse;
    }

    public void setTagInuse(String tagInuse) {
        this.tagInuse = tagInuse;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
