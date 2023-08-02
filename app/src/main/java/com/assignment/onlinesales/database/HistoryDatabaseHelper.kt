package com.assignment.onlinesales.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "history_items.db"

        // Table and column names
        private const val TABLE_HISTORY = "history"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EXPRESSION = "expression"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_HISTORY (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_EXPRESSION TEXT NOT NULL, " +
                "$COLUMN_DATE INTEGER NOT NULL" +
                ");"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    fun addHistoryItem(item: HistoryItem) {
        val database: SQLiteDatabase? = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EXPRESSION, item.itemExpression)
        values.put(COLUMN_DATE, item.itemDate)

        database?.insert(TABLE_HISTORY, null, values)

        this.close()
    }

    fun getAllHistoryItems(): List<HistoryItem> {
        val database: SQLiteDatabase? = this.readableDatabase

        val items = mutableListOf<HistoryItem>()
        val cursor = database?.query(
            TABLE_HISTORY,
            null,
            null,
            null,
            null,
            null,
            null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndex(COLUMN_ID))
                val expression =
                    it.getString(it.getColumnIndex(COLUMN_EXPRESSION))
                val date =
                    it.getString(it.getColumnIndex(COLUMN_DATE))
                items.add(HistoryItem(id, expression, date))
            }
        }
        return items
    }
}