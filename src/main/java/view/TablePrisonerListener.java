package view;

import java.util.EventListener;

public interface TablePrisonerListener extends EventListener {
	public void tableEventDetail(int id);
	public void tableEventRelease(int id);
	public void tableEventTransfer(int id);
}
