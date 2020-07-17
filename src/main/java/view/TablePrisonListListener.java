package view;

import java.util.EventListener;

import entity.PrisonList;

public interface TablePrisonListListener extends EventListener{
	public void displayPrisonListDetail(int id);
}
