package com.daihao.DTO;

public class DepartTopDTO
{
    private String id;
    private String name;
    private int staffCount;
    private int numGoodRecord;
    private int numBadRecord;
    private int totalPoint;

    public DepartTopDTO() {
    }

    public DepartTopDTO(String id, String name, int staffCount, int numGoodRecord, int numBadRecord, int totalPoint) {
        this.id = id;
        this.name = name;
        this.staffCount = staffCount;
        this.numGoodRecord = numGoodRecord;
        this.numBadRecord = numBadRecord;
        this.totalPoint = totalPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(int staffCount) {
        this.staffCount = staffCount;
    }

    public int getNumGoodRecord() {
        return numGoodRecord;
    }

    public void setNumGoodRecord(int numGoodRecord) {
        this.numGoodRecord = numGoodRecord;
    }

    public int getNumBadRecord() {
        return numBadRecord;
    }

    public void setNumBadRecord(int numBadRecord) {
        this.numBadRecord = numBadRecord;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }
}
