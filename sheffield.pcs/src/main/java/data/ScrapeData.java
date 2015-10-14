package data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ScrapeData {
	public Date scrapeDate;
	public int scrapedComputers;
	public List<PCs_info> pcsInfo;

	public ScrapeData() {
		pcsInfo = new LinkedList<PCs_info>();
	}
}
