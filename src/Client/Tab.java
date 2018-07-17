package Client;

public class Tab {
	String tabname;
	String getfield;
	String sendfield;

	public Tab(String tabname, String getfield, String sendfield) {
		this.tabname = tabname;
		this.getfield = getfield;
		this.sendfield = sendfield;
	}

	public String getTabname() {
		return tabname;
	}

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}

	public String getGetfield() {
		return getfield;
	}

	public void setGetfield(String getfield) {
		this.getfield = getfield;
	}

	public String getSendfield() {
		return sendfield;
	}

	public void setSendfield(String sendfield) {
		this.sendfield = sendfield;
	}
}
