package uk.ac.ed.inf;import uk.ac.ed.inf.ilp.data.LngLat;import uk.ac.ed.inf.ilp.data.NamedRegion;import uk.ac.ed.inf.ilp.interfaces.LngLatHandling;import java.util.Arrays;import static uk.ac.ed.inf.ilp.constant.SystemConstants.DRONE_IS_CLOSE_DISTANCE;public class MainLngLatHandle implements LngLatHandling {    /**     * get the distance between two positions     * @param startPosition is where the start is     * @param endPosition is where the end is     * @return the euclidean distance between the positions     */    @Override    public double distanceTo(LngLat startPosition, LngLat endPosition) {        double startLat = startPosition.lat();        double startLng = startPosition.lng();        double endLat = endPosition.lat();        double endLng = endPosition.lng();        double calcuLat = Math.pow((endLat - startLat), 2);        double calcuLng = Math.pow((endLng - startLng), 2);        double distance = Math.sqrt(calcuLat + calcuLng);        return distance;    }    /**     * check if two positions are close (< than SystemConstants.DRONE_IS_CLOSE_DISTANCE)     * @param startPosition is the starting position     * @param otherPosition is the position to check     * @return if the positions are close     */    @Override    public boolean isCloseTo(LngLat startPosition, LngLat otherPosition) {        double distance = distanceTo(startPosition, otherPosition);//        Being close to the location will be sufficient, where l1 is close to l2 if//        the distance between l1 and l2 is strictly less than the distance tolerance of 0.00015 degrees.        if (distance < DRONE_IS_CLOSE_DISTANCE){            return true;        }        return false;    }    /**     * check if the @position is in the @region (includes the border)     * @param position to check     * @param region as a closed polygon     * @return if the position is inside the region (including the border)     */    @Override//    public boolean isInRegion(LngLat position, NamedRegion region) {//        //Raycasting algorithm//        String regionName = region.name();//        LngLat[] regionBoundary = region.vertices();//        for (LngLat vertex : regionBoundary) {//            if (position.equals(vertex)) {//                return true;//            }//        }//        // Check if the point is on an edge of the polygon//        int boundaryLength = regionBoundary.length;//        for (int i = 0, j = boundaryLength - 1; i < boundaryLength; j = i++) {//            LngLat vi = regionBoundary[i];//            LngLat vj = regionBoundary[j];//            if (isOnSegment(vi, vj, position)) {//                return true;//            }//        }//        double positionLat = position.lat();//        double positionLng = position.lng();//        int i, j;//        boolean result = false;//        for (i = 0, j = boundaryLength - 1; i < boundaryLength; j = i++) {//            double iLat = regionBoundary[i].lat();//            double iLng = regionBoundary[i].lng();//            double jLat = regionBoundary[j].lat();//            double jLng = regionBoundary[j].lng();//            boolean condition1 = (iLat > positionLat) != (jLat > positionLat);////            // Avoid division by zero//            if (jLat != iLat) {//                boolean condition2 = (positionLng < (jLng - iLng) * (positionLat - iLat) / (jLat - iLat) + iLng);//                if (condition1 && condition2) {//                    result = !result;//                }//            }//        }//        return result;//    }//    public boolean isOnSegment(LngLat vi, LngLat vj, LngLat position) {//        double xi = vi.lng(), xj = vj.lng(), yi = vi.lat(), yj = vj.lat();//        double x = position.lng(), y = position.lat();//        return (xi - x) * (y - yj) == (xj - x) * (y - yi);//    }    public boolean isInRegion(LngLat position, NamedRegion region) {        int counter = 0;        LngLat[] vertices = region.vertices();        for(int i = 0;i< vertices.length ;i++){            LngLat currVertex = vertices[i];            LngLat nextVertex = vertices[(i+1)% vertices.length];            //Checking if the point is on edge            boolean pointOnEdgeCondition1 = position.lng() > Math.min(currVertex.lng(), nextVertex.lng());            boolean pointOnEdgeCondition2 = position.lng() <= Math.max(currVertex.lng(), nextVertex.lng());            if (pointOnEdgeCondition1 && pointOnEdgeCondition2) {                // Calculating the slope of the line between current and endPoint                double lineSlope = (nextVertex.lat() - currVertex.lat()) / (nextVertex.lng() - currVertex.lng());                double interpolatedLatitude = lineSlope * (position.lng() - currVertex.lng()) + currVertex.lat();                if (position.lat() == interpolatedLatitude) {                    return true;                }            }            // Checking if the point is on a vertex            if (position.lng() == currVertex.lng() && position.lat() == currVertex.lat()) {                return true;            }            boolean latitudeCheck =  position.lat() <= Math.max(nextVertex.lat(),currVertex.lat()) && position.lat() > Math.min(nextVertex.lat(),currVertex.lat());            double latitudeFraction = ((position.lat() - currVertex.lat()) / (nextVertex.lat() - currVertex.lat())) * (nextVertex.lng() - currVertex.lng());            boolean longitudeCheck = position.lng() < (currVertex.lng() + latitudeFraction);            if (longitudeCheck && latitudeCheck) {                counter++;            }        }        return counter%2 == 1;    }    /**     * find the next position if an @angle is applied to a @startPosition     * @param startPosition is where the start is     * @param angle is the angle to use in degrees     * @return the new position after the angle is used     */    @Override    public LngLat nextPosition(LngLat startPosition, double angle) {        double[] validAngles = {0, 22.5, 45, 67.5, 90, 112.5, 135, 157.5, 180, 202.5, 225, 247.5, 270, 292.5, 315, 337.5};        boolean isValidAngle = Arrays.stream(validAngles).anyMatch(a -> a == angle);        if (!isValidAngle) {            return startPosition;        }        double startLat = startPosition.lat();        double startLng = startPosition.lng();        double nextLat = startLat;        double nextLng = startLng;        double angleInRadian = Math.toRadians(angle);        double distance = 0.00015;        double distanceLat = distance * Math.sin(angleInRadian);        double distanceLng = distance * Math.cos(angleInRadian);        nextLat += distanceLat;        nextLng += distanceLng;        LngLat nextPosition = new LngLat(nextLng, nextLat);        return nextPosition;    }}