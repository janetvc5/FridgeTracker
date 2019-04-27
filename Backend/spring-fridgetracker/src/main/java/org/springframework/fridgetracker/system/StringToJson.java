package org.springframework.fridgetracker.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringToJson {
	public static Map<String, Object> stringToJsonObject(String s) {
		s=s.substring(s.indexOf('{')+1,s.lastIndexOf('}'));
		Map<String, Object> ret = readProperties(s);
		return ret;
	}
	
	private static Map<String, Object> readProperties(String s) {
		Map<String, Object> ret = new HashMap<String, Object>();
		while(s.indexOf(':')>0) {
			int i, j;
			String a = s.substring(0, s.indexOf(':'));
			a=a.substring(a.indexOf('"')+1,a.lastIndexOf('"'));
			int[] sw = {s.indexOf('"',s.indexOf(':')),s.indexOf('{',s.indexOf(':')),s.indexOf('[',s.indexOf(':')),s.indexOf(',',s.indexOf(':'))};
			Object b = new Object();
			switch(findMin(sw)) {
			case 0:
				b=(Object) s.substring(s.indexOf('"',s.indexOf(':'))+1,s.indexOf('"',s.indexOf('"',s.indexOf(':'))+1));
				if(s.indexOf(',')<0) {s="";} else {
					s=s.substring(s.indexOf(',',s.indexOf((String) b))+1);
				}
				break;
			case 1:
				i = s.indexOf('{')+1;
				j = 1;
				while(j>0) {
					if(s.indexOf('{',i)<s.indexOf('}',i) && s.indexOf('{',i)>-1) {
						i=s.indexOf('{',i)+1;
						j++;
					} else {
						i=s.indexOf('}',i)+1;
						j--;
					}
				}
				b=(Object) stringToJsonObject(s.substring(s.indexOf('{',s.indexOf(':')),i));
				if(s.indexOf(',',i)<0) {s="";} else {
					s=s.substring(s.indexOf(',',i-1)+1);
				}
				break;
			case 2:
				i = s.indexOf('[')+1;
				j = 1;
				while(j>0) {
					if(s.indexOf('[',i)<s.indexOf(']',i) && s.indexOf('[',i)>-1) {
						i=s.indexOf('[',i)+1;
						j++;
					} else {
						i=s.indexOf(']',i)+1;
						j--;
					}
				}
				b=(Object) stringToJsonArray(s.substring(s.indexOf('[',s.indexOf(':')),i));
				if(s.indexOf(',',i)<0) {s="";} else {
					s=s.substring(s.indexOf(',',i-1)+1);
				}
				break;
			case 3:
				int c = s.indexOf(',');
				if(c<0) c=s.length();
				b=(String) s.substring(s.indexOf(':')+1,c).trim();
				if(((String) b).indexOf('.')>-1) {
					b=(Object)Double.parseDouble((String) b);
				} else {
					b=(Object)Integer.parseInt((String) b);
				}
				if(c==s.length()) {s="";} else {
					s=s.substring(s.indexOf(',')+1);
				}
				break;
			}
			ret.put(a, b);
		}
		return ret;
	}
	private static List<Object> readItems(String s) {
		ArrayList<Object> ret = new ArrayList<Object>();
		while(s.length()>0) {
			int i, j;
			int[] sw = {s.indexOf('"'),s.indexOf('{'),s.indexOf('['),s.indexOf(',')};
			Object b = new Object();
			switch(findMin(sw)) {
			case 0:
				b=(Object) s.substring(s.indexOf('"')+1,s.indexOf('"',s.indexOf('"')));
				if(s.indexOf(',')<0) {s="";} else {
					s=s.substring(s.indexOf(',',s.indexOf((String) b))+1);
				}
				break;
			case 1:
				i = s.indexOf('{')+1;
				j = 1;
				while(j>0) {
					if(s.indexOf('{',i)<s.indexOf('}',i) && s.indexOf('{',i)>-1) {
						i=s.indexOf('{',i)+1;
						j++;
					} else {
						i=s.indexOf('}',i)+1;
						j--;
					}
				}
				b=(Object) stringToJsonObject(s.substring(s.indexOf('{'),i));
				if(s.indexOf(',',i)<0) {s="";} else {
					s=s.substring(s.indexOf(',',i-1)+1);
				}
				break;
			case 2:
				i = s.indexOf('[')+1;
				j = 1;
				while(j>0) {
					if(s.indexOf('[',i)<s.indexOf(']',i) && s.indexOf('[',i)>-1) {
						i=s.indexOf('[',i)+1;
						j++;
					} else {
						i=s.indexOf(']',i)+1;
						j--;
					}
				}
				b=(Object) stringToJsonArray(s.substring(s.indexOf('['),i));
				if(s.indexOf(',',i)<0) {s="";} else {
					s=s.substring(s.indexOf(',',i-1)+1);
				}
				break;
			case 3:
				int c = s.indexOf(',');
				if(c<0) c=s.length();
				b=(String) s.substring(0,c).trim();
				if(((String) b).indexOf('.')>-1) {
					b=(Object)Double.parseDouble((String) b);
				} else {
					b=(Object)Integer.parseInt((String) b);
				}
				if(c==s.length()) {s="";} else {
					s=s.substring(s.indexOf(',')+1);
				}
				break;
			}
			ret.add(b);
		}
		return ret;
	}
	
	private static int findMin(int[] in) {
		int i=0;
		int sum=0;
		for(int j=0; j<in.length;j++) {
			if((in[j]<in[i] && in[j]>-1) || in[i]<0) i=j;
			sum+=in[j];
		}
		if(sum==-1*in.length) {
			return in.length-1;
		}
		return i;
	}
	public static List stringToJsonArray(String s) {
		s=s.substring(s.indexOf('[')+1,s.lastIndexOf(']'));
		List ret = readItems(s);
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/search")
	public Map<String, Object> searchAPI(@RequestParam("ingr") String searchTerm, @RequestParam(value="session", required=false) Integer session) throws IOException
	{
		StringBuilder result = new StringBuilder();
		String getUrl = "https://api.edamam.com/api/food-database/parser?app_id=cabafde8&app_key=302c40ba00505410d9b0e8e9bf7ca8e2&ingr="+URLEncoder.encode(searchTerm, "UTF-8");
		if(session!=null) getUrl+=("&session="+session);
	    URL url = new URL(getUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = rd.readLine()) != null) {
	       result.append(line);
	    }
	    rd.close();
	    return stringToJsonObject(result.toString());
	}
}
