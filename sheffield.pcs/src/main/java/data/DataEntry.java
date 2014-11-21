package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.jsonIn.OverallData;
import data.jsonIn.SpaceInfoIn;

public class DataEntry {
    public static double getLatitude(String url) {
	String matchedTotal = null;
	Pattern pattern = Pattern.compile(".*lat=(.*?)&");
	Matcher matcher = pattern.matcher(url);
	if (matcher.find()) {
	    matchedTotal = matcher.group(1);
	}

	try {
	    return Double.parseDouble(matchedTotal);
	} catch (Exception e) {
	    return 0.0;
	}
    }

    public static double getLongitude(String url) {
	String matchedTotal = null;
	Pattern pattern = Pattern.compile(".*lon=(.*?)&");
	Matcher matcher = pattern.matcher(url);
	if (matcher.find()) {
	    matchedTotal = matcher.group(1);
	}

	try {
	    return Double.parseDouble(matchedTotal);
	} catch (Exception e) {
	    return 0.0;
	}
    }

    public static Integer getMax(String total_text) {
	String matchedTotal = null;
	Pattern pattern = Pattern.compile(".* out of (.*) PCs available");
	Matcher matcher = pattern.matcher(total_text);
	if (matcher.find()) {
	    matchedTotal = matcher.group(1);
	}

	try {
	    return Integer.parseInt(matchedTotal);
	} catch (Exception e) {
	    return null;
	}
    }

    public static int getCurrent(String current) {
	try {
	    return Integer.parseInt(current);
	} catch (Exception e) {
	    return 0;
	}
    }

    public static void enter(OverallData totalData) {
	for (SpaceInfoIn info : totalData.getResults().getPc_info()) {
	    Long buildingId = Building.findIdFromName(info.getBuilding());

	    if (buildingId == null) {
		double latitude = getLatitude(info.getLocation());
		double longitude = getLongitude(info.getLocation());
		Integer maxComputers = getMax(info.getTotal_text());

		buildingId = Building.insertNew(info.getBuilding(),
			maxComputers, info.getPhoto(), latitude, longitude);
	    }
	}
    }
}
