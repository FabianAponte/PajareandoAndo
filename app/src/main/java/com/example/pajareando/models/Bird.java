package com.example.pajareando.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bird {
    public static final String TABLE_NAME = "birds";

    int id;
    String name, type, size, color1, color2, color3, color4, review, imagePath;
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
            Context context
    ) {
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
                birdInfo.get("moreColors") == "yes",
                birdInfo.get("review") != null ? birdInfo.get("review").toString() : "",
                birdInfo.get("imagePath") != null ? birdInfo.get("imagePath").toString() : "",
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
                bird.get("moreColors") == "yes",
                bird.get("review") != null ? bird.get("review").toString() : "",
                bird.get("imagePath") != null ? bird.get("imagePath").toString() : "",
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

    @Override
    public String toString() {
        return "Bird{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color1='" + color1 + '\'' +
                ", color2='" + color2 + '\'' +
                ", color3='" + color3 + '\'' +
                ", color4='" + color4 + '\'' +
                ", review='" + review + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", moreColors=" + moreColors +
                '}';
    }
}
