package view;

public interface ToolbarListener {
	public void addPersonEventOccured();
	public void addComplaintEventOccured();
	public void searchTextEventOccured(String txt);
	public void refreshEventOccured();
}
