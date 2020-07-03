package view;

import java.util.EventListener;

import entity.Complaint;

public interface FormComplaintListener extends EventListener{
	public void insertEventListener(Complaint cpt);
}
