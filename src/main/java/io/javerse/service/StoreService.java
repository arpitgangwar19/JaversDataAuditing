package io.javerse.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import io.javerse.domain.Product;
import io.javerse.domain.Store;
import io.javerse.repo.ProductRepository;
import io.javerse.repo.StoreRepository;

@Service
public class StoreService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public StoreService(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public void updateProductPrice(Integer productId, Double price) {
        Optional<Product> productOpt = productRepository.findById(productId);
        productOpt.ifPresent(product -> {
            product.setPrice(price);
            productRepository.save(product);
        });
    }

    public void rebrandStore(int storeId, String updatedName) {
        Optional<Store> storeOpt = storeRepository.findById(storeId);
        storeOpt.ifPresent(store -> {
            store.setName(updatedName);
            store.getProducts().forEach(product -> {
                product.setNamePrefix(updatedName);
            });
            storeRepository.save(store);
        });
    }

    public void createRandomProduct(Integer storeId) {
        Optional<Store> storeOpt = this.storeRepository.findById(storeId);
        storeOpt.ifPresent(store -> {
            Random random = new Random();
            Product product = new Product("Product#" + random.nextInt(), random.nextDouble() * 100);
            store.addProduct(product);
            storeRepository.save(store);
        });
    }

    public Store findStoreById(int storeId) {
        return storeRepository.findById(storeId).get();
    }

    public Product findProductById(int id) {
        return this.productRepository.findById(id).get();
    }
}