package com.sales.system.service.product;

import com.sales.system.dto.admin.product.AdminProductCreateDTO;
import com.sales.system.dto.product.ProductResponseDTO;
import com.sales.system.entity.Product;
import com.sales.system.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDTO create(AdminProductCreateDTO dto) {

        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new IllegalArgumentException("Stock must be zero or greater");
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        Product saved = productRepository.save(product);
        return mapToDTO(saved);
    }

    @Transactional
    public ProductResponseDTO update(Long id, AdminProductCreateDTO dto) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());

        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new IllegalArgumentException("Stock must be zero or greater");
        }

        existing.setStock(dto.getStock());

        Product updated = productRepository.save(existing);
        return mapToDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(id);
    }


    public ProductResponseDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return mapToDTO(product);
    }

    public List<ProductResponseDTO> listAll() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private ProductResponseDTO mapToDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}