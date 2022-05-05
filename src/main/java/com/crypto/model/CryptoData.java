package com.crypto.model;

/**
 * Model class holding cryptocurrency and price information
 * 
 * @author Sujit Dhali
 *
 */
public class CryptoData {
	String crypto;
	String ipaddress;
	String price;

	public String getCrypto() {
		return crypto;
	}

	public void setCrypto(String crypto) {
		this.crypto = crypto;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}