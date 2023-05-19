package com.example.drawing.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper


class DrawingDb(context: Context?, factory: CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY, " +
                img_name + " TEXT," + img_uri + " TEXT,"+ creator_name + " TEXT," + design_description + " TEXT," + contributed_by + " TEXT" + ")"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addData(
        imgName: String?,
        imgUri: String?,
        creator: String?,
        desc: String?,
        contri: String?
    ) {
        val values = ContentValues()
        values.put(img_name, imgName)
        values.put(img_uri,imgUri)
        values.put(creator_name, creator)
        values.put(design_description, desc)
        values.put(contributed_by, contri)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun update(name:String?, creator: String?, desc: String?, contri: String?,uri:String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(img_name, name)
        values.put(creator_name, creator)
        values.put(design_description, desc)
        values.put(contributed_by, contri)
        db.update(TABLE_NAME, values, "uri=?", arrayOf(uri))
        db.close()
    }

    val name: Cursor
        get() {
            val db = this.readableDatabase
            return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        }

    companion object {
        const val DATABASE_NAME = "SQL"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "example"
        const val ID_COL = "id"
        const val img_name = "name"
        const val img_uri = "uri"
        const val creator_name = "creator"
        const val design_description = "description"
        const val contributed_by="contribution"
    }
}
