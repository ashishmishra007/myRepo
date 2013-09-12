package com.hiddenbrains.avtaltid.main.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hiddenbrains.avtaltid.Parsing.PostDataAndGetData;
import com.hiddenbrains.avtaltid.Parsing.parseListner;
import com.hiddenbrains.avtaltid.Webservices.DataHandler;
import com.hiddenbrains.avtaltid.common.CommonActivity;
import com.hiddenbrains.avtaltid.common.CommonDialog;
import com.hiddenbrains.avtaltid.main.R;
import com.hiddenbrains.avtaltid.main.MyAppointments.MyAppointmentsScreen;
import com.hiddenbrains.avtaltid.main.Myprofile.MyProfileScreen;
import com.hiddenbrains.avtaltid.main.Search.BusinessAdapterList;
import com.hiddenbrains.avtaltid.main.Search.CalendarView;
import com.hiddenbrains.avtaltid.main.Search.EmpolyeeAdapterList;
import com.hiddenbrains.avtaltid.main.Search.InquiryScreen;
import com.hiddenbrains.avtaltid.main.Search.SearchScreen;
import com.hiddenbrains.avtaltid.main.Search.ServiceAdapterList;
import com.hiddenbrains.avtaltid.utility.CommonUtility;
import com.hiddenbrains.avtaltid.utility.Details;

