//package com.backend.ServiceImpl;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import com.backend.Entity.SaleSoOrder;
//import com.backend.Entity.StockTransaction;
//import com.backend.Service.SaleSoOrderService;
//import com.backend.Service.StockTransactionService;
//
//@SpringBootTest
//public class SaleSoOrderValidationTest {
//
//    @Autowired
//    private SaleSoOrderService saleSoOrderService;
//
//    @Autowired
//    private StockTransactionService stockTransactionService;
//
//    @Test
//    public void testInsufficientStockThrowsException() {
//        // We use a dummy product/variation ID. Even if it doesn't exist,
//        // stock service will return 0, and requesting 1 should fail.
//        Long productId = 999999L;
//        Long variationId = 999999L;
//
//        SaleSoOrder order = new SaleSoOrder();
//        List<StockTransaction> transactions = new ArrayList<>();
//
//        StockTransaction transaction = new StockTransaction();
//        transaction.setProductId(productId);
//        transaction.setVariationId(variationId);
//        transaction.setQuantity(1); // Any quantity > 0 will fail if stock is 0
//        transaction.setTransactionType("so_sale");
//
//        transactions.add(transaction);
//        order.setStockTransactions(transactions);
//
//        // This should throw a RuntimeException due to the new validation
//        assertThrows(RuntimeException.class, () -> {
//            saleSoOrderService.saveSaleSooOrder(order);
//        });
//    }
//}
