package com.ecommerce.services.interfaces;

import java.util.List;

import com.ecommerce.models.Storage;

public interface IStorageService {
    
    List<Storage> getStoragesWithOfferProducts();
    Storage getStorageById(Integer storageId);
    List<Storage> getllAllStorages();
}
