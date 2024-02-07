package com.igorbarreto.ecommerce.service;

import com.igorbarreto.ecommerce.domain.category.Category;
import com.igorbarreto.ecommerce.domain.product.Product;
import com.igorbarreto.ecommerce.domain.user.User;
import com.igorbarreto.ecommerce.domain.user.enums.Role;
import com.igorbarreto.ecommerce.dtos.UserPurchaseRequestDTO;
import com.igorbarreto.ecommerce.repository.CategoryRepository;
import com.igorbarreto.ecommerce.repository.ProductRepository;
import com.igorbarreto.ecommerce.repository.UserRepository;
import org.hibernate.sql.ast.tree.expression.CaseSimpleExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    Product product;
    Category category;

    @BeforeEach
    public void setup() {
        product = new Product(1L, "name", "description",
                100.0, 100, 1L);

        category = new Category(1L, "automotivos");
    }

    @Test
    @DisplayName("Create a product should return the product created")
    public void createProduct_Case1() {

        when(categoryRepository.findById(product.getCategoryId())).thenReturn(Optional.ofNullable(category));
        when(productRepository.save(product)).thenReturn(product);

        Product productCreated = productService.saveProduct(product);

        assertNotNull(productCreated);
        assertEquals(productCreated.getName(), product.getName());
        assertEquals(productCreated.getDescription(), product.getDescription());
        assertEquals(productCreated.getPrice(), product.getPrice());

    }

    @Test
    @DisplayName("Create a product with a category who doesn't exists throw exception")
    public void createProduct_Case2() {

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {

            productService.saveProduct(product);
        });
    }

    @Test
    @DisplayName("Updated a product should return the product updated")
    public void updateProduct_Case1() {

        Product updatedProduct = new Product(1L, "name updated", "description updated",
                100.0, 100, 1L);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(updatedProduct));
        doReturn(updatedProduct).when(productRepository).save(any());

        Product product1 = productService.updateProduct(updatedProduct);

        assertNotNull(product1);
        assertEquals(updatedProduct.getName(), product1.getName());
        assertEquals(updatedProduct.getDescription(), product1.getDescription());
    }

    @Test
    @DisplayName("Update a product who doesn't exists should throw exception")
    public void updateProduct_Case2() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            this.productService.updateProduct(product);
        });
    }

    @Test
    @DisplayName("Should return all products")
    public void getAllProducets_Case1() {

        Product product1 = new Product(1L, "name 1", "description 1",
                100.0, 100, 1L);

        when(productRepository.findAll()).thenReturn(List.of(product, product1));

        List<Product> allProducts = this.productService.findAllProducts();

        assertNotNull(allProducts);
        assertEquals(allProducts.get(0), product);
        assertEquals(allProducts.get(1), product1);
    }

    @Test
    @DisplayName("Should return a product by ID when ID is valid")
    public void findById_Case1() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product productFound = productService.findProductById(product.getId());

        assertNotNull(productFound);
        assertEquals(product.getName(), productFound.getName());
        assertEquals(product.getDescription(), productFound.getDescription());
        assertEquals(product.getInventory(), productFound.getInventory());

    }

    @Test
    @DisplayName("Should throw exception when use findById with invalid ID")
    public void findById_Case2() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            this.productService.findProductById(99L);
        });
    }

    @Test
    @DisplayName("Should return a list of products filtered by category")
    public void findByCategoryId_Case1() {

        Long categoryId = 1L;
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());

        when(productRepository.findByCategoryId(categoryId)).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.findProductsByCategory(categoryId);

        assertEquals(expectedProducts, actualProducts);

        verify(productRepository).findByCategoryId(categoryId);
    }

    @Test
    @DisplayName("Delete a product with valid ID doesn't throw exception")
    public void deleteProduct_Case1() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        productRepository.delete(product);

        assertDoesNotThrow(() -> {
            productService.deleteProduct(product.getId());
        });
    }

    @Test
    @DisplayName("Should throw exception when use deleteProduct with invalid ID")
    public void deleteProduct_Case2() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(99L);
        });
    }

    @Test
    @DisplayName("Buy product with valid datas return response")
    public void buyProduct_Case1() {

        var purchaseRequest = new UserPurchaseRequestDTO(1L, "login");

        var user = new User("name", "name@email.com", "senha", 200.0, Role.USER);

        when(userRepository.findByLogin(anyString())).thenReturn(user);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        var purchaseResponse = productService.buyProduct(purchaseRequest);

        assertNotNull(purchaseResponse);
        assertEquals(purchaseResponse.buyerName(), user.getName());
        assertEquals(purchaseResponse.moneyAccount(), 100.0);
        assertEquals(purchaseResponse.message(), "Compra efetuada com sucesso");

    }

    @Test
    @DisplayName("Buy product with unexist user should throw exception")
    public void buyProduct_Case2() {

        var request = new UserPurchaseRequestDTO(99L, "name");

        when(userRepository.findByLogin(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            this.productService.buyProduct(request);
        });
    }

    @Test
    @DisplayName("Buy product with infusicient amount should throw exception")
    public void buyProduct_Case3() {

        var request = new UserPurchaseRequestDTO(99L, "name");
        var user = new User("name", "name@email.com", "senha", 30.0, Role.USER);

        when(userRepository.findByLogin(anyString())).thenReturn(user);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        assertThrows(RuntimeException.class, () -> {
            this.productService.buyProduct(request);
        });

    }
}
