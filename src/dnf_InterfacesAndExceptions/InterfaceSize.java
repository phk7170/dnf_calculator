package dnf_InterfacesAndExceptions;

public interface InterfaceSize {
	int INFO_BUTTON_SIZE=40;
	int INVENTORY_BUTTON_SIZE=40;
	
	int USER_INFO_INTERVAL=10;
	int USER_INFO_ITEM_SIZE_X=360; int USER_INFO_ITEM_SIZE_Y=160;
	int USER_STAT_MODE_SIZE_X=360; int USER_STAT_MODE_SIZE_Y=30;
	int USER_INFO_STAT_SIZE_X=360; int USER_INFO_STAT_SIZE_Y=220;
	int USER_INFO_NONSTAT_SIZE_X=380; int USER_INFO_NONSTAT_SIZE_Y=USER_INFO_ITEM_SIZE_Y+USER_INFO_INTERVAL+USER_STAT_MODE_SIZE_Y+USER_INFO_INTERVAL+USER_INFO_STAT_SIZE_Y+8;
	int VAULT_SIZE_Y=600;
}
