package vn.com.example.locationbase.data.model.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.com.example.locationbase.data.model.place.Location;

public class Step {
    @SerializedName("distance")
    @Expose
    private Distance stepDistance;
    @SerializedName("duration")
    @Expose
    private Duration stepDuration;
    @SerializedName("end_location")
    @Expose
    private Location stepEndLocation;
    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;
    @SerializedName("polyline")
    @Expose
    private Polyline polyline;
    @SerializedName("start_location")
    @Expose
    private Location stepStartLocation;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("maneuver")
    @Expose
    private String maneuver;

    public Distance getStepDistance() {
        return stepDistance;
    }

    public void setStepDistance(Distance stepDistance) {
        this.stepDistance = stepDistance;
    }

    public Duration getStepDuration() {
        return stepDuration;
    }

    public void setStepDuration(Duration stepDuration) {
        this.stepDuration = stepDuration;
    }

    public Location getStepEndLocation() {
        return stepEndLocation;
    }

    public void setStepEndLocation(Location stepEndLocation) {
        this.stepEndLocation = stepEndLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public Location getStepStartLocation() {
        return stepStartLocation;
    }

    public void setStepStartLocation(Location stepStartLocation) {
        this.stepStartLocation = stepStartLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }
}
