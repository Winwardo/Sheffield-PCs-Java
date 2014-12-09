package other;

public class Geo {
    /**
     * @author 
     *         https://stackoverflow.com/questions/3694380/calculating-distance-between
     *         -two-points-using-latitude-longitude-what-am-i-doi
     * 
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @param unit
     * @return
     */
    public double distance(double lat1, double lon1, double lat2, double lon2) {
	double theta = lon1 - lon2;
	double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
		+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
		* Math.cos(deg2rad(theta));
	dist = Math.acos(dist);
	dist = rad2deg(dist);
	dist = dist * 60 * 1.1515;
	dist = dist * 1.609344;
	return (dist);
    }

    /**
     * @author 
     *         https://stackoverflow.com/questions/3694380/calculating-distance-between
     *         -two-points-using-latitude-longitude-what-am-i-doi
     * 
     * @param deg
     * @return
     */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    /* :: This function converts decimal degrees to radians : */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    private double deg2rad(double deg) {
	return (deg * Math.PI / 180.0);
    }

    /**
     * @author 
     *         https://stackoverflow.com/questions/3694380/calculating-distance-between
     *         -two-points-using-latitude-longitude-what-am-i-doi
     * 
     * @param deg
     * @return
     */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    /* :: This function converts radians to decimal degrees : */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    private double rad2deg(double rad) {
	return (rad * 180.0 / Math.PI);
    }
}
