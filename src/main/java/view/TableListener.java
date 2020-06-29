package view;

import java.util.EventListener;

import entity.Complaint;

public interface TableListener extends EventListener{
	public void tableEventDeleted(int id);
}
