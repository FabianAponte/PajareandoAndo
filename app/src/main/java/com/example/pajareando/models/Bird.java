package com.example.pajareando.models;

import android.content.Context;

import com.example.pajareando.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bird {
    public static final String TABLE_NAME = "birds";

    int id;
    String name, type, size, color1, color2, color3, color4, review, imagePath, longitud, latitud, photoDate;
    boolean moreColors;
    Context context;

    public Bird(
            int id,
            String name,
            String type,
            String size,
            String color1,
            String color2,
            String color3,
            String color4,
            boolean moreColors,
            String review,
            String imagePath,
            String longitud,
            String latitud,
            String photoDate,
            Context context
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.moreColors = moreColors;
        this.review = review;
        this.imagePath = imagePath;
        this.longitud = longitud;
        this.latitud = latitud;
        this.photoDate = photoDate;
        this.context = context;
    }

    public Bird(
            String name,
            String type,
            String size,
            String color1,
            String color2,
            String color3,
            String color4,
            boolean moreColors,
            String review,
            String imagePath,
            String longitud,
            String latitud,
            String photoDate,
            Context context
    ) {
        this.setName(name);
        this.type = type;
        this.size = size;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.moreColors = moreColors;
        this.review = review;
        this.imagePath = imagePath;
        this.longitud = longitud;
        this.latitud = latitud;
        this.photoDate = photoDate;
        this.context = context;
    }

    public void save() {
        Map<String, String> birdInfo = new HashMap<String, String>();

        birdInfo.put("name", this.name);
        birdInfo.put("type", this.type);
        birdInfo.put("size", this.size);
        birdInfo.put("color1", this.color1);
        birdInfo.put("color2", this.color2);
        birdInfo.put("color3", this.color3);
        birdInfo.put("color4", this.color4);
        birdInfo.put("moreColors", this.moreColors ? "yes" : "no");
        birdInfo.put("review", this.review);
        birdInfo.put("imagePath", this.imagePath);
        birdInfo.put("longitud", this.longitud);
        birdInfo.put("latitud", this.latitud);
        birdInfo.put("photoDate", this.photoDate);

        ModelDb.save(birdInfo, Bird.TABLE_NAME, this.context);
    }

    public static ArrayList<Bird> getAll(Context context) {
        ArrayList<Map<String, String>> birdsInfo = ModelDb.getAll(Bird.TABLE_NAME, context);

        ArrayList<Bird> birds = new ArrayList<Bird>();
        try {

        for (Map birdInfo : birdsInfo) {
            birds.add(new Bird(
                birdInfo.get("id") != null ?  Integer.parseInt(birdInfo.get("id").toString()) : 0,
                birdInfo.get("name") != null ? birdInfo.get("name").toString() : "",
                birdInfo.get("type") != null ? birdInfo.get("type").toString() : "",
                birdInfo.get("size") != null ? birdInfo.get("size").toString() : "",
                birdInfo.get("color1") != null ? birdInfo.get("color1").toString() : "",
                birdInfo.get("color2") != null ? birdInfo.get("color2").toString() : "",
                birdInfo.get("color3") != null ? birdInfo.get("color3").toString() : "",
                birdInfo.get("color4") != null ? birdInfo.get("color4").toString() : "",
                birdInfo.get("moreColors").toString().equals("yes"),
                birdInfo.get("review") != null ? birdInfo.get("review").toString() : "",
                birdInfo.get("imagePath") != null ? birdInfo.get("imagePath").toString() : "",
                birdInfo.get("longitud") != null ? birdInfo.get("longitud").toString() : "",
                birdInfo.get("latitud") != null ? birdInfo.get("latitud").toString() : "",
                birdInfo.get("photoDate") != null ? birdInfo.get("photoDate").toString() : "",
                context
            ));
        }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return birds;
    }

    public static Bird findById (Context context, int id) {
        Map<String, String> bird = ModelDb.findById(id, TABLE_NAME, context);

        return new Bird(
                bird.get("id") != null ? Integer.parseInt(bird.get("id").toString()) : 0,
                bird.get("name") != null ? bird.get("name").toString() : "",
                bird.get("type") != null ? bird.get("type").toString() : "",
                bird.get("size") != null ? bird.get("size").toString() : "",
                bird.get("color1") != null ? bird.get("color1").toString() : "",
                bird.get("color2") != null ? bird.get("color2").toString() : "",
                bird.get("color3") != null ? bird.get("color3").toString() : "",
                bird.get("color4") != null ? bird.get("color4").toString() : "",
                bird.get("moreColors").toString().equals("yes"),
                bird.get("review") != null ? bird.get("review").toString() : "",
                bird.get("imagePath") != null ? bird.get("imagePath").toString() : "",
                bird.get("longitud") != null ? bird.get("longitud").toString() : "",
                bird.get("latitud") != null ? bird.get("latitud").toString() : "",
                bird.get("photoDate") != null ? bird.get("photoDate").toString() : "",

                context
        );
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getColor3() {
        return color3;
    }

    public String getColor4() {
        return color4;
    }

    public String getReview() {
        return review;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isMoreColors() {
        return moreColors;
    }

    public String getMoreColors() {
        String b;
        if (this.isMoreColors()) {
            b = context.getString(R.string.Tiene_mas_de_4_colores);
        } else {
            b = context.getString(R.string.no_tiene_mas_de_cuatro_colores);
        }
        return b;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getPhotoDate() {
        return photoDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public void setColor4(String color4) {
        this.color4 = color4;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setPhotoDate(String photoDate) {
        this.photoDate = photoDate;
    }

    public void setMoreColors(boolean moreColors) {
        this.moreColors = moreColors;
    }
}
