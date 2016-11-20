package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db.ExpenseManagerDbHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by chanuka on 11/20/16.
 */
public class PersistantAccountDAO implements AccountDAO {
    private ExpenseManagerDbHelper dbHelper;
    private Context context;

    public  PersistantAccountDAO(Context ctx){
        this.context = ctx;
        this.dbHelper = ExpenseManagerDbHelper.getDbManInst(ctx);
    }


    @Override
    public List<String> getAccountNumbersList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor results = db.rawQuery("SELECT accountNo FROM Accounts",null);

        List<String> accounts = new ArrayList<>();

        if (results.moveToFirst()){
            do {
                accounts.add(results.getString(results.getColumnIndex("accountNo")));
            } while (results.moveToNext());
        }

        results.close();

        return accounts;
    }

    @Override
    public List<Account> getAccountsList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Account> accounts = new ArrayList<>();

        Cursor results = db.rawQuery("SELECT * FROM Accounts",null);

        if(results.moveToFirst()) {
            do {
                Account account = new Account(results.getString(results.getColumnIndex("accountNo")),
                        results.getString(results.getColumnIndex("bankName")),
                        results.getString(results.getColumnIndex("accountHolderName")),
                        results.getDouble(results.getColumnIndex("balance")));

                accounts.add(account);
            } while (results.moveToNext());
        }

        results.close();

        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor results = db.rawQuery("SELECT * FROM Accounts WHERE accountNo = " + accountNo,null);

        Account account = null;

        if(results.moveToFirst()) {
            do {
                account = new Account(results.getString(results.getColumnIndex("accountNo")),
                        results.getString(results.getColumnIndex("bankName")),
                        results.getString(results.getColumnIndex("accountHolderName")),
                        results.getDouble(results.getColumnIndex("balance")));
            } while (results.moveToNext());
        }

        results.close();

        return account;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        // prepared statement
        String sql = "INSERT INTO Accounts (accountNo,bankName,accountHolderName,balance) VALUES (?,?,?,?)";
        SQLiteStatement stmt = dbw.compileStatement(sql);


        //Prepared statements use 1-based index, NOT 0.
        stmt.bindString(1, account.getAccountNo());
        stmt.bindString(2, account.getBankName());
        stmt.bindString(3, account.getAccountHolderName());
        stmt.bindDouble(4, account.getBalance());

        stmt.executeInsert();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        String sql = "DELETE FROM Account WHERE accountNo = ?";
        SQLiteStatement statement = dbw.compileStatement(sql);

        statement.bindString(1,accountNo);

        statement.executeUpdateDelete();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        String sql = "UPDATE Account SET balance = balance + ? WHERE accountNo=" + accountNo;
        SQLiteStatement stmt = dbw.compileStatement(sql);

        if(expenseType == ExpenseType.EXPENSE){
            stmt.bindDouble(1,-amount);
        }else{
            stmt.bindDouble(1,amount);
        }

        stmt.executeUpdateDelete();
    }
}
