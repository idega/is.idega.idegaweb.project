/*
 * $Id: CategoryTypeHandler.java,v 1.2 2001/12/13 09:45:20 palli Exp $
 *
 * Copyright (C) 2001 Idega hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 *
 */
package is.idega.idegaweb.project.business;

import com.idega.builder.handler.PropertyHandler;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.IWContext;
import is.idega.idegaweb.project.business.ProjectBusiness;
import is.idega.idegaweb.project.data.IPCategoryType;
import java.util.Iterator;
import java.util.List;
import java.sql.SQLException;


/**
 * @author <a href="gummi@idega.is">Gu�mundur �g�st S�mundsson</a>
 * @version 1.0
 */
public class CategoryTypeHandler implements PropertyHandler {
  /**
   *
   */
  public CategoryTypeHandler() {
  }

  /**
   *
   */
  public List getDefaultHandlerTypes() {
    return(null);
  }

  /**
   *
   */
  public PresentationObject getHandlerObject(String name, String stringValue, IWContext iwc) {
    DropdownMenu menu = new DropdownMenu(name);

    try {
      List list = ProjectBusiness.getInstance().getCategoryTypes();
      if (list != null) {
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
          IPCategoryType item = (IPCategoryType)iter.next();
          menu.addMenuElement(item.getID(),item.getName());
        }
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    menu.setSelectedElement(stringValue);

    return(menu);
  }

  /**
   *
   */
  public void onUpdate(String values[], IWContext iwc) {
  }
}