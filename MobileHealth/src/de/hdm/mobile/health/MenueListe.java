package de.hdm.mobile.health;
import java.util.ArrayList;
import java.util.List;


public class MenueListe {

	private List<DrawerItem> dataList;
	public MenueListe() {
		 dataList = new ArrayList<DrawerItem>();
		 dataList.add(new DrawerItem("Home", R.drawable.ic_action_email));
		 dataList.add(new DrawerItem("Trainingspl�ne", R.drawable.ic_action_good));
		 dataList.add(new DrawerItem("Trainingtage", R.drawable.ic_action_gamepad));
		 dataList.add(new DrawerItem("�bungen", R.drawable.ic_action_labels));
		 dataList.add(new DrawerItem("Statisitik", R.drawable.ic_action_search));
		 dataList.add(new DrawerItem("About", R.drawable.ic_action_cloud));
	}
	public List<DrawerItem> getDataList() {
		return dataList;
	}
	public void setDataList(List<DrawerItem> dataList) {
		this.dataList = dataList;
	}
	
	
}
