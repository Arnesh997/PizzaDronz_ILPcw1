package uk.ac.ed.inf;import uk.ac.ed.inf.ilp.constant.SystemConstants;import uk.ac.ed.inf.ilp.data.LngLat;import uk.ac.ed.inf.ilp.data.NamedRegion;import uk.ac.ed.inf.ilp.data.Restaurant;import java.util.*;public class AStarPathController {    private NamedRegion centralArea;    private List<NamedRegion> noFlyZones;    private Restaurant[] restaurants;    private MainLngLatHandle lngLatHandle = new MainLngLatHandle();    private LngLat endPosition;    public AStarPathController(Restaurant[] restaurants, List<NamedRegion> noFlyZones, NamedRegion centralArea) {        this.restaurants = restaurants;        this.noFlyZones = noFlyZones;        this.centralArea = centralArea;    }    public List<Node> findPath(LngLat start, LngLat endPosition) {        System.out.println("Generating Path....");        this.endPosition = endPosition;        Node startNode = new Node(null, start, 0, lngLatHandle.distanceTo(start, endPosition),0);        System.out.println("Start Node: " + startNode.getLocation());        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost));        Set<LngLat> closedSet = new HashSet<>();        Map<LngLat, Node> gScores = new HashMap<>();        gScores.put(start, startNode);        openSet.add(startNode);        while (!openSet.isEmpty()) {            Node currentNode = openSet.poll();            if (lngLatHandle.isCloseTo(currentNode.getLocation(), endPosition)) {                System.out.println("Path Found");                return constructPath(currentNode);            }            closedSet.add(currentNode.getLocation());            List<Node> neighbors = getNeighbors(currentNode);            for (Node neighbor : neighbors) {                if (closedSet.contains(neighbor.getLocation())) {                    continue;                }                if (gScores.containsKey(neighbor.getLocation())) {                    Node existingNeighbor = gScores.get(neighbor.getLocation());                    if (existingNeighbor.getGCost() > neighbor.getGCost()) {                        openSet.remove(existingNeighbor);                        openSet.add(neighbor);                        gScores.put(neighbor.getLocation(), neighbor);                    }                } else {                    openSet.add(neighbor);                    gScores.put(neighbor.getLocation(), neighbor);                }            }        }        return new ArrayList<>(); // Return an empty path if there is no path from start to end    }    private List<Node> constructPath(Node lastNode) {        List<Node> path = new ArrayList<>();        Node currentNode = lastNode;        while (currentNode != null) {            path.add(0, currentNode); // Add to the beginning of the list to reverse the order            currentNode = currentNode.getParent();        }        return path;    }    public boolean isValid(LngLat node) {        for (NamedRegion noFly : noFlyZones) {            if (lngLatHandle.isInRegion(node, noFly)) {                return false;            }        }        // Additional check to ensure that once the drone enters the central area, it doesn't leave        if (lngLatHandle.isInRegion(node, centralArea)) {            return true;        } else {            // If not in central area, check if the path has ever entered the central area            for (Node pathNode = new Node(null, node, 0, 0, 0); pathNode != null; pathNode = pathNode.getParent()) {                if (lngLatHandle.isInRegion(pathNode.getLocation(), centralArea)) {                    return false; // If it has entered, the current node is invalid as it's outside                }            }            return true; // If it has never entered the central area, the node is valid        }    }    public List<Node> getNeighbors(Node currNode) {        List<Node> neighbors = new ArrayList<>();        double[] angles = {0, 22.5, 45, 67.5, 90, 112.5, 135, 157.5, 180, 202.5, 225, 247.5, 270, 292.5, 315, 337.5};        for (Double angle : angles) {            LngLat nextPos = lngLatHandle.nextPosition(currNode.getLocation(), angle);            if (isValid(nextPos)) {                Node nextNode = new Node(currNode, nextPos, currNode.getGCost() + SystemConstants.DRONE_MOVE_DISTANCE, lngLatHandle.distanceTo(nextPos, endPosition), angle);                neighbors.add(nextNode);            }        }        return neighbors;    }}