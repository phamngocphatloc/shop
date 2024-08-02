package group6.ecommerce.Repository;

import group6.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product saveAndFlush(Product product);
    @Query (value = "select * from product \n" +
            "where product_id in (select distinct product_id \n" +
            "from product join category on product.category_id = category.category_id \n" +
            "where category_name = ?1) and product_id in ((select distinct product_details.product_id \n" +
            "from product_details \n" +
            "group by product_details.product_id, product_details.quantity\n" +
            "having product_details.quantity > 0))\n", nativeQuery = true)
    Page<Product> findByCategoryNameQuantityLarger0 (String categoryName,Pageable pageAble);


    @Query( value = "SELECT TOP 10 p.product_id " +
            "FROM product p " +
            "INNER JOIN Order_details od ON p.product_id = od.product_id " +
            "INNER JOIN Orders o ON od.order_id = o.order_id " +
            "WHERE YEAR(o.order_date) = :year AND MONTH(o.order_date) = :month " +
            "GROUP BY p.product_id, p.product_name " +
            "ORDER BY COUNT(o.order_id) - COUNT(DISTINCT o.user_id) DESC ", nativeQuery = true)
    List<Integer> getTopRepurchaseProduct(@Param("year") int year,@Param("month") int month);





    @Query("SELECT DISTINCT p " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.category c " +  // LEFT JOIN FETCH để nạp dữ liệu từ bảng Category
            "LEFT JOIN FETCH p.listProductDetails pd " +  // LEFT JOIN FETCH để nạp dữ liệu từ bảng ProductDetails
            "WHERE (:categoryId IS NULL OR c.id = :categoryId) " +  // Lọc theo categoryId (nếu được chỉ định)
            "AND (:colorId IS NULL OR pd.color.id = :colorId)")
    Page<Product> findByCategoryAndSort(@Param("categoryId") Integer categoryId, @Param("colorId") Integer colorId, Pageable pageable);



    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    void deleteById(Integer id);

//     Product save(Product product);

    @Query (value = "select * \n" +
            "from product \n" +
            "where product_id in (select top 3 order_details.product_id \n" +
            "from orders join order_details on orders.order_id = order_details.order_id \n" +
            "group by order_details.product_id\n" +
            "order by sum(order_details.quantity) desc)", nativeQuery = true)
    public List<Product> findProductFamous (int Size);

    @Query (value = "select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from users where user_id in (\n" +
            "select orders.user_id from product join product_details on product.product_id = product_details.product_id\n" +
            "join order_details on order_details.product_id = product.product_id join orders on orders.order_id = order_details.order_id\n" +
            "where orders.user_id = ?1 and order_details.product_id = ?2 and orders.status = 'success')",nativeQuery = true)
    public Integer checkUserBuyed (int userId, int productId);
}
