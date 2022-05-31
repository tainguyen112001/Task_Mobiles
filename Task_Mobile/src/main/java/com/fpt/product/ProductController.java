package com.fpt.product;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/products/add")
    public String showFormProduct(Model model, HttpSession session, RedirectAttributes ra) {
        int tou =0;
        if(session.getAttribute("tou")!=null){

            tou=(int) session.getAttribute("tou");
        }
        if (tou == 1) {
            String idu = "admin";
            model.addAttribute("product", new Product());
            model.addAttribute("idu", idu);
            return "product_add";
        } else {
            ra.addFlashAttribute("message", "This is Admin Page");
            return "redirect:/products/show";
        }
    }
    @PostMapping("/products/add")
    public String addNewProduct(Model model,Product product){
        service.save(product);
        String idu = "admin";
        model.addAttribute("product", new Product());
    model.addAttribute("idu", idu);
        return "product_add";

    }

    @GetMapping("/products/show")
    public String showProduct(Model model, HttpSession session) {
        int tou =0;
        if(session.getAttribute("tou")!=null){

            tou=(int) session.getAttribute("tou");
        }

        if (tou == 1) {
            String idu = "admin";
            model.addAttribute("idu", idu);
            return "addproducts";
        } else {
            if (session.getAttribute("username") != null) {
                String idu = session.getAttribute("username").toString();
                model.addAttribute("idu", idu);
            }
            List<Product> listProducts = service.listAll();
            model.addAttribute("listProducts", listProducts);
            return "Product_list";
    }

    }

    @GetMapping("/products/show/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model, HttpSession session) throws ProductNotFoundException {
        try {
            int tou =0;
            if(session.getAttribute("tou")!=null){

                tou=(int) session.getAttribute("tou");
            }
            if (tou == 1) {
                String idu = "admin";
                model.addAttribute("idu", idu);
                return "addproducts";
            } else {
                Product product = service.get(id);
                model.addAttribute("products", product);
                return "product_details";
            }
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }
        return "login";
        }

    @PostMapping("/users/save")
    public RedirectView saveUser(@ModelAttribute(name="product") Product product, RedirectAttributes ra,
                                 @RequestParam("fileimage") MultipartFile multipartfile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename());
        product.setPhoto(fileName);
        Product saveproduct = service.save(product);

        String uploadDir = "/product-photos/" + saveproduct.getIdproduct();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartfile.getInputStream()){ ;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
    }catch ( IOException e){
            throw new IOException("Could not save upload file:" +fileName);
        }
        FileUploadUtil.saveFile(uploadDir, fileName, multipartfile);

        return new RedirectView("/product", true);
    }
}
