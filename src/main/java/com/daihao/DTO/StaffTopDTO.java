package com.daihao.DTO;

//@SqlResultSetMapping(
//        name = "StaffTopDTOMapping",
//        entities = @EntityResult(
//                entityClass = StaffTopDTO.class,
//                fields = {
//                        @FieldResult(name = "id", column = "id"),
//                        @FieldResult(name = "name", column = "name"),
//                        @FieldResult(name = "numGoodRecord", column = "numGoodRecord"),
//                        @FieldResult(name = "numBadRecord", column = "numBadRecord"),
//                        @FieldResult(name = "totalPoint", column = "totalPoint"),
//                }
//        )
//)
public class StaffTopDTO
{
    private int id;
    private String name;
    private int numGoodRecord;
    private int numBadRecord;
    private int totalPoint;

    public StaffTopDTO() {
    }

    public StaffTopDTO(int id, String name, int numGoodRecord, int numBadRecord, int totalPoint) {
        this.id = id;
        this.name = name;
        this.numGoodRecord = numGoodRecord;
        this.numBadRecord = numBadRecord;
        this.totalPoint = totalPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
