package com.fpt;

import com.fpt.product.Product;
import com.fpt.product.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TestProduct {
    @Autowired
    private ProductRepository repo;
    @Test
    public void testAddNew(){
        Product product = new Product();
        product.setProduct_name("iPhone SE 20222 ");
        product.setCategory("apple");
        product.setCondition_id(1);
        product.setManufacturer("Apple");
        product.setPrice(499);
        product.setStock(500);
        product.setDescription(" Điện thoại iPhone SE 2020 đã bất ngờ ra mắt với thiết kế 4.7 inch nhỏ gọn, chip A13 Bionic mạnh mẽ như trên iPhone 11 và đặc biệt sở hữu mức giá tốt chưa từng có.");
        product.setPhoto("https://cdn.tgdd.vn/Products/Images/42/230410/iphone-se-64gb-2020-hop-moi-do-1-1-org.jpg");
        Product savedProduct = repo.save(product);
        Assertions.assertThat(savedProduct).isNotNull();

    }
    @Test
    public void testListAll(){
        Iterable<Product> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
        for(Product product:users){
            System.out.println(product);
        }
    }

}
