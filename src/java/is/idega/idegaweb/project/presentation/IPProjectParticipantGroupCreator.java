package is.idega.idegaweb.project.presentation;

import com.idega.idegaweb.presentation.IWAdminWindow;
import com.idega.presentation.ui.TextInput;
import com.idega.presentation.ui.TextArea;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.RadioGroup;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.Table;
import com.idega.presentation.ui.FramePane;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Text;
import com.idega.core.accesscontrol.business.LoginDBHandler;
import com.idega.core.user.data.User;
import com.idega.util.idegaTimestamp;
import com.idega.core.data.GenericGroup;
import com.idega.core.accesscontrol.data.PermissionGroup;
import com.idega.builder.dynamicpagetrigger.data.PageTriggerInfo;

import is.idega.idegaweb.project.business.ProjectBusiness;
import is.idega.idegaweb.project.data.IPParticipantGroup;

import java.sql.SQLException;
import java.util.Vector;

/**
 * Title:        IW Project
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="gummi@idega.is">Gu�mundur �g�st S�mundsson</a>
 * @version 1.0
 */

public class IPProjectParticipantGroupCreator extends IWAdminWindow {

  private Text groupNameText;
  private Text descriptionText;
  private Text groupTypeText;

  private TextInput groupNameField;
  private TextArea descriptionField;
  private RadioGroup groupTypeField;

  private SubmitButton okButton;
  private SubmitButton cancelButton;

  private Vector groupType;

  private Form myForm;

  public static String okButtonParameterValue = "ok";
  public static String cancelButtonParameterValue = "cancel";
  public static String submitButtonParameterName = "submit";

  public static String groupNameFieldParameterName = "groupName";
  public static String descriptionFieldParameterName = "description";
  public static String groupTypeFieldParameterName = "group_type";

  private ProjectBusiness business;

  private String rowHeight = "37";

  public IPProjectParticipantGroupCreator() {
    super();
    groupType = new  Vector();
    this.setName("idegaWeb Project - Stofna H�p");
    this.setHeight(340);
    this.setWidth(390);
    //this.setBackgroundColor("#d4d0c8");
    myForm = new Form();

    initializeTexts();
    initializeFields();
    init();
  }

  protected void initializeTexts(){
    groupNameText = new Text("Group name:");
    descriptionText = new Text("Description : ");
    groupTypeText = new Text("Type: ");
  }

  protected void initializeFields(){
    groupNameField = new TextInput(groupNameFieldParameterName);
    groupNameField.setLength(20);

    descriptionField = new TextArea(descriptionFieldParameterName);
    descriptionField.setHeight(3);
    descriptionField.setWidth(20);

    groupTypeField = new RadioGroup(groupTypeFieldParameterName);
    groupTypeField.setWidth(1);

    okButton = new SubmitButton("     OK     ",submitButtonParameterName,okButtonParameterValue);
    cancelButton = new SubmitButton(" Cancel ",submitButtonParameterName,cancelButtonParameterValue);

  }

  public void init(){
    this.addGroupType(GenericGroup.class);
    this.addGroupType(PermissionGroup.class);
  }



  public void addGroupType(Class genricGroup){
    groupType.add(genricGroup);
  }


  public void lineUpElements(){

    Table frameTable = new Table(1,3);
    frameTable.setAlignment("center");
    frameTable.setVerticalAlignment("middle");
    frameTable.setCellpadding(0);
    frameTable.setCellspacing(0);

    // nameTable begin
    Table nameTable = new Table(1,4);
    nameTable.setCellpadding(0);
    nameTable.setCellspacing(0);
    nameTable.setHeight(1,rowHeight);
    nameTable.setHeight(2,rowHeight);

    nameTable.add(groupNameText,1,1);
    nameTable.add(groupNameField,1,2);
    nameTable.add(descriptionText,1,3);
    nameTable.add(descriptionField,1,4);
    // nameTable end
/*
    // Property begin
    int size = groupType.size();
    if(size > 1){
      Table propertyTable = new Table(2,1);
      propertyTable.setCellpadding(0);
      propertyTable.setCellspacing(0);
      propertyTable.setHeight(1,rowHeight);

      FramePane frPane = new FramePane("Type");


      for (int i = 0; i < groupType.size(); i++){
        String value = ((GenericGroup)com.idega.core.data.GenericGroupBMPBean.getStaticInstance((Class)groupType.get(i))).getGroupTypeValue();
        String text = value.substring(1);
        text = value.substring(0,1).toUpperCase() + text;

        if(i==0){
          groupTypeField.addRadioButton(value,new Text(text),true);
        }else{
          groupTypeField.addRadioButton(value,new Text(text));
        }
      }

      frPane.add(groupTypeField);
      frPane.setWidth(200);
      propertyTable.add(frPane,1,1);
      frameTable.add(propertyTable,1,2);
    }else if (size == 1){
      frameTable.add(new HiddenInput(((GenericGroup)com.idega.core.data.GenericGroupBMPBean.getStaticInstance((Class)groupType.get(0))).getGroupTypeValue()));
    }else{
      frameTable.add(new HiddenInput(groupTypeFieldParameterName,com.idega.core.data.GenericGroupBMPBean.getStaticInstance().getGroupTypeValue()));
    }
    // Property end

*/

    // buttonTable begin
    Table buttonTable = new Table(3,1);
    buttonTable.setCellpadding(0);
    buttonTable.setCellspacing(0);
    buttonTable.setHeight(1,rowHeight);
    buttonTable.setWidth(2,"5");

    buttonTable.add(okButton,1,1);
    buttonTable.add(cancelButton,3,1);
    // buttonTable end


    frameTable.add(nameTable,1,1);


    frameTable.add(buttonTable,1,3);
    frameTable.setAlignment(1,3,"right");

    myForm.add(frameTable);

  }



  public void commitCreation(IWContext iwc) throws Exception{

    GenericGroup newGroup;

    String name = iwc.getParameter(this.groupNameFieldParameterName);
    String description = iwc.getParameter(this.descriptionFieldParameterName);
//    String type = iwc.getParameter(this.groupTypeFieldParameterName);
    String type = is.idega.idegaweb.project.data.IPParticipantGroupBMPBean.getStaticGroupInstance().getGroupTypeValue();
 /*
   if(type == null){
      throw new Exception("no group_type selected");
    }
*/
    /**
     * @todo implement connection between Project instnces and participantgroups ()
     */
    business.createParticipantGroup(((com.idega.builder.dynamicpagetrigger.data.PageTriggerInfoHome)com.idega.data.IDOLookup.getHomeLegacy(PageTriggerInfo.class)).findByPrimaryKeyLegacy(1),name,description);

  }


  public void main(IWContext iwc) throws Exception {
    myForm.empty();
    this.add(myForm);
    business = ProjectBusiness.getInstance();

    String submit = iwc.getParameter("submit");
    if(submit != null){
      if(submit.equals("ok")){
        this.commitCreation(iwc);
        this.close();
        this.setParentToReload();
      }else if(submit.equals("cancel")){
        this.close();
      }
    } else {
      lineUpElements();
    }
  }


}
