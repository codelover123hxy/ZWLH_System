package team.CowsAndHorses.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.CowsAndHorses.dto.AjaxResult;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.entity.FileEntity;
import team.CowsAndHorses.service.FileService;
import team.CowsAndHorses.util.ParseUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    final FileService fileService;

    @Value("${url.imageHostBaseUrl}")
    String imageBaseUrl;
    String rootDir = "C:\\Users\\hxy123\\Desktop\\files\\";
    final String dir = System.getProperty("user.dir");

    @PostMapping("/upload")
    @ResponseBody
    public Object uploadFile(@RequestBody MultipartFile file,
                             @RequestParam Integer courseId,
                             HttpServletRequest request) throws IOException {
        final String relativePath = "/imagehost/file/";
        Integer userId = ParseUtil.parseToken(request, "userId");
        String originalName = file.getOriginalFilename();
        String contentType = file.getContentType();
        System.out.println("type " + contentType);
        file.transferTo(new File(dir + relativePath + originalName));
        String filePath = imageBaseUrl + "/file/" + originalName;
        FileEntity newFile = new FileEntity();
        newFile.setName(originalName);
        newFile.setSize((double) file.getSize());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newFile.setCreateDate(sdf.format(date));
        newFile.setTeacherId(ParseUtil.parseToken(request, "userId"));
        newFile.setCourseId(courseId);
        newFile.setFileUrl(filePath);
        fileService.save(newFile);
        return AjaxResult.SUCCESS();
    }

    @GetMapping("/download")
    @ResponseBody
    public void downloadFile(@RequestParam String filename, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader(
                "Content-disposition",
                "attachment; filename=" + filename);

        BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get
                    (dir + "/imagehost/file/" + new String(filename.getBytes(), StandardCharsets.UTF_8))));
        System.out.println(rootDir + filename);
            // 输出流
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = bis.read(buff)) > 0) {
            bos.write(buff, 0, len);
        }
        bos.flush();
    }

    /**
     * admin 部分
     */

    @RequestMapping("/query/{courseId}")
    public AjaxResult<Object> getFiles(PageQueryDto pageQueryDto, @PathVariable Integer courseId) {
        IPage<FileEntity> files =  fileService.page(
                new Page<>(pageQueryDto.getPageNum(), pageQueryDto.getPageSize()),
                new QueryWrapper<FileEntity>().eq("course_id", courseId)
        );
        return AjaxResult.SUCCESS(files);
    }

    @DeleteMapping("/remove/{id}")
    public AjaxResult<Object> removeFile(@PathVariable Integer id) {
        String fileUrl = fileService.getById(id).getFileUrl();
        File file = new File(fileUrl);
        boolean flag = file.delete();
        fileService.removeById(id);
        return AjaxResult.SUCCESS();
    }
}
