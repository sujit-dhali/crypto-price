package com.crypto.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Request Service implementation to get crytocurrency information.
 * 
 * @author Sujit Dhali
 *
 */
@Service
public class RequestServiceImpl implements RequestService {

	private static final String EN = "EN";
	private static final String DOLLAR = "$";
	private static final String USD = "USD";
	private static final String NAME = "name";
	private static final String COUNTRY = "country";
	private static final String COUNTRY_CODE = "country_code";
	private static final String COUNTRY_CODE_US = "US";
	private static final String COUNTRY_US = "United States";
	private static final String CURRENT_PRICE = "current_price";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UtilityService utilityService;

	/* (non-Javadoc)
	 * @see com.crypto.service.RequestService#getCryptoCurrentPrice(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)
	 */
	@Override
	public String getCryptoCurrentPrice(HttpServletRequest request, String cryptoName, String ipAddress) {
		String price = null;
		String countryCode = null;
		String currencySymbol = null;
		String currency = null;
		String language = null;

		if (StringUtils.isEmpty(ipAddress)) {
			countryCode = request.getLocale().getCountry().toUpperCase();
			language = request.getLocale().getLanguage().toUpperCase();
			currency = utilityService.getCurrencyName(countryCode);
			currencySymbol = utilityService.getCurrencySymbol(countryCode);
		} else {
			Map<String, Object> map = getLocaleInfo(ipAddress);
			if (map.get(COUNTRY) == null || COUNTRY_US.equalsIgnoreCase(map.get(COUNTRY).toString())
					|| map.get(COUNTRY_CODE) == null
					|| COUNTRY_CODE_US.equalsIgnoreCase(map.get(COUNTRY_CODE).toString())) {
				countryCode = COUNTRY_CODE_US;
				language = EN;
				currency = USD;
				currencySymbol = DOLLAR;
			} else {
				countryCode = map.get(COUNTRY_CODE).toString();
				currency = utilityService.getCurrencyName(countryCode);
				currencySymbol = utilityService.getCurrencySymbol(countryCode);
				language = utilityService.getLanguage(countryCode);
			}
		}
		System.out.println(countryCode+" "+currency+" "+currencySymbol+" "+language);
		
		currencySymbol = currencySymbol.equals("US$")?DOLLAR:currencySymbol;

		String cryptoInformation = getCryptoInformation(currency);
		JsonParser springParser = JsonParserFactory.getJsonParser();
		List<Object> list = springParser.parseList(cryptoInformation);

		for (Object o : list) {
			if (o instanceof Map) {
				Map<String, Object> map2 = (Map<String, Object>) o;
				if (cryptoName.equalsIgnoreCase(map2.get(NAME).toString())) {
					price = utilityService.formatterDoubleToString(utilityService.formatterStringToDouble(
							map2.get(CURRENT_PRICE).toString(), language, countryCode), language, countryCode);
				}
			}
		}

		return (new StringBuffer(currencySymbol).append(price)).toString();
	}

	/**
	 * Get cryptocurrency price by calling open API using specific currency
	 * 
	 * @param currency
	 * @return String
	 */
	public String getCryptoInformation(String currency) {
		ResponseEntity<String> cryptoDetails = restTemplate.getForEntity(
				"https://api.coingecko.com/api/v3/coins/markets?vs_currency=" + currency.toLowerCase(), String.class);
		return cryptoDetails.getBody();
	}

	/**
	 * Get localized information by calling open API using ip address
	 * 
	 * @param ipAddress
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getLocaleInfo(String ipAddress) {
		ResponseEntity<String> localeInfo = restTemplate.getForEntity("https://www.iplocate.io/api/lookup/" + ipAddress,
				String.class);
		Map<String, Object> map = utilityService.parseJson(localeInfo.getBody());
		return map;
	}

}