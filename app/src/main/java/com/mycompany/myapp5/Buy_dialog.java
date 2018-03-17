package com.mycompany.myapp5;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.view.inputmethod.*;
import android.view.ViewDebug.*;

public class Buy_dialog extends Activity
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private LinearLayout interceptor;
	
	EditText price_dialog;
	EditText notes_dialog;
	//алушатель кнопки
	OnClickListener listbutton;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		editor.putInt("add", 0);
		
		editor.commit();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//устанока титла окна
		String title = pref.getString("name_coin" + 0, "");
		setTitle(title);
		setContentView(R.layout.buy_dialog);
		this.setFinishOnTouchOutside(false);
		//инициализация едита цены
		price_dialog = (EditText) findViewById(R.id.price_dialog);
		notes_dialog = (EditText) findViewById(R.id.notes_dialog);
		//запись при старте в edit нижняя цена
		try {
			if(pref.contains("price_dialog")){
				price_dialog.setText(pref.getString("price_dialog", ""));
			}
		} catch (Exception e){}
		//запись при старте в edit нижняя цена
		try {
			if(pref.contains("notes_dialog")){
				notes_dialog.setText(String.valueOf( pref.getInt("notes_dialog", 0)));
			}
		} catch (Exception e){}
		//слушатель,  layot
		interceptor = (LinearLayout) findViewById(R.id.lin_dialog);
		interceptor.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						price_dialog.setFocusable(false);
						notes_dialog.setFocusable(false);
						
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
		price_dialog.setOnTouchListener(on_touch_listener);
		notes_dialog.setOnTouchListener(on_touch_listener);
		//кнопка отмена
		Button close_button = (Button) findViewById(R.id.cancel);
		close_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		//кнопка отправить заявку
		Button but_ok = (Button) findViewById(R.id.ok);
		but_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String price_dialog_str = price_dialog.getText().toString();
					String notes_dialog_str = notes_dialog.getText().toString();
					editor.putString("price_dialog", price_dialog_str);
					editor.putInt("notes_dialog", Integer.parseInt(notes_dialog_str));
					editor.putInt("add", 1);
					editor.commit();
					finish();
				}
			});
		//переключатель
		Switch switch_dialog = (Switch) findViewById(R.id.monitored_switch);
		int btn_z = pref.getInt("btn_z", 0);
		if (btn_z == 0){
			editor.putInt("typ_oper",1);
			switch_dialog.setChecked(true);
		}
		if(btn_z == 1){
			editor.putInt("typ_oper", 0);
			
		}
		
		if (switch_dialog != null) {
			switch_dialog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							editor.putInt("typ_oper", 1);
						} else{editor.putInt("typ_oper",0);}
						Toast.makeText(getApplication(), "Отслеживание переключения: " + (isChecked ? "on" : "off"),
									   Toast.LENGTH_SHORT).show();
					}
				
			});
		}
		
		
	}

	@Override
	public void finish()
	{
		String price_dialog_str = price_dialog.getText().toString();
		String notes_dialog_str = notes_dialog.getText().toString();
		editor.putString("price_dialog", price_dialog_str);
		try {
		editor.putInt("notes_dialog", Integer.parseInt(notes_dialog_str));
		}catch(Exception e){editor.putInt("notes_dialog", 0);}
		editor.commit();
		// TODO: Implement this method
		super.finish();
	}

	@Override
	protected void onStart()
	{
		
		// TODO: Implement this method
		super.onStart();
	}

	@Override
	protected void onStop()
	{
		// TODO: Implement this method
		super.onStop();
	}
	
	
	
}