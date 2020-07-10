package view;

import java.util.EventListener;

import entity.ComplaintDetail;
import entity.Criminal;

public interface RelevantIncidentFormListener extends EventListener {
	public void incidentFormEventListener(ComplaintDetail comDetail, Criminal criminal);
}
