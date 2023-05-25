package com.ecommerce.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.customObjects.AdvertisementRequest;
import com.ecommerce.helpers.UploadFiles;
import com.ecommerce.models.Advertisement;
import com.ecommerce.services.interfaces.IAdvertisementService;


@Controller
@RequestMapping("/advertisements")
public class AdvertisementController {

    @Autowired
    private IAdvertisementService advertisementService;

    @Value("${ecommerceapp.path.advertisements}")
    private String advertisementImagePath;
    
    @GetMapping("/details/{id}")
    public String advertisementDetails(@PathVariable("id") Integer advertisementId, Model model){

        Advertisement advertisement = advertisementService.getAdvertisementById(advertisementId);

        if(advertisement == null){
            return "generic/not_found";
        }

        model.addAttribute("advertisement", advertisement);

        return "advertisement/advertisement_details";
    }

    @GetMapping("/manage-advertisements")
    public String mangeAdvertisements(Model model, Pageable pageable){

        Page<Advertisement> advertisements = advertisementService.getAllAdvertisements(pageable);
        model.addAttribute("advertisements", advertisements);
        return "advertisement/manage_advertisement";
    }

    @GetMapping("/update-advertisement/{id}")
    public String updateAdvertisement(@PathVariable("id") Integer id, Model model){

        Advertisement advertisement = advertisementService.getAdvertisementById(id);

        if(advertisement == null){
            return "generic/not_found";
        }

        model.addAttribute("advertisement", advertisement);
        return "advertisement/update_advertisement";
    }

    @GetMapping("/create-advertisement")
    public String createAdvertisement(){
        return "advertisement/create_advertisement";
    }

    @PostMapping("/save-advertisement")
    public String saveAdvertisement(@ModelAttribute AdvertisementRequest advertisementRequest, RedirectAttributes attributes){

        if(advertisementRequest.getId() != null){
            Advertisement advertisement = advertisementService.getAdvertisementById(advertisementRequest.getId());

            advertisement.setTitle(advertisementRequest.getTitle());
            advertisement.setSubtitle(advertisementRequest.getSubtitle());
            advertisement.setContent(advertisementRequest.getContent());
            
            if(!advertisementRequest.getImage().isEmpty()){
                String imageName = UploadFiles.saveFile(advertisementRequest.getImage(), advertisementImagePath);
                advertisement.setImage(imageName);
            }

            advertisementService.advertisementCreateUpdate(advertisement);

            attributes.addFlashAttribute("msg", "Advertisement "+advertisement.getTitle()+" has been updated successfully");
            return "redirect:/advertisements/manage-advertisements";
        }

        Advertisement newAdvertisement = new Advertisement();

        // set values
        newAdvertisement.setTitle(advertisementRequest.getTitle());
        newAdvertisement.setSubtitle(advertisementRequest.getSubtitle());
        newAdvertisement.setContent(advertisementRequest.getContent());
        
        if(!advertisementRequest.getImage().isEmpty()){
            String imageName = UploadFiles.saveFile(advertisementRequest.getImage(), advertisementImagePath);
            newAdvertisement.setImage(imageName);
        }

        newAdvertisement.setCreatedAt(new Date());

        advertisementService.advertisementCreateUpdate(newAdvertisement);

        attributes.addFlashAttribute("msg", "Advertisement "+newAdvertisement.getTitle()+" has been created successfully");
        return "redirect:/advertisements/manage-advertisements";
    }

    @PostMapping("/delete-advertisement")
    public String deleteAdvertisement(@RequestParam("advertisementId") Integer id, RedirectAttributes attributes){

        Advertisement advertisement = advertisementService.getAdvertisementById(id);

        advertisementService.deleteAdvertisement(advertisement);

        attributes.addFlashAttribute("msg", "Advertisement "+advertisement.getTitle()+" has been deleted successfully");
        return "redirect:/advertisements/manage-advertisements";
    }

}
