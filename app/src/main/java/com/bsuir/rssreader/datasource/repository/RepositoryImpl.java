package com.bsuir.rssreader.datasource.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.bsuir.rssreader.datasource.model.RSSModel;
import com.bsuir.rssreader.datasource.specification.SqlSpecification;
import com.bsuir.rssreader.datasource.util.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugene Novik
 */
public class RepositoryImpl implements IRepository {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public RepositoryImpl(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }


    @Override
    public void add(RSSModel model) {
        this.open();
        ContentValues insertValues = new ContentValues();
        insertValues.put(SQLiteHelper.COLUMN_TITLE, model.getTitle());
        insertValues.put(SQLiteHelper.COLUMN_LINK, model.getLink());
        long val = database.insert(SQLiteHelper.TABLE_ITEMS, null, insertValues);
        model.setId(val);
        this.close();
    }

    @Override
    public void remove(RSSModel note) {
        this.open();
        database.delete(SQLiteHelper.TABLE_ITEMS, SQLiteHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
        this.close();
    }

    @Override
    public void update(RSSModel model) {
        this.open();
        ContentValues updateValues = new ContentValues();
        updateValues.put(SQLiteHelper.COLUMN_TITLE, model.getTitle());
        updateValues.put(SQLiteHelper.COLUMN_LINK, model.getLink());

        database.update(SQLiteHelper.TABLE_ITEMS, updateValues, SQLiteHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(model.getId())});
        this.close();
    }

    @Override
    public List<RSSModel> query(SqlSpecification specification) {
        this.open();
        List<RSSModel> items = new ArrayList<>();
        Cursor cursor = database.rawQuery(specification.toSqlClauses(), null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                long id = cursor.getLong(cursor.getColumnIndex(SQLiteHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TITLE));
                String link = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LINK));
                items.add(new RSSModel(id, title, link));
                cursor.moveToNext();
            }
        }
        this.close();
        return items;
    }
}
