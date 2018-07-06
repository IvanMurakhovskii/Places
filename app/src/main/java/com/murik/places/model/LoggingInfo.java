
package com.murik.places.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoggingInfo {

    @SerializedName("experiment_id")
    @Expose
    private List<Object> experimentId = null;
    @SerializedName("query_geographic_location")
    @Expose
    private String queryGeographicLocation;

    public List<Object> getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(List<Object> experimentId) {
        this.experimentId = experimentId;
    }

    public LoggingInfo withExperimentId(List<Object> experimentId) {
        this.experimentId = experimentId;
        return this;
    }

    public String getQueryGeographicLocation() {
        return queryGeographicLocation;
    }

    public void setQueryGeographicLocation(String queryGeographicLocation) {
        this.queryGeographicLocation = queryGeographicLocation;
    }

    public LoggingInfo withQueryGeographicLocation(String queryGeographicLocation) {
        this.queryGeographicLocation = queryGeographicLocation;
        return this;
    }

}
