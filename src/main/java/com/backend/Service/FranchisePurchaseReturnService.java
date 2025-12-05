package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.FranchisePurchaseReturn;

public interface FranchisePurchaseReturnService {
	FranchisePurchaseReturn saveFranchisePurchaseReturn(FranchisePurchaseReturn purchaseReturn);

	List<FranchisePurchaseReturn> getAllFranchisePurchaseReturns();

	Optional<FranchisePurchaseReturn> getFranchisePurchaseReturnById(Long id);

	FranchisePurchaseReturn updateFranchisePurchaseReturn(Long id, FranchisePurchaseReturn purchaseReturn);

	void deleteFranchisePurchaseReturn(Long id);

	List<String> getAllReturnIds();

	Optional<FranchisePurchaseReturn> getFranchisePurchaseReturnByIdString(String id);

	List<String> getReturnIdsByStatus(Long status);

	List<FranchisePurchaseReturn> getPendingReturns();

	List<FranchisePurchaseReturn> getAcceptedReturns();

	List<FranchisePurchaseReturn> getRejectedReturns();

	List<FranchisePurchaseReturn> getShipReturns();

	Long getTotalShippedItems(String id);
}
