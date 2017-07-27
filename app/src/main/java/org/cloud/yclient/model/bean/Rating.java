package org.cloud.yclient.model.bean;

/**
 * @author d05660ddw
 * @version 1.0 2016/9/22
 */

public class Rating {
    private float max;
    private float average;
    private String stars;
    private float min;

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String starts) {
        this.stars = starts;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "max=" + max +
                ", average=" + average +
                ", stars='" + stars + '\'' +
                ", min=" + min +
                '}';
    }
}
