package Client;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class Tab {
	String tabname;
	ListView getfield;
	ListView sendfield;
	ObservableList<String> getitems;
	ObservableList<String> senditems;

	public Tab(String tabname, ListView getfield, ListView sendfield, ObservableList<String> getitems, ObservableList<String> senditems) {
		this.tabname = tabname;
		this.getfield = getfield;
		this.sendfield = sendfield;
		this.getitems = getitems;
		this.senditems = senditems;
	}

	public String getTabname() {

		return tabname;
	}

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}

	public ListView getGetfield() {
		return getfield;
	}

	public void setGetfield(ListView getfield) {
		this.getfield = getfield;
	}

	public ListView getSendfield() {
		return sendfield;
	}

	public void setSendfield(ListView sendfield) {
		this.sendfield = sendfield;
	}

	public ObservableList<String> getGetitems() {
		return getitems;
	}

	public void setGetitems(ObservableList<String> getitems) {
		this.getitems = getitems;
	}

	public ObservableList<String> getSenditems() {
		return senditems;
	}

	public void setSenditems(ObservableList<String> senditems) {
		this.senditems = senditems;
	}
}
