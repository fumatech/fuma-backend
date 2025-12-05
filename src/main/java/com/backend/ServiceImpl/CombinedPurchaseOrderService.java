package com.backend.ServiceImpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Repository.PurchasePoOrderRepo;
import com.backend.Repository.PurchaseDIOrderRepo;
import com.backend.Entity.PurchasePoOrder;
import com.backend.Entity.PurchaseDIOrder;


@Service
public class CombinedPurchaseOrderService {

    @Autowired
    private PurchasePoOrderRepo purchasePoOrderRepo;

    @Autowired
    private PurchaseDIOrderRepo purchaseDIOrderRepo;

    public Map<String, List<Object>> getPurchasesByVendor(String vendorName) {
        Map<String, List<Object>> vendorWiseOrders = new HashMap<>();

        List<PurchasePoOrder> poOrders = purchasePoOrderRepo.findByVendorName(vendorName);
        vendorWiseOrders.put(vendorName, new ArrayList<>(poOrders));

        List<PurchaseDIOrder> diOrders = purchaseDIOrderRepo.findByVendorName(vendorName);
        vendorWiseOrders.get(vendorName).addAll(diOrders);

        return vendorWiseOrders;
    }
}
