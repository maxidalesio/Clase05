package com.example.alumno.clase05;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {

	private String url;
	private HttpURLConnection conn;

	//url de donde voy a sacar la info
	public HttpManager(String url)
	{
		conn = crearHttpUrlConn(url);
	}

	private HttpURLConnection crearHttpUrlConn(String strUrl)
	{
		URL url = null;
		try {
			//paso 1 y 2
			url = new URL(strUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			//las relevantes son solo las dos primeras
			urlConnection.setReadTimeout(10000 /* milliseconds */);
			urlConnection.setConnectTimeout(15000 /* milliseconds */);
			return  urlConnection;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public boolean isReady()
	{
		return conn!=null;
	}

	//peticiones (paso 3) de text
	public String getStrDataByGET() throws IOException
	{
		byte[] bytes = getBytesDataByGET();
		return new String(bytes,"UTF-8");
	}

	//peticiones (paso 3) de imagen
	public byte[] getBytesDataByGET() throws IOException
	{
		conn.setRequestMethod("GET"); // <manera que me conecto
        //puede ser POST u otro método y finalmente me contecto
		conn.connect();
		int response = conn.getResponseCode(); //ver si me respondió o no
		Log.d("http", "Response code:" + response);
		if(response==200) {
			InputStream is = conn.getInputStream(); //obtengo mi InputStream
			return readFully(is);
		}
		else
			throw new IOException();
	}

	public String getStrDataByPOST(Uri.Builder postParams) throws IOException
	{
		byte[] bytes = getBytesDataByPOST(postParams);
		return new String(bytes,"UTF-8"); //constructor que recibe bytes y codificación
	}
	public byte[] getBytesDataByPOST(Uri.Builder postParams) throws IOException
	{
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		String query = postParams.build().getEncodedQuery();

		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(query);
		writer.flush();
		writer.close();
		os.close();

		int response = conn.getResponseCode();
		Log.d("http", "Response code:" + response);
		if(response==200) {
			InputStream is = conn.getInputStream();
			return readFully(is);
		}
		else
			throw new IOException();
	}

	private byte[] readFully(InputStream inputStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		inputStream.close();
		return baos.toByteArray();
	}




}
