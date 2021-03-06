/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

import javax.validation.Valid;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.forms.SourceDocumentForm;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.SiteTitle;

/**
 *
 * @author Empt
 */
@Controller
@RequestMapping(value = "/sourcedocument")
@SiteTitle(mainTitle = "{website.title}", separator = " - ")
public class SourceDocumentController
{
    @Autowired private SourceDocumentService sourceDocumentService;
    @Autowired private Mapper mapper;
    
    @RequestMapping(value={"/","/list","/list/"},method = RequestMethod.GET)
    @SiteTitle("{navigation.sourcedocument.list}")
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("sourceDocumentList", sourceDocumentService.getAllDocuments());
        
        return new ModelAndView("sourcedocument_list",mm);
    }
    
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.GET)
    @SiteTitle("{navigation.sourcedocument.create}")
    public ModelAndView create()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("sourceDocumentForm", new SourceDocumentForm());
        
        return new ModelAndView("sourcedocument_create",mm);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.POST)
    @SiteTitle("{navigation.sourcedocument.create}")
    public ModelAndView createSubmit(@Valid @ModelAttribute("sourceDocumentForm") SourceDocumentForm sourceDocumentForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("soureDocumentForm", sourceDocumentForm);
            mm.addAttribute(model);
            
            return new ModelAndView("sourcedocument_create",mm);
        }
        else
        {
            sourceDocumentService.createSourceDocument(mapper.map(sourceDocumentForm, SourceDocument.class));
            return new ModelAndView("redirect:/sourcedocument/list/");
        }
    }

    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value={"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteSourceDocument(@PathVariable Long id)
    {
        sourceDocumentService.deleteSourceDocument(EntityFactory.createSourceDocument(id));

        return new ModelAndView("redirect:/sourcedocument/list/");
    }

    @RequestMapping(value ={"/edit/{id}","/edit/{id}/"},method = RequestMethod.GET)
    @SiteTitle("{entity.sourceDocument.edit}")
    public ModelAndView editSourceDocument(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("sourceDocumentForm", mapper.map(sourceDocumentService.getSourceDocumentByID(id), SourceDocument.class));

        return new ModelAndView("sourcedocument_edit",mm);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @SiteTitle("{entity.sourceDocument.edit}")
    @RequestMapping(value={"/edit","/edit/"}, method = RequestMethod.POST)
    public ModelAndView editSourceDocumentSubmit(@Valid @ModelAttribute("sourceDocumentForm") SourceDocumentForm sourceDocumentForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("sourceDocumentForm", sourceDocumentForm);
            mm.addAttribute(model);
            
            return new ModelAndView("sourcedocument_edit",mm);
        }
        else
        {
            sourceDocumentService.updateSourceDocument(mapper.map(sourceDocumentForm, SourceDocument.class));
            
            return new ModelAndView("redirect:/sourcedocument/list/");
        }
    }
    
}
