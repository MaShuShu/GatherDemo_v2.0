package com.modules.sys.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.modules.base.orm.FileInfo;
import com.modules.base.orm.Result;
import com.modules.base.svc.FileInfoSVC;
import com.modules.util.orm.Plupload;
import com.modules.util.svc.PluploadUtil;

@Controller
@RequestMapping("/file")
public class FileCTRL {

	@Autowired
	private FileInfoSVC fileSVC;
	
	public static final String FileDir = "uploadfile\\";
	
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
		//File dir = new File(filePath + FileDir+"/"+path);
		
		System.out.println("�ļ��洢·��========"+dir.getPath()); 
        //���磺D:\workplace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\GatherDemo_v2  ��uploadfile\20160425
		FileInfo fileInfo = new FileInfo();
		fileInfo.setPath(FileDir + path);  //�ļ��ϴ�·��
		fileInfo.setName(plupload.getName());  //�ļ�����
		fileInfo.setExtName(plupload.getName());  //�ļ���չ��
		fileInfo.setStatus("0");  //ʹ����
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
        fileSVC.save(fileInfo);
        return Result.data(fileInfo);
	}

	/**
	 * ��ȡͼƬ
	 * @param icon
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/icon/{icon}")
	public void getIcon(@PathVariable("icon") String icon,HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(StringUtils.isEmpty(icon)){
			icon = "";
		}
		
		FileInfo fileInfo = fileSVC.queryById(icon);
		String filePath = request.getSession().getServletContext().getRealPath("/")+fileInfo.getPath()+"\\"+fileInfo.getName();
		
		File file = new File(filePath);
		
		//�ж��ļ��Ƿ���ڣ���������ڣ��ͷ���Ĭ��ͼ��
		if(!file.canRead()){
			file = new File(request.getSession().getServletContext().getRealPath("/") + FileDir+"icon.png");
		}
		response.setContentType("image/*");
		FileInputStream inputStream = null;
		OutputStream stream = response.getOutputStream();
		try{
			inputStream = new FileInputStream(file);
	        byte[] data = new byte[(int)file.length()];
	        int length = inputStream.read(data);
	        if(length!=-1){
	        	stream.write(data, 0, length);
	        	stream.flush();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			inputStream.close();
	        stream.close();
		}
	}
	
	/**
	 * ɾ������
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deltFile/{id}",method = RequestMethod.POST)
	public void delt(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response){
		if(id != null || !"".equals(id)){
			FileInfo fileInfo = fileSVC.queryById(id);
			String filePath = request.getSession().getServletContext().getRealPath("/")+fileInfo.getPath()+"\\"+fileInfo.getName();
			
			File file = new File(filePath);
			if(file.isFile() && file.exists()){
				fileSVC.delete(id);
				file.delete();
			}
		}
	}
	
	
}
