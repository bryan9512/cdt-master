package com.hufs.cdt;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DowonYoon on 2017-07-11.
 */

@IgnoreExtraProperties
public class FirebasePost {
    public String id;
    public String address;
    public String price;
    public String floor;
    public String room;
    public String option;
    public String guan;
    public String parking;
    public String seol;

    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String id, String address, String price, String floor, String room, String option, String guan, String parking,
 String seol) {
        this.id = id;
        this.address = address;
        this.price = price;
        this.floor=floor;
        this.room=room;
        this.option=option;
        this.guan=guan;
        this.parking=parking;
        this.seol=seol;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("address",address);
        result.put("prce", price);
        result.put("floor", floor);
        result.put("room",room);
        result.put("option",option);
        result.put("guan",guan);
        result.put("parking",parking);
        result.put("seol",seol);

        return result;
    }
}
