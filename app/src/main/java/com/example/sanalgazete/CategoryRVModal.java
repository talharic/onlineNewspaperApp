package com.example.sanalgazete;

public class CategoryRVModal {
    private String category;
    private String apiName;
    private String categoryImageUrl;

    public String getCategory() {
        return category;
    }

    public String getApiName() {
        return this.apiName;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }

    public CategoryRVModal(String category, String categoryImageUrl) {
        switch (category){
            case "All":
                this.category = "Tümü";
                break;
            case "Technology":
                this.category = "Teknoloji";
                break;
            case "Science":
                this.category = "Bilim";
                break;
            case "Sports":
                this.category = "Spor";
                break;
            case "Business":
                this.category = "İş";
                break;
            case "Entertainment":
                this.category = "Eğlence";
                break;
            case "Health":
                this.category = "Sağlık";
                break;
        }
        this.apiName = category;
        this.categoryImageUrl = categoryImageUrl;
    }
}
