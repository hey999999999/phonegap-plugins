# Menu plugin for Android/Phonegap
By @hey999999999

## project configuration
Add android-support-v4.jar to build-path previously.

## Using the plugin sample

### xml/plugins.xml
    <plugin name="Menu" value="hey.phonegap.plugin.MenuHandler"/>

### menu/menu.xml
    <?xml version="1.0" encoding="utf-8"?>
    <menu xmlns:android="http://schemas.android.com/apk/res/android" >
    <item android:id="@+id/menu_search" android:title="@string/search" android:icon="@android:drawable/ic_menu_search"/>
    <item android:id="@+id/menu_share" android:title="@string/share" android:icon="@android:drawable/ic_menu_share"/>
    <item android:id="@+id/menu_edit" android:title="@string/edit" android:icon="@android:drawable/ic_menu_edit"/>
    <item android:id="@+id/menu_delete" android:title="@string/delete" android:icon="@android:drawable/ic_menu_delete"/>
    </menu>

### Activity (subclass of DroidGap)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        
        for(int i=0; i<menu.size(); i++){
        	MenuItem item = menu.getItem(i);
        	MenuItemCompat.setShowAsAction(item, 
        	    MenuItemCompat.SHOW_AS_ACTION_IF_ROOM | MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT);
        	if(item.getItemId() == R.id.menu_search){
        		View search = SearchViewCompat.newSearchView(this);
        		if(search != null){// if API Level > 10
            		SearchViewCompat.setOnQueryTextListener(search, 
            		    new SearchViewCompat.OnQueryTextListenerCompat() {
            			@Override
            			public boolean onQueryTextChange (String newText){
            				return super.onQueryTextChange(newText);
            			}
            			@Override
            			public boolean onQueryTextSubmit (String query){
            		    	if(this instanceof DroidGap){
                	        	DroidGap gap = (DroidGap)this;
                	        	gap.postMessage("onQueryTextSubmit", query);
                	        	return true;
            		    	}else{
                				return super.onQueryTextSubmit(query);
            		    	}
            			}
    				});
            		MenuItemCompat.setActionView(item, search);
        		}
        	}
        }
        return super.onCreateOptionsMenu(menu);
    }

### HTML/JavaScript (in deviceReady event)
    <script type="text/javascript" charset="utf-8" src="menu.js"></script>
        :
        :
    var menuListener = function(args){
        alert('Menu ' + args + ' was selected.');
    };
    window.plugins.Menu.addListener(
        {},
        menuListener,
        function(){console.log('fail');}
    );