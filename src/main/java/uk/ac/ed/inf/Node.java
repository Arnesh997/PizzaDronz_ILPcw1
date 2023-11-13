package uk.ac.ed.inf;import uk.ac.ed.inf.ilp.data.LngLat;public class Node {    private Node parent;    private LngLat location;    private double gCost;    private double hCost;    private double fCost;    private double angle;    public Node(Node parent, LngLat location, double gCost, double hCost, double angle){        this.parent = parent;        this.location = location;        this.gCost = gCost;        this.hCost = hCost;        this.angle = angle;        this.fCost = this.gCost + this.hCost;    }    public Node getParent(){        return this.parent;    }    public LngLat getLocation(){        return this.location;    }    public double getGCost(){        return this.gCost;    }    public double getHCost(){        return this.hCost;    }    public double getFCost(){        return this.fCost;    }    public double getAngle(){        return this.angle;    }    public void setReverseAngle(double angle){        this.angle = (angle+180)%360;    }}