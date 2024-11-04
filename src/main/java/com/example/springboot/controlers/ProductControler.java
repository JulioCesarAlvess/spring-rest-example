package com.example.springboot.controlers;

import com.example.springboot.dto.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProducRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductControler {
    @Autowired
    ProducRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct (@RequestBody @Valid ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productModelList = productRepository.findAll();
        if (!productModelList.isEmpty()) {
            for (ProductModel productModel : productModelList) {
                UUID id = productModel.getIdProduct();
                productModel.add(
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductControler.class)
                                .getOneProduct(id)).withSelfRel()
                );
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> product0ptional = productRepository.findById(id);
        if (product0ptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        product0ptional.get().add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductControler.class)
                        .getAllProducts()).withRel("Product List")
        );
        return ResponseEntity.status(HttpStatus.OK).body(product0ptional.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct (
            @PathVariable(value="id") UUID id,
            @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> product0ptional = productRepository.findById(id);
        if (product0ptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        var productModel = product0ptional.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct (
            @PathVariable(value="id") UUID id) {
        Optional<ProductModel> product0ptional = productRepository.findById(id);
        if (product0ptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepository.delete(product0ptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }

}
