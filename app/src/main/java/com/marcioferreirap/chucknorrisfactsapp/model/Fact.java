package com.marcioferreirap.chucknorrisfactsapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


@Entity
@Parcel
public class Fact{
    @PrimaryKey
    @SerializedName("id")
    public String id;
    @SerializedName("value")
    public String value;
    @SerializedName("url")
    public String url;
    @SerializedName("icon_url")
    public String icon_url;
    @SerializedName("category")
    public String category;

}



