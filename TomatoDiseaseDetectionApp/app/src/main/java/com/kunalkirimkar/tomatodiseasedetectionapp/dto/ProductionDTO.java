package com.kunalkirimkar.tomatodiseasedetectionapp.dto;

public class ProductionDTO {
    private String id;
    private String name;
    private int alignNumber;

    private String productionImage;

    public ProductionDTO(){

    }

    public ProductionDTO(String id, String name, int alignNumber, String skillImage) {
        this.id = id;
        this.name = name;
        this.alignNumber = alignNumber;
        this.productionImage = skillImage;
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

    public int getAlignNumber() {
        return alignNumber;
    }

    public void setAlignNumber(int alignNumber) {
        this.alignNumber = alignNumber;
    }

    public String getSkillImage() {
        return productionImage;
    }

    public void setSkillImage(String skillImage) {
        this.productionImage = skillImage;
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", alignNumber=" + alignNumber +
                ", skillImage='" + productionImage + '\'' +
                '}';
    }
}
