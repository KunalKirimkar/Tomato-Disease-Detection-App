package com.kunalkirimkar.tomatodiseasedetectionapp.datastore;

import com.kunalkirimkar.tomatodiseasedetectionapp.model.ProductionItems;

import java.util.ArrayList;
import java.util.List;

public class ProductionItemDatastore {
    public static List<ProductionItems> getProductionItems() {
        List<ProductionItems> productionItems = new ArrayList<>();

        ProductionItems productionItem1 = new ProductionItems();
        productionItem1.setName("Plant Selection");
        productionItem1.setId(1);
        productionItems.add(productionItem1);

        ProductionItems productionItem2 = new ProductionItems();
        productionItem2.setName("Planting");
        productionItem2.setId(2);
        productionItems.add(productionItem2);

        ProductionItems productionItem3 = new ProductionItems();
        productionItem3.setName("Plant Training");
        productionItem3.setId(3);
        productionItems.add(productionItem3);

        ProductionItems productionItem4 = new ProductionItems();
        productionItem4.setName("Plant Monitoring");
        productionItem4.setId(4);
        productionItems.add(productionItem4);

        ProductionItems productionItem5 = new ProductionItems();
        productionItem5.setName("Site Selection");
        productionItem5.setId(5);
        productionItems.add(productionItem5);

        ProductionItems productionItem6 = new ProductionItems();
        productionItem6.setName("Field Preparation");
        productionItem6.setId(6);
        productionItems.add(productionItem6);

        ProductionItems productionItem7 = new ProductionItems();
        productionItem7.setName("Weeding");
        productionItem7.setId(7);
        productionItems.add(productionItem7);

        ProductionItems productionItem8 = new ProductionItems();
        productionItem8.setName("Irrigation");
        productionItem8.setId(8);
        productionItems.add(productionItem8);

        ProductionItems productionItem9 = new ProductionItems();
        productionItem9.setName("Organic Fertilization");
        productionItem9.setId(9);
        productionItems.add(productionItem9);

        ProductionItems productionItem10 = new ProductionItems();
        productionItem10.setName("Chemical Fertilization");
        productionItem10.setId(10);
        productionItems.add(productionItem10);

        ProductionItems productionItem11 = new ProductionItems();
        productionItem11.setName("Preventive Measures");
        productionItem11.setId(11);
        productionItems.add(productionItem11);

        ProductionItems productionItem12 = new ProductionItems();
        productionItem12.setName("Plant Protection Chemical");
        productionItem12.setId(12);
        productionItems.add(productionItem12);

        ProductionItems productionItem13 = new ProductionItems();
        productionItem13.setName("Organic Plant Projection");
        productionItem13.setId(13);
        productionItems.add(productionItem13);

        ProductionItems productionItem14 = new ProductionItems();
        productionItem14.setName("Harvesting");
        productionItem14.setId(14);
        productionItems.add(productionItem14);

        ProductionItems productionItem15 = new ProductionItems();
        productionItem15.setName("Post Harvest");
        productionItem15.setId(15);
        productionItems.add(productionItem15);

        return productionItems;
    }

/*    public static int getImageById(int id) {
        if (id == 1) {
            return 0;
        }
        return -1;
    }*/
}
