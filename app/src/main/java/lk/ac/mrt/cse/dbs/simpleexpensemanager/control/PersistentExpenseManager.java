package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by chanuka on 11/20/16.
 */
public class PersistentExpenseManager extends ExpenseManager {

    private Context ctx = null;

    //Constructor
    public PersistentExpenseManager(Context ctx) {
        this.ctx = ctx;
        setup();
    }


    public void setup()  {

        //Setup AccountDAO
        AccountDAO persistentAccountDAO = new PersistentAccountDAO(ctx);
        setAccountsDAO(persistentAccountDAO);

        //Setup TransactionDAO
        TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(ctx);
        setTransactionsDAO(persistentTransactionDAO);


        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);

    }
}
