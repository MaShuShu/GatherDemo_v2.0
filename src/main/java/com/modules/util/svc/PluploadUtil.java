package com.modules.util.svc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.modules.base.orm.FileInfo;
import com.modules.util.orm.Plupload;

@Service("pluploadUtil")
public class PluploadUtil {
	
	private static final int BUF_SIZE = 2 * 1024;
	
	/**�ϴ�ʧ����Ӧ�ĳɹ�״̬��*/
	public static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
	
	/**�ϴ�ʧ����Ӧ��ʧ��״̬��*/
	public static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
	
	/**
	 * ����Plupload������ļ��ϴ�,�Զ�����Ψһ���ļ�������
	 * @param plupload - ����ϴ����������bean
	 * @param dir - ����Ŀ���ļ�Ŀ¼
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static void upload(Plupload plupload, File dir,FileInfo fileInfo) throws IllegalStateException, IOException {
		//����Ψһ���ļ���
		String filename = "" + System.currentTimeMillis()+plupload.getName();
		System.out.println("�Զ�����������=============="+filename);
		System.out.println("�ļ�ԭ��=============="+plupload.getName());
		upload(plupload, dir, filename,fileInfo);
	}
	
	/**
	 * ����Plupload������ļ��ϴ�
	 * @param plupload - ����ϴ����������bean
	 * @param dir - ����Ŀ���ļ�Ŀ¼
	 * @param filename - ������ļ���
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static void upload(Plupload plupload, File dir, String filename,FileInfo fileInfo) throws IllegalStateException, IOException {
		int chunks = plupload.getChunks();	//��ȡ�ܵ���Ƭ��
		int chunk = plupload.getChunk();	//��ȡ��ǰ��Ƭ(��0��ʼ����)
		
		System.out.println(plupload.getMultipartFile() + "----------");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
		
	    if(map != null) {
	    	if (!dir.exists()) dir.mkdirs();	//���Ŀ���ļ��в������򴴽��µ��ļ���
			
	    	//��ʵ�ϵ�������ֻ����һ��ֵ,����ֻ��Ҫ����һ��ֵ����
	    	Iterator<String> iter = map.keySet().iterator();
			while(iter.hasNext()) {
			    String str = (String) iter.next();
			    List<MultipartFile> fileList =  map.get(str);
			    for(MultipartFile multipartFile : fileList) {
			    	
			    	//��Ϊֻ����һ��ֵ,������󷵻صļ��ǵ�һ��Ҳ�����һ��ֵ
		    		plupload.setMultipartFile(multipartFile);
		    		
			    	//������Ŀ���ļ�
			    	File targetFile = new File(dir.getPath()+ "/" + filename);
			    	System.out.println("��Ŀ���ļ�======="+targetFile.getPath());
			    	
			    	//��chunks>1��˵����ǰ�����ļ�Ϊһ����Ƭ����Ҫ�ϲ�
			    	if (chunks > 1) {
			    		//��Ҫ������ʱ�ļ���������ٸ�������
			    		File tempFile = new File(dir.getPath()+ "/" + multipartFile.getName());
			    		//���chunk==0,������һ����Ƭ,����Ҫ�ϲ�
			    		saveUploadFile(multipartFile.getInputStream(), tempFile, chunk == 0 ? false : true);
			    		
			    		//�ϴ����ϲ���ɣ�����ʱ���Ƹ���Ϊָ������
			    		if (chunks - chunk == 1) {
			    			tempFile.renameTo(targetFile);
			    		}
			    		
			    	} else {
			    		//����ֱ�ӽ��ļ����ݿ��������ļ�
			    		multipartFile.transferTo(targetFile);
			    		
			    		fileInfo.setId("�����Լ�д��id��10086");
			    		fileInfo.setName(plupload.getName());
			    	}
		    	}
	        }
	    }
	    
	}
	
	/**
	 * �����ϴ��ļ�����ϲ�����
	 */
	private static void saveUploadFile(InputStream input, File targetFile, boolean append) throws IOException {
        OutputStream out = null;
        try {
            if (targetFile.exists() && append) {
                out = new BufferedOutputStream(new FileOutputStream(targetFile, true), BUF_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(targetFile), BUF_SIZE);
            }
            
            byte[] buffer = new byte[BUF_SIZE];
            int len = 0;
            //д���ļ�
            while ((len = input.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw e;
        } finally {
        	//�ر����������
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
	 * �ж��Ƿ�ȫ���ϴ����
	 * ��Ƭ��ϲ���ŷ�����
	 */
	public static boolean isUploadFinish(Plupload plupload) {
		return (plupload.getChunks() - plupload.getChunk() == 1);
	}
	
}
