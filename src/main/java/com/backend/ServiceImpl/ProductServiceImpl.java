package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Product;
import com.backend.Entity.ProductVariations;
import com.backend.Repository.ProductRepo;
import com.backend.Repository.ProductVariationsRepo;
import com.backend.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private ProductVariationsRepo productVariationRepo;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);

            if (product.getProductVariations() != null) {
                for (ProductVariations variation : product.getProductVariations()) {
                    if (variation.getId() != null) {
                        // Existing variation, ensure to fetch the existing one
                        ProductVariations existingVariation = product.getProductVariations().stream()
                                .filter(v -> v.getId().equals(variation.getId())).findFirst().orElse(null);

                        if (existingVariation != null) {
                            variation.setProduct(product); // Set reference to existing product
                            variation.setVariationProductImages(existingVariation.getVariationProductImages()); // Preserve
                            // image
                            // if
                            // not
                            // updated
                        }
                    } else {
                        variation.setId(null);
                    }
                }
            }

            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.searchProducts(query);
    }

    @Override
    public Optional<Product> findProductDetailsByIdAndVariation(Long productId, Long productVariationId) {
        return productVariationRepo.findProductDetailsByIdAndVariation(productId, productVariationId)
                .map(ProductVariations::getProduct);
    }

    @Override
    public Product saveProduct(Product product) {
        // Check if SKU is unique
        if (isSkuExists(product.getSku())) {
            throw new IllegalArgumentException("SKU already exists: " + product.getSku());
        }

        // If SKU is not provided, generate the next SKU
        if (product.getSku() == null || product.getSku().isEmpty()) {
            product.setSku(generateNextSku());
        }

        if (product.getProductVariations() != null) {
            for (ProductVariations variation : product.getProductVariations()) {
                variation.setProduct(product); // Set the product reference
                if (product.getProductType() == Product.ProductType.COMBO) {
                    variation.setComboVariations(variation.getComboVariations());
                }
            }
        }
        return productRepository.save(product);
    }

    @Override
    public boolean isSkuExists(String sku) {
        return productRepository.findBySku(sku).isPresent(); // Check if the SKU already exists in the database
    }

    @Override
    public String generateNextSku() {
        Optional<Product> lastProduct = productRepository.findTopByOrderByIdDesc(); // Get the latest product
        if (lastProduct.isPresent()) {
            Product last = lastProduct.get();
            String lastSku = last.getSku();

            // Check if SKU is null or empty
            if (lastSku == null || lastSku.isEmpty()) {
                return "1000"; // If the SKU is null or empty, start with "1000"
            }

            // Extract numeric part from the SKU (e.g., if SKU is "SKU1000", extract "1000")
            String numericPart = lastSku.replaceAll("[^0-9]", "");

            if (numericPart.isEmpty()) {
                // If no numeric part is found, handle the case appropriately (e.g., start with
                // "1000")
                return "1000";
            }

            try {
                // Parse the numeric part and increment it
                long nextSkuNumber = Long.parseLong(numericPart) + 1;

                // Extract the prefix (non-numeric part) of the SKU (e.g., "SKU" from "SKU1000")
                String prefix = lastSku.replaceAll("[0-9]", "");

                // Combine the prefix with the incremented number and return the new SKU
                return prefix + nextSkuNumber;
            } catch (NumberFormatException e) {
                // Handle the error if the numeric part is not parsable
                throw new IllegalArgumentException("Invalid SKU format, unable to parse numeric part: " + lastSku, e);
            }
        } else {
            // If no products exist, start with SKU "1000"
            return "1000";
        }
    }

    @Override
    public boolean isSkuUnique(String sku) {
        return !productRepository.existsBySku(sku); // Assuming you have a method in the repository to check SKU
        // uniqueness
    }

    @Override
    public List<Product> getProductsByStatus(Long status) {
        return productRepository.findByStatus(status);
    }

    @Override
    public Product updateProductStatus(Product product) {
        return productRepository.save(product); // No SKU duplication check here
    }

    @Override
    public List<Product> searchActiveProducts(String query) {
        return productRepository.searchActive(query);
    }

    @Override
    public List<Product> searchInactiveProducts(String query) {
        return productRepository.searchInactive(query);
    }

    @Override
    public Optional<Product> findByBarcode(String barcode) {
        List<Product> products = productRepository.findByBarcodeOrVariationSubSku(barcode);
        return products.stream().findFirst();
    }


}
