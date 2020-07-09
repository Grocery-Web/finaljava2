package view;

import java.util.ArrayList;
import java.util.EventListener;

import entity.Complaint;
import entity.Criminal;

public interface TableComplaintDetailListener extends EventListener{
	public void tableEventDeleted(int id);
	public void tableEventUpdated(Complaint cpl);
	public void tableEventSubmited(Complaint cpl, ArrayList<Criminal> lstCri);
}
