package uk.ac.ed.inf.Pathfinding;import uk.ac.ed.inf.Handler.MainLngLatHandle;import uk.ac.ed.inf.Model.Node;import uk.ac.ed.inf.ilp.constant.SystemConstants;import uk.ac.ed.inf.ilp.data.LngLat;import uk.ac.ed.inf.ilp.data.NamedRegion;import uk.ac.ed.inf.ilp.data.Restaurant;import java.util.*;/** * AStarPathFinder class implements the A* path finding algorithm to find the optimal drone delivery path. * It takes into account various geographical constraints such as no-fly zones, and targets efficient delivery * from restaurants to the delivery points. */public class AStarPathFinder {    private final NamedRegion centralArea;    private final List<NamedRegion> noFlyZones;    private final Restaurant[] restaurants;    private final MainLngLatHandle lngLatHandle = new MainLngLatHandle();    private LngLat endPosition;    /**     * Constructs an instance of AStarPathFinder.     *     * @param restaurants An array of Restaurant objects representing potential starting points for delivery paths.     * @param noFlyZones A list of NamedRegion objects representing areas where drones are not allowed to fly.     * @param centralArea A NamedRegion object representing the central area of operation for drone deliveries.     */    public AStarPathFinder(Restaurant[] restaurants, List<NamedRegion> noFlyZones, NamedRegion centralArea) {        this.restaurants = restaurants;        this.noFlyZones = noFlyZones;        this.centralArea = centralArea;    }    /**     * Use the A* search algorithm to find the optimal path from a given start position to a given end position     * using the euclidean distance to the goal as the heuristic     * (<a href="https://en.wikipedia.org/wiki/A">...</a>*_search_algorithm#Pseudocode).     * @param start The start position of the path.     * @param endPosition The end position of the path.     * @return A list of Node objects representing the optimal path from start to end position.     */    public List<Node> findPath(LngLat start, LngLat endPosition) {        this.endPosition = endPosition;        Node startNode = new Node(null, start, 0, lngLatHandle.distanceTo(start, endPosition),0);        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost));        Set<LngLat> closedSet = new HashSet<>();        Map<LngLat, Node> gScores = new HashMap<>();        gScores.put(start, startNode);        openSet.add(startNode);        while (!openSet.isEmpty()) {            Node currentNode = openSet.poll();            if (lngLatHandle.isCloseTo(currentNode.getLocation(), endPosition)) {                return constructPath(currentNode);            }            closedSet.add(currentNode.getLocation());            List<Node> neighbors = getNeighbors(currentNode);            for (Node neighbor : neighbors) {                if (closedSet.contains(neighbor.getLocation())) {                    continue;                }                if (gScores.containsKey(neighbor.getLocation())) {                    Node existingNeighbor = gScores.get(neighbor.getLocation());                    if (existingNeighbor.getGCost() > neighbor.getGCost()) {                        openSet.remove(existingNeighbor);                        openSet.add(neighbor);                        gScores.put(neighbor.getLocation(), neighbor);                    }                } else {                    openSet.add(neighbor);                    gScores.put(neighbor.getLocation(), neighbor);                }            }        }        // Return an empty path if there is no path from start to end        return new ArrayList<>();    }    /**     * Constructs a path from the start node to the end node by traversing the parent nodes.     * @param lastNode The end node of the path.     * @return A list of Node objects representing the path from start to end.     */    private List<Node> constructPath(Node lastNode) {        List<Node> path = new ArrayList<>();        Node currentNode = lastNode;        while (currentNode != null) {            path.add(0, currentNode); // Add to the beginning of the list to reverse the order            currentNode = currentNode.getParent();        }        return path;    }    /**     * Checks if a given node is valid, i.e. it is not in a no-fly zone and it is not outside the central area     * after having entered it.     * @param node The node to be checked.     * @return True if the node is valid, false otherwise.     */    public boolean isValid(LngLat node) {        for (NamedRegion noFly : noFlyZones) {            if (lngLatHandle.isInRegion(node, noFly)) {                return false;            }        }        // Additional check to ensure that once the drone enters the central area, it doesn't leave        if (lngLatHandle.isInRegion(node, centralArea)) {            return true;        } else {            // If not in central area, check if the path has ever entered the central area            for (Node pathNode = new Node(null, node, 0, 0, 0); pathNode != null; pathNode = pathNode.getParent()) {                if (lngLatHandle.isInRegion(pathNode.getLocation(), centralArea)) {                    // If it has entered, the current node is invalid as it's outside                    return false;                }            }            // If it has never entered the central area, the node is valid            return true;        }    }    /**     * Gets the neighbors of a given node by checking all possible angles of movement.     * @param currNode The node whose neighbors are to be found.     * @return A list of Node objects representing the valid neighbors of the given node.     */    public List<Node> getNeighbors(Node currNode) {        List<Node> neighbors = new ArrayList<>();        double[] angles = {0, 22.5, 45, 67.5, 90, 112.5, 135, 157.5, 180, 202.5, 225, 247.5, 270, 292.5, 315, 337.5};        for (Double angle : angles) {            LngLat nextPos = lngLatHandle.nextPosition(currNode.getLocation(), angle);            if (isValid(nextPos)) {                Node nextNode = new Node(currNode, nextPos, currNode.getGCost() + SystemConstants.DRONE_MOVE_DISTANCE,                        lngLatHandle.distanceTo(nextPos, endPosition), angle);                neighbors.add(nextNode);            }        }        return neighbors;    }}