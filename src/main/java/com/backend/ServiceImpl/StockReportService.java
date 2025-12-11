package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.Entity.Categories;
import com.backend.Entity.Product;
import com.backend.Entity.ProductVariations;
import com.backend.Entity.StockReportDTO;
import com.backend.Entity.StockTransaction;
import com.backend.Repository.CategoriesRepo;
import com.backend.Repository.ProductRepo;
import com.backend.Repository.ProductVariationsRepo;
import com.backend.Repository.StockTransactionRepo;

@Service
public class StockReportService {

	private final ProductRepo productRepo;
	private final ProductVariationsRepo variationsRepo;
	private final StockTransactionRepo stockRepo;
	private final CategoriesRepo categoriesRepo;

	public StockReportService(ProductRepo productRepo, ProductVariationsRepo variationsRepo,
			StockTransactionRepo stockRepo, CategoriesRepo categoriesRepo) {
		this.productRepo = productRepo;
		this.variationsRepo = variationsRepo;
		this.stockRepo = stockRepo;
		this.categoriesRepo = categoriesRepo;
	}

	public List<StockReportDTO> getStockReport() {

		List<Product> products = productRepo.findAll();
		List<StockReportDTO> reportList = new ArrayList<>();

		for (Product product : products) {

			if (product.getProductType().name().equalsIgnoreCase("Single")) {

				List<StockTransaction> list = stockRepo.findByProductId(product.getId());
				StockReportDTO dto = computeStock(product, null, list);
				reportList.add(dto);
			}

			if (product.getProductType().name().equalsIgnoreCase("Variable")) {

				for (ProductVariations pv : product.getProductVariations()) {

					List<StockTransaction> list = stockRepo.findByProductIdAndVariationId(product.getId(), pv.getId());

					StockReportDTO dto = computeStock(product, pv, list);
					reportList.add(dto);
				}
			}
		}

		return reportList;
	}

	// ===================== FINAL STOCK CALCULATION =====================
	private StockReportDTO computeStock(Product product, ProductVariations pv, List<StockTransaction> transactions) {

		StockReportDTO dto = new StockReportDTO();

		dto.setProductId(product.getId());
		dto.setProductName(product.getProductName());
		dto.setSku(product.getSku());
		dto.setBusinessLocation(product.getBusinessLocation());

		// --------- Get CATEGORY NAME by ID -------------
		try {
			Long categoryId = Long.parseLong(product.getCategory());
			Categories cat = categoriesRepo.findById(categoryId).orElse(null);
			dto.setCategory(cat != null ? cat.getCategoryName() : "N/A");
		} catch (Exception e) {
			dto.setCategory("N/A");
		}

		// Variation values
		if (pv != null) {
			dto.setVariationId(pv.getId());
			dto.setVariationName(pv.getVariationName());
			dto.setVariationValue(pv.getVariationValue());
			dto.setSku(pv.getSubSku());
			dto.setUnitSellingPrice(pv.getDefaultSellingPrice());
			dto.setDefaultPurchasePrice(pv.getDefaultPurchasePriceExcTax());
		}

		// ===================== STOCK COUNTS =====================
		int purchased = 0;
		int sold = 0;
		int adjusted = 0;
		int returnedSale = 0;

		for (StockTransaction st : transactions) {

			String type = st.getTransactionType().toLowerCase();

			switch (type) {

			case "po_purchase":
			case "di_purchase":
			case "open_stock":
				purchased += st.getQuantity();
				break;

			case "di_sale":
			case "so_sale":
				sold += st.getQuantity();
				break;

			case "purchase_return":
				purchased -= st.getQuantity();
				break;

			case "sale_return":
				returnedSale += st.getQuantity();
				break;

			case "product_claimed":
				purchased += st.getQuantity();
				break;

			case "product_replaced":
				sold += st.getQuantity();
				break;

			case "adjustment":
				adjusted += st.getQuantity();
				break;
			}
		}

		int currentStock = purchased - sold + adjusted + returnedSale;

		dto.setTotalPurchased(purchased);
		dto.setTotalSold(sold);
		dto.setTotalAdjusted(adjusted);
		dto.setCurrentStock(currentStock);

		// ===================== STOCK VALUE =====================
		BigDecimal purchaseRate = pv != null ? pv.getDefaultPurchasePriceExcTax() : BigDecimal.ZERO;
		BigDecimal sellingRate = pv != null ? pv.getDefaultSellingPrice() : BigDecimal.ZERO;

		dto.setCurrentStockValueByPurchase(purchaseRate.multiply(BigDecimal.valueOf(currentStock)));
		dto.setCurrentStockValueBySale(sellingRate.multiply(BigDecimal.valueOf(currentStock)));
		dto.setPotentialProfit(sellingRate.subtract(purchaseRate).multiply(BigDecimal.valueOf(currentStock)));

		return dto;
	}
}
