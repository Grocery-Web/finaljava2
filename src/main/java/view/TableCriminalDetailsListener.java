package view;

import java.util.EventListener;

import entity.Criminal;

public interface TableCriminalDetailsListener extends EventListener{
	public void tableEventUpdated(Criminal cri);
}
