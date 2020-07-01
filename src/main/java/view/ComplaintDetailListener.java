package view;

import java.util.EventListener;

import entity.Complaint;

public interface ComplaintDetailListener extends EventListener{
	public void updateEventListener(Complaint cpl);
}
