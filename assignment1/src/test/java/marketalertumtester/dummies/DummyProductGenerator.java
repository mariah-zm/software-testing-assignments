package marketalertumtester.dummies;

import org.productalerter.model.domain.CategoryEnum;
import org.productalerter.model.domain.MaltaParkProduct;

import java.util.ArrayList;
import java.util.List;

public class DummyProductGenerator {

    public static MaltaParkProduct getDummyProduct() {
        MaltaParkProduct product = new MaltaParkProduct();
        product.setCategory(CategoryEnum.CAR);
        product.setName("Name");
        product.setDescription("Description");
        product.setPriceInEuros(2500.0);
        product.setImageUrl("https://www.maltapark.com/asset/itemphotos/9523990/9523990_1.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=4");
        product.setUrl("https://www.maltapark.com/item/details/9523990");

        return product;
    }

    public static List<MaltaParkProduct> getDummyProducts(int numProducts) {
        List<MaltaParkProduct> products = new ArrayList<>();
        for (int i = 0; i <numProducts; ++i) {
            products.add(DummyProductGenerator.getDummyProduct());
        }
        return products;
    }

    public static MaltaParkProduct getDummyProductWithCategory(int category) {
        MaltaParkProduct product = DummyProductGenerator.getDummyProduct();
        product.setCategory(CategoryEnum.getByValue(category));
        return product;
    }

}
