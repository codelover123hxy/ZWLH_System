package team.CowsAndHorses.util;

import com.luciad.imageio.webp.WebPWriteParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageCompressUtil {
    public static void jpg2webp(String oldFilePath, String newFilePath) {
        try {
            // 获取原始文件的编码
            BufferedImage image = ImageIO.read(new File(oldFilePath));
            // 创建WebP ImageWriter实例
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            // 配置编码参数
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            // 设置压缩模式
            writeParam.setCompressionMode(WebPWriteParam.LOSSY_COMPRESSION);
            // 配置ImageWriter输出
            writer.setOutput(new FileImageOutputStream(new File(newFilePath)));
            // 进行编码，重新生成新图片
            writer.write(null, new IIOImage(image, null, null), writeParam);
            System.out.println("jpg文件转成webp格式成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void uploadAsWebp(MultipartFile file, String newFilePath) {
        try {
            // 获取原始文件的编码
            BufferedImage image = ImageIO.read(file.getInputStream());
            // 创建WebP ImageWriter实例
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            // 配置编码参数
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            // 设置压缩模式
            writeParam.setCompressionMode(WebPWriteParam.LOSSY_COMPRESSION);
            // 配置ImageWriter输出
            writer.setOutput(new FileImageOutputStream(new File(newFilePath)));
            // 进行编码，重新生成新图片
            writer.write(null, new IIOImage(image, null, null), writeParam);
            System.out.println("上传成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
