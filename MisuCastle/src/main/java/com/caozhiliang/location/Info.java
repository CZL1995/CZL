package com.caozhiliang.location;

import com.caozhiliang.main.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Info implements Serializable
{
	private static final long serialVersionUID = -1010711775392052966L;
	private double latitude;
	private double longitude;
	private int imgId;
	private String name;
	private String distance;
	private int zan;

	public static List<Info> infos = new ArrayList<Info>();

	static
	{
		infos.add(new Info(28.683456,
				116.033769, R.mipmap.home_hua1, "爱的滋味，香浓美味",
				"距离209米", 1456));
		infos.add(new Info(28.683556,
				116.033769, R.mipmap.home_hua2, "献给天下父母的爱",
				"距离897米", 456));
		infos.add(new Info(28.683656,
				116.033769, R.mipmap.home_hua3, "享受精美蛋糕，尽在郁金香",
				"距离249米", 1456));
		infos.add(new Info(28.683756,
				116.033769, R.mipmap.home_hua4, "常温家常蛋糕",
				"距离679米", 1456));
		infos.add(new Info(28.683856,
				116.033769, R.mipmap.home_hua5, "新鲜美味水果蛋糕",
				"距离679米", 1456));


	}

	public Info(double latitude, double longitude, int imgId, String name,
				String distance, int zan)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.imgId = imgId;
		this.name = name;
		this.distance = distance;
		this.zan = zan;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public int getImgId()
	{
		return imgId;
	}

	public void setImgId(int imgId)
	{
		this.imgId = imgId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public int getZan()
	{
		return zan;
	}

	public void setZan(int zan)
	{
		this.zan = zan;
	}

}
