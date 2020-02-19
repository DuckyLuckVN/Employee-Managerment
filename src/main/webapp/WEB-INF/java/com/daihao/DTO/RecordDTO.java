package com.daihao.DTO;

public class RecordDTO
{
    private int staffId;
    private String staffPhoto;
    private String staffName;
    private String departName;
    private int numRecordGood;
    private int numRecordBad;

    public RecordDTO() {
    }

    public RecordDTO(int staffId, String staffPhoto, String staffName, String departName, int numRecordGood, int numRecordBad) {
        this.staffId = staffId;
        this.staffPhoto = staffPhoto;
        this.staffName = staffName;
        this.departName = departName;
        this.numRecordGood = numRecordGood;
        this.numRecordBad = numRecordBad;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffPhoto() {
        return staffPhoto;
    }

    public void setStaffPhoto(String staffPhoto) {
        this.staffPhoto = staffPhoto;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getNumRecordGood() {
        return numRecordGood;
    }

    public void setNumRecordGood(int numRecordGood) {
        this.numRecordGood = numRecordGood;
    }

    public int getNumRecordBad() {
        return numRecordBad;
    }

    public void setNumRecordBad(int numRecordBad) {
        this.numRecordBad = numRecordBad;
    }
}
