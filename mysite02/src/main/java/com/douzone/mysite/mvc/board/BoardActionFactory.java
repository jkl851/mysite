package com.douzone.mysite.mvc.board;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {
	
	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if("viewform".equals(actionName)) {
			action = new ViewFormAction();
		} else if("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("reply".equals(actionName)) {
			action = new replyAction();
		} else {
			action = new ListAction();
		}
		
		return action;
		
	}
}
