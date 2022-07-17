package com.itbl.ibill;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

@Repository
public class MainDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	SimpleJdbcCall getAllStatesJdbcCall;
	SimpleJdbcCall getAllStatesJdbcCall1;
	SimpleJdbcCall getAllStatesJdbcCall2;
	SimpleJdbcCall getAllStatesJdbcCall3;
	SimpleJdbcCall getAllStatesJdbcCall4;
	SimpleJdbcCall getAllStatesJdbcCall5;

	public MainDao(DataSource dataSource) {
		this.getAllStatesJdbcCall = new SimpleJdbcCall(dataSource);
		this.getAllStatesJdbcCall1 = new SimpleJdbcCall(dataSource);
		this.getAllStatesJdbcCall2 = new SimpleJdbcCall(dataSource);
		this.getAllStatesJdbcCall3 = new SimpleJdbcCall(dataSource);
		this.getAllStatesJdbcCall4 = new SimpleJdbcCall(dataSource);
		this.getAllStatesJdbcCall5 = new SimpleJdbcCall(dataSource);
	}

	public LoginModel getLogin(String name, String pass) {

		LoginModel user = null;
		Map<String, Object> result = getAllStatesJdbcCall5.withCatalogName("DPG_MBILL_USER_LOGIN")
				.withProcedureName("DPD_USER_LOGIN_DATA")
				.declareParameters(new SqlOutParameter("results_cursor", OracleTypes.VARCHAR)).execute(name, pass);

		JSONObject json = new JSONObject(result);
		String fjfhdj = json.get("CUR_DATA").toString();
		JSONArray jsonArray = new JSONArray(fjfhdj);

		System.out.println(jsonArray);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonData = jsonArray.getJSONObject(i);
			user = new LoginModel(jsonData.getString("USER_NAME"), jsonData.getString("USER_PASSWORD"),
					jsonData.getString("ROLE_ID"));

		}

		return user;

	}

	public ArrayList<BillCycleModel> billCycle(String id) {
		ArrayList<BillCycleModel> billCycleModels = new ArrayList<>();

		Map<String, Object> result = getAllStatesJdbcCall.withCatalogName("DPG_MBILL_MRS_ENTRY")
				.withProcedureName("DPD_BILL_CYCLE_LIST")
				.declareParameters(new SqlOutParameter("CUR_DATA", OracleTypes.CURSOR)).execute(id);

		JSONObject json = new JSONObject(result);
		String jesonString = json.get("CUR_DATA").toString();
		JSONArray jsonArray = new JSONArray(jesonString);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonData = jsonArray.getJSONObject(i);
			billCycleModels.add(new BillCycleModel(jsonData.optString("BILL_CYCLE")));
		}

		return billCycleModels;
	}

	public ArrayList<LocationModel> location(String id) {
		ArrayList<LocationModel> locationModels = new ArrayList<>();

		Map<String, Object> result = getAllStatesJdbcCall3.withCatalogName("DPG_MBILL_MRS_ENTRY")
				.withProcedureName("DPD_LOCATION_LIST")
				.declareParameters(new SqlOutParameter("CUR_DATA", OracleTypes.CURSOR)).execute(id);

		JSONObject json = new JSONObject(result);
		String jesonString = json.get("CUR_DATA").toString();
		JSONArray jsonArray = new JSONArray(jesonString);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonData = jsonArray.getJSONObject(i);
			locationModels
					.add(new LocationModel(jsonData.optString("LOCATION_DESC"), jsonData.optString("LOCATION_CODE")));
		}

		return locationModels;
	}

	public ArrayList<BillGroupModel> billgroup(String id) {
		ArrayList<BillGroupModel> billGroupModels = new ArrayList<BillGroupModel>();

		Map<String, Object> result = getAllStatesJdbcCall4.withCatalogName("DPG_MBILL_MRS_ENTRY")
				.withProcedureName("DPD_BILL_GROUP_LIST")
				.declareParameters(new SqlOutParameter("CUR_DATA", OracleTypes.CURSOR)).execute(id);

		JSONObject json = new JSONObject(result);
		String jesonString = json.get("CUR_DATA").toString();
		JSONArray jsonArray = new JSONArray(jesonString);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonData = jsonArray.getJSONObject(i);
			billGroupModels
					.add(new BillGroupModel(jsonData.optString("BILL_GROUP"), jsonData.optString("BILL_GRP_DESCR")));
		}

		return billGroupModels;
	}

	public ArrayList<MRSEntryModel> funMrsEntry1(ArrayList<String> CUST_ID, ArrayList<String> BILL_CYCLE_CODE,
			ArrayList<String> PRE_READING, ArrayList<String> ADV, ArrayList<String> PRE_OFPK_RDNG,
			ArrayList<String> OFPK_ADV, ArrayList<String> PRE_PK_RDNG, ArrayList<String> PK_ADV,
			ArrayList<String> MTR_COND, String cycle, String location, String billgrp, String book, String walkseq,
			String id, String wstype) {
		ArrayList<MRSEntryModel> mrsEntryModels = new ArrayList<>();

		Map<String, Object> result = getAllStatesJdbcCall2.withCatalogName("DPG_MBILL_MRS_ENTRY")
				.withProcedureName("DPD_MBILL_MRS_DATA_GRID")
				.declareParameters(new SqlOutParameter("CUR_DATA", OracleTypes.CURSOR))
				.execute(cycle, location, billgrp, book, walkseq, id, wstype);

		JSONObject json = new JSONObject(result);
		String jesonString = json.get("CUR_DATA").toString();
		JSONArray jsonArray = new JSONArray(jesonString);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonData = jsonArray.getJSONObject(i);
			// for (int i1 = 0; i1 < CUST_ID.size(); i1++) {
			// if (jsonData.optString("CUST_ID").equals(CUST_ID.get(i1))) {
			mrsEntryModels.add(new MRSEntryModel(jsonData.optString("CUST_ID"), jsonData.optString("CUSTOMER_NUM"),
					jsonData.optString("CUSTOMER_NAME"), jsonData.optString("METER_NUM"),
					jsonData.optString("TOD_CODE"), MTR_COND.get(i), jsonData.optString("PREV_READING"),
					PRE_READING.get(i), ADV.get(i), jsonData.optString("PREV_OFPK_RDNG"), PRE_OFPK_RDNG.get(i),
					OFPK_ADV.get(i), jsonData.optString("PREV_PK_RDNG"), PRE_PK_RDNG.get(i), PK_ADV.get(i),
					jsonData.optString("WALK_SEQ"), jsonData.optString("BILL_CYCLE_CODE"),
					formatter.format(Date.valueOf(jsonData.optString("PRV_RDG_DATE").replace(" 00:00:00.0", ""))),
					jsonData.optString("PREV_AC_NO"),
					formatter.format(Date.valueOf(jsonData.optString("PRE_RDG_DATE").replace(" 00:00:00.0", ""))),
					jsonData.optString("TARIFF")));
			// }
//				mrsEntryModels.add(new MRSEntryModel(CUST_ID.get(i1), "lol", "lol", "lol", "lol", MTR_COND.get(i1),
//						PRE_READING.get(i1), "lol", ADV.get(i1), PRE_OFPK_RDNG.get(i1), "lol", OFPK_ADV.get(i1),
//						PRE_PK_RDNG.get(i1), "lol", PK_ADV.get(i1), "lol", BILL_CYCLE_CODE.get(i1), "lol"));
			// }

		}

		return mrsEntryModels;
	}

	public ArrayList<MRSEntryModel> funMrsEntry(String billcycle, String location, String billgrp, String book,
			String walkseq, String id, String wstype) {
		ArrayList<MRSEntryModel> mrsEntryModels = new ArrayList<>();

		Map<String, Object> result = getAllStatesJdbcCall2.withCatalogName("DPG_MBILL_MRS_ENTRY")
				.withProcedureName("DPD_MBILL_MRS_DATA_GRID")
				.declareParameters(new SqlOutParameter("CUR_DATA", OracleTypes.CURSOR))
				.execute(billcycle, location, billgrp, book, walkseq, id, wstype);

		JSONObject json = new JSONObject(result);
		String jesonString = json.get("CUR_DATA").toString();
		JSONArray jsonArray = new JSONArray(jesonString);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonData = jsonArray.getJSONObject(i);
			mrsEntryModels.add(new MRSEntryModel(jsonData.optString("CUST_ID"), jsonData.optString("CUSTOMER_NUM"),
					jsonData.optString("CUSTOMER_NAME"), jsonData.optString("METER_NUM"),
					jsonData.optString("TOD_CODE"), jsonData.optString("MTR_COND"), jsonData.optString("PREV_READING"),
					jsonData.optString("PRE_READING"), jsonData.optString("ADV"), jsonData.optString("PREV_OFPK_RDNG"),
					jsonData.optString("PRE_OFPK_RDNG"), jsonData.optString("OFPK_ADV"),
					jsonData.optString("PREV_PK_RDNG"), jsonData.optString("PRE_PK_RDNG"), jsonData.optString("PK_ADV"),
					jsonData.optString("WALK_SEQ"), jsonData.optString("BILL_CYCLE_CODE"),
					formatter.format(Date.valueOf(jsonData.optString("PRV_RDG_DATE").replace(" 00:00:00.0", ""))),
					jsonData.optString("PREV_AC_NO"),
					formatter.format(Date.valueOf(jsonData.optString("PRE_RDG_DATE").replace(" 00:00:00.0", ""))),
					jsonData.optString("TARIFF")));
		}

		return mrsEntryModels;
	}

	public int updateMrsData(ArrayList<String> CUST_ID, ArrayList<String> BILL_CYCLE_CODE,
			ArrayList<String> PRE_READING, ArrayList<String> ADV, ArrayList<String> PRE_OFPK_RDNG,
			ArrayList<String> OFPK_ADV, ArrayList<String> PRE_PK_RDNG, ArrayList<String> PK_ADV,
			ArrayList<String> MTR_COND, String id) {
		String one[] = optStringArray(CUST_ID);
		String two[] = optStringArray(BILL_CYCLE_CODE);
		String three[] = optStringArray(PRE_READING);
		String four[] = optStringArray(ADV);
		String five[] = optStringArray(PRE_OFPK_RDNG);
		String six[] = optStringArray(OFPK_ADV);
		String seven[] = optStringArray(PRE_PK_RDNG);
		String eight[] = optStringArray(PK_ADV);
		String nine[] = optStringArray(MTR_COND);
		ArrayDescriptor des1, des2, des3, des4, des5, des6, des7, des8, des9;
		ARRAY aOne = null, aTwo = null, aThree = null, aFour = null, aFive = null, aSix = null, aSeven = null,
				aEight = null, aNine = null;
		CallableStatement st = null;
		int out = 0;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@172.16.1.73:1521/ibill", "MBILL", "MBILL");
			des1 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_CUST_ID", con);
			aOne = new ARRAY(des1, con, one);
			des2 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_BILL_CYCLE_CODE", con);
			aTwo = new ARRAY(des2, con, two);
			des3 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_CLS_KWH_SR_RDNG", con);
			aThree = new ARRAY(des3, con, three);
			des4 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_CONS_KWH_SR", con);
			aFour = new ARRAY(des4, con, four);
			des5 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_CLS_KWH_OFPK_RDNG", con);
			aFive = new ARRAY(des5, con, five);
			des6 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_CONS_KWH_OFPK", con);
			aSix = new ARRAY(des6, con, six);
			des7 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_CLS_KWH_PK_RDNG", con);
			aSeven = new ARRAY(des7, con, seven);
			des8 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_CONS_KWH_PK", con);
			aEight = new ARRAY(des8, con, eight);
			des9 = ArrayDescriptor.createDescriptor("MBILL.ARRAY_METER_COND_KWH", con);
			aNine = new ARRAY(des9, con, nine);
			st = con.prepareCall("call DPG_MBILL_MRS_ENTRY.DPD_MBILL_MRS_DATA_SAVE(?,?,?,?,?,?,?,?,?,?,?)");
			st.registerOutParameter(1, Types.INTEGER);
			st.setArray(2, aOne);
			st.setArray(3, aTwo);
			st.setArray(4, aThree);
			st.setArray(5, aFour);
			st.setArray(6, aFive);
			st.setArray(7, aSix);
			st.setArray(8, aSeven);
			st.setArray(9, aEight);
			st.setArray(10, aNine);
			st.setString(11, id);
			st.execute();
			out = st.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public static String[] optStringArray(ArrayList<String> arr) {

		// declaration and initialise String Array
		String str[] = new String[arr.size()];

		// Convert ArrayList to object array
		Object[] objArr = arr.toArray();

		// Iterating and converting to String
		int i = 0;
		for (Object obj : objArr) {
			str[i++] = (String) obj;
		}

		return str;
	}

	/*
	 * public static int[] optIntegerArray(ArrayList<String> nums) { int[] arr = new
	 * int[nums.size()]; for (int i = 0; i < nums.size(); i++) { try { arr[i] =
	 * Integer.valueOf(nums.get(i)); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * return arr; }
	 */

}
