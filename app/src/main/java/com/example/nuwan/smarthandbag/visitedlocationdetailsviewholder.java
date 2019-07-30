package com.example.nuwan.smarthandbag;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class visitedlocationdetailsviewholder extends RecyclerView.ViewHolder  {
    private final TextView visitedlocation,timeStamp,datetime,fulldate,bagstatus;

    public visitedlocationdetailsviewholder(View itemView) {
        super(itemView);
        visitedlocation=(TextView) itemView.findViewById(R.id.visitedlocation);
        timeStamp=(TextView) itemView.findViewById(R.id.date);
        datetime=(TextView) itemView.findViewById(R.id.datetime);
        fulldate=(TextView) itemView.findViewById(R.id.fulldate);
        bagstatus=(TextView) itemView.findViewById(R.id.bagstatus);
    }


    public void setVisitedlocation(String t){
        visitedlocation.setText(t);
    }
    public void setTimeStamp(String t){
        Long time = Long.parseLong(t)*1000L;
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date netDate = (new Date(time));
        int dateonly = Integer.parseInt(sdf.format(netDate));
        String ordinal[] = new String[]{"zero","1st","2nd","3rd","4th","5th","6th","7th","8th","9th","10th","11th","12th","13th","14th","15th","16th","17th","18th","19th","20th","21st","22nd","23rd","24th","25th","26th","27th","28th","29th","30th","31st",
        };
        timeStamp.setText(ordinal[dateonly]); }
    public void setTime(String t) {
        Long time = Long.parseLong(t)*1000L;
        SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm");
        Date netDate = (new Date(time));
        String time2 = sdf.format(netDate);
        datetime.setText(time2);
    }
    public void setDate(String t) {
        Long time = Long.parseLong(t)*1000L;
        SimpleDateFormat sdf = new SimpleDateFormat(" dd-MM-yyyy");
        Date netDate = (new Date(time));
        String time2 = sdf.format(netDate);
        fulldate.setText(time2);
    }
    public void setBagstatus(String t) {
       bagstatus.setText(t);
    }

}


