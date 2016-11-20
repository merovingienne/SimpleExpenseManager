package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db;

import android.provider.BaseColumns;

/**
 * Created by chanuka on 11/20/16.
 */
public class ExpenseManagerContract {

    public static class Account implements BaseColumns{
        public static final String TABLE_NAME = "Accounts";
        public static final String COLUMN_NAME_ACCOUNT_NO = "accountNo";
        public static final String COLUMN_NAME_BANK_NAME = "bankName";
        public static final String COLUMN_NAME_ACCOUNT_HOLDER_NAME = "accountHolderName";
        public static final String COLUMN_NAME_BALANCE = "balance";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ACCOUNTS_TABLE =
            "CREATE TABLE " + Account.TABLE_NAME + " (" +
                    Account._ID + " INTEGER," +
                    Account.COLUMN_NAME_ACCOUNT_NO + TEXT_TYPE + " PRIMARY KEY"+ COMMA_SEP +
                    Account.COLUMN_NAME_BANK_NAME + TEXT_TYPE + COMMA_SEP +
                    Account.COLUMN_NAME_ACCOUNT_HOLDER_NAME + TEXT_TYPE + COMMA_SEP +
                    Account.COLUMN_NAME_BALANCE + " DECIMAL(9,2)" + " )";

    private static final String SQL_DELETE_ENTRIES_ACCOUNTS =
            "DROP TABLE IF EXISTS " + Account.TABLE_NAME;

    public static class Transaction implements BaseColumns{
        public static final String TABLE_NAME = "Transactions";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_ACCOUNT_NO = "accountNo";
        public static final String COLUMN_NAME_EXPENSE_TYPE = "expenseType";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }


    private static final String SQL_CREATE_TRANSACTIONS_TABLE =
            "CREATE TABLE " + Transaction.TABLE_NAME + " (" +
                    Transaction._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Transaction.COLUMN_NAME_DATE + " DATE" + COMMA_SEP +
                    Transaction.COLUMN_NAME_ACCOUNT_NO + TEXT_TYPE + COMMA_SEP +
                    Transaction.COLUMN_NAME_EXPENSE_TYPE + TEXT_TYPE + COMMA_SEP +
                    Transaction.COLUMN_NAME_AMOUNT + " DECIMAL(9,2)" + " )";

    private static final String SQL_DELETE_ENTRIES_TRANSACTIONS =
            "DROP TABLE IF EXISTS " + Transaction.TABLE_NAME;

    public static String createAccountsTable(){
        return SQL_CREATE_ACCOUNTS_TABLE;
    }

    public static String createTransactionsTable(){
        return SQL_CREATE_TRANSACTIONS_TABLE;
    }

}
