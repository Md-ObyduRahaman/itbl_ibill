package com.itbl.ibill;

import java.util.ArrayList;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oracle.jdbc.OracleTypes;

@RestController
@RequestMapping(path = "/api")
public class ApiController {

	SimpleJdbcCall getAllStatesJdbcCall;

	public ApiController(DataSource dataSource) {
		this.getAllStatesJdbcCall = new SimpleJdbcCall(dataSource);
	}

	@GetMapping("/book/{location}/{billgrp}")
	public ArrayList<BillCycleModel> book(@PathVariable("location") String location,
			@PathVariable("billgrp") String billgrp,
			@CookieValue(value = "user_name", defaultValue = "") String user_name) {
		ArrayList<BillCycleModel> billCycleModels = new ArrayList<>();

		Map<String, Object> result = getAllStatesJdbcCall.withCatalogName("DPG_MBILL_MRS_ENTRY")
				.withProcedureName("DPD_BOOK_LIST")
				.declareParameters(new SqlOutParameter("CUR_DATA", OracleTypes.CURSOR))
				.execute(location, billgrp, user_name);

		JSONObject json = new JSONObject(result);
		String jesonString = json.get("CUR_DATA").toString();
		JSONArray jsonArray = new JSONArray(jesonString);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonData = jsonArray.getJSONObject(i);
			billCycleModels.add(new BillCycleModel(jsonData.optString("BLOCK_NUM")));
		}

		return billCycleModels;
	}

//	@GetMapping("/SaveButton/{mrsEntryModels}")
//	public String SaveButton(MRSEntryModel mrsEntryModel) {
//		String lol = "";
//		return lol;
//	}

}
