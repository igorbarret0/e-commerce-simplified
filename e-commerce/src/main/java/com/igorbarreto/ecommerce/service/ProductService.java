package com.igorbarreto.ecommerce.service;

import com.igorbarreto.ecommerce.domain.product.Product;
import com.igorbarreto.ecommerce.dtos.UserPurchaseRequestDTO;
import com.igorbarreto.ecommerce.dtos.UserPurchaseResponseDTO;
import com.igorbarreto.ecommerce.repository.CategoryRepository;
import com.igorbarreto.ecommerce.repository.ProductRepository;
import com.igorbarreto.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    private UserRepository userRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    public Product saveProduct(Product product) {

        categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Não existe uma categoria com esse ID"));

        var saveProduct = productRepository.save(product);


        return saveProduct;
    }

    public Product updateProduct(Product product) {


        var entity = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setInventory(product.getInventory());

        this.productRepository.save(entity);

        return entity;
    }

    public List<Product> findAllProducts() {

        return productRepository.findAll();
    }

    public Product findProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto com esse ID não encontrado"));
    }

    public List<Product> findProductsByCategory(Long categoryId) {

        return productRepository.findByCategoryId(categoryId);
    }


    public void deleteProduct(Long id) {

        var productExists = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto com esse ID não encontrad"));

        productRepository.delete(productExists);
    }

    public UserPurchaseResponseDTO buyProduct(UserPurchaseRequestDTO request) {

        var user = userRepository.findByLogin(request.buyerLogin());
        var product = this.findProductById(request.productId());

        if (user == null) {
            throw new RuntimeException("Usuário não existe");
        }

        if (user.getMoneyAccount() == 0 || user.getMoneyAccount() < product.getPrice()) {
            throw new RuntimeException("Quantia insuficiente para comprar o produto");
        }

        user.setMoneyAccount(user.getMoneyAccount() - product.getPrice());
        product.setInventory(product.getInventory() - 1);

        userRepository.save(user);
        productRepository.save(product);

        return new UserPurchaseResponseDTO(user.getName(), user.getMoneyAccount(), "Compra efetuada com sucesso");

    }

}
