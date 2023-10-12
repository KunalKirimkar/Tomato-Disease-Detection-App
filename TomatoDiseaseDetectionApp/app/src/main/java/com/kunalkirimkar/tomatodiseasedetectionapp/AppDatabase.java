package com.kunalkirimkar.tomatodiseasedetectionapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kunalkirimkar.tomatodiseasedetectionapp.model.ProductionItems;
import com.kunalkirimkar.tomatodiseasedetectionapp.repository.ProductionItemsRepository;


@Database(entities = {ProductionItems.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductionItemsRepository productionItemsRepository();

    public static AppDatabase appDatabase;

    public static synchronized AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "db_tomato_hub")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }


}
