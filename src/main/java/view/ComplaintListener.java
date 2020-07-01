package view;

import java.util.EventListener;

import entity.Complaint;

public interface ComplaintListener extends EventListener{
	public void complaintListener(Complaint cpt);
}
