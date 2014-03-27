package SZSE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.beanutils.BeanUtils;

public class ExcelUtil {

	private String[] fTags = null;  //��ǩ�����Ϣ

	private String[] fNames = null; //����������Ϣ

	private Hashtable allTags= null ; //��ǩ����

	public ExcelUtil(){

	}

	public ExcelUtil(String[] fTags,String[] fNames,Hashtable allTags){
		this.fTags = fTags;
		this.fNames = fNames;
		this.allTags = allTags;
	}

	/**
	 * ���Excel�ļ�
	 * @param path
	 * @param dataList
	 * @return
	 */
	public String createExcel(String path,List dataList){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
       // deleteAllFile(file);
		file = new File(file,System.currentTimeMillis()+".xls");
		WritableWorkbook book=null;
		try {
			book = Workbook.createWorkbook(file);
			WritableSheet sheet = book.createSheet(file.getName(), 0);
			this.setHeader(sheet); //����Excel������Ϣ
            this.setBody(sheet,dataList); // ����Excel����������Ϣ
			book.write();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				book.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file.getAbsolutePath();
	}

	/**
	 * ���file�µ��������ļ�
	 * @param file
	 */
	public void deleteAllFile(File file){
		try{
			File[] files = file.listFiles();
			for(int i=0;i<files.length;i++){
				files[i].delete();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * ����Excel������Ϣ
	 * @param sheet
	 * @throws WriteException
	 */
	public void setHeader(WritableSheet sheet) throws WriteException{
		String[] header = new String[fTags.length];
		for(int i=0;i<fTags.length;i++){
			String fTagsName=(String)allTags.get("F_"+fTags[i].toUpperCase());
			header[i] = fTagsName!=null ? fTagsName : fTags[i];
		}
		this.setHeader(sheet, header);
	}

	/**
	 * ����Excel������Ϣ
	 * @param sheet
	 * @param column
	 * @throws WriteException
	 */
	public void setHeader(WritableSheet sheet,String[] column) throws WriteException{
		WritableCellFormat headerFormat = new  WritableCellFormat();
        headerFormat.setAlignment(Alignment.CENTRE);  //ˮƽ���ж���
        headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);   //��ֱ������ж���
		headerFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
		for(int i=0;i<column.length;i++){
			Label label = new Label(i,0,column[i],headerFormat);
			sheet.addCell(label);
			sheet.setColumnView(i, 20);
			sheet.setRowView(0, 500);
		}
	}



    /**
     * ����Excel����������Ϣ
     * @param sheet
     * @param rowList
     * @throws Exception
     */
	public void setBody(WritableSheet sheet,List rowList) throws Exception{
	    this.setBody(sheet, rowList, fNames);
	}

	 /**
     * ����Excel����������Ϣ
     * @param sheet
     * @param rowList
     * @param column
     * @throws Exception
     */
	public void setBody(WritableSheet sheet,List rowList,String[] column) throws Exception{
	   WritableCellFormat bodyFormat = new  WritableCellFormat();
	   bodyFormat.setAlignment(Alignment.CENTRE); //ˮƽ���ж���
	   bodyFormat.setVerticalAlignment(VerticalAlignment.CENTRE);   //��ֱ������ж���
	   bodyFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
	   Object obj =null;
	   Label label = null;
	   for(int i=0;i<rowList.size();i++){
		   obj = rowList.get(i);
		   for(int j=0;j<column.length;j++){
			   if(obj instanceof Map){
				   label = new Label(j,i+1,String.valueOf(((Map)obj).get(column[j].toLowerCase())),bodyFormat);
			   }else{
				   label = new Label(j,i+1,BeanUtils.getProperty(obj, column[j]),bodyFormat);
			   }
			   sheet.addCell(label);
			   sheet.setRowView(i+1, 350);
		   }
	   }
	}


	/**
	 * �ļ�����
	 * @param response
	 * @param filePath �ļ�·��
	 * @param fileName �ļ����
	 */
	public void download(HttpServletResponse response, String filePath, String fileName)throws IOException {
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(filePath);
			os = response.getOutputStream();// ȡ�������
			response.reset();// ��������
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);// �趨����ļ�ͷ
			response.setContentType("application/x-download");
			byte[] mybyte = new byte[8192];
			int len = 0;
			while ((len = fis.read(mybyte)) != -1) {
				os.write(mybyte, 0, len);
			}
			
			os.close();
		}catch (IOException e) {
            throw e;
		}
	}
	public static void main(String[] args){
		ExcelUtil tmp = new ExcelUtil();
		HttpServletResponse res = new HttpServletResponse();
	}
	

}


