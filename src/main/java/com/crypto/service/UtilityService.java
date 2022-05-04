package com.crypto.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

	private static final String EN = "EN";
	private static final String DOLLAR = "$";
	private static final String NUMBER_FORMAT = "###,##0.00";
	private static final String USD = "USD";
	private static final String INR = "INR";

	public Map<String, Object> parseJson(String jsonMessage) {
		JsonParser springParser = JsonParserFactory.getJsonParser();
		Map<String, Object> map = springParser.parseMap(jsonMessage);
		return map;
	}

	public Map<String, Object> parseJson(Map<String, Object> map, String key) {
		Map<String, Object> map2 = new HashMap<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(key)) {
				if (entry.getValue() instanceof Map) {
					map2 = (Map<String, Object>) entry.getValue();
				}
			}
		}
		return map2;
	}

	public String getCurrencyName(String countryCode) {
		Locale locale = new Locale(EN, countryCode);
		String currencyName = null;
		try {
			Currency currency = Currency.getInstance(locale);
			currencyName = currency.getSymbol();
		} catch (Exception e) {
			currencyName = USD;
		}
		return currencyName.equalsIgnoreCase("Rs.")?INR:currencyName;
	}

	public String getCurrencySymbol(String countryCode) {
		Locale locale = new Locale(EN, countryCode);
		String symbol = null;
		try {
			Currency currency = Currency.getInstance(locale);
			symbol = getCurrencyLocaleMap().get(currency) == null ? currency.getSymbol()
					: currency.getSymbol(getCurrencyLocaleMap().get(currency));
		} catch (Exception e) {
			symbol = DOLLAR;
		}
		return symbol;
	}

	private Map<Currency, Locale> getCurrencyLocaleMap() {
		Map<Currency, Locale> map = new HashMap<>();
		for (Locale locale : Locale.getAvailableLocales()) {
			try {
				Currency currency = Currency.getInstance(locale);
				map.put(currency, locale);
			} catch (Exception e) {
				// skip strange locale
			}
		}
		return map;
	}

	public double formatterStringToDouble(String value, String language, String countryCode) {
		double d = 0.0;
		Locale locale = new Locale(language, countryCode);
		NumberFormat format = NumberFormat.getInstance(locale);
		try {
			Number number = format.parse(value);
			d = number.doubleValue();
		} catch (ParseException e) {
			d = Double.valueOf(value.replaceAll(",", ""));
		}

		return d;
	}

	public String formatterDoubleToString(double value, String language, String countryCode) {
		Locale locale = new Locale(language, countryCode);

		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(NUMBER_FORMAT);
		String number = df.format(value);
		return number;
	}

	public String getLanguage(String countryCode) {
		String language = EN;// default
		Locale[] all = Locale.getAvailableLocales();
		for (Locale locale : all) {
			String country = locale.getCountry();
			if (country.equalsIgnoreCase(countryCode)) {
				language = locale.getLanguage().toUpperCase();
				if (language.equalsIgnoreCase(EN))
					return language;
			}
		}
		return language;
	}

}
