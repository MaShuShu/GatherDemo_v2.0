package com.modules.base.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modules.base.dao.FileInfoDao;
import com.modules.base.orm.FileInfo;
import com.modules.base.orm.Result;

@Service("fileInfoSVC")
public class FileInfoSVC {

	@Autowired
	private FileInfoDao dao;
	
	/**
	 * ����
	 * @param files
	 * @return
	 */
	public Result save(FileInfo file){
		try{
			if(file != null){
				dao.insertFile(file);
				//dao.insertFile(file);
			}
			return Result.ok();
		}catch(Exception e){
			e.printStackTrace();
			return Result.error("�ϴ�����");
		}
	}
	
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	public Result delete(String id){
		if(id != null){
			dao.deleteByPrimaryKey(id);
			return Result.ok();
		}
		return Result.error("ɾ��ʧ�ܣ�");
	}
	
	/**
	 * ��ѯ
	 * @param id
	 * @return
	 */
	public FileInfo queryById(String id){
		if(id != null){
			return dao.selectByPrimaryKey(id);
		}
		return null;
	}
	
	
}
