package com.example.nahimana.imanage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabarray = new String[]{"Deposited","Spent","Debited","Credited"};
    Integer tabNumber = 4;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CharSequence getPageTitle(int position) {
        return tabarray[position];
   }
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                TransactionTab transactionTab = new TransactionTab();
                return transactionTab;
            case 1:
                SpentTab spentTab = new SpentTab();
                return spentTab;
            case 2:
                DebitedTab debitedTab = new DebitedTab();
                return debitedTab;
            case 3:
                CreditedTab creditedTab = new CreditedTab();
                return creditedTab;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabNumber;
    }
}
