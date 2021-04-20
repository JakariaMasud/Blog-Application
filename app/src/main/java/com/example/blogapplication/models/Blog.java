package com.example.blogapplication.models;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "blog_table")
public class Blog{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String cover_photo;
    private List<String> categories;
    @Embedded(prefix = "blog_author")
    private Author author;


    public Blog(int id, String title, String description, String cover_photo, List<String> categories, Author author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cover_photo = cover_photo;
        this.categories = categories;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}