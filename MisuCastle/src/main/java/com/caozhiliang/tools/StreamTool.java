package com.caozhiliang.tools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

    ByteArrayOutputStream out=new ByteArrayOutputStream();
	byte[] buffer=new byte[1024];
	int len=0;
	public byte[] read(InputStream in) throws Exception
	{
		while((len=in.read(buffer))!=-1)
		{
			out.write(buffer, 0, len);
		}
		
		in.close();
		return out.toByteArray();
		
	}
	


}
