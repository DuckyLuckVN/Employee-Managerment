package com.daihao.DTO;

public class DepartDTO
{
    private String id;
    private String name;
    private long staffCount;

    public DepartDTO() {
    }

    public DepartDTO(String id, String name, long staffCount) {
        this.id = id;
        this.name = name;
        this.staffCount = staffCount;
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

    public long getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(long staffCount) {
        this.staffCount = staffCount;
    }
}
