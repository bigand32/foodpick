package com.example.sosimtapa;

public class Model {

String title,image;

    public Model(){}

    public Model(String name,String ima){
        if (name.trim().equals("")){
            name="No Name";
        }
        title=name;
        image=ima;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
