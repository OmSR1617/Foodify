package com.example.fooddeliveryapp.Domain;

public class CategoryDomain {
    public CategoryDomain(String title, String pic) {
        this.title = title;
        this.pic = pic;
    }

    private String title;
    private String pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
