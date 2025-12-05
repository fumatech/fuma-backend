package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.SaleReturn;
import com.backend.Entity.SaleReturnItems;
import com.backend.Entity.StockTransaction;
import com.backend.Repository.SaleReturnRepo;
import com.backend.Service.SaleReturnService;

@Service
public class SaleReturnServiceImpl implements SaleReturnService {

    @Autowired
    private SaleReturnRepo saleReturnRepo;

    @Override
    public SaleReturn saveSaleReturn(SaleReturn saleReturn) {
        if (saleReturn.getSaleReturnItems() != null) {
            for (SaleReturnItems item : saleReturn.getSaleReturnItems()) {
                item.setSaleReturn(saleReturn);
            }
        }
        if (saleReturn.getStockTransactions() != null) {
            for (StockTransaction stock : saleReturn.getStockTransactions()) {
                stock.setSaleReturn(saleReturn);
            }
        }

        return saleReturnRepo.save(saleReturn);
    }

    @Override
    public List<SaleReturn> getAllSaleReturns() {
        return saleReturnRepo.findAll();
    }

    @Override
    public Optional<SaleReturn> getSaleReturnById(Long id) {
        return saleReturnRepo.findById(id);
    }

    @Override
    public SaleReturn updateSaleReturn(Long id, SaleReturn updatedSaleReturn) {
        Optional<SaleReturn> existingSaleReturn = saleReturnRepo.findById(id);

        if (existingSaleReturn.isPresent()) {
            SaleReturn saleReturn = existingSaleReturn.get();
            saleReturn.setVendor(updatedSaleReturn.getVendor());
            saleReturn.setStatus(updatedSaleReturn.getStatus());
            saleReturn.setAddedBy(updatedSaleReturn.getAddedBy());
            saleReturn.setReferenceNumber(updatedSaleReturn.getReferenceNumber());
            saleReturn.setOrderDate(updatedSaleReturn.getOrderDate());
            saleReturn.setLocation(updatedSaleReturn.getLocation());
            saleReturn.setTotalItems(updatedSaleReturn.getTotalItems());
            saleReturn.setTotalShippedItems(updatedSaleReturn.getTotalShippedItems());
            saleReturn.setAdditionalNotes(updatedSaleReturn.getAdditionalNotes());

            // Update SaleReturnItems
            saleReturn.getSaleReturnItems().clear();
            saleReturn.getSaleReturnItems().addAll(updatedSaleReturn.getSaleReturnItems());
            for (SaleReturnItems item : saleReturn.getSaleReturnItems()) {
                item.setSaleReturn(saleReturn);
            }

            // Update StockTransactions
            saleReturn.getStockTransactions().clear();
            saleReturn.getStockTransactions().addAll(updatedSaleReturn.getStockTransactions());
            for (StockTransaction stock : saleReturn.getStockTransactions()) {
                stock.setSaleReturn(saleReturn);
            }

            return saleReturnRepo.save(saleReturn);
        }

        return null; // or throw an exception if not found
    }

    @Override
    public void deleteSaleReturn(Long id) {
        if (saleReturnRepo.existsById(id)) {
            saleReturnRepo.deleteById(id);
        }
    }

    @Override
    public List<String> getAllReturnIds() {
        return saleReturnRepo.findAll().stream()
                .map(SaleReturn::getPurchaseReturnId) // assuming you have a getPurchaseReturnId method in SaleReturn
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SaleReturn> getSaleReturnByPRId(String purchaseReturnId) {
        return Optional.ofNullable(saleReturnRepo.findByPurchaseReturnId(purchaseReturnId));
    }

    @Override
    public List<String> getSaleReturnIdsByStatus(Long status) {
        return saleReturnRepo.findSaleReturnIdsByStatus(status);
        
    }

    @Override
    public List<SaleReturn> getPendingOrders() {
        return saleReturnRepo.findBySaleReturnStatus(0L); // Fetch orders with status 0
    }

    @Override
    public List<SaleReturn> getAcceptedOrders() {
        return saleReturnRepo.findBySaleReturnStatus(1L); // Fetch orders with status 1
    }

    @Override
    public List<SaleReturn> getRejectedOrders() {
        return saleReturnRepo.findBySaleReturnStatus(2L); // Fetch orders with status 2
    }

    @Override
    public List<SaleReturn> getShipOrders() {
        return saleReturnRepo.findBySaleReturnStatus(3L); // Fetch orders with status 3
    }

    @Override
    public Long getTotalShippedItems(String purchaseReturnId) {
        return saleReturnRepo.getTotalShippedItems(purchaseReturnId);
    }
}
