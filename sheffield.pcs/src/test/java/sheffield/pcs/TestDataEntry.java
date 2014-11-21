package sheffield.pcs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import data.DataEntry;

public class TestDataEntry {
    double geoEpsilon = 0.00001;

    @Test
    public void getLatitude_Simple() {
	double latitude = DataEntry
		.getLatitude("https://www.sheffield.ac.uk/cics/findapc/map.php?lat=53.382692&lon=-1.487194&loc=Arts%20Tower%20-%2010.12");
	assertEquals(latitude, 53.382692, geoEpsilon);
    }

    @Test
    public void getLatitude_Zero() {
	double latitude = DataEntry
		.getLatitude("https://www.sheffield.ac.uk/cics/findapc/map.php?lat=0.0&lon=-1.487194&loc=Arts%20Tower%20-%2010.12");
	assertEquals(latitude, 0.0, geoEpsilon);
    }

    @Test
    public void getLatitude_None() {
	double latitude = DataEntry
		.getLatitude("https://www.sheffield.ac.uk/cics/findapc/map.php?lon=-1.487194&loc=Arts%20Tower%20-%2010.12");
	assertEquals(latitude, 0.0, geoEpsilon);
    }

    @Test
    public void getLatitude_Negative() {
	double latitude = DataEntry
		.getLatitude("https://www.sheffield.ac.uk/cics/findapc/map.php?lat=-53.382692&lon=-1.487194&loc=Arts%20Tower%20-%2010.12");
	assertEquals(latitude, -53.382692, geoEpsilon);
    }

    @Test
    public void getLongitude_Simple() {
	double longitude = DataEntry
		.getLongitude("https://www.sheffield.ac.uk/cics/findapc/map.php?lat=53.382692&lon=1.487194&loc=Arts%20Tower%20-%2010.12");
	assertEquals(longitude, 1.4871942, geoEpsilon);
    }

    @Test
    public void getLongitude_Zero() {
	double longitude = DataEntry
		.getLongitude("https://www.sheffield.ac.uk/cics/findapc/map.php?lat=0.0&lon=-0.0&loc=Arts%20Tower%20-%2010.12");
	assertEquals(longitude, 0.0, geoEpsilon);
    }

    @Test
    public void getLongitude_None() {
	double longitude = DataEntry
		.getLongitude("https://www.sheffield.ac.uk/cics/findapc/map.php?lat=-1.487194&loc=Arts%20Tower%20-%2010.12");
	assertEquals(longitude, 0.0, geoEpsilon);
    }

    @Test
    public void getLongitude_Negative() {
	double longitude = DataEntry
		.getLongitude("https://www.sheffield.ac.uk/cics/findapc/map.php?lat=-53.382692&lon=-1.487194&loc=Arts%20Tower%20-%2010.12");
	assertEquals(longitude, -1.487194, geoEpsilon);
    }

    @Test
    public void getCurrent_Simple() {
	int current = DataEntry.getCurrent("48");
	assertEquals(48, current);
    }

    @Test
    public void getCurrent_Booked() {
	int current = DataEntry.getCurrent("Room booked");
	assertEquals(0, current);
    }

    @Test
    public void getMax_Simple() {
	Integer max = DataEntry.getMax("48 out of 48 PCs available");
	assertEquals(48, max.intValue());
    }

    @Test
    public void getMax_Booked() {
	Integer max = DataEntry.getMax("Room booked");
	assertEquals(null, max);
    }
}
