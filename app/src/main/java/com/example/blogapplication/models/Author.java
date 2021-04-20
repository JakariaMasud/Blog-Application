package com.example.blogapplication.models;



public class Author{
    private int id;
    private String name;
    private String avatar;
    private String profession;

    public Author( int id,String name, String avatar, String profession) {
        this.id=id;
        this.name = name;
        this.avatar = avatar;
        this.profession = profession;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}