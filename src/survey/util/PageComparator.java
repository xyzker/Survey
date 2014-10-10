package survey.util;

import java.util.Comparator;

import survey.model.Page;

public class PageComparator implements Comparator<Page>{

	public int compare(Page p1, Page p2) {
		if((p1.getOrderno() - p2.getOrderno()) > 0) return 1;
		else if((p1.getOrderno() - p2.getOrderno()) == 0) return 0;
		else return -1;
	}
}