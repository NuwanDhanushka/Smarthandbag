package com.example.nuwan.smarthandbag;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

public class mandatoryitemsdetailsviewholder extends RecyclerView.ViewHolder {
    private final TextView itemName,itemstatus,tagid,description,priority,tagInuse;
    ImageButton mandatroeyoptionsbtn;

    public mandatoryitemsdetailsviewholder(View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.itemNames);
        itemstatus = (TextView) itemView.findViewById(R.id.itemstatus);
        tagid = (TextView) itemView.findViewById(R.id.tagid);
        description = (TextView) itemView.findViewById(R.id.itemdescription);
        priority = (TextView) itemView.findViewById(R.id.itemPrority);
        tagInuse = (TextView) itemView.findViewById(R.id.iteminuse);
        mandatroeyoptionsbtn = (ImageButton) itemView.findViewById(R.id.mandatroeyoptionsbtn);
    }
    public void setItemName(String t){
        itemName.setText(t);
    }
    public void setItemStatus(String t){
        itemstatus.setText(t);
    }
    public void setTagID(String t){
        tagid.setText(t);
    }
    public void setDescriptions(String t){
        description.setText(t);
    }
    public void setPriority(String t){
        priority.setText(t);
    }
    public void setTagInuse(String t){
        tagInuse.setText(t);
    }
}
