package group6.ecommerce.service;

import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.PaginationResponse;

import group6.ecommerce.payload.response.ProductRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {
    public Product findById(int id);

    Page<Product> findByPage(Pageable pages);
    Product addNewProduct(Product product);


    List<Integer> getTopRepurchaseProduct(@Param("year") int year, @Param("month") int month);


    PaginationResponse listProduct(Integer pageSize, Integer pageNum, String fields, String orderBy, Boolean getAll, Integer colorId, Integer categoryId);

    public PaginationResponse listProductByName(Integer pageSize,Integer pageNum,String fields,String orderBy,Boolean getAll,String search);

    void deleteProduct (Integer id);
    public List<ProductRespone> findProductFamous (int Size);

    public boolean checkUserBuyed (int userId, int productId);


}
