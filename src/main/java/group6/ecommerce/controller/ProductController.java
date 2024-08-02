package group6.ecommerce.controller;

import group6.ecommerce.model.*;
import group6.ecommerce.payload.request.ProductDetailRequest;
import group6.ecommerce.payload.request.ProductRequest;
import group6.ecommerce.payload.request.ReviewRequest;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.payload.response.ReviewResponse;
import group6.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final TypeService typeService;
    private final CategoryService categoryService;
    private final ProductDetailsService productDetailsService;
    private final ColorService colorService;
    private final SizeService sizeService;
    private final UserService userService;
    private final ReviewSerive reviewSerive;
    @GetMapping ("get_product")
    public ResponseEntity<HttpResponse> getProduct(
            @RequestParam(required = false, defaultValue = "12", value = "pageSize") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0", value = "pageNum") Integer pageNum,
            @RequestParam(required = false, defaultValue = "id", value = "fields") String fields,
            @RequestParam(required = false, defaultValue = "desc", value = "orderBy") String orderBy,
            @RequestParam(required = false, defaultValue = "false", value = "getAll") Boolean getAll,
            @RequestParam(required = false, value = "categoryId") Integer categoryId,
            @RequestParam(required = false, value = "colorId") Integer colorId) {
        System.out.println(pageNum);
        HttpResponse httpResponse = new HttpResponse(
                HttpStatus.OK.value(),
                null,
                productService.listProduct(pageSize, pageNum, fields, orderBy, getAll,colorId, categoryId));
        return ResponseEntity.status(HttpStatus.OK).body(httpResponse);
    }

    @GetMapping (value = "search")
    public ResponseEntity<HttpResponse> getProductByName(
            @RequestParam(required = false, defaultValue = "12", value = "pageSize") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0", value = "pageNum") Integer pageNum,
            @RequestParam(required = false, defaultValue = "id", value = "fields") String fields,
            @RequestParam(required = false, defaultValue = "desc", value = "orderBy") String orderBy,
            @RequestParam(required = false, defaultValue = "false", value = "getAll") Boolean getAll,
            @RequestParam(required = false, value = "value") String search) {
        HttpResponse httpResponse = new HttpResponse(
                HttpStatus.OK.value(),
                null,
                productService.listProductByName(pageSize, pageNum, fields, orderBy, getAll, search));
        return ResponseEntity.status(HttpStatus.OK).body(httpResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductRespone> DetailsProduct (@PathVariable("id") Optional<Integer> id){
        Product Product = productService.findById(id.get());
        ProductRespone productRespone = new ProductRespone(Product);
        return ResponseEntity.status(HttpStatus.OK).body(productRespone);
    }

    @PostMapping ("/add_product")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest){
        // Get type object in DB
        Type type = typeService.getTypeById(productRequest.getType());

        // Get category object in DB
        Category category = categoryService.findCategoryByName(productRequest.getCategory());

        Product product = new Product();
        product.setDescription(productRequest.getDescription());
        product.setDimension(productRequest.getDimension());
        product.setImageUrls(productRequest.getImageUrls());
        product.setMaterial(productRequest.getMaterial());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setWeight(productRequest.getWeight());

        product.setType(type);
        product.setCategory(category);

        Product productSaved = productService.addNewProduct(product);

        if(productRequest.getProductDetails() != null){
            for (ProductDetailRequest productDetailRequest : productRequest.getProductDetails()){
                ProductDetails productDetails = new ProductDetails();
                // Get size object in DB
                Size size = sizeService.findSizeByName(productDetailRequest.getSize());

                // Get color object in DB
                Color color = colorService.findColorByName(productDetailRequest.getColor());

                productDetails.setSize(size);
                productDetails.setColor(color);
                productDetails.setQuantity(productDetailRequest.getQuantity());
                productDetails.setOutOfStock(productDetailRequest.isOutOfStock());
                productDetails.setProducts(productSaved);

                productDetailsService.addNewProductDetail(productDetails);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @PostMapping("edit/{id}")
    public ResponseEntity<String> editProduct(@RequestBody ProductRequest productRequest, @PathVariable("id") Integer id){
        // Find current product
        Product findProductInDb = productService.findById(id);

        // Get type object in DB
        Type type = typeService.getTypeById(productRequest.getType());

        // Get category object in DB
        Category category = categoryService.findCategoryByName(productRequest.getCategory());

        Product product = new Product();
        // Add product Id for update
        product.setId(findProductInDb.getId());

        product.setDescription(productRequest.getDescription());
        product.setDimension(productRequest.getDimension());
        product.setImageUrls(productRequest.getImageUrls());
        product.setMaterial(productRequest.getMaterial());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setWeight(productRequest.getWeight());

        product.setType(type);
        product.setCategory(category);

        Product productSaved = productService.addNewProduct(product);

        if(productRequest.getProductDetails() != null){
            for(ProductDetailRequest productDetailRequest : productRequest.getProductDetails()){
                ProductDetails productDetails = new ProductDetails();

                // Find current product detail in db and update if existed
                if(productDetailRequest.getProductDetailsId() != null){
                    ProductDetails findProductDetailInDb = productDetailsService.findById(productDetailRequest.getProductDetailsId());
                    productDetails.setProductDetailsId(findProductDetailInDb.getProductDetailsId());
                }
                    // Get size object in DB
                    Size size = sizeService.findSizeByName(productDetailRequest.getSize());

                    // Get color object in DB
                    Color color = colorService.findColorByName(productDetailRequest.getColor());

                    productDetails.setSize(size);
                    productDetails.setColor(color);
                    productDetails.setQuantity(productDetailRequest.getQuantity());
                    productDetails.setOutOfStock(productDetailRequest.isOutOfStock());
                    productDetails.setProducts(productSaved);

                    productDetailsService.addNewProductDetail(productDetails);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
    @RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @GetMapping ("select_product_trending")
    public ResponseEntity<HttpResponse> SelectProductTrending (@RequestParam (value = "size",defaultValue = "1")int size){
        return ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(), "success",productService.findProductFamous(size)));
    }

    @GetMapping("topRepurchase/{year}/{month}")
    public ResponseEntity<List<ProductRespone>> getTopRepurchaseProduct(@PathVariable int year, @PathVariable int month) {
        // Get list id of top 10 repurchase product
        List<Integer> list = productService.getTopRepurchaseProduct(year, month);

        // Convert list of product ids to list of ProductResponses
        List<ProductRespone> list1 = list.stream()
                .map(productId -> productService.findById(productId))
                .filter(product -> product != null)
                .map(ProductRespone::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(list1, HttpStatus.OK);
    }

    @GetMapping ("get_all_category")
    public ResponseEntity<HttpResponse> GettAllCategory (){
        return ResponseEntity.ok(
                new HttpResponse(HttpStatus.OK.value(),"success",categoryService.finaAllCategory()));
    }

    @GetMapping ("get_all_color")
    public ResponseEntity<HttpResponse> GetAllColor (){
        return ResponseEntity.ok(
                new HttpResponse(HttpStatus.OK.value(),"success",colorService.ListColorResponse())
        );
    }


}


