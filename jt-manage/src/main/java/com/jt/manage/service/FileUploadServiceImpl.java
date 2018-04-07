package com.jt.manage.service;

import com.jt.common.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    /**
     * 1. 判断是否为图片
     * 2. 判断是否为恶意的软件
     * 3. 实现文件磁盘入库操作
     * 1. 为了检索方便采用分文件存储 yyyy/MM/dd 一般采用三级，递归-内存
     * 2. 为了防止图片重名
     * 1. 名称+ 随机参数 + hash
     * 2. UUID的方式
     * 3. 随机数 + 毫秒数 + hash
     * 4. 准备图片的虚拟路径，为了让用户访问图片
     */
    @Override
    public PicUploadResult uploadFile(MultipartFile uploadFile) {
        PicUploadResult result = new PicUploadResult();
        // 1. 获取图片的名称 abc.jpg
        String filename = uploadFile.getOriginalFilename();
        // 2. 判断是否为一个图片
        if (!filename.matches("^.*(jpg|png|gif)$")) {
            result.setError(1);
            return result;
        }
        // 3. 判断是否恶意程序
        try {
            // 使用图片流来读取文件
            BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
            // 取出图片的宽和高用来判断是否是图片
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if (height * width == 0) { // 有一个等于0就证明不是图片
                result.setError(1);
                return result;
            }
            // 4. 确定是图片后定义本地磁盘的路径
            String localDir = "E:/jt-image/";
            // 5. 准备日志格式的目录
            String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            // 6. 创建文件夹
            String localPath = localDir + dateDir;
            File picFile = new File(localPath);
            // 7. 判定文件夹是否存在
            if (!picFile.exists())
                picFile.mkdirs(); // 不存则创建文件夹
            // 8. 准备UUID数据
            String uuid = UUID.randomUUID().toString().replace("-", "");
            // 9. 获取文件后缀 .jpg
            String fileType = filename.substring(filename.lastIndexOf("."));
            // 10. 将文件写入磁盘
            String realPath = localPath + "/" + uuid + fileType;
            uploadFile.transferTo(new File(realPath));
            // 11. 准备虚拟路径
            String urlPre = "http://image.jt.com/";
            String url = urlPre + dateDir + "/" + uuid + fileType;
            // 12. 准备返回值数据
            result.setUrl(url);
            result.setHeight(height + "");
            result.setWidth(width + "");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            return result;
        }
    }
}
