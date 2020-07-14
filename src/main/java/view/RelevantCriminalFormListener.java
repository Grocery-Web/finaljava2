package view;

import java.util.EventListener;

import entity.ComplaintDetail;
import entity.Criminal;

public interface RelevantCriminalFormListener extends EventListener {
	public void criminalFormEventListener(ComplaintDetail comDetail, Criminal criminal);
}
