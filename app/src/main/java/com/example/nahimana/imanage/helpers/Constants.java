package com.example.nahimana.imanage.helpers;

public class Constants {

  //public static final String ROOT_URL="http:/10.0.0.2:8000/api/";
  //public static final String ROOT_URL="https://fathomless-taiga-31341.herokuapp.com/api/";    //production url
public static final String ROOT_URL="http://192.168.1.72:8000/api/";
   // public static final String ROOT_URL=" http://192.168.43.29:8000/api/";

    public static final String USER_REGISTER_URL=ROOT_URL+"users";
    public static final String LOGIN_URL = ROOT_URL+"auth/user";

    public static final String EXPENSE_URL = ROOT_URL+"expenses";

    public static final String DEBIT_URL = ROOT_URL+"debits";
    public static final String CREDIT_URL = ROOT_URL+"credits";
    public static final String DEPOSIT_URL = ROOT_URL+"deposit";

    public static final String PAY_CREDIT_URL = ROOT_URL+"pay/credit";
    public static final String PAY_DEBIT_URL = ROOT_URL+"pay/debit";
    public  static final String TRANSACTION_URL = ROOT_URL+"dashboard/";








}

