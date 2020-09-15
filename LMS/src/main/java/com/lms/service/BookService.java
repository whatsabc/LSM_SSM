package com.lms.service;

public interface BookService {
    /**
     * 自己实现的事务控制方法，后续再调试中先被注释
    String bookBorrow(int userId, String ISBN);
    String proxyBookBorrow(int userId, String ISBN);
    String springAOPBookBorrow(int userId, String ISBN);
     */
    String springAPIBookBorrow(int userId, String ISBN);
}
