package marketalertumtester.dummies;

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
        product.setPriceInEuros(360000.0);
        product.setImageUrl("https://www.maltapark.com/asset/itemphotos/9521343/9521343_1.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=10");
        product.setUrl("https://www.maltapark.com/item/details/9521343");

        return product;
    }

    public static List<MaltaParkProduct> getDummyProducts(int numProducts) {
        List<MaltaParkProduct> products = new ArrayList<>();
        for (int i = 0; i < numProducts; ++i) {
            products.add(DummyProductCreator.getDummyProduct());
        }
        return products;
    }

    public static MaltaParkProduct getDummyProductWithCategory(int category) {
        MaltaParkProduct product = DummyProductCreator.getDummyProduct();
        product.setCategory(CategoryEnum.getByValue(category));
        return product;
    }

}
