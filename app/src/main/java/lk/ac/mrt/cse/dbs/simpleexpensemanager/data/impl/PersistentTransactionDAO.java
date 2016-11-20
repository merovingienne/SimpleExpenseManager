package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db.ExpenseManagerDbHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by chanuka on 11/20/16.
 */
public class PersistentTransactionDAO implements TransactionDAO{
    private ExpenseManagerDbHelper dbHelper;
    private Context context;

    public PersistentTransactionDAO(Context ctx){
        this.context = ctx;
        this.dbHelper = ExpenseManagerDbHelper.getDbManInst(ctx);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        String sql = "INSERT INTO Transactions (date,accountNo,expenseType,amount) VALUES (?,?,?,?)";
        SQLiteStatement stmt = dbw.compileStatement(sql);

        stmt.bindString(1, String.valueOf(date));
        stmt.bindString(2,accountNo);
        stmt.bindLong(3,(expenseType == ExpenseType.EXPENSE) ? 0 : 1);
        stmt.bindDouble(4,amount);

        stmt.executeInsert();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        Cursor results = db.rawQuery("SELECT * FROM Transactions",null);
        List<Transaction> transactions = new ArrayList<>();

        if(results.moveToFirst()) {
            do {
                Transaction trx = new Transaction(new Date(results.getLong(results.getColumnIndex("date"))),
                        results.getString(results.getColumnIndex("accountNo")),
                        (results.getInt(results.getColumnIndex("expenseType")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME,
                        results.getDouble(results.getColumnIndex("amount")));

                transactions.add(trx);
            } while (results.moveToNext());
        }

        results.close();

        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        Cursor results = db.rawQuery("SELECT * FROM Transactions LIMIT " + limit,null);
        List<Transaction> transactions = new ArrayList<>();

        if(results.moveToFirst()) {
            do {
                Transaction trx = new Transaction(new Date(results.getLong(results.getColumnIndex("date"))),
                        results.getString(results.getColumnIndex("accountNo")),
                        (results.getInt(results.getColumnIndex("expenseType")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME,
                        results.getDouble(results.getColumnIndex("amount")));

                transactions.add(trx);
            } while (results.moveToNext());
        }

        results.close();

        return transactions;
    }
}
