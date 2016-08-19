package dnf_calculator;
//import java.util.HashMap;

interface StatusList
{
	int ELEM_FIRE=0; int  ELEM_WATER=1; int  ELEM_LIGHT=2; int  ELEMT_DARKNESS=3;
	int ATK_PHY=4; int  ATK_MAG=5; int  ATK_NODEF_PHY=6; int  ATK_NODEF_MAG=7; 
	int DEF_DEC_FIXED=8; int  DEF_DEC_PERCENT=9; 
	int DAM_INC=10; int  DAM_CRT=11; int  DAM_ADD=12;
	int STR=13; int INTELL=14; int STA=15; int WILL=16;
	
	public static final int STATNUM = 17;
	public static final int ELEMENTNUM = 4;
}

public class Status {
	
	private StatusInfo<?>[] statInfo; 
	
	public Status()
	{
		statInfo = new StatusInfo<?>[StatusList.STATNUM];
		int i;
		for(i=0; i<StatusList.ELEMENTNUM; i++)
			statInfo[i] = new StatusInfo<StatInfo>(new StatInfo(0));
		
		for(; i<StatusList.STATNUM; i++)
			statInfo[i] = new StatusInfo<ElementInfo>(new ElementInfo(false, 0));
	}
	
	public void setStatus(Status_Public stat)
	{
		
	}
}

class Status_Public
{
	public StatusInfo<?>[] statInfo; 
	
	public Status_Public()
	{
		statInfo = new StatusInfo<?>[StatusList.STATNUM];
		int i;
		for(i=0; i<StatusList.ELEMENTNUM; i++)
			statInfo[i] = new StatusInfo<StatInfo>(new StatInfo(0));
		
		for(; i<StatusList.STATNUM; i++)
			statInfo[i] = new StatusInfo<ElementInfo>(new ElementInfo(false, 0));
	}
	
	public void setStat(int stat, int strength)
	{
		statInfo[stat].setStatus(strength);
	}
	
	public void setElementStat(int stat, int strength, boolean activated)
	{
		if(StatusList.ELEMENTNUM>stat) statInfo[stat].setElementStatus(strength, activated);
		else; //Make Error
	}
}

class StatInfo
{
	int str;
	public StatInfo(int strength)
	{
		str=strength;
	}
	
	public void setInfo(int strength) { str=strength;}
}

class ElementInfo extends StatInfo
{
	boolean hasElement;
	
	public ElementInfo(boolean activated, int strength)
	{
		super(strength);
		hasElement=activated;
	}
	
	public void setInfo(boolean activated, int strength)
	{
		super.setInfo(strength);
		hasElement=activated;
	}
	
	public void setInfo(int strength)
	{
		super.setInfo(strength);
	}
}

class StatusInfo<T extends StatInfo>
{
	private T str;
	
	public StatusInfo(T strength){
		str=strength;
	}
	
	public void setStatus(int strength){
		str.setInfo(strength);
	}
	
	public void setElementStatus(int strength, boolean activated)
	{
		if(str instanceof ElementInfo) ElementInfo temp = (ElementInfo)str;
		temp.setInfo(activated, strength);
	}
}