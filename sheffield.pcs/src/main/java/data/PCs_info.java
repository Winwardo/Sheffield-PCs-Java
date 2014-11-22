package data;

import java.util.Date;

public class PCs_info {
    public long buildingId;
    public int current;
    public Date date;

    public PCs_info(long buildingId, int current) {
	this.buildingId = buildingId;
	this.current = current;
    }

    public PCs_info(long buildingId, int current, Date date) {
	this.buildingId = buildingId;
	this.current = current;
	this.date = date;
    }
}
