package com.bcit.MA_KWON;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Source implements Serializable {

    @SerializedName("id")
    private String sourceId;
    @SerializedName("name")
    private String name;

    public Source(String sourceId, String name) {
        this.sourceId = sourceId;
        this.name = name;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getName() {
        return name;
    }
}
