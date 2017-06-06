package com.acesconn.acesmportal;
/**
 * 因為FIrebase的setPersistenceEnabled
 * 只能在FirebaseDatabase.getInstance()後執行一次,
 * 否則會造成crash,
 * 使用static靜態變數只會有一個,
 * 所以獨立以static(靜態)方法取得唯一的FirebaseDatabase
 */

import com.google.firebase.database.FirebaseDatabase;


class DatabaseUtil {
    private static FirebaseDatabase mDatabase;

    static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);  //設為可離線存取
        }

        return mDatabase;
    }
}
