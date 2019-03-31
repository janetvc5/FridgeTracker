package org.springframework.fridgetracker.system;

import java.util.HashMap;
import java.util.Map;

public class StringToJson {
	public static Map<String, Object> stringToJsonObject(String s) {
		s=s.substring(s.indexOf('{')+1,s.lastIndexOf('}'));
		Map<String, Object> ret = readProperties(s);
		return ret;
	}
	
	private static Map<String, Object> readProperties(String s) {
		Map<String, Object> ret = new HashMap<String, Object>();
		while(s.indexOf(':')>0) {
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
				break;
			case 2:
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
	private static int findMin(int[] in) {
		int i=0;
		int sum=0;
		for(int j=0; j<in.length;j++) {
			if(in[j]<in[i] && in[j]>-1) i=j;
			sum+=in[j];
		}
		if(sum==-1*in.length) {
			return in.length-1;
		}
		return i;
	}
	public static void main(String arg[]) {
		String object = "{ \"asdf\" : \"were my famiyl\" , \"numor\" : 2 , \"numoragain\" : 3.2324242 }";
		System.out.println(object + ", String length: "+object.length());
		System.out.println(stringToJsonObject(object));
	}
}
