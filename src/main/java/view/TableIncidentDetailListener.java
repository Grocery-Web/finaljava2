  
package view;

import java.util.EventListener;

import entity.Complaint;

public interface TableIncidentDetailListener extends EventListener {
	public void tableEventUpdated(Complaint inc);
}