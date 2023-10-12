package com.kunalkirimkar.tomatodiseasedetectionapp.service;

import android.content.Context;

import com.kunalkirimkar.tomatodiseasedetectionapp.AppDatabase;
import com.kunalkirimkar.tomatodiseasedetectionapp.model.ProductionItems;
import com.kunalkirimkar.tomatodiseasedetectionapp.repository.ProductionItemsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductionService {

    private final ProductionItemsRepository productionItemsRepository;
    private final Executor executor;

    public ProductionService(Context context, ProductionItemsRepository productionItemsRepository) {
        this.productionItemsRepository = productionItemsRepository;
        productionItemsRepository = AppDatabase.getAppDatabase(context).productionItemsRepository();
        executor = Executors.newSingleThreadExecutor();
    }

    public List<ProductionItems> getAllProductionItems() {
        return productionItemsRepository.getProductionItemsList();
    }

    public void insertAchievement(ProductionItems productionItems) {
        productionItemsRepository.insert(productionItems);
    }

    public void deleteAchievement(ProductionItems productionItems) {
        productionItemsRepository.delete(productionItems);
    }

    public void truncateAchievements() {
        productionItemsRepository.truncateProductionItems();
    }

    public int getAchievementsCount() {
        return productionItemsRepository.getProductionItemsCount();
    }

    /*public List<ProductionItems> getImagesByItemId() {
        List<ProductionItems> productionItemsList = new ArrayList<>();

        if (productionItemsRepository.getProductionItemsId(1)) {
            productionItemsList.add(productionItemsRepository);
        }

        if (achievementRepository.existsMaxProfileBadgesByType("Streak")) {
            achievementList.add(achievementRepository.getMaxProfileBadgesByType("Streak"));
        }

        if (achievementRepository.existsMaxProfileBadgesByType("Todo")) {
            achievementList.add(achievementRepository.getMaxProfileBadgesByType("Todo"));
        }

        return achievementList;
    }*/

    private void insertAchievementTask(ProductionItems productionItems) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productionItemsRepository.insert(productionItems);
            }
        });
    }

    private void deleteAchievementTask(ProductionItems productionItems) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productionItemsRepository.delete(productionItems);
            }
        });
    }
}
