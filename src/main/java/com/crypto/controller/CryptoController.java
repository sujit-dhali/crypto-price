package com.crypto.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.crypto.model.CryptoData;
import com.crypto.service.RequestService;

/**
 * Controller class to get cryptocurrency information
 * 
 * @author Sujit Dhali
 *
 */
@Controller
public class CryptoController {
	@RequestMapping("/")
	public String cryptStart() {
		return "crypto-start";
	}

	/**
	 * Controller class to get the cryptocurrency price information
	 * 
	 * @param cryptodata
	 * @param request
	 * @return ModelAndView Crypto price information screen
	 */
	@RequestMapping(value = "/getcryptoprice", method = RequestMethod.GET)
	public ModelAndView getCryptoPrice(@ModelAttribute CryptoData cryptodata, HttpServletRequest request) {
		String crypto = cryptodata.getCrypto();
		String ipAddress = cryptodata.getIpaddress();

		String price = requestService.getCryptoCurrentPrice(request, crypto, ipAddress);
		cryptodata.setPrice(price);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("crypto-price");
		modelAndView.addObject("cryptodata", cryptodata);

		return modelAndView;
	}

	@Autowired
	private RequestService requestService;

}