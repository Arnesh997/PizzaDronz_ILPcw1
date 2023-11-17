package uk.ac.ed.inf.Model;public class DroneMove {    private String orderNo;    private double fromLongitude;    private double fromLatitude;    private double angle;    private double toLongitude;    private double toLatitude;    // Constructor    public DroneMove(String orderNo, double fromLongitude, double fromLatitude,                     double angle, double toLongitude, double toLatitude) {        this.orderNo = orderNo;        this.fromLongitude = fromLongitude;        this.fromLatitude = fromLatitude;        this.angle = angle;        this.toLongitude = toLongitude;        this.toLatitude = toLatitude;    }    // Public no-argument constructor (optional, but can help with certain serialization/deserialization frameworks)    public DroneMove() {    }    // Getters    public String getOrderNo() {        return orderNo;    }    public double getFromLongitude() {        return fromLongitude;    }    public double getFromLatitude() {        return fromLatitude;    }    public double getAngle() {        return angle;    }    public double getToLongitude() {        return toLongitude;    }    public double getToLatitude() {        return toLatitude;    }    // Setters (optional, useful if you need to change properties after object creation)    public void setOrderNo(String orderNo) {        this.orderNo = orderNo;    }    public void setFromLongitude(double fromLongitude) {        this.fromLongitude = fromLongitude;    }    public void setFromLatitude(double fromLatitude) {        this.fromLatitude = fromLatitude;    }    public void setAngle(double angle) {        this.angle = angle;    }    public void setToLongitude(double toLongitude) {        this.toLongitude = toLongitude;    }    public void setToLatitude(double toLatitude) {        this.toLatitude = toLatitude;    }}