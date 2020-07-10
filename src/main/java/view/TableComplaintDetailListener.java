package view;

import java.util.EventListener;
import java.util.List;

import entity.Complaint;
import entity.Criminal;

public interface TableComplaintDetailListener extends EventListener{
	public void tableEventDeleted(int id);
	public void tableEventUpdated(Complaint cpl);
	public void tableEventSubmited(Complaint cpl, List<Criminal> lstCri);
}
