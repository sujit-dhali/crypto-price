package com.crypto.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {
	String getCryptoCurrentPrice(HttpServletRequest request, String cryptoName, String ipAddress);
}
