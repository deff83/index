package com.mycompany.myapp5;

import android.app.*;
import android.os.*;
import okhttp3.*;
import java.io.*;
import android.widget.*;
import org.apache.http.*;
import org.w3c.dom.*;
import java.util.*;
import org.json.*;
import android.view.*;
import android.content.*;
import android.view.inputmethod.*;
import android.view.View.*;
import android.text.*;
import android.graphics.*;
import android.preference.*;
import android.icu.util.*;
import android.view.ViewDebug.*;
import android.widget.Toolbar.*;
import android.support.v4.content.*;
import android.content.res.*;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends Activity
{
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	//
	String textr; 
	BroadcastReceiver br;
	private RelativeLayout interceptor;
	private AlarmManager am;
	private PendingIntent pi;
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	//поля нижней цены
	EditText price_edit;
	TextView price_minuse;
	//поля верхней цены
	EditText price_edit2;
	TextView price_pluse;
	LinearLayout llt;
	int str_schet;
	//перечисляем кнопки
	
    
    OnClickListener getButtonText;
	LinearLayout.LayoutParams lButtonParams;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		count_coin2 = 0;
      
	str_schet = 0;
	
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		editor.putInt("count_coin",0);

		
		
		
		editor.commit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		//кнопка акшнбара
		//final ActionBar actionBar = getSupportActionBar();
		//actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher);
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.cats_array_ru);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);

        // подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		
		llt = (LinearLayout) findViewById(R.id.layout_button);
		//  LinearLayout llt = new LinearLayout(this);
        //layout params for every Button

		
		getButtonText = new OnClickListener() {
		 @Override
		 public void onClick(View v) {
		 int buttonId = v.getId() - 1000;
			 editor.putString(buttonId + "10002", "1");
			
for (int j = 0; j < count_coin; j++){
	if (buttonId != j){
		editor.putString(j + "10002", "0");
	}
	editor.commit();
}




		 }
		 };

		




		
		
		
		
		
		
		//код обработки кнопок
		final Button btnStart = (Button) findViewById(R.id.button_start);
        final Button btnStop = (Button) findViewById(R.id.button_stop);
		//инициализация полей нижней цены
		price_edit = (EditText) findViewById(R.id.price_edit);
		price_minuse = (TextView) findViewById(R.id.price_minus);
		//инициализация полей верхней цены
		price_edit2 = (EditText) findViewById(R.id.price_edit2);
		price_pluse = (TextView) findViewById(R.id.price_plus);
		//запись при старте в edit нижняя цена 
		try {
		if(pref.contains("edit_price")){
			price_edit.setText(pref.getString("edit_price", ""));
		}
		} catch (Exception e){}
		//запись при старте в edit верхняя цена 
		try {
			if(pref.contains("edit_price2")){
				price_edit2.setText(pref.getString("edit_price2", ""));
			}
		} catch (Exception e){}
		
		//слушатель,  layot 
		interceptor = (RelativeLayout) findViewById(R.id.rel_layout);

		interceptor.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						price_edit.setFocusable(false);
						price_edit2.setFocusable(false);
					}
					return v.performClick();
				}
			});
			//слушатель edit, установка акиивности вызов клавиатуры
		OnTouchListener on_touch_listener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					v.setFocusableInTouchMode(true);


				}
				return v.performClick();
			}
		};
		//вешаем на каждый edit один слушатель
		price_edit.setOnTouchListener(on_touch_listener);
		price_edit2.setOnTouchListener(on_touch_listener);
		
        // запуск службы, нажатие на кнопку старт
        btnStart.setOnClickListener(new  View.OnClickListener() {


				@Override
				public void onClick(View view) {
					//пепевод введенного в edit записи в значение double
					String edit = price_edit.getText().toString();
					String edit2 = price_edit2.getText().toString();
					Double text, text2;
					if (edit.equals("")){
						edit = "0.0";
						price_edit.setText("0.0");
					}
					if (edit2.equals("")){
						edit2 = "999.9";
						price_edit2.setText("999.9");
					}
						


					
					// сохраниние нулевых значений в файле, и значений введенных в edit
					editor = pref.edit();
					editor.putString("edit_price2", edit2);
					editor.putString("edit_price", edit);
					editor.putString("price_plus","0");
					editor.putString("price_minus", "0");
					editor.commit();
					//вызов функции включения в запись менеджера временных задач
					final Intent i = new Intent(MainActivity.this, PlayService.class);
					startService(i);
						
					
				
					
					
}});
//остановка временой щадачи и остановка службы
		btnStop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//Intent intentstop = new Intent(MainActivity.this, PlayService.class);
					
					stopService(
                        new Intent(MainActivity.this, PlayService.class));
				}
			});
	

			
			
	
	

			

		// кнопка "широковещптельное сообщение", для тестов
		Button intent_buttin = (Button) findViewById(R.id.intent_button);
		//int color = ContextCompat.(this, R.color.colorGreen);
		//intent_buttin.setBackgroundColor(getResources().getColor( R.color.colorGreen));
		intent_buttin.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){

				
				
			
		
				
			}
			
			
		});
	
		
		// создаем BroadcastReceiver, слушатель главного MainActivity приложения
		br = new BroadcastReceiver() {
		
			// действия при получении сообщений
		public void onReceive(Context context, Intent intent) {
			
			
		
			
						   try{
							   
						
		String coin_price = intent.getStringExtra("coin_price");
			coin_prices(coin_price);/*
			editor.putInt("mainReceive", 2);
			//editor.commit();
			
			
				
				//String coin_price = intent.getStringExtra("coin_price");
				//coin_prices(coin_price);
			
			
		//	if (name_intent.equals("balance")){
*/
				//Toast.makeText(MainActivity.this, "1",
							//   Toast.LENGTH_SHORT).show();
				Double price_intent = intent.getDoubleExtra("price", 0.0);
				String price_intent_string = price_intent.toString();
				TextView text_balance = (TextView) findViewById(R.id.text2);
				TextView text_fullanswer = (TextView) findViewById(R.id.text);
				//Answer answerxx = new Answer();
				
			
				text_balance.setText(price_intent_string);
				text_fullanswer.setText("");
				//надписи внизу edit
				
			
				String price_minus =pref.getString("price_minus", "");
				Double price_minus_d = Double.parseDouble(price_minus); 
				if (price_minus_d < 0){
					price_minuse.setTextColor(Color.RED);	
				}
				else {
					price_minuse.setTextColor(Color.WHITE);
				}
				String price_edit =pref.getString("edit_price", "");
				Double price_edit_d = Double.parseDouble(price_edit); 
				Double minuses = Math.round((price_edit_d + price_minus_d) * 100.0) / 100.0;
				price_minuse.setText(minuses.toString() + "   (" + price_minus + ")");
			
			
			

				String price_plus =pref.getString("price_plus", "");
				Double price_plus_d = Double.parseDouble(price_plus); 
				if (price_plus_d > 0){
					price_pluse.setTextColor(Color.GREEN);
				} else {
					price_pluse.setTextColor(Color.WHITE);
				}
				String price_edit2 =pref.getString("edit_price2", "");
				Double price_edit2_d = Double.parseDouble(price_edit2); 
				
				Double pluses = Math.round((price_edit2_d + price_plus_d) * 100.0) / 100.0;
				price_pluse.setText(pluses.toString() + "   (" + price_plus + ")");
			}
			catch (Exception e){}
						
			editor.putInt("fin", 1);
			
				editor.commit();
			}
			
			
			};
		
		
		// создаем фильтр для BroadcastReceiver
		IntentFilter intFilt = new IntentFilter("CAT");
		// регистрируем (включаем) BroadcastReceiver
		registerReceiver(br, intFilt);
		
	}

	@Override
	protected void onPause()
	{
		//System.out.println( "сохраниние");
		String edit = price_edit.getText().toString();
		
		String edit2 = price_edit2.getText().toString();
		//editor = pref.edit();
		try{
		editor.putString("edit_price", edit);
		editor.putString("edit_price2",edit2);
		editor.commit();
		} catch (Exception e){}
		// TODO: Implement this method
		super.onPause();
	}
	
	public void run_alarm (){
		//Double th = text;

	
	

	}
	Integer count_coin;
	Integer count_coin2;
	Integer i_str_color;
	Double price_double;
	String i_file_coin;
	Double i_file_coin_double;
	public void coin_prices(String answir){
		try {
		
		count_coin = pref.getInt("count_coin", 0);
		// инициализируем кнопки
		
		try{
		JSONObject jsonObject;
		jsonObject = new JSONObject(answir);
		
		
		JSONArray jsonarray = jsonObject.getJSONArray("value");
		
		if (count_coin != jsonarray.length() || count_coin2 != 1){
			
				llt.removeAllViews();
			
			for (int i = 0; i < jsonarray.length(); i++) {
				
				Button btn =  new Button(this);
				int wight;
				
				wight = LayoutParams.WRAP_CONTENT;
				
				btn.setLayoutParams( new LinearLayout.LayoutParams(
										wight,  100));
				btn.setId(i + 1000);
				btn.setOnClickListener(getButtonText);

				llt.addView(btn);       


			}
		count_coin2 = 1;
		editor.putInt("count_coin", jsonarray.length());
		editor.commit();
		}
		
		//String jsonarra = IntToString( jsonarray.length());
			//Toast.makeText(this, jsonarray.length(), Toast.LENGTH_SHORT).show();
			Button but;


			Toast.makeText(MainActivity.this, "-",
						   Toast.LENGTH_SHORT).show();
			for (int i = 0; i < jsonarray.length(); i++) {
		
			JSONObject friend = jsonarray.getJSONObject(i);
			String name = friend.getString("name");
			String price = friend.getString("price");
			String i_str = String.valueOf(i) + "1000";
			
			i_str_color = pref.getInt(i_str + "1", 2);
			
			but = (Button) findViewById(i + 1000);
			but.setText(name + "\n" + price);
			
			
			price_double = Double.parseDouble(price);
			i_file_coin = pref.getString(i_str, "0.0");
			i_file_coin_double = Double.parseDouble(i_file_coin);
			String up_button = pref.getString(i_str +"2", "0");
			if (up_button.equals("0")){
			
			but.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
			} else {
				but.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_up));
				editor.putString(i_str + "2", "1");
				editor.commit();
			}
			
			
			try{
			if (i_str_color == 0){
				but.setTextColor(getResources().getColor( R.color.colorGreen));
			}
			if(i_str_color == 1){
				but.setTextColor(getResources().getColor( R.color.colorRed));
			}
			else{}
			} catch(Exception e){}
			
			if (price_double > (i_file_coin_double + (price_double * 0.001))){
				
				editor.putInt(i_str + "1", 0);
			}
			if (price_double < (i_file_coin_double - (price_double * 0.001) )){
				
				editor.putInt(i_str + "1", 1);
			}
			if (i_file_coin_double == 0){
				editor.putInt(i_str + "1", 2);
			}
				editor.putString(i_str, price);
				editor.putString("otvet_prices", "0");
				//editor.putInt("fin", 1);
				editor.commit();
			}
			
			
			
			
			
			
			
			
			
			 } catch (JSONException e){
				 /*
			 //обработка исключения при ненахождении balance
				 //Toast.makeText(this, name + ": " + price, Toast.LENGTH_SHORT).show();
				  but1 = (Button) findViewById(R.id.button1);
				  but2 = (Button) findViewById(R.id.button2);
				  but3 = (Button) findViewById(R.id.button3);
				  Button[] butt = new Button[]{but1,but2,but3};
				  Button button = butt[i];
				  Button but1;
				  Button but2;
				  Button but3;
				  
				  
				  */
				 
			 }
	}catch(Exception e){
		
	}
		
	};
		
	public void tabl_price (String name_coin){
		
	}

}