public class CategoriesScreen extends CommonActivity implements
		OnClickListener, parseListner {

	private CommonUtility comonUti;
	private ListView mycategorylist;
	private final int Cotegory = 0, SubCotegory = 1, County = 2, LocatArea = 3,
			Business = 4, Service = 5, Employee = 6;
	private String Flow = "";
	private String tempcategorytype = "cate";
	private Details d;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories_screen);
		img_back = (ImageButton) findViewById(R.id.btn_back);
		img_back.setVisibility(View.INVISIBLE);
		comonUti = new CommonUtility(this);
		comonUti.findViewById();
		d = Details.getInstance();
		comonUti.categorytype = "cate";
		((TextView) findViewById(R.id.title)).setText(getResources().getString(
				R.string.str_categories));

		ArrayList<String> categoryname = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			categoryname.add("category " + i);
		}

		mycategorylist = (ListView) findViewById(R.id.listview_categorylist);
		mycategorylist.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				if (comonUti.categorytype.equalsIgnoreCase("cate")) {
					Categories_adapter = (AdapterCategoriesList) arg0
							.getAdapter();
					temp = (LinkedHashMap<String, Object>) Categories_adapter
							.getItem(pos);
					if (null != temp) {
						((TextView) findViewById(R.id.title))
								.setText(getResources().getString(
										R.string.str_subcategories));

						img_back.setVisibility(View.VISIBLE);
						img_back.setOnClickListener(CategoriesScreen.this);
						// tempcategorytype = comonUti.categorytype;

						category_id = (String) temp.get("category_id");
						if (!TextUtils.isEmpty(category_id)) {

							comonUti.categorytype = "subcate";
							tempcategorytype = comonUti.categorytype;
							CallWsSubCategories(category_id);
						} else {
							CommonDialog cd = new CommonDialog(
									CategoriesScreen.this,
									CategoriesScreen.this
											.getString(R.string.str_dialog_sorry),
									CategoriesScreen.this
											.getString(R.string.str_subcategories_not_found));
							cd.show();
						}
					}

				} else if (comonUti.categorytype.equalsIgnoreCase("subcate")) {

					try {
						((TextView) findViewById(R.id.title))
								.setText(getResources().getString(
										R.string.str_counties));
						subcategories_adapter = (SubCategoriesList_adapter) arg0
								.getAdapter();
						// county_adapter = (CountiesAdapterList)
						// arg0.getAdapter();

						temp = (LinkedHashMap<String, Object>) subcategories_adapter
								.getItem(pos);
						if (null != temp) {
							// tempcategorytype = comonUti.categorytype;
							subcategory_id = (String) temp.get("category_id");
							if (!TextUtils.isEmpty(subcategory_id)) {

								comonUti.categorytype = "county";
								tempcategorytype = comonUti.categorytype;
								CallWsCounty(subcategory_id);
							} else {
								CommonDialog cd = new CommonDialog(
										CategoriesScreen.this,
										CategoriesScreen.this
												.getString(R.string.str_dialog_sorry),
										CategoriesScreen.this
												.getString(R.string.str_country_not_found));
								cd.show();
							}

						}
					} catch (Exception e) {
						e.getMessage();
					}

				} else if (comonUti.categorytype.equalsIgnoreCase("county")) {

					try {

						((TextView) findViewById(R.id.title))
								.setText(getResources().getString(
										R.string.str_localarea));
						county_adapter = (CountiesAdapterList) arg0
								.getAdapter();
						temp = (LinkedHashMap<String, Object>) county_adapter
								.getItem(pos);
						if (null != temp) {
							// tempcategorytype = comonUti.categorytype;
							county_id = (String) temp.get("country_id");
							if (!TextUtils.isEmpty(county_id)) {

								comonUti.categorytype = "local";
								tempcategorytype = comonUti.categorytype;
								CallWsLocalArea(county_id,subcategory_id);
							} else {
								CommonDialog cd = new CommonDialog(
										CategoriesScreen.this,
										CategoriesScreen.this
												.getString(R.string.str_dialog_sorry),
										CategoriesScreen.this
												.getString(R.string.str_localarea_not_found));
								cd.show();
							}

						}
					} catch (Exception e) {
						e.getMessage();
					}

				} else if (comonUti.categorytype.equalsIgnoreCase("local")) {

					try {
						((TextView) findViewById(R.id.title))
								.setText(getResources().getString(
										R.string.str_business));
						local_area_adapter = (LocalAreaList_adapter) arg0
								.getAdapter();
						temp = (LinkedHashMap<String, Object>) local_area_adapter
								.getItem(pos);
						if (null != temp) {
							// tempcategorytype = comonUti.categorytype;
							localarea_id = (String) temp.get("local_area_id");
							if (!TextUtils.isEmpty(localarea_id)) {

								comonUti.categorytype = "business";
								tempcategorytype = comonUti.categorytype;
								CallWsBusiness(localarea_id,subcategory_id);
							} else {
								CommonDialog cd = new CommonDialog(
										CategoriesScreen.this,
										CategoriesScreen.this
												.getString(R.string.str_dialog_sorry),
										CategoriesScreen.this
												.getString(R.string.str_business_not_found));
								cd.show();
							}

						}
					} catch (Exception e) {
						e.getMessage();
					}

				} else if (comonUti.categorytype.equalsIgnoreCase("business")) {

					try {

						
						business_adapter = (BusinessAdapterList) arg0
								.getAdapter();
						temp = (LinkedHashMap<String, Object>) business_adapter
								.getItem(pos);
						if (null != temp) {
							// tempcategorytype = comonUti.categorytype;
							business_id = (String) temp.get("business_id");
							d.setBusiness_id(business_id);
							booking_option = (String) temp
									.get("booking_options");
							if (booking_option.equalsIgnoreCase("calendar")) {
								if (!TextUtils.isEmpty(business_id)) {

									((TextView) findViewById(R.id.title))
									.setText(getResources().getString(
											R.string.str_service));
									comonUti.categorytype = "service";
									tempcategorytype = comonUti.categorytype;
									CallWsService(business_id,localarea_id,subcategory_id);
								} else {
									CommonDialog cd = new CommonDialog(
											CategoriesScreen.this,
											CategoriesScreen.this
													.getString(R.string.str_dialog_sorry),
											CategoriesScreen.this
													.getString(R.string.str_service_not_found));
									cd.show();
								}

							} else {

								try {
									d.setBooking_option("calendar");
									intent = new Intent(CategoriesScreen.this,
											InquiryScreen.class);
									comonUti.findViewById();
									intent.putExtra("flow", "cate");
									startActivity(intent);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						}
					} catch (Exception e) {
						e.getMessage();
					}

				} else if (comonUti.categorytype.equalsIgnoreCase("service")) {

					try {

						((TextView) findViewById(R.id.title))
								.setText(getResources().getString(
										R.string.str_empolyee));
						service_adapter = (ServiceAdapterList) arg0
								.getAdapter();
						temp = (LinkedHashMap<String, Object>) service_adapter
								.getItem(pos);
						if (null != temp) {
							// tempcategorytype = comonUti.categorytype;
							service_id = (String) temp.get("service_id");
							d.setService_id(service_id);
							if (!TextUtils.isEmpty(business_id)) {
								comonUti.categorytype = "employee";
								tempcategorytype = comonUti.categorytype;
								CallWsEmployee(service_id);
							} else {
								CommonDialog cd = new CommonDialog(
										CategoriesScreen.this,
										CategoriesScreen.this
												.getString(R.string.str_dialog_sorry),
										CategoriesScreen.this
												.getString(R.string.str_emp_not_found));
								cd.show();
							}

						}
					} catch (Exception e) {
						e.getMessage();
					}

				} else if (comonUti.categorytype.equalsIgnoreCase("employee")) {
					d.setBook_tag("booknow");
					temp = (LinkedHashMap<String, Object>) service_adapter
							.getItem(pos);
					d.setEmployee_id((String) temp.get("employee_id"));
					county_id = (String) temp.get("country_id");
					intent = new Intent(CategoriesScreen.this,
							CalendarView.class);
					comonUti.findViewById();
					intent.putExtra("flow", "cate");
					startActivity(intent);
					// finish();
				}

			}

		});
		CallWsCategories();
	}

	HashMap<String, String> Paramter;
	PostDataAndGetData psd;

	public void CallWsCategories() {

		psd = new PostDataAndGetData(CategoriesScreen.this, null, null, "GET",
				comonUti.Category_Url+"language="+DataHandler.LANGUAGE_TYPE, Cotegory, 0, false, true);

		psd.execute();
	}

	public void CallWsSubCategories(String categoryid) {

		psd = new PostDataAndGetData(CategoriesScreen.this, null, null, "GET",
				comonUti.Subcategory_Url + categoryid + "&language="+DataHandler.LANGUAGE_TYPE,
				SubCotegory, 0, false, true);

		psd.execute();
	}

	public void CallWsCounty(String subcategoryid) {

		// subcategoryid = "2";
		psd = new PostDataAndGetData(CategoriesScreen.this, null, null, "GET",
				comonUti.County_Url + subcategoryid + "&language="+DataHandler.LANGUAGE_TYPE,
				County, 0, false, true);

		psd.execute();
	}

	private void CallWsLocalArea(String county_id,String subcategoryid) {

//		http://184.164.156.56/projects/avtaltid_website/ws/Service/localarea/?&&language=en&subcategory_id=2&county_id=8
		
		
		psd = new PostDataAndGetData(CategoriesScreen.this, null, null, "GET",
				comonUti.LocalArea_Url +county_id +"&subcategory_id="+subcategoryid+ "&language="+DataHandler.LANGUAGE_TYPE,
				LocatArea, 0, false, true);

		psd.execute();

	}

	private void CallWsBusiness(String localarea_id,String subcategoryid) {

		// localarea_id = "3";
//		http://184.164.156.56/projects/avtaltid_website/ws/Service/businesses/?&language=en&local_area_id=12&subcategory_id=2
		psd = new PostDataAndGetData(CategoriesScreen.this, null, null, "GET",
				comonUti.Business_Url + localarea_id + "&subcategory_id="+subcategoryid+"&language="+DataHandler.LANGUAGE_TYPE,
				Business, 0, false, true);

		psd.execute();

	}

	private void CallWsService(String business_id,String localarea_id,String subcategoryid) {

		
		// business_id = "1";
//		http://184.164.156.56/projects/avtaltid_website/ws/Service/services/?&local_area_id=12&language=en&subcategory_id=2&business_id=22
		psd = new PostDataAndGetData(CategoriesScreen.this, null, null, "GET",
				comonUti.Service_Url + business_id +"&subcategory_id="+subcategoryid+"&local_area_id="+localarea_id+ "&language="+DataHandler.LANGUAGE_TYPE,
				Service, 0, false, true);

		psd.execute();
	}

	private void CallWsEmployee(String service_id) {

		// service_id = "3";
		psd = new PostDataAndGetData(CategoriesScreen.this, null, null, "GET",
				comonUti.Employee_Url + service_id + "&language="+DataHandler.LANGUAGE_TYPE,
				Employee, 0, false, true);

		psd.execute();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_search:
			intent = new Intent(CategoriesScreen.this, SearchScreen.class);
			intent.putExtra("flow", "search");
			startActivity(intent);
			finish();
			break;
		case R.id.imgbtn_categories:
			// intent = new Intent(CategoriesScreen.this,
			// CategoriesScreen.class);
			// intent.putExtra("flow", "cate");
			// startActivity(intent);
			// finish();
			break;
		case R.id.imgbtn_myappint:
			intent = new Intent(CategoriesScreen.this,
					MyAppointmentsScreen.class);
			intent.putExtra("flow", "myapp");
			startActivity(intent);
			finish();
			break;
		case R.id.imgbtn_myprofile:
			intent = new Intent(CategoriesScreen.this, MyProfileScreen.class);
			intent.putExtra("flow", "mypro");
			startActivity(intent);
			finish();
			break;
		case R.id.btn_back:

			if (!comonUti.categorytype.equalsIgnoreCase("")) {
				if (comonUti.categorytype.equalsIgnoreCase("employee")) {
					comonUti.categorytype = "service";
					((TextView) findViewById(R.id.title))
							.setText(getResources().getString(
									R.string.str_service));
					mycategorylist.setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.txt_msg))
							.setVisibility(View.GONE);
					mycategorylist.setAdapter(service_adapter);
				} else if (comonUti.categorytype.equalsIgnoreCase("service")) {
					comonUti.categorytype = "business";
					((TextView) findViewById(R.id.title))
							.setText(getResources().getString(
									R.string.str_business));
					mycategorylist.setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.txt_msg))
							.setVisibility(View.GONE);
					mycategorylist.setAdapter(business_adapter);
				} else if (comonUti.categorytype.equalsIgnoreCase("business")) {
					comonUti.categorytype = "local";
					((TextView) findViewById(R.id.title))
							.setText(getResources().getString(
									R.string.str_localarea));
					mycategorylist.setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.txt_msg))
							.setVisibility(View.GONE);
					mycategorylist.setAdapter(local_area_adapter);
				} else if (comonUti.categorytype.equalsIgnoreCase("local")) {
					comonUti.categorytype = "county";
					((TextView) findViewById(R.id.title))
							.setText(getResources().getString(
									R.string.str_counties));
					mycategorylist.setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.txt_msg))
							.setVisibility(View.GONE);
					mycategorylist.setAdapter(county_adapter);
				} else if (comonUti.categorytype.equalsIgnoreCase("county")) {
					comonUti.categorytype = "subcate";
					((TextView) findViewById(R.id.title))
							.setText(getResources().getString(
									R.string.str_subcategories));
					mycategorylist.setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.txt_msg))
							.setVisibility(View.GONE);
					mycategorylist.setAdapter(subcategories_adapter);
				} else if (comonUti.categorytype.equalsIgnoreCase("subcate")) {
					comonUti.categorytype = "cate";
					((TextView) findViewById(R.id.title))
							.setText(getResources().getString(
									R.string.str_categories));
					mycategorylist.setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.txt_msg))
							.setVisibility(View.GONE);
					img_back.setVisibility(View.INVISIBLE);
					mycategorylist.setAdapter(Categories_adapter);
				}
			}
		}
	}

	private AdapterCategoriesList Categories_adapter;
	private SubCategoriesList_adapter subcategories_adapter;
	private CountiesAdapterList county_adapter;
	private LocalAreaList_adapter local_area_adapter;
	private BusinessAdapterList business_adapter;
	private EmpolyeeAdapterList employee_adapter;
	private ServiceAdapterList service_adapter;
	private LinkedHashMap<String, Object> temp;
	private ArrayList<LinkedHashMap<String, Object>> jsonData;
	private String WebUrl, category_id, subcategory_id, county_id,
			localarea_id, business_id, service_id, booking_option;
	ArrayList<Object> arrayList;

	@SuppressWarnings("unchecked")
	@Override
	public void GetResult(Object jsonDataO, int responce) {
		try {
			arrayList = (ArrayList<Object>) jsonDataO;
			ArrayList<LinkedHashMap<String, Object>> jsonDataTemp = (ArrayList<LinkedHashMap<String, Object>>) jsonDataO;
			int size = arrayList.size();
			if (size != 0) {
				temp = (LinkedHashMap<String, Object>) arrayList.get(0);
				if (temp.get("success").equals("1")) {
					jsonData = jsonDataTemp;
					switch (responce) {
					case Cotegory:

						Categories_adapter = new AdapterCategoriesList(this,
								jsonData);
						Categories_adapter.notifyDataSetInvalidated();
						mycategorylist.setAdapter(Categories_adapter);
						comonUti.findViewById();
						Flow = getIntent().getExtras().getString("flow");

						break;
					case SubCotegory:
						// ((TextView) findViewById(R.id.title))
						// .setText(getResources().getString(
						// R.string.str_subcategories));
						comonUti.categorytype = "subcate";
						subcategories_adapter = new SubCategoriesList_adapter(
								CategoriesScreen.this, jsonData);
						comonUti.findViewById();
						subcategories_adapter.notifyDataSetInvalidated();
						// town_id=(String) temp.get(FeckTVUtils.CITY_ID_TAG);
						mycategorylist.setAdapter(subcategories_adapter);

						break;
					case County:
						// ((TextView) findViewById(R.id.title))
						// .setText(getResources().getString(
						// R.string.str_counties));
						comonUti.findViewById();
						comonUti.categorytype = "county";
						county_adapter = new CountiesAdapterList(
								CategoriesScreen.this, jsonData);
						county_adapter.notifyDataSetInvalidated();
						// mycategorylist.invalidate();
						mycategorylist.setAdapter(county_adapter);
						break;
					case LocatArea:
						// ((TextView) findViewById(R.id.title))
						// .setText(getResources().getString(
						// R.string.str_localarea));
						comonUti.findViewById();
						comonUti.categorytype = "local";
						local_area_adapter = new LocalAreaList_adapter(
								CategoriesScreen.this, jsonData);
						local_area_adapter.notifyDataSetInvalidated();
						// mycategorylist.invalidate();
						mycategorylist.setAdapter(local_area_adapter);
						break;
					case Business:
						// ((TextView) findViewById(R.id.title))
						// .setText(getResources().getString(
						// R.string.str_business));
						comonUti.findViewById();
						comonUti.categorytype = "business";
						business_adapter = new BusinessAdapterList(
								CategoriesScreen.this, jsonData);
						business_adapter.notifyDataSetInvalidated();
						// mycategorylist.invalidate();
						mycategorylist.setAdapter(business_adapter);
						break;
					case Service:
						// ((TextView) findViewById(R.id.title))
						// .setText(getResources().getString(
						// R.string.str_service));
						comonUti.findViewById();
						comonUti.categorytype = "service";
						service_adapter = new ServiceAdapterList(
								CategoriesScreen.this, jsonData);
						service_adapter.notifyDataSetInvalidated();
						// mycategorylist.invalidate();
						mycategorylist.setAdapter(service_adapter);
						break;
					case Employee:
						// ((TextView) findViewById(R.id.title))
						// .setText(getResources().getString(
						// R.string.str_empolyee));
						comonUti.findViewById();
						comonUti.categorytype = "employee";
						employee_adapter = new EmpolyeeAdapterList(
								CategoriesScreen.this, jsonData);
						service_adapter.notifyDataSetInvalidated();
						// mycategorylist.invalidate();
						mycategorylist.setAdapter(employee_adapter);
						break;

					default:
						break;
					}
				} else {
					comonUti.categorytype = tempcategorytype;
					mycategorylist.setVisibility(View.GONE);

					Setmsg();
				}
			} else {
				Setmsg();
				comonUti.categorytype = tempcategorytype;
				mycategorylist.setVisibility(View.GONE);
				((TextView) findViewById(R.id.txt_msg))
						.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			comonUti.categorytype = tempcategorytype;
			mycategorylist.setVisibility(View.GONE);
			((TextView) findViewById(R.id.txt_msg)).setVisibility(View.VISIBLE);
			Setmsg();
			e.getStackTrace();
		}

	}

	public void Setmsg() {
		((TextView) findViewById(R.id.txt_msg)).setVisibility(View.VISIBLE);
		if (tempcategorytype == "subcate") {
			((TextView) findViewById(R.id.txt_msg)).setText(getResources()
					.getString(R.string.str_subcategories_not_found));
		} else if (tempcategorytype == "county") {
			((TextView) findViewById(R.id.txt_msg)).setText(getResources()
					.getString(R.string.str_country_not_found));

		} else if (tempcategorytype == "local") {
			((TextView) findViewById(R.id.txt_msg)).setText(getResources()
					.getString(R.string.str_localarea_not_found));

		} else if (tempcategorytype == "business") {
			((TextView) findViewById(R.id.txt_msg)).setText(getResources()
					.getString(R.string.str_business_not_found));

		} else if (tempcategorytype == "service") {
			((TextView) findViewById(R.id.txt_msg)).setText(getResources()
					.getString(R.string.str_service_not_found));

		} else if (tempcategorytype == "employee") {
			((TextView) findViewById(R.id.txt_msg)).setText(getResources()
					.getString(R.string.str_emp_not_found));

		}
	}
}
