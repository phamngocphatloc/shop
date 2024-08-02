package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ProductDetailsRepository;
import group6.ecommerce.Repository.ProductRepository;
import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.PaginationResponse;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.service.ProductService;
import group6.ecommerce.utils.HandleSort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpls implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailsRepository productDetailsRepository;

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Page<Product> findByPage(Pageable pages) {
        return productRepository.findAll(pages);
    }


    @Override
    public Product addNewProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }


    @Override
    public PaginationResponse listProduct(
            Integer pageSize,
            Integer pageNum,
            String fields,
            String orderBy,
            Boolean getAll,
            Integer colorId,
            Integer categoryId) {
        Sort sort = HandleSort.buildSortProperties(fields, orderBy);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> pageProduct = productRepository.findByCategoryAndSort(categoryId,colorId, pageable);
        return new PaginationResponse(
                pageNum,
                pageSize,
                pageProduct.getTotalElements(),
                pageProduct.isLast(),
                pageProduct.getTotalPages(),
                pageProduct.getContent().stream().map(product -> new ProductRespone(product)).toList());
    }

    @Override
    public List<Integer> getTopRepurchaseProduct(int year, int month) {
        return productRepository.getTopRepurchaseProduct(year, month);
    }


    @Override
    public void deleteProduct(Integer id) {
        productDetailsRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductRespone> findProductFamous(int Size) {
        List<ProductRespone> response = new ArrayList<>();
        productRepository.findProductFamous(Size).stream().forEach(item -> {
            response.add(new ProductRespone(item));
        });
        return response;
    }

    @Override
    public boolean checkUserBuyed(int userId, int productId) {
        return productRepository.checkUserBuyed(userId,productId)==1?true:false;
    }

    @Override
    public PaginationResponse listProductByName (
                Integer pageSize,
                Integer pageNum,
                String fields,
                String orderBy,
                Boolean getAll,
                String name){
            Sort sort = HandleSort.buildSortProperties(fields, orderBy);
            Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
            Page<Product> pageProduct = productRepository.findByNameContainingIgnoreCase(name, pageable);
            return new PaginationResponse(
                    pageNum,
                    pageSize,
                    pageProduct.getTotalElements(),
                    pageProduct.isLast(),
                    pageProduct.getTotalPages(),
                    pageProduct.getContent().stream().map(product -> new ProductRespone(product)).toList());

    }


}
