package view;

import java.util.EventListener;

import entity.Criminal;
import entity.Prisoner;

public interface TableCriminalDetailsListener extends EventListener{
	public void tableUpdatedCriminal(Criminal criminal);
	public void tableInsertPrisoner(Prisoner prisoner);
	public void tableDumpEvent();
}
