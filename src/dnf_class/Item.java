package dnf_class;

import dnf_calculator.StatusList;
import dnf_InterfacesAndExceptions.Item_rarity;

@SuppressWarnings("serial")
public class Item implements Cloneable, java.io.Serializable
{
	private String name;										//이름
	private	String iconAddress;									//아이콘
	private Item_rarity rarity;									//희귀도
	public StatusList vStat;									//마을스탯
	public StatusList dStat;									//인던스탯
	
	public Item(String name, String icon, Item_rarity rarity)
	{
		this.name=name;
		iconAddress=icon;
		this.rarity=rarity;
		vStat = new StatusList();
		dStat = new StatusList();
	}
	public Item()
	{
		name="이름없음";
		iconAddress="image\\default.png";
		rarity=Item_rarity.NONE;
		vStat = new StatusList();
		dStat = new StatusList();
	}
	
	public String getName() { return name;}
	public void setName(String name) { this.name = name;}
	
	public String getIcon() { return iconAddress;}
	public void setIcon(String icon) { iconAddress = icon;}
	
	public Item_rarity getRarity() { return rarity;}
	public void setRarity(Item_rarity rarity) { this.rarity = rarity;}
	
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Item)
			if(name.equals(((Item) o).name)) return true;
		return false;
	}
}