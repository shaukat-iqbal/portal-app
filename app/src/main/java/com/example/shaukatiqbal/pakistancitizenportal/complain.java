package com.example.shaukatiqbal.pakistancitizenportal;

public class complain {
    String category;
    String detail;
    String department;
    String roomNo;
    String status;

    public complain(){}

    public complain(String category,String department,String roomNo,String detail,String status){
    this.category=category;
    this.department=department;
    this.detail=detail;
    this.roomNo=roomNo;
    this.status=status;

    }
}
