package uk.ac.ed.inf;import uk.ac.ed.inf.ilp.data.LngLat;public class Node {    private Node parent;    private LngLat location;    private double gCost;    private double hCost;    private double fCost;    public Node(Node parent, LngLat location, double gCost, double hCost){        this.parent = parent;        this.location = location;        this.gCost = gCost;        this.hCost = hCost;        this.fCost = gCost + hCost;    }    public Node getParent(){        return this.parent;    }    public LngLat getLocation(){        return this.location;    }    public double getGCost(){        return this.gCost;    }    public double getHCost(){        return this.hCost;    }    public double getFCost(){        return this.fCost;    }    public void setParent(Node parent){        this.parent = parent;    }    public void setgCost(double gCost){        this.gCost = gCost;    }    public void setfCost(double fCost){        this.fCost = fCost;    }}