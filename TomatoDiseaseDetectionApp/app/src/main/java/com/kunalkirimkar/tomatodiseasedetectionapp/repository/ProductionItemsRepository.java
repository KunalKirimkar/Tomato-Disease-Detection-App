package com.kunalkirimkar.tomatodiseasedetectionapp.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.kunalkirimkar.tomatodiseasedetectionapp.model.ProductionItems;

@Dao
public interface ProductionItemsRepository {

    @Query("SELECT * FROM ProductionItems")
    List<ProductionItems> getProductionItemsList();

    @Query("SELECT COUNT(id) FROM ProductionItems")
    int getProductionItemsCount();

//    @Query("SELECT id FROM ProductionItems")
//    boolean getProductionItemsId(int i);

    @Insert
    void insertAll(ProductionItems... productionItems);

    @Insert
    void insert(ProductionItems productionItems);

    @Delete
    void delete(ProductionItems productionItems);

    @Query("DELETE FROM ProductionItems")
    void truncateProductionItems();
}
