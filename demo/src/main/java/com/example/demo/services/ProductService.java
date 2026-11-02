package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.models.Product;
import com.example.demo.repositories.IProductRepository;

@Service
public class ProductService implements IProductService {

    private final IProductRepository repository;

    public ProductService(IProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productDb = findById(id);
        if (productDb.isPresent()) {
            repository.delete(productDb.get());
        }
        return productDb;
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> productDb = findById(id);
        if (productDb.isPresent()) {
            Product productUpdated = productDb.get();
            productUpdated.setDescription(product.getDescription());
            productUpdated.setName(product.getName());
            productUpdated.setPrice(product.getPrice());
            return Optional.of(repository.save(productUpdated));
        }
        return productDb;
    }
}
