package com.example.springbootcommon.serviceImpl;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.MyXWPFDocument;
import com.example.springbootcommon.model.easyPoi.User;
import com.example.springbootcommon.service.EasyPoiWordService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Service
public class EasyPoiWordServiceImpl implements EasyPoiWordService {

    @Override
    public void buildEasyPoiTemplateWorld(HttpServletResponse response) {
        // 准备数据
        Map<String, Object> map = new HashMap<>();
        map.put("projectName", "A计划");
        map.put("content", "开始执行");
        map.put("tableName", "用户");
        User user1 = new User(null, "1", "account1", "大娃", 16, "1", "add1", new Date(), null);
        User user2 = new User(null, "2", "account1", "二娃", 15, "2", "add2", new Date(), null);
        List<User> userList = new ArrayList() {{
            add(user1);
            add(user2);
        }};
        map.put("userList", userList);
        // 图片
        ImageEntity image = new ImageEntity(this.getClass().getClassLoader().getResource("static/snow1.jpg").getPath(), 500, 500);
        image.setType(ImageEntity.URL);
        map.put("image", image);

        XWPFDocument document = null;
        InputStream inputStream = null;
        try {
            // 获取模板
            inputStream = this.getClass().getClassLoader().getResourceAsStream("templates/easy_poi_temp.docx");
            document = new MyXWPFDocument(inputStream);
            WordExportUtil.exportWord07(document, map);

            // 保存到本地
            /*OutputStream os = null;
            try {
                os = new FileOutputStream("e:/wordWrite.docx");
                document.write(os);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

            //返回流
            String filename = "wordWrite.docx";
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/force-download");
//            response.setHeader("Content-Disposition", "attachment; filename=" + new String("模板.docx".getBytes("utf-8"), "ISO-8859-1"));
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            OutputStream outputStream = response.getOutputStream();
            document.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
