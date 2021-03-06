package dnf_UI_32;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import dnf_InterfacesAndExceptions.Location;
import dnf_class.Characters;
import dnf_infomation.GetDictionary;

public class VillageUI extends DnFComposite
{
	private TabFolder villageFolder;
	private Shell shell;
	private EquipmentInfoUI equipUI;
	private TabItem equipTab;
	private EquipmentInfoUI avatarUI;
	private TabItem avatarTab;
	private Button toDungeonButton;
	private Button selectCharacterButton;
	private Characters character;
	private Canvas version;
	private Inventory inventory;
	
	VillageUI(Shell shell, Characters character)
	{
		this.character=character;
		this.shell=shell;
		mainComposite = new Composite(shell, SWT.NONE);
		mainComposite.setLayout(new FormLayout());
		mainComposite.setBackgroundImage(GetDictionary.getBackground(character.getJob(), shell));
		
		toDungeonButton = new Button(mainComposite, SWT.PUSH);
		toDungeonButton.setText("수련의 방 입장");
		
		selectCharacterButton = new Button(mainComposite, SWT.PUSH);
		selectCharacterButton.setText("캐릭터 선택");
	}
	
	public void makeComposite(SkillTree skillTree, Vault vault)
	{
		villageFolder = new TabFolder(mainComposite, SWT.NONE);
		villageFolder.setLayoutData(new FormData());
		mainComposite.layout();
		shell.layout();
		
		inventory = new Inventory(mainComposite, character, this, Location.VILLAGE);
		inventory.setListener(vault);
		vault.setInventory(inventory);
		
		equipTab = new TabItem(villageFolder, SWT.NONE);
		String str1 = "장비";
		equipTab.setText(str1);
		equipUI = new EquipmentInfoUI(villageFolder, character, vault, skillTree, inventory, 0);
		equipTab.setControl(equipUI.getComposite());
		
		avatarTab = new TabItem(villageFolder, SWT.NONE);
		String str2 = "아바타/크리쳐/휘장";
		avatarTab.setText(str2);
		avatarUI = new EquipmentInfoUI(villageFolder, character, vault, skillTree, inventory, 1);
		avatarTab.setControl(avatarUI.getComposite());
		
		villageFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
				if(villageFolder.getSelection()[0].getText().equals(str1)) equipUI.itemInfo.renew();
				else if(villageFolder.getSelection()[0].getText().equals(str2)) avatarUI.itemInfo.renew();
			}
		});
		
		FormData inventoryData = new FormData();
		inventoryData.top = new FormAttachment(0, villageFolder.computeSize(-1, -1).y+5);
		inventoryData.bottom = new FormAttachment(100, -5);
		inventory.getComposite().setLayoutData(inventoryData);
		
		FormData buttonData = new FormData(100, 100);
		buttonData.bottom = new FormAttachment(100, -10);
		buttonData.right = new FormAttachment(100, -10);
		toDungeonButton.setLayoutData(buttonData);
		
		buttonData = new FormData(100, 100);
		buttonData.bottom = new FormAttachment(toDungeonButton, -10);
		buttonData.right = new FormAttachment(100, -10);
		selectCharacterButton.setLayoutData(buttonData);
		
		version = new Canvas(mainComposite, SWT.NO_REDRAW_RESIZE | SWT.TRANSPARENT);
		FormData formData = new FormData(200, 40);
		formData.right = new FormAttachment(toDungeonButton, -10);
		formData.bottom = new FormAttachment(100, 0);
		version.setLayoutData(formData);
		version.addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
	         e.gc.drawImage(GetDictionary.versionImage, 0, 0);
	        }
	    });
		
		skillTree.superInfo=this;
		mainComposite.layout();
		shell.setText("수련의 방");
	}
	
	@Override
	public void renew()
	{
		equipUI.renew();
		avatarUI.renew();
	}
	
	public void disposeContent()
	{
		villageFolder.dispose();
		inventory.getComposite().dispose();
	}
	
	public Button get_toDungeonButton() {return toDungeonButton;}
	public Button get_selectCharacterButton() {return selectCharacterButton;}
}