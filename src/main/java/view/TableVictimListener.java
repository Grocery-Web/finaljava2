package view;

import java.util.EventListener;

public interface TableVictimListener extends EventListener{
	public void tableEventDeleted(int personalId);
}
