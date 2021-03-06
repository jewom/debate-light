package com.example.com.liltof.library.PageGetter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class PageGetter extends AsyncTask<String, String, String> {
	private OnActionEnd actionEnd;
	private Boolean isPost = false;
	private Boolean loadFromCache = false;
	private String cookieHost = null;
	private SimpleMethod actionResponse = null;
	public List<Header> cookieHeader = null;
	View ressource = null;
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	private Context context = null;
	public Boolean isGzip = false;
	public PageGetter() {
		super();
	}

	public PageGetter(View ressource) {
		super();
		this.ressource = ressource;
	}

	public void setPostMethodValues(List<NameValuePair> pairs) {
		nameValuePairs = pairs;
	}

	public Boolean isPost() {
		return isPost;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (ressource != null)
			ressource.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onPostExecute(String result) {
		if (ressource != null)
			ressource.setVisibility(View.INVISIBLE);
		if (result.startsWith("libtofError"))
		{
			actionEnd.onError(result);
			return ;
		}

		if (result == null)
			return;
		if (isGzip == true)
		{
			
			ByteArrayInputStream bais = new ByteArrayInputStream(result.getBytes());
			GZIPInputStream gzis;
			try {
				gzis = new GZIPInputStream(bais);
				InputStreamReader reader = new InputStreamReader(gzis);
				BufferedReader in = new BufferedReader(reader);

				String readed;
				String finalres = "";
				while ((readed = in.readLine()) != null) {
				    finalres += readed;
				}
				result = finalres;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		try {
			if (result.length() == 0) {
				actionEnd.onError("Server responded with an empty page");
				return;
			}
			if (result.charAt(0) == '{') {
				JSONObject n1 = new JSONObject(result);
				Log.d("PageGetter", "JSON object method");
				actionEnd.onActionEndJSON(n1);
			} else if (result.charAt(0) == '[') {
				JSONArray n = new JSONArray(result);
				Log.d("PageGetter", "JSON Array method");
				actionEnd.onActionEndJSONArray(n);
			}

			else if (result.length() >= "libtofError".length()
					&& result.startsWith("lilbofError")) {

				actionEnd.onError("Connection issue.");
			} else {
				Log.d("PageGetter", "Simple String method");
				actionEnd.onActionEnd(result);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("PageGetter", "Error Post Execute");
			actionEnd.onError("JSON Error");
			e.printStackTrace();

		}

	}

	public String loadDataFromCache(File cacheFile) throws IOException {
		// File cacheFile = null;
		String cacheText = "";
		BufferedReader in = new BufferedReader(new FileReader(cacheFile));
		String str;
		while ((str = in.readLine()) != null) {
			cacheText += str;
		}
		in.close();
		return cacheText;
	}

	public void saveDataToCache(File cacheFile, String data) {
		try {
			// Create file
			FileWriter fstream = new FileWriter(cacheFile);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(data);
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public void setOnResponseAction(SimpleMethod action) {
		actionResponse = action;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		File cacheFile = null;
		if (context != null) {
			try {
				cacheFile = new File(context.getCacheDir()
						+ URLEncoder.encode(params[0], "UTF-8"));

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (loadFromCache == true) {

				if (cacheFile != null && cacheFile.exists()) {

					try {
						Log.d("LOGCACHE", "Load Data From Cache");
						return loadDataFromCache(cacheFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		Log.d("LOGCACHE", "Load Data From NETWORK" + params[0]);

		String ret = "";
		try {
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			// The default value is zero, that means the timeout is not used. 
			int timeoutConnection = 4000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
			int timeoutSocket = 30000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			
			
			
			HttpClient hc = new DefaultHttpClient(httpParameters);
			HttpResponse rp;
			if (nameValuePairs.size() == 0) {
				HttpGet get = new HttpGet(params[0]);
				if (cookieHeader != null)
				{
					get.setHeader(cookieHeader.get(0));
				}
				rp = hc.execute(get);
				if (actionResponse != null)
					actionResponse.simpleMethod(rp);

			} else {
				HttpPost post = new HttpPost(params[0]);
				if (cookieHeader != null)
				{
					post.setHeader(cookieHeader.get(0));
				}
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				Log.d("PageGetter ", "POST");
				rp = hc.execute(post);
			}
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ret = EntityUtils.toString(rp.getEntity());
				Log.d("PageGetter ", "Will process postResult with :{" + ret
						+ "}");
			}
		} catch (IOException e) {
		
			Log.e("PageGetter", "Error Background action");
			return "libtofError connection";
		}
		if (context != null)
			saveDataToCache(cacheFile, ret);
		return ret;
	}

	public Boolean getLoadFromCache() {
		return loadFromCache;
	}

	public void setLoadFromCache(Boolean loadFromCache, Context cacheContext) {
		this.loadFromCache = loadFromCache;
		this.context = cacheContext;
	}

	public OnActionEnd getActionEnd() {
		return actionEnd;
	}

	public void setActionEnd(OnActionEnd actionEnd) {
		this.actionEnd = actionEnd;
	}

}
