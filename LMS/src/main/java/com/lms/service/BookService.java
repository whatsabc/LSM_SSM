package com.lms.service;

public interface BookService {
    String BookBorrow(int userId,String ISBN);
    String BookBorrowProxy(int userId,String ISBN);
}
