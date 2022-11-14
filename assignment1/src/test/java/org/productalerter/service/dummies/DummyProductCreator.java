package org.productalerter.service.dummies;

import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.domain.MaltaParkProduct;

import java.util.ArrayList;
import java.util.List;

public class DummyProductCreator {

    public static MaltaParkProduct getDummyProduct() {
        MaltaParkProduct product = new MaltaParkProduct();
        product.setCategory(CategoryEnum.CAR);
        product.setName("Name");
        product.setDescription("Description");
        product.setPriceInEuros(2500.0);
        product.setImageUrl("image-url");
        product.setUrl("url");

        return product;
    }

    public static List<MaltaParkProduct> getDummyProducts(int numProducts) {
        List<MaltaParkProduct> products = new ArrayList<>();
        for (int i = 0; i <numProducts; ++i) {
            products.add(DummyProductCreator.getDummyProduct());
        }
        return products;
    }

}
