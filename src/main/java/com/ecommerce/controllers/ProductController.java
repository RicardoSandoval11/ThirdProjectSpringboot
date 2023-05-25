package com.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.customObjects.ProductRequest;
import com.ecommerce.helpers.UploadFiles;
import com.ecommerce.models.Brand;
import com.ecommerce.models.Category;
import com.ecommerce.models.Color;
import com.ecommerce.models.Product;
import com.ecommerce.models.Ram;
import com.ecommerce.models.Storage;
import com.ecommerce.services.interfaces.IBrandService;
import com.ecommerce.services.interfaces.ICategoryService;
import com.ecommerce.services.interfaces.IColorService;
import com.ecommerce.services.interfaces.IProductService;
import com.ecommerce.services.interfaces.IRamService;
import com.ecommerce.services.interfaces.IStorageService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IBrandService brandService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IColorService colorService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IRamService ramService;

    @Autowired
    private IStorageService storageService;

    @Value("${ecommerceapp.path.products}")
    private String productImagePath;

    @GetMapping("/details/{productId}")
    public String productDetails(@PathVariable("productId") Integer productId, Model model){
        
        Product product = productService.getProductById(productId);

        if(product == null){
            return "generic/not_found";
        }

        model.addAttribute("product", product);

        return "products/product_details";
    }

    @GetMapping("/all-products")
    public String allProducts(HttpServletRequest request, Model model, Pageable pageable){

        // filter attributes
        List<Category> categories = categoryService.getAllCategories();
        List<Brand> brands = brandService.getAllBrands();
        List<Ram> rams = ramService.getAllRam();
        List<Storage> storages = storageService.getllAllStorages();

        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("rams", rams);
        model.addAttribute("storages", storages);

        // filter products
        Object filter_by_obj = request.getParameter("filter_by");
        Object value_obj = request.getParameter("value");
        
        if(filter_by_obj != null && value_obj != null){
            String filter_by = filter_by_obj.toString();
            String value = value_obj.toString();
            model.addAttribute("filter_by", filter_by);
            model.addAttribute("value", value);
            if(filter_by.equals("category")){
                Category category = categoryService.getCategoryById(Integer.parseInt(value));
                if(category == null){
                    Page<Product> products = productService.getAllProducts(pageable);
                    model.addAttribute("products", products);
                    return "products/all_products.html";
                }
                Page<Product> products = productService.getProductsByCategory(category, pageable);
                model.addAttribute("products", products);
                return "products/all_products.html";
            }else if(filter_by.equals("brand")){
                Brand brand = brandService.getBrandById(Integer.parseInt(value));
                if(brand == null){
                    Page<Product> products = productService.getAllProducts(pageable);
                    model.addAttribute("products", products);
                    return "products/all_products.html";
                }
                Page<Product> products = productService.getProductsByBrand(brand, pageable);
                model.addAttribute("products", products);
                return "products/all_products.html";
            }else if(filter_by.equals("price")){
                Integer lowIndex = value.indexOf("to");
                Integer topIndex = lowIndex + 2;
                String lowerStr = value.substring(0, lowIndex);
                String topStr = value.substring(topIndex, value.length());
                Double lower = Double.parseDouble(lowerStr);
                Double top = Double.parseDouble(topStr);
                Page<Product> normalPriceProducts = productService.getProductsByPriceRange(lower, top, 0, pageable);
                model.addAttribute("products", normalPriceProducts);
                return "products/all_products.html";
            }else if(filter_by.equals("ram")){
                Integer ramId = Integer.parseInt(value);
                Page<Product> products = productService.getProductsByRam(ramId, pageable);
                model.addAttribute("products", products);
                return "products/all_products.html";
            }else if(filter_by.equals("storage")){
                Integer storageId = Integer.parseInt(value);
                Page<Product> products = productService.getProductsByStorage(storageId, pageable);
                model.addAttribute("products", products);
                return "products/all_products.html";
            }else if(filter_by.equals("kword")){
                Page<Product> products = productService.getProductsBykword(value, pageable);
                model.addAttribute("products", products);
                return "products/all_products.html";
            }else{
                Page<Product> products = productService.getAllProducts(pageable);
                model.addAttribute("products", products);
                return "products/all_products.html";
            }
        }else{
            Page<Product> products = productService.getAllProducts(pageable);
            model.addAttribute("products", products);
            return "products/all_products.html";
        }
    }

    @GetMapping("/manage-products")
    public String manageProducts(Authentication authentication, Model model, Pageable pageable){
        Page<Product> allProducts = productService.getAllProducts(pageable);
        model.addAttribute("products", allProducts);
        return "products/manage_products.html";
    }

    @GetMapping("/out-of-stock")
    public String outOfStockProducts(Model model, Pageable pageable){
        Page<Product> products = productService.getOutOfStockProducts(0, pageable);
        model.addAttribute("products", products);
        return "products/no_stock_products";
    }

    @GetMapping("/update-product/{productId}")
    public String updateProductForm(@PathVariable("productId") Integer productId, Model model){

        Product product = productService.getProductById(productId);

        if(product == null){
            return "generic/not_found";
        }

        model.addAttribute("product", product);

        // Options
        List<Ram> ramOptions = ramService.getAllRam();
        List<Storage> storageOptions = storageService.getllAllStorages();
        List<Category> categories = categoryService.getAllCategories();
        List<Brand> brands = brandService.getAllBrands();
        List<Color> colors = colorService.getAllColors();

        model.addAttribute("ramOptions", ramOptions);
        model.addAttribute("storageOptions", storageOptions);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("colorOptions", colors);

        return "products/update_product";
    }

    @PostMapping("/save-changes")
    public String saveChanges(@ModelAttribute ProductRequest updatedProduct, RedirectAttributes attributes){

        Product product = productService.getProductById(updatedProduct.getId());

        // verify if image fields were updated

        if(!updatedProduct.getImage1().isEmpty()){
            String image1Path = UploadFiles.saveFile(updatedProduct.getImage1(), productImagePath);
            product.setImage1(image1Path);
        }

        if(!updatedProduct.getImage2().isEmpty()){
            String image1Path = UploadFiles.saveFile(updatedProduct.getImage2(), productImagePath);
            product.setImage2(image1Path);
        }

        if(!updatedProduct.getImage3().isEmpty()){
            String image1Path = UploadFiles.saveFile(updatedProduct.getImage3(), productImagePath);
            product.setImage3(image1Path);
        }

        if(!updatedProduct.getImage4().isEmpty()){
            String image1Path = UploadFiles.saveFile(updatedProduct.getImage4(), productImagePath);
            product.setImage4(image1Path);
        }

        // update other fields
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPurchase_price(updatedProduct.getPurchase_price());
        product.setSellPrice(updatedProduct.getSellPrice());
        product.setIsOffer(updatedProduct.getIsOffer());
        if(updatedProduct.getRam_option() != null){
            product.setRam_option(updatedProduct.getRam_option());
        }
        if(updatedProduct.getStorage_option() != null){
            product.setStorage_option(updatedProduct.getStorage_option());
        }
        product.setOfferPrice(updatedProduct.getOfferPrice());
        product.setSpecifications(updatedProduct.getSpecifications());
        product.setStock(updatedProduct.getStock());
        product.setCategory(updatedProduct.getCategory());
        product.setBrand(updatedProduct.getBrand());
        if(updatedProduct.getRam_options() != null){
            product.setRam_options(updatedProduct.getRam_options());
        }
        if(updatedProduct.getStorage_options() != null){
            product.setStorage_options(updatedProduct.getStorage_options());
        }
        product.setColor_options(updatedProduct.getColor_options());

        productService.saveProduct(product);

        attributes.addFlashAttribute("msg", "The product "+product.getName()+" has been updated successfully");
        return "redirect:/products/manage-products";
    }

    @GetMapping("/new-product")
    public String createProduct(Model model){

        List<Ram> ramOptions = ramService.getAllRam();
        List<Storage> storageOptions = storageService.getllAllStorages();
        List<Category> categories = categoryService.getAllCategories();
        List<Brand> brands = brandService.getAllBrands();
        List<Color> colors = colorService.getAllColors();

        model.addAttribute("ramOptions", ramOptions);
        model.addAttribute("storageOptions", storageOptions);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("colorOptions", colors);

        return "products/create_product";
    }

    @PostMapping("/save-product")
    public String asveNewProduct(@ModelAttribute ProductRequest newProduct, RedirectAttributes attributes){

        Product product = new Product();

        // save images
        if(!newProduct.getImage1().isEmpty()){
            String image1Path = UploadFiles.saveFile(newProduct.getImage1(), productImagePath);
            product.setImage1(image1Path);
        }

        if(!newProduct.getImage2().isEmpty()){
            String image1Path = UploadFiles.saveFile(newProduct.getImage2(), productImagePath);
            product.setImage2(image1Path);
        }

        if(!newProduct.getImage3().isEmpty()){
            String image1Path = UploadFiles.saveFile(newProduct.getImage3(), productImagePath);
            product.setImage3(image1Path);
        }

        if(!newProduct.getImage4().isEmpty()){
            String image1Path = UploadFiles.saveFile(newProduct.getImage4(), productImagePath);
            product.setImage4(image1Path);
        }

        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPurchase_price(newProduct.getPurchase_price());
        product.setSellPrice(newProduct.getSellPrice());
        product.setIsOffer(newProduct.getIsOffer());
        product.setOfferPrice(newProduct.getOfferPrice());
        product.setSpecifications(newProduct.getSpecifications());
        product.setStock(newProduct.getStock());
        product.setCategory(newProduct.getCategory());
        product.setBrand(newProduct.getBrand());
        product.setRam_option(newProduct.getRam_option());
        if(newProduct.getRam_options() != null){
            product.setRam_options(newProduct.getRam_options());
        }
        product.setStorage_option(newProduct.getStorage_option());
        if(newProduct.getStorage_options() != null){
            product.setStorage_options(newProduct.getStorage_options());
        }
        product.setColor_options(newProduct.getColor_options());

        productService.saveProduct(product);

        attributes.addFlashAttribute("msg", "The product "+newProduct.getName()+" has been created successfully");
        return "redirect:/products/manage-products";
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam("productId") Integer productId, RedirectAttributes attributes){

        Product product = productService.getProductById(productId);
        
        productService.deleteProduct(product);

        attributes.addFlashAttribute("msg", "The product "+product.getName()+" has been deleted successfully");
        return "redirect:/products/manage-products";
    }
}
