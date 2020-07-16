package view;

import java.util.EventListener;

import entity.Victim;

public interface TableVictimListener extends EventListener {
	public void linkNewVictim(Victim victim);
}
