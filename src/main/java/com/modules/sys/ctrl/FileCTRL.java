package com.modules.sys.ctrl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.modules.base.orm.FileInfo;
import com.modules.base.orm.Result;
import com.modules.util.orm.Plupload;
import com.modules.util.svc.PluploadUtil;

@Controller
@RequestMapping("/file")
public class FileCTRL {

	public static final String FileDir = "uploadfile/";
	
	/**
	 * �ļ��ϴ�
	 * @param request
	 * @param plupload
	 * @throws IOException 
	 */
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public @ResponseBody Result upload(HttpServletRequest request, Plupload plupload) throws IOException{
		plupload.setRequest(request);
		
		//�ļ��洢·��
		Date day = new Date();//��ȡʱ��
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String path = format.format(day);
		
        File dir = new File(plupload.getRequest().getSession().getServletContext().getRealPath("/") + FileDir+"/"+path);
		System.out.println("�ļ��洢·��========"+dir.getPath());
		FileInfo fileInfo = new FileInfo();
        try{
        	PluploadUtil.upload(plupload, dir,fileInfo);
        	System.out.println("name="+plupload.getName() + "----id="+plupload.getId());
        	//�ж��ļ��Ƿ��ϴ��ɹ������ֳɿ���ļ��Ƿ�ȫ���ϴ���ɣ�
            if (PluploadUtil.isUploadFinish(plupload)) {
                System.out.println("name="+plupload.getName() + "----id="+plupload.getId());
            }
        }catch(IllegalStateException e){
        	e.printStackTrace();
        }
        
        return Result.data(fileInfo);
	}

	
}
