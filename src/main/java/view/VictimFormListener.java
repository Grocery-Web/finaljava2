package view;

import java.util.EventListener;

import entity.Complaint;
import entity.Victim;

public interface VictimFormListener extends EventListener {
	public void linkNewVictim(Victim victim, Complaint cpl);
}
