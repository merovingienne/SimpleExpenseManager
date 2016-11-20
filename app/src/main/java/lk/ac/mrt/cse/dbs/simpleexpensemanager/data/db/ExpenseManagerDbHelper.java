package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;

/**
 * Created by chanuka on 11/20/16.
 */
public class ExpenseManagerDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "140674F";
    private static final ExpenseManagerContract  EXPENSE_MANAGER_CONTRACT = new ExpenseManagerContract();
    private static ExpenseManagerDbHelper expenseManagerDbHelper = null;

    private ExpenseManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EXPENSE_MANAGER_CONTRACT.createAccountsTable());
        db.execSQL(EXPENSE_MANAGER_CONTRACT.createTransactionsTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // Singleton Database helper
    public static ExpenseManagerDbHelper getDbManInst(Context ctx){
        if (expenseManagerDbHelper == null){
            expenseManagerDbHelper = new ExpenseManagerDbHelper(ctx);
        }
        return expenseManagerDbHelper;
    }
}
