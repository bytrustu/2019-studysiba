package com.bytrustu.page.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bytrustu.common.util.DBConnection;

public class PageDAO {

    private static PageDAO instance = new PageDAO();

    private PageDAO() {
    }

    public static PageDAO getInstance() { // 싱글톤패턴 객체화
        return instance;
    }

   
    
    
    
    
    
}
