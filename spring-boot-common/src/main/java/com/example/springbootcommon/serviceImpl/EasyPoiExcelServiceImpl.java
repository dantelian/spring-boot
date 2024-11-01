package com.example.springbootcommon.serviceImpl;

import com.example.springbootcommon.common.util.EasyPoiUtil;
import com.example.springbootcommon.model.easyPoi.User;
import com.example.springbootcommon.service.EasyPoiExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EasyPoiExcelServiceImpl implements EasyPoiExcelService {

    @Override
    public void exportExcelClass(HttpServletResponse response) throws IOException {
        User user1 = new User(null, "1", "account1", "大娃", 16, "1", "add1", new Date(), this.getClass().getClassLoader().getResource("static/snow1.jpg").getPath());
        User user2 = new User(null, "2", "account2", "二娃", 15, "2", "add2", new Date(), "");
        List<User> userList = new ArrayList() {{
            add(user1);
            add(user2);
        }};

        EasyPoiUtil.exportExcel(userList, "标题", "sheetName", User.class, "fileName", response);
    }

    @Override
    public List<User> importExcelClass(MultipartFile file) throws IOException {
        List<User> list = EasyPoiUtil.importExcel(file, User.class);
        return list;
    }


}
