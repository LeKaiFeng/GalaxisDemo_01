package com.galaxis.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestExcel {
    public static void main(String[] args) {
        try {
            ExcelReaderUtil excel = new ExcelReaderUtil();
            excel.readOneSheet("SF_sort.xlsx", 1);			//必须是xlsx文件
//            excel.readOneSheet("SF_sort1.xls", 1);
            List<Map<String, String>> list = excel.getDataList();
            for (int i = 1; i < list.size(); i++) {
            	Map<String, String> map = list.get(i);
            	for (String s : map.keySet()) {
            		System.out.print(s+"------"+map.get(s) + "\t");
            	}
            	System.out.println();
			}
//            for (Map<String, String> map : list) {
//				
//            	for (String s : map.keySet()) {
//            		System.out.print(s+"------"+map.get(s) + "\t");
//            	}
//            	System.out.println();
//			}
          

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void t1() {
    	
    }
    
}
