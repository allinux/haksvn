package com.haks.haksvn.general.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haks.haksvn.common.message.model.DefaultMessage;
import com.haks.haksvn.common.message.model.ResultMessage;
import com.haks.haksvn.general.model.CommitLogTemplate;
import com.haks.haksvn.general.model.MailTemplate;
import com.haks.haksvn.general.service.GeneralService;

@Controller
@RequestMapping(value="/configuration/general")
public class GeneralAjaxController {
     
    @Autowired
    private GeneralService generalService;
    
    @RequestMapping(value="/commitLog/{repositoryKey}", method=RequestMethod.GET)
    public @ResponseBody CommitLogTemplate retrieveCommitLogTemplate(
    												@RequestParam(value = "logType", required = true) String logTemplateTypeCodeId,
													@PathVariable String repositoryKey){
    	return generalService.retrieveCommitLogTemplate(repositoryKey, logTemplateTypeCodeId);
    }
    
    @RequestMapping(value="/commitLog/default", method=RequestMethod.GET)
    public @ResponseBody CommitLogTemplate retrieveDefaultCommitLogTemplate(
    												@RequestParam(value = "logType", required = true) String logTemplateTypeCodeId){
    	return generalService.retrieveDefaultCommitLogTemplate(logTemplateTypeCodeId);
    }
    
    @RequestMapping(value="/commitLog/{repositoryKey}", method=RequestMethod.POST)
    public @ResponseBody DefaultMessage saveCommitLogTemplate(
													@PathVariable String repositoryKey,
													@RequestParam(value = "logType", required = true) String logTemplateTypeCodeId,
													@ModelAttribute("commitLogTemplate") CommitLogTemplate commitLogTemplate){
    	generalService.saveCommitLogTemplate(commitLogTemplate, logTemplateTypeCodeId);
    	return new ResultMessage("save success");
    }
    
    @RequestMapping(value="/mailTemplate/{repositoryKey}", method=RequestMethod.GET)
    public @ResponseBody MailTemplate retrieveMailTemplate(
    												@RequestParam(value = "template", required = true) String mailTemplateTypeCodeId,
													@PathVariable String repositoryKey){
    	return generalService.retrieveMailTemplate(repositoryKey, mailTemplateTypeCodeId);
    }
    
    @RequestMapping(value="/mailTemplate/default", method=RequestMethod.GET)
    public @ResponseBody MailTemplate retrieveDefaultMailTemplate(
    												@RequestParam(value = "template", required = true) String mailTemplateTypeCodeId){
    	return generalService.retrieveDefaultMailTemplate(mailTemplateTypeCodeId);
    }
    
    @RequestMapping(value="/mailTemplate/{repositoryKey}", method=RequestMethod.POST)
    public @ResponseBody DefaultMessage saveMailTemplate(
													@PathVariable String repositoryKey,
													@RequestParam(value = "template", required = true) String mailTemplateTypeCodeId,
													@ModelAttribute("mailTemplate") MailTemplate mailTemplate){
    	generalService.saveMailTemplate(mailTemplate, mailTemplateTypeCodeId);
    	return new ResultMessage("save success");
    }
    
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception e, HttpServletResponse response) throws Exception{
    	e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(e.getMessage());
        response.flushBuffer();
    }
}
