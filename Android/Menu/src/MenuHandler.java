package hey.phonegap.plugin;

import org.json.JSONArray;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.phonegap.api.LOG;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class MenuHandler extends Plugin {
	private final static int android_R_id_home = 16908332;
	private String mCallbackID = null;
	@Override
	public PluginResult execute(String action, JSONArray args, String callbackID) {
		try{
			mCallbackID = callbackID;
			PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
			result.setKeepCallback(true);
			return result;
		}catch(Exception e){
			LOG.e("menu", "error", e);
			return new PluginResult(PluginResult.Status.ERROR);
		}
	}
    @Override
    public void onMessage(String activityMethod, Object data) {
    	super.onMessage(activityMethod, data);
    	LOG.i("PhoneGapLog", "onMessage():" + activityMethod);
    	if(activityMethod.equals("onCreateOptionsMenu")){
            MenuInflater menuInflater = ctx.getMenuInflater();
            menuInflater.inflate(R.menu.main, (Menu)data);
    	}else if(mCallbackID == null){
    		//LOG.e("error", "callbackID is null. illegal event call method: " + activityMethod);
    	}else if(activityMethod.equals("onOptionsItemSelected")){
    		MenuItem item = (MenuItem)data;
    		PluginResult result;
    		if(item.getItemId() == android_R_id_home){
    			result = new PluginResult(PluginResult.Status.OK, "AppIcon");
    		}else{
    			result = new PluginResult(PluginResult.Status.OK, item.getTitle().toString());
    		}
			result.setKeepCallback(true);
			super.success(result, mCallbackID);
    	}else if(activityMethod.equals("onQueryTextSubmit")){
    		String query = (String)data;
			PluginResult result = new PluginResult(PluginResult.Status.OK, query);
			result.setKeepCallback(true);
			super.success(result, mCallbackID);
    	}else{
    		LOG.e("error", "undefined event call method: " + activityMethod);
    	}
    }
}