package view;

import java.util.EventListener;
import java.util.List;

import entity.Complaint;
import entity.Criminal;

public interface TableComplaintDetailListener extends EventListener{
	public void tableEventUpdated(Complaint cpl,List<Integer> lstID);
	public void tableEventSubmited(Complaint cpl, List<Criminal> lstCri,List<Integer> lstID);
}
