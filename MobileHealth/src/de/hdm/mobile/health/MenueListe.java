package de.hdm.mobile.health;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse welche bei der Initialiserung eine Liste mit Menüelementen erstellt. 
 * @author remi
 *
 */
public class MenueListe {

	private List<DrawerItem> dataList;
	public MenueListe() {
		 dataList = new ArrayList<DrawerItem>();
		 dataList.add(new DrawerItem("Logbuch", R.drawable.ic_action_email));
		 dataList.add(new DrawerItem("Ziele", R.drawable.ic_action_good));
		 dataList.add(new DrawerItem("Neue Lebensmittel", R.drawable.ic_action_gamepad));
		
	}
	public List<DrawerItem> getDataList() {
		return dataList;
	}
	public void setDataList(List<DrawerItem> dataList) {
		this.dataList = dataList;
	}
	
	
}
