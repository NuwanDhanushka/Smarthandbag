package com.example.nuwan.smarthandbag;

public class visitedlocationdetails {
    private String VisitedLocation;
    private Long TimeStamp;
    private String DeviceLock;

    public visitedlocationdetails() {
    }

    public visitedlocationdetails(String visitedLocation,Long timeStamp,String deviceLock) {
        VisitedLocation = visitedLocation;
        TimeStamp = timeStamp;
        DeviceLock = deviceLock;
    }

    public String getVisitedLocation() {
        return VisitedLocation;
    }
    public void setVisitedLocation(String visitedLocation) {
        VisitedLocation = visitedLocation;
    }

    public Long getTimeStamp() {
        return TimeStamp;
    }
    public void setTimeStamp(Long timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getDeviceLock() {
        return DeviceLock;
    }
    public void setDeviceLock(String deviceLock) {
        DeviceLock = deviceLock;
    }

}
