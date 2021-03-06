package dnf_UI_32;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import dnf_InterfacesAndExceptions.DnFColor;
import dnf_InterfacesAndExceptions.InterfaceSize;
import dnf_class.Characters;
import dnf_class.Skill;
import dnf_class.SwitchingSkill;

public class SkillTree extends Dialog{

	Characters character;
	LinkedList<ItemButton<Skill>>[] skillListByLevel;
	LinkedList<ItemButton<Skill>> TPSkillList;
	int[] skillLevel;
	Point contentSize;
	DnFComposite superInfo;
	Button burningButton;
	Button contractButton;
	Combo representSkillCombo;
	
	public SkillTree(Shell parent, Characters character, DnFComposite superInfo)
	{
		super(parent);
		this.character=character;
		this.superInfo=superInfo;
		
		skillLevel = new int[] { 1, 5, 10, 15, 20, 25, 30, 35, 40, 45, 48, 50, 55, 60, 70, 75, 80, 85 };
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected Control createDialogArea(Composite parent)
	{
		skillListByLevel = (LinkedList<ItemButton<Skill>>[]) new LinkedList<?>[skillLevel.length];
		TPSkillList = new LinkedList<ItemButton<Skill>>();
		
		Composite content = (Composite) super.createDialogArea(parent);
		content.getShell().setBackground(DnFColor.infoBackground);
		RowLayout contentLayout = new RowLayout(SWT.VERTICAL);
		contentLayout.spacing=10;
		contentLayout.wrap=false;
		content.setLayout(contentLayout);
		
		Label infoLabel = new Label(content, SWT.NONE);
		infoLabel.setText(" 레벨은 \'던전 스펙\'을 기준으로 한 스킬레벨/표기수치 입니다.\n"
				+ " 해당 수치는 TP / 크로니클 / 스증뎀 / 일부 스증뎀(특정 스킬 수치를 올리는 스증뎀)이 포함된 수치입니다.\n"
				+ " 일반스킬은 마스터(좌클릭) 혹은 0레벨(우클릭) 만 가능합니다.\n"
				+ " TP스킬은 +1레벨(좌클릭), -1레벨(우클릭) 조정이 가능합니다.\n");
		infoLabel.setLayoutData(new RowData());
		
		Group skillGroup = new Group(content, SWT.NONE);
		skillGroup.setData(new RowData());
		skillGroup.setLayout(new FormLayout());
		skillGroup.setText("일반스킬 목록");
		
		int bSize = InterfaceSize.SKILL_BUTTON_SIZE;
		
		Iterator<Skill> charSkillIter = character.getSkillList().iterator();
		Skill tempSkill=null;
		while(true){
			if(charSkillIter.hasNext()){
				tempSkill = charSkillIter.next();
				if(!tempSkill.isTPSkill() && !tempSkill.isOptionSkill() && !tempSkill.isSubSkill()) break;
			}
			else break;
		}
		
		for(int i=0; i<skillLevel.length; i++)
		{
			skillListByLevel[i] = new LinkedList<ItemButton<Skill>>();
			
			while(true) {
				if(tempSkill.firstLevel==skillLevel[i])
				{
					((LinkedList<ItemButton<Skill>>)skillListByLevel[i]).add(new ItemButton<Skill>(skillGroup, tempSkill, bSize, bSize));
					if(!charSkillIter.hasNext()) break;
					while(true){
						if(charSkillIter.hasNext()){
							tempSkill = charSkillIter.next();
							if(!tempSkill.isTPSkill() && !tempSkill.isOptionSkill() && !tempSkill.isSubSkill()) break;
						}
						else break;
					}
				}
				else break;
			}
		}
		
		FormData buttonData;
		
		int index=0;
		Button upButton = null;
		Button leftButton = null;
		
		for(LinkedList<ItemButton<Skill>> list : skillListByLevel)
		{
			leftButton = new Button(skillGroup, SWT.BORDER);
			leftButton.setEnabled(false);
			leftButton.setText("\n"+skillLevel[index]);
			buttonData = new FormData(20, bSize+5);
			
			if(index==9 || index==0){
				upButton=null;
				buttonData.top = new FormAttachment(0, 3);
			}
			else buttonData.top = new FormAttachment(upButton, 3);
			if(index>=9) buttonData.left = new FormAttachment(0, bSize*8+20);
			else buttonData.left = new FormAttachment(0, 3);
			leftButton.setLayoutData(buttonData);
			
			if(list.isEmpty()){
				index++;
				leftButton.dispose();
				continue;
			}
			
			int num=0;
			for(ItemButton<Skill> button : list){
				if(num!=0 && num%5==0){
					upButton=leftButton;
					leftButton = new Button(skillGroup, SWT.BORDER);
					leftButton.setEnabled(false);
					leftButton.setText("\n"+skillLevel[index]);
					buttonData = new FormData(20, bSize+5);
					buttonData.top = new FormAttachment(upButton, 3);
					if(index>=9) buttonData.left = new FormAttachment(0, bSize*8+20);
					else buttonData.left = new FormAttachment(0, 3);
					leftButton.setLayoutData(buttonData);
				}
				
				buttonData = new FormData();
				if(upButton == null) buttonData.top = new FormAttachment(0, 3);
				else buttonData.top = new FormAttachment(upButton, 3);
				buttonData.left = new FormAttachment(leftButton, 3);
				
				button.getButton().setLayoutData(buttonData);
				leftButton = button.getButton();
				
				SetListener listenerGroup = new SetListener(button, character, superInfo, parent);
				
				button.getButton().addListener(SWT.MouseEnter, listenerGroup.makeSkillInfoListener(this.getShell()));		// add MouseEnter Event - make composite
				button.getButton().addListener(SWT.MouseExit, listenerGroup.disposeItemInfoListener()); 		// add MouseExit Event - dispose composite
				button.getButton().addListener(SWT.MouseMove, listenerGroup.moveItemInfoListener());			// add MouseMove Event - move composite
				button.getButton().addListener(SWT.MouseDown, listenerGroup.skillLevelModifyListener(this.getShell(), false));
				if(button.getItem() instanceof SwitchingSkill) 
					button.getButton().addListener(SWT.MouseDoubleClick, listenerGroup.skillModifyListener(((SwitchingSkill)button.getItem()).getStatList()));
				
				num++;
			}
			index++;
			upButton = leftButton;
		}
		
		
		Group TPSkillGroup = new Group(content, SWT.NONE);
		TPSkillGroup.setData(new RowData());
		TPSkillGroup.setLayout(new GridLayout(5, true));
		TPSkillGroup.setText("TP스킬 목록");
		
		for(Skill skill : character.getSkillList())
		{
			if(!skill.isTPSkill()) continue;
			ItemButton<Skill> temp = new ItemButton<Skill>(TPSkillGroup, skill, bSize, bSize);
			temp.getButton().setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
			
			SetListener listenerGroup = new SetListener(temp, character, superInfo, parent);
			temp.getButton().addListener(SWT.MouseEnter, listenerGroup.makeSkillInfoListener(this.getShell()));		// add MouseEnter Event - make composite
			temp.getButton().addListener(SWT.MouseExit, listenerGroup.disposeItemInfoListener()); 		// add MouseExit Event - dispose composite
			temp.getButton().addListener(SWT.MouseMove, listenerGroup.moveItemInfoListener());			// add MouseMove Event - move composite
			temp.getButton().addListener(SWT.MouseDown, listenerGroup.skillLevelModifyListener(this.getShell(), true));
			
			TPSkillList.add(temp);
		}
		
		Group contractSetGroup = new Group(content, SWT.NONE);
		contractSetGroup.setData(new RowData());
		GridLayout contractLayout = new GridLayout(2, false);
		contractLayout.marginLeft=20;
		contractSetGroup.setLayout(contractLayout);
		contractSetGroup.setText("달계 / 버닝 / 대표스킬 설정");
		
		Label setTP = new Label(contractSetGroup, SWT.NONE);
		setTP.setText("달계 설정");
		setTP.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		
		contractButton = new Button(contractSetGroup, SWT.CHECK);
		contractButton.setText("설정");
		contractButton.setSelection(character.hasContract());
		
		Label setBurning = new Label(contractSetGroup, SWT.NONE);
		setBurning.setText("버닝 설정");
		setBurning.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		
		burningButton = new Button(contractSetGroup, SWT.CHECK);
		burningButton.setText("설정");
		burningButton.setSelection(character.isBurning());
		
		Label setRepresentative = new Label(contractSetGroup, SWT.NONE);
		setRepresentative.setText("대표스킬 설정");
		setRepresentative.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		
		representSkillCombo = new Combo(contractSetGroup, SWT.READ_ONLY);
		String[] items = new String[character.getRepresentableSkillList().size()];
		int i=0;
		for(Skill skill : character.getRepresentableSkillList())
			items[i++] = skill.getItemName();
		representSkillCombo.setItems(items);
		representSkillCombo.setText(character.getRepresentSkill().getName());
		
		contentSize = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		return content;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
	    super.configureShell(newShell);
	    newShell.setText("스킬트리");
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	    createButton(parent, IDialogConstants.CANCEL_ID, "나가기", false);
	}
	
	@Override
	protected Point getInitialSize() {
	    return new Point(contentSize.x+130, contentSize.y+100);
	}
	
	@Override
	public boolean close()
	{
		LinkedList<Skill> list = new LinkedList<Skill>();
		
		character.setBurning(burningButton.getSelection());
		boolean contractChanged = (character.hasContract() != contractButton.getSelection());
		if(contractChanged)
			character.setContract(contractButton.getSelection());
		
		character.setRepresentSkill(representSkillCombo.getText());
		
		for(LinkedList<ItemButton<Skill>> l1 : skillListByLevel){
			for(ItemButton<Skill> l2 : l1){
				if(contractChanged){
					Skill skill = l2.getItem();
					if(skill.getActiveEnabled()) skill.masterSkill(character.getLevel(), contractButton.getSelection());
				}
				list.add(l2.getItem());
			}
		}
		
		for(ItemButton<Skill> l1 : TPSkillList)
			list.add(l1.getItem());
		
		Collections.sort(list);

		character.setSkillLevel(list);
		superInfo.renew();
		return super.close();
	}
}

