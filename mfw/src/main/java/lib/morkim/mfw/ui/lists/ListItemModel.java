package lib.morkim.mfw.ui.lists;


public abstract class ListItemModel implements Comparable<ListItemModel>{

	public String id;
	
	public boolean isEnabled = true;
	
	@Override
	public int compareTo(ListItemModel arg0) {
		
		return 0;
	}

}
