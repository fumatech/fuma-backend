package com.backend.Service;

import java.util.List;

import com.backend.Entity.BrandWiseReportDTO;
import com.backend.Entity.CategoryWiseReportDTO;
import com.backend.Entity.GroupedDateReportDTO;

public interface ReportService {

	List<BrandWiseReportDTO> getBrandWiseReport();

	List<CategoryWiseReportDTO> getCategoryWiseReport();

	List<GroupedDateReportDTO> getGroupedDateReport();

}
