package com.cryption.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cryption.model.Content;
import com.cryption.service.CryptionDelegate;

@Controller
public class CryptionController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CryptionDelegate delegate;

	@RequestMapping(value="/cryption", method=RequestMethod.POST, params="action=encrypt")
    public String encrypt(@ModelAttribute Content content, Model model) {
		 content = delegate.encryptContent(content);
		 model.addAttribute("content", content);
        return "result";
    }
	
	@RequestMapping(value="/cryption", method=RequestMethod.POST, params="action=decrypt")
    public String decrypt(@ModelAttribute Content content, Model model) {
		content = delegate.decryptContent(content);
		model.addAttribute("content", content);
        return "result";
    }
	
	
	@RequestMapping(value="/cryption", method=RequestMethod.GET)
    public String welcome(Model model) {
		 model.addAttribute("content", new Content(""));
        return "cryption";
    }
}
