package view;

import java.util.EventListener;

import entity.Complaint;

public interface TableComplaintsListener extends EventListener{
	public void tableEventDeleted(int id);
	public void tableEventDetail(int id);
}
