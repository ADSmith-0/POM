package com.adam.pom.Objects;

public class Cards {
    private int id, img;
    private String name;

    public Cards(int id, String name, int img){
        this.id = id;
        this.name = name;
        this.img = img;

    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setImg(int img){
        this.img = img;
    }

    public int getImg(){
        return img;
    }
}